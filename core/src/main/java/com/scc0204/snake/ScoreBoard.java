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
 * Responsible for rendering the scores and the Game Over screen.
 */
public class ScoreBoard {
    private BitmapFont font;

    private GlyphLayout p2Layout;
    private int lastScoreP2 = -1;
    private String p2Text = "P2 Score: 0";

    public ScoreBoard() {

        // Load the .ttf file from the assets folder (ensure the file name matches
        // exactly)
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Kenney Pixel.ttf"));

        // Configure font parameters (like the size in pixels)
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
     * Draws the current scores and the end-game state if applicable.
     */
    public void draw(SpriteBatch batch, int scoreP1, int scoreP2) {
        float screenWidth = GameScreen.V_WIDTH;
        float screenHeight = GameScreen.V_HEIGHT;

        // --- PLAYER 1 SCORE (Left Aligned) ---
        font.setColor(Color.WHITE);
        String p1Text = "P1 Score: " + scoreP1;
        // While x=20 is static, using the layout keeps the code standardized
        font.draw(batch, p1Text, 20, screenHeight - 20);

        // --- PLAYER 2 SCORE (Right Aligned) ---

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
