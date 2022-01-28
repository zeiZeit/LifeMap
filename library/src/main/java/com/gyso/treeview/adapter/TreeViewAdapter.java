package com.gyso.treeview.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.gyso.treeview.line.BaseLine;
import com.gyso.treeview.listener.TreeViewNotifier;
import com.gyso.treeview.model.NodeModel;
import com.gyso.treeview.model.TreeModel;

/**
 * 适配器
 */
public abstract class TreeViewAdapter<T> {
    private TreeViewNotifier notifier;
    private TreeModel<T> treeModel;

    public void setTreeModel(TreeModel<T> treeModel) {
        this.treeModel = treeModel;
        notifyDataSetChange();
    }

    /**
     * @return tree model
     */
    public TreeModel<T> getTreeModel(){
        return treeModel;
    }

    /**
     * 创建自定义的view
     * @param viewGroup parent
     * @param model node
     * @return holder
     */
    public abstract TreeViewHolder<T> onCreateViewHolder(@NonNull ViewGroup viewGroup, NodeModel<T> model);

    /**
     * 数据绑定视图
     * @param holder holder
     */
    public abstract void onBindViewHolder(@NonNull TreeViewHolder<T> holder);

    /**
     * 画节点之间的连接线
     * 如果返回为空就是默认的线
     * @param drawInfo 提供画线的所有信息
     * @return 如果想在不同的节点间用不同的线，那么返回你自己的line
     */
    public abstract BaseLine onDrawLine(DrawInfo drawInfo);

    /**
     * 回收容器，准确的说是回收view
     * @param node
     * @return
     */
    public int getHolderType(NodeModel<?> node){
        return 0;
    }

    public void setNotifier(TreeViewNotifier notifier){
        this.notifier = notifier;
    }

    public void notifyDataSetChange(){
        if(notifier!=null){
            notifier.onDataSetChange();
        }
    }

    public void notifyItemViewChange(NodeModel<T> node){
        if(notifier!=null){
            notifier.onItemViewChange(node);
        }
    }
}
