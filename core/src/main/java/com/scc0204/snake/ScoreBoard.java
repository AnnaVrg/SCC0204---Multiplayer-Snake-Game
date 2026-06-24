package com.scc0204.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

/**
 * Handles the Heads-Up Display (HUD).
 * Responsible for tracking, updating, and rendering player scores during
 * gameplay.
 */
public class ScoreBoard {
    private BitmapFont font;

    private GlyphLayout p2Layout;
    private int lastScoreP2 = -1;
    private String p2Text = "P2 Score: 0";
    private String p1Text = "P1 Score: 0";

    private int scoreP1 = 0;
    private int scoreP2 = 0;

    /**
     * Initializes the ScoreBoard by loading and generating the arcade font.
     */
    public ScoreBoard() {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Kenney Pixel.ttf"));

        // Configure font parameters
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 32; // Tamanho ideal para placares arcade
        parameter.color = Color.WHITE; // Cor base

        // Generate the font
        font = generator.generateFont(parameter);

        // Free the generator from memory to prevent leaks
        generator.dispose();

        p2Layout = new GlyphLayout();
    }

    /**
     * Updates Player 1 score and ensures it never drops below zero.
     *
     * @param points The points to add or subtract.
     */
    public void addScoreP1(int points) {

        scoreP1 = Math.max(0, scoreP1 + points);
        p1Text = "P1 Score: " + scoreP1;
    }

    /**
     * Updates Player 2 score and ensures it never drops below zero.
     *
     * @param points The points to add or subtract.
     */
    public void addScoreP2(int points) {
        scoreP2 = Math.max(0, scoreP2 + points);
    }

    /** @return The current score for Player 1. */
    public int getScoreP1() {
        return scoreP1;
    }

    /** @return The current score for Player 2. */
    public int getScoreP2() {
        return scoreP2;
    }

    /**
     * Renders the current scores on the screen using the game's SpriteBatch.
     *
     * @param batch The SpriteBatch instance used for drawing UI elements.
     */
    public void draw(SpriteBatch batch) {
        float screenWidth = GameScreen.V_WIDTH;
        float screenHeight = GameScreen.V_HEIGHT;

        // Render Player 1 Score (Left Aligned)
        font.setColor(Color.WHITE);
        font.draw(batch, p1Text, 20, screenHeight - 20);

        // Render Player 2 Score (Right Aligned)
        if (scoreP2 != lastScoreP2) {
            p2Text = "P2 Score: " + scoreP2;
            p2Layout.setText(font, p2Text); // Reaproveita o objeto na memória
            lastScoreP2 = scoreP2;
        }
        // Dynamically calculate X to perfectly fit the text with a 20px margin
        float p2X = screenWidth - p2Layout.width - 20;
        font.draw(batch, p2Text, p2X, screenHeight - 20);

    }

    /**
     * Frees the memory used by the font.
     */
    public void dispose() {
        font.dispose();
    }
}
