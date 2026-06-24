package com.scc0204.snake;

/**
 * Manages game configuration settings.
 * This class is designed to be instantiable to ensure testability and
 * avoid global state conflicts during runtime.
 */
public class GameSettings {

    // Grid dimensions and movement timing
    private int tileSize = 32;
    private float startingSpeed = 0.15f;

    /**
     * @return The current tile size in pixels.
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     * @return The initial movement interval in seconds.
     */
    public float getStartingSpeed() {
        return startingSpeed;
    }

    /**
     * Cycles through available grid sizes: 32 (Normal), 16 (Large), 40 (Small).
     */
    public void toggleGridSize() {
        if (tileSize == 32)
            tileSize = 16;
        else if (tileSize == 16)
            tileSize = 40;
        else
            tileSize = 32;
    }

    /**
     * Cycles through available speed presets: 0.15s (Normal), 0.10s (Fast), 0.20s
     * (Slow).
     */
    public void toggleSpeed() {
        if (startingSpeed == 0.15f)
            startingSpeed = 0.10f;
        else if (startingSpeed == 0.10f)
            startingSpeed = 0.20f;
        else
            startingSpeed = 0.15f;
    }

    /**
     * @return A descriptive name for the current grid size setting.
     */
    public String getGridSizeName() {
        if (tileSize == 40)
            return "SMALL";
        if (tileSize == 32)
            return "NORMAL";
        return "LARGE";
    }

    /**
     * @return A descriptive name for the current speed setting.
     */
    public String getSpeedName() {
        if (startingSpeed == 0.20f)
            return "SLOW";
        if (startingSpeed == 0.15f)
            return "NORMAL";
        return "FAST";
    }

    /**
     * Resets settings to their default values.
     * Useful for unit testing and restoring state.
     */
    public void resetToDefaults() {
        tileSize = 32;
        startingSpeed = 0.15f;
    }
}
