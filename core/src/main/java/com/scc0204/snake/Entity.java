package com.scc0204.snake;

/**
 * Abstract base class for all grid-based objects in the game.
 * It enforces the use of X and Y coordinates to fit the grid movement system.
 */
public abstract class Entity {
    protected int x;
    protected int y;

    /**
     * Initializes the entity at the specified grid position.
     *
     * @param x The initial X grid coordinate.
     * @param y The initial Y grid coordinate.
     */
    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Updates the entity's position on the grid.
     *
     * @param x The new X coordinate.
     * @param y The new Y coordinate.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Updates the entity's logic. This method must be implemented by subclasses
     * to define specific behaviors (e.g., movement, growth, or logic).
     *
     * @param deltaTime The time elapsed since the last frame (in seconds).
     */
    public abstract void update(float deltaTime);
}
