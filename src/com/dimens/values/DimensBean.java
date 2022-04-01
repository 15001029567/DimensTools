package com.dimens.values;

public class DimensBean {
    private int width;
    private int height;
    private float scale;

    public DimensBean(int width, int height, float scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
