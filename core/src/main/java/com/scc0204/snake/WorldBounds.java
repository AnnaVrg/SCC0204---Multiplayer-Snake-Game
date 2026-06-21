package com.scc0204.snake;

public class WorldBounds {

    private int width;
    private int height;

    public WorldBounds(int width, int height) {
        this.width = width;
        this.height = height;
    }


    public int wrapX(int x) {

        if (x < 0)
            return width - 1;

        if (x >= width)
            return 0;

        return x;
    }


    public int wrapY(int y) {

        if (y < 0)
            return height - 1;

        if (y >= height)
            return 0;

        return y;
    }
}