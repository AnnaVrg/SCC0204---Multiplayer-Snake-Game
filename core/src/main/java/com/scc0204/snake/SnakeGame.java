package com.scc0204.snake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The main entry point for the Snake game.
 * Extends the LibGDX Game class to manage screen transitions,
 * shared rendering resources, and global game settings.
 */
public class SnakeGame extends Game {
    /** Global SpriteBatch shared across all screens for optimized rendering. */
    public SpriteBatch batch;

    /** Global settings instance for configuration management. */
    public GameSettings settings;

    /**
     * Initializes core game resources and sets the initial screen to the Main Menu.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        settings = new GameSettings();

        // Transition to the main menu upon startup
        this.setScreen(new MainMenuScreen(this));
    }

    /**
     * {@inheritDoc}
     * Delegates rendering to the currently active screen.
     */
    @Override
    public void render() {
        super.render();
    }

    /**
     * Disposes of global resources to prevent memory leaks.
     */
    @Override
    public void dispose() {
        batch.dispose();
        // The active screen is disposed of automatically by the Game class
    }
}
