package com.gyso.treeview.cache_pool;

import android.graphics.PointF;

import androidx.core.util.Pools;

/**
 * Point pool.
 * NOTE: 不是安全的池子，只能在主线程中使用
 */
public class PointPool extends Pools.SimplePool<PointF> {
    public static final int DEFAULT_SIZE = 20;
    private final static PointPool POOL = new PointPool();
    /**
     * Creates a new instance.
     */
    private PointPool() {
        super(DEFAULT_SIZE);
    }

    public static PointF obtain(){
        PointF point = POOL.acquire();
        if(point==null){
            return new PointF();
        }
        return point;
    }

    public static PointF obtain(float x, float y){
        PointF point = POOL.acquire();
        if(point==null){
            return new PointF(x,y);
        }
        point.set(x,y);
        return point;
    }

    public static void free(PointF p){
        POOL.release(p);
    }

    public static void freeAll(){
        while (POOL.acquire()!=null);
    }
}
