package com.scc0204.snake;

import java.util.Random;

/**
 * Represents the food (apple) in the game.
 * Inherits coordinates from Entity and handles random respawning,
 * specialized apple types, and real-time state degeneration via timers.
 */
public class Food extends Entity {

    /**
     * Defines the available types of apples in the game.
     */
    public enum AppleType {
        NORMAL,
        GOLDEN,
        ROTTEN
    }

    private Random random;
    private int gridWidth;
    private int gridHeight;

    // State attributes for the specific food instance
    private AppleType type;
    private int points;
    private int sizeChange;
    private float timer;
    private boolean hasTimer;

    /**
     * Initializes the food object inside the grid boundaries.
     * Starts as a NORMAL apple by default.
     *
     * @param gridWidth  The width of the playable game grid.
     * @param gridHeight The height of the playable game grid.
     */
    public Food(int gridWidth, int gridHeight) {
        super(0, 0); // Initial dummy position overridden by respawn
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.random = new Random();
        respawn(); // Automatically spawns at a random location
    }

    /**
     * Teleports the food to a random grid coordinate and resets it to a NORMAL
     * apple.
     */
    public void respawn() {
        this.x = random.nextInt(gridWidth);
        this.y = random.nextInt(gridHeight);
        setAppleType(AppleType.NORMAL);
    }

    /**
     * Teleports the food to a random grid coordinate and forces a specific apple
     * type.
     * Useful when the game engine decides to spawn a rare apple.
     *
     * @param newType The specific AppleType to spawn.
     */
    public void respawnAs(AppleType newType) {
        this.x = random.nextInt(gridWidth);
        this.y = random.nextInt(gridHeight);
        setAppleType(newType);
    }

    /**
     * Configures the apple's traits, scoring rules, and timers
     * based on the assigned AppleType.
     * * @param newType The AppleType to apply to this instance.
     */
    private void setAppleType(AppleType newType) {
        this.type = newType;

        switch (newType) {
            case GOLDEN:
                this.points = 10;
                this.sizeChange = 2;
                this.timer = 5.0f; // Lasts 5 seconds before spoiling
                this.hasTimer = true;
                break;

            case ROTTEN:
                this.points = -5;
                this.sizeChange = -1; // Snake loses 1 segment
                this.timer = 10.0f; // Lasts 10 seconds before decomposing
                this.hasTimer = true;
                break;

            case NORMAL:
            default:
                this.points = 1;
                this.sizeChange = 1;
                this.timer = 0.0f;
                this.hasTimer = false;
                break;
        }
    }

    /**
     * Updates the countdown timer every frame.
     * If a special apple's time runs out, it smoothly turns into a NORMAL apple.
     *
     * @param deltaTime The elapsed time since the last frame in seconds.
     */
    @Override
    public void update(float deltaTime) {
        if (hasTimer) {
            this.timer -= deltaTime;

            // Once the timer hits 0, degenerate into a normal apple
            if (this.timer <= 0) {
                setAppleType(AppleType.NORMAL);
            }
        }
    }

    // --- Getters for Game Engine Interactions ---

    /**
     * @return The current AppleType enum state.
     */
    public AppleType getType() {
        return type;
    }

    /**
     * @return The point value given (or taken) when consumed.
     */
    public int getPoints() {
        return points;
    }

    /**
     * @return The number of segments the snake should grow or shrink by.
     */
    public int getSizeChange() {
        return sizeChange;
    }
}
