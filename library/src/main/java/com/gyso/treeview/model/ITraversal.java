package com.gyso.treeview.model;

import java.io.Serializable;

/**
 * 遍历回调
 */

public interface ITraversal<T> extends Serializable {
    void next(T next);
    default void finish(){}
}
