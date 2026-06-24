package com.scc0204.snake;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SnakeGame extends Game {
    // Public batch so screens can share the same rendering tool
    public SpriteBatch batch;
    public GameSettings settings;

    @Override
    public void create() {
        batch = new SpriteBatch();
        settings = new GameSettings();
        // Start the application by showing the Main Menu
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        // Important: delegates the render call to the currently active screen
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        // The active screen is automatically disposed by the Game class
    }
}
