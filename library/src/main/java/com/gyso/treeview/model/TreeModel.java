package com.gyso.treeview.model;

import android.util.Log;
import android.util.SparseArray;

import com.gyso.treeview.util.TreeViewLog;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 管理节点数据的类
 */
public class TreeModel<T> implements Serializable {
    private static final String TAG = TreeModel.class.getSimpleName();
    /**
     * 根节点
     */
    private NodeModel<T> rootNode;

    /**
     * 二维结构的 节点数组
     */
    private SparseArray<LinkedList<NodeModel<T>>> arrayByFloor = new SparseArray<>(10);
    /**
     * 不能被序列化的遍历回调
     */
    private transient ITraversal<NodeModel<?>> iTraversal;

    public TreeModel(NodeModel<T> rootNode) {
        this.rootNode = rootNode;
    }

    //结束回调通知
    private boolean finishTraversal = false;


    /**
     * 在父节点添加子节点
     * @param parent
     * @param childNodes
     */
    @SafeVarargs
    public final void addNode(NodeModel<?> parent, NodeModel<?>... childNodes) {
        if(parent!=null&&childNodes!=null && childNodes.length>0){
            List<NodeModel<T>> nodeModels = new LinkedList<>();
            for (int i = 0; i < childNodes.length; i++) {
                nodeModels.add((NodeModel<T>)childNodes[i]);
            }
            ((NodeModel<T>)parent).addChildNodes(nodeModels);
            List<NodeModel<T>> floorList = getFloorList(nodeModels.get(0).floor);
            floorList.addAll(nodeModels);
        }
    }

    /**
     * 移除某个节点
     * @param parent p node
     * @param childNode c node
     */
    public void removeNode(NodeModel<?> parent, NodeModel<?> childNode) {
        if(parent!=null&&childNode!=null){
            ((NodeModel<T>)parent).removeChildNode((NodeModel<T>)childNode);
            List<NodeModel<T>> floorList = getFloorList(childNode.floor);
            floorList.remove(childNode);
        }
    }

    public NodeModel<T> getRootNode() {
        return rootNode;
    }

    /**
     * 计算数的所有叶子节点
     */
    public void calculateTreeNodesLeaves(){

    }

    /**
     * 计算节点深度
     */
    public void calculateTreeNodesDeep(){
        TreeViewLog.e(TAG,"calculateTreeNodesDeep start");
        Stack<NodeModel<T>> stack = new Stack<>();
        NodeModel<T> rootNode = getRootNode();
        stack.add(rootNode);
        while (!stack.isEmpty()) {
            NodeModel<T> cur = stack.pop();
            NodeModel<T> parentNode = cur.getParentNode();
            cur.deep = 0;
            //if has peer, peer.deep+peer.leafCount+1/*peer self*/
            //if has no peer, parentNode.deep;
            if(parentNode!=null){
                int indexOfCur = parentNode.childNodes.indexOf(cur);
                if(indexOfCur>0){
                    NodeModel<T> prePeer = parentNode.childNodes.get(indexOfCur - 1);
                    cur.deep += prePeer.deep;
                    cur.deep += (prePeer.leafCount==0?1:prePeer.leafCount);
                }else{
                    cur.deep = parentNode.deep;
                }
            }
            TreeViewLog.e(TAG,"calculateTreeNodesDeep--->"+cur.toString());
            LinkedList<NodeModel<T>> childNodes = cur.getChildNodes();
            for (int i = childNodes.size()-1; i >=0; i--) {
                stack.add(childNodes.get(i));
            }
        }
        TreeViewLog.e(TAG,"calculateTreeNodesDeep end");
    }

    /**
     *子节点将在最后遍历
     */
    private void ergodicTreeByQueue() {
        Deque<NodeModel<T>> queue = new ArrayDeque<>();
        NodeModel<T> rootNode = getRootNode();
        queue.add(rootNode);
        while (!queue.isEmpty()) {
            rootNode = queue.poll();
            if (iTraversal != null) {
                iTraversal.next(rootNode);
            }
            if(this.finishTraversal){
                break;
            }
            if(rootNode==null){
                continue;
            }
            LinkedList<NodeModel<T>> childNodes = rootNode.getChildNodes();
            if (childNodes.size() > 0) {
                queue.addAll(childNodes);
            }
        }
        if (iTraversal != null) {
            iTraversal.finish();
            this.finishTraversal = false;
        }
    }

    /**
     * 获取某一层所有的 节点
     * @param floor  楼层
     * @return all nodes in the same floor
     */
    private List<NodeModel<T>> getFloorList(int floor){
        LinkedList<NodeModel<T>> nodeModels = arrayByFloor.get(floor);
        if(nodeModels==null){
            nodeModels = new LinkedList<>();
            arrayByFloor.put(floor,nodeModels);
        }
        return nodeModels;
    }

    public void setFinishTraversal(boolean finishTraversal) {
        this.finishTraversal = finishTraversal;
    }

    /**
     * when ergodic this tree, it will call back on {@link ITraversal)}
     * @param ITraversal node
     */
    public void doTraversalNodes(ITraversal<NodeModel<?>> ITraversal) {
        this.iTraversal = ITraversal;
        this.finishTraversal = false;
        ergodicTreeByQueue();
    }
}
