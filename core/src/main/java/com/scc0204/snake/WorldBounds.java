package com.scc0204.snake;

/**
 * Manages the boundaries of the game world.
 * Handles coordinate wrapping logic to allow the snake to pass through walls
 * and emerge on the opposite side of the grid.
 */
public class WorldBounds {

    private int width;
    private int height;

    /**
     * Initializes the world boundaries with the specified grid dimensions.
     *
     * @param width  The total width of the game grid.
     * @param height The total height of the game grid.
     */
    public WorldBounds(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Wraps the X coordinate if it exceeds grid boundaries.
     *
     * @param x The X coordinate to check.
     * @return The wrapped X coordinate within [0, width - 1].
     */
    public int wrapX(int x) {
        if (x < 0)
            return width - 1;
        if (x >= width)
            return 0;
        return x;
    }

    /**
     * Wraps the Y coordinate if it exceeds grid boundaries.
     *
     * @param y The Y coordinate to check.
     * @return The wrapped Y coordinate within [0, height - 1].
     */
    public int wrapY(int y) {
        if (y < 0)
            return height - 1;
        if (y >= height)
            return 0;
        return y;
    }
}
