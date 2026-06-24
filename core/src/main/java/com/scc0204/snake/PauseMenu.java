package com.scc0204.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * Handles the Pause Menu Overlay.
 * Encapsulates the fonts, UI drawing, and pause state logic.
 */
public class PauseMenu {
    private BitmapFont fontLarge;
    private BitmapFont fontMedium;
    private boolean isPaused = false;

    private GlyphLayout titleLayout;
    private GlyphLayout opt1Layout;
    private GlyphLayout opt2Layout;

    private final String opt1Text = "[1] Resume";
    private final String opt2Text = "[2] Main Menu";

    /**
     * Initializes the Pause Menu, loading fonts and pre-calculating layouts.
     */
    public PauseMenu() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Kenney Pixel.ttf"));

        // Configure Title Font
        FreeTypeFontParameter paramLarge = new FreeTypeFontParameter();
        paramLarge.size = 80;
        paramLarge.color = Color.YELLOW;
        fontLarge = generator.generateFont(paramLarge);

        // Configure Option Font
        FreeTypeFontParameter paramMedium = new FreeTypeFontParameter();
        paramMedium.size = 40;
        paramMedium.color = Color.WHITE;
        fontMedium = generator.generateFont(paramMedium);

        generator.dispose();

        // Initialize layouts for optimized rendering
        titleLayout = new GlyphLayout(fontLarge, "PAUSED");
        opt1Layout = new GlyphLayout(fontMedium, opt1Text);
        opt2Layout = new GlyphLayout(fontMedium, opt2Text);
    }

    /**
     * @return True if the game is currently paused.
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * @return True if the game is currently paused.
     */
    public void togglePause() {
        this.isPaused = !this.isPaused;
    }

    /**
     * Renders the pause menu overlay and processes user menu navigation.
     *
     * @param batch        The SpriteBatch used for drawing.
     * @param screenWidth  The current viewport width.
     * @param screenHeight The current viewport height.
     * @return True if the user selected "Main Menu" and wants to exit the game.
     */
    public boolean updateAndDraw(SpriteBatch batch, float screenWidth, float screenHeight) {
        if (!isPaused)
            return false;

        float centerX = screenWidth / 2f;
        float centerY = screenHeight / 2f;

        // Render UI elements
        fontLarge.draw(batch, "PAUSED", centerX - (titleLayout.width / 2f), centerY + 100);
        fontMedium.draw(batch, opt1Text, centerX - (opt1Layout.width / 2f), centerY);
        fontMedium.draw(batch, opt2Text, centerX - (opt2Layout.width / 2f), centerY - 60);

        // Handle navigation inputs
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
            this.isPaused = false;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2)) {
            return true; // Signal to the GameScreen that we should quit
        }

        return false;
    }

    /**
     * Disposes of font assets to prevent memory leaks.
     */
    public void dispose() {
        fontLarge.dispose();
        fontMedium.dispose();
    }
}
