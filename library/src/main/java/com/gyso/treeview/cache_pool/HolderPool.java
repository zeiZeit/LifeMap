package com.gyso.treeview.cache_pool;

import androidx.core.util.Pools;

import com.gyso.treeview.adapter.TreeViewHolder;

/**
 *  * Holder pool. {@link com.gyso.treeview.adapter.TreeViewHolder}
 *  * NOTE: 不是安全的池子，只能在主线程中使用
 */
public class HolderPool  extends Pools.SimplePool<TreeViewHolder<?>> {
    public static final int DEFAULT_SIZE = 30;
    private final static HolderPool POOL = new HolderPool();
    /**
     * Creates a new instance.
     */
    public HolderPool() {
        super(DEFAULT_SIZE);
    }

    public TreeViewHolder<?> obtain(){
        return POOL.acquire();
    }

    public void free(TreeViewHolder<?> holder){
        try {
            POOL.release(holder);
        }catch (IllegalStateException e){
            e.printStackTrace();
        }

    }

    public static void freeAll(){
        while (POOL.acquire()!=null);
    }
}
