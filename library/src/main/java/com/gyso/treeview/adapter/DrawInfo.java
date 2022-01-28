package com.gyso.treeview.adapter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * 为了画出 自定义的线 ，这里有太多信息要被忽略，因此，这个类是为了 把那些 能画出有颜色的线的类整合在一起
 */
public class DrawInfo {
    /**
     * 画布
     */
    private Canvas canvas;
    /**
     * 节点来处
     */
    private TreeViewHolder<?> fromHolder;
    /**
     * 节点去处
     */
    private TreeViewHolder<?> toHolder;
    /**
     * 画笔，用之前要重置
     */
    private Paint paint;
    /**
     * 路径，用之前要重置
     */
    private Path path;
    /**
     * 同级节点之间的距离
     */
    private int spacePeerToPeer;
    /**
     * 父子节点之间的距离
     */
    private int spaceParentToChild;
    /**
     * 屏幕宽度
     */
    private int windowWidth;
    /**
     * 屏幕高度
     */
    private int windowHeight;
    /**
     * layout type {@link com.gyso.treeview.layout.TreeLayoutManager#LAYOUT_TYPE_HORIZON_RIGHT #LAYOUT_TYPE_VERTICAL_DOWN}
     */
    private int layoutType;

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public TreeViewHolder<?> getFromHolder() {
        return fromHolder;
    }

    public void setFromHolder(TreeViewHolder<?> fromHolder) {
        this.fromHolder =  fromHolder;
    }

    public TreeViewHolder<?> getToHolder() {
        return toHolder;
    }

    public void setToHolder(TreeViewHolder<?> toHolder) {
        this.toHolder = toHolder;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setSpace(int spacePeerToPeer,int spaceParentToChild) {
        this.spacePeerToPeer = spacePeerToPeer;
        this.spaceParentToChild = spaceParentToChild;
    }

    public int getSpacePeerToPeer() {
        return spacePeerToPeer;
    }

    public int getSpaceParentToChild() {
        return spaceParentToChild;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(int layoutType) {
        this.layoutType = layoutType;
    }
}
