package com.gyso.treeview.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *  Note:
 *  如果继承此类必须重写它的 equals 方法。因为某些类引用了Array的contains()
 */
public class NodeModel<T> implements Serializable {

    /**
     * 父节点
     */
    public NodeModel<T> parentNode;

    /**
     * 数据
     */
    public T value;

    /**
     * 子节点们
     */
    public LinkedList<NodeModel<T>> childNodes;

    /**
     * focus tag for the tree add nodes
     */
    public transient boolean focus = false;

    /**
     * 层级
     */
    public int floor;

    /**
     * 同一层的广度？？
     */
    public int deep = 0;

    /**
     * 叶子节点的数量
     */
    public int leafCount=0;

    /**
     * 是否显示
     */
    public boolean hidden = false;

    public NodeModel(T value) {
        this.value = value;
        this.childNodes = new LinkedList<>();

        this.focus = false;
        this.parentNode = null;
    }

    public NodeModel<T> getParentNode() {
        return parentNode;
    }

    public void setParentNode(NodeModel<T> parentNode) {
        this.parentNode = parentNode;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @NonNull
    public LinkedList<NodeModel<T>> getChildNodes() {
        return childNodes;
    }

    public void addChildNodes(List<NodeModel<T>> childNodes) {
        int allLeafByAddChild = 0;
        boolean isLeafCur = leafCount==0;
        boolean isContainChildBefore;
        for (NodeModel<T> aChild: childNodes) {
            isContainChildBefore = addChildNode(aChild);
            allLeafByAddChild +=
                    aChild.getChildNodes().isEmpty()?
                    (isContainChildBefore?0:1):
                    (isContainChildBefore?aChild.leafCount-1:aChild.leafCount);
        }
        leafCount +=allLeafByAddChild;
        if(isLeafCur){
            allLeafByAddChild--;
        }
        if(allLeafByAddChild>0&&!childNodes.isEmpty()){
            NodeModel<T> parentNode = getParentNode();
            while (parentNode!=null){
                parentNode.leafCount +=allLeafByAddChild;
                parentNode = parentNode.getParentNode();
            }
        }
    }

    /**
     * @param aChild node
     */
    private boolean addChildNode(NodeModel<T> aChild){
        boolean isExist = childNodes.contains(aChild);
        if(!isExist){
            aChild.setParentNode(this);
            childNodes.add(aChild);
        }
        traverse(aChild,node -> {
            if(node.parentNode!=null){
                node.floor = node.parentNode.floor+1;
            }
        });
        return isExist;
    }

    /**
     * traverse
     * @param node to deal node
     */
    private void traverse(NodeModel<T> node, INext<T> next){
        traverse(node,next,true);
    }

    private void traverse(NodeModel<T> node, INext<T> next, boolean isIncludeSelf){
        if(node==null || next==null){
            return;
        }
        Stack<NodeModel<T>> stack = new Stack<>();
        stack.add(node);
        while (!stack.isEmpty()) {
            NodeModel<T> tmp = stack.pop();
            if(isIncludeSelf){
                next.next(tmp);
            }else if(tmp!=this){
                next.next(tmp);
            }
            LinkedList<NodeModel<T>> childNodes = tmp.getChildNodes();
            stack.addAll(childNodes);
        }
    }
    /**
     * traverse all sub node include self
     * @param next callback
     */
    public void traverseIncludeSelf(INext<T> next){
        if(next==null){
            return;
        }
        traverse(this,next,true);
    }

    /**
     * traverse all sub node exclude self
     * @param next callback
     */
    public void traverseExcludeSelf(INext<T> next){
        if(next==null){
            return;
        }
        traverse(this,next,false);
    }

    /**
     * traverse only direct children
     * @param next callback
     */
    public void traverseDirectChildren(INext<T> next){
        if(next==null){
            return;
        }
        for (NodeModel<T> childNode : new LinkedList<>(childNodes)) {
            next.next(childNode);
        }
    }

    public void removeChildNode(NodeModel<T> aChild){
        childNodes.remove(aChild);

        int removeCount = Math.max(1,aChild.leafCount);
        leafCount -= removeCount;

        NodeModel<T> parentNode = getParentNode();

        while (parentNode!=null){
            parentNode.leafCount -=removeCount;
            //if current become a leaf
            if(childNodes.isEmpty()){
                parentNode.leafCount++;
            }
            parentNode = parentNode.getParentNode();
        }
        aChild.setParentNode(null);
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public interface INext<E>{
        void next(NodeModel<E> node);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj instanceof NodeModel){
            NodeModel<T> nodeModel = (NodeModel)obj;
            return value!=null&&value.equals(nodeModel.value);
        }
        return false;
    }

    @Override
    public String toString() {
        return "NodeModel{" +
                "value=" + value +
                ", floor=" + floor +
                ", deep=" + deep +
                ", leafCount=" + leafCount +
                ", parent=" + (parentNode==null?null:parentNode.value) +
                '}';
    }
}
