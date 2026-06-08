package com.scc0204.snake;

/**
 * Abstract base class for all grid-based objects in the game.
 * It enforces the use of X and Y coordinates to fit the grid movement system.
 */
public abstract class Entity {
    protected int x;
    protected int y;

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

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Updates the entity's logic.
     *
     * @param deltaTime The time elapsed since the last frame (in seconds).
     */
    public abstract void update(float deltaTime);
}
