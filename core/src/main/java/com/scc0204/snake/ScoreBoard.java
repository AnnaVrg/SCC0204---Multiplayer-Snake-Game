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
    }

    /**
     * Draws the current scores and the end-game state if applicable.
     */
    public void draw(SpriteBatch batch, int scoreP1, int scoreP2, boolean gameOver) {
        float screenWidth = GameScreen.V_WIDTH;
        float screenHeight = GameScreen.V_HEIGHT;

        // --- PLAYER 1 SCORE (Left Aligned) ---
        font.setColor(Color.WHITE);
        String p1Text = "P1 Score: " + scoreP1;
        // While x=20 is static, using the layout keeps the code standardized
        font.draw(batch, p1Text, 20, screenHeight - 20);

        // --- PLAYER 2 SCORE (Right Aligned) ---
        font.setColor(Color.WHITE);
        String p2Text = "P2 Score: " + scoreP2;
        GlyphLayout p2Layout = new GlyphLayout(font, p2Text);
        // Dynamically calculate X to perfectly fit the text with a 20px margin
        float p2X = screenWidth - p2Layout.width - 20;
        font.draw(batch, p2Text, p2X, screenHeight - 20);

        // --- GAME OVER OVERLAY (Perfectly Centered) ---
        if (gameOver) {
            float centerX = screenWidth / 2f;
            float centerY = screenHeight / 2f;

            // MAIN TITLE: GAME OVER
            font.setColor(Color.WHITE);
            font.getData().setScale(2.5f);
            String overText = "GAME OVER";
            GlyphLayout overLayout = new GlyphLayout(font, overText);
            // Perfectly center the text by subtracting half of its exact mathematical width
            font.draw(batch, overText, centerX - (overLayout.width / 2f), centerY + 50);

            // SUBTITLE: WINNER ANNOUNCEMENT
            font.setColor(Color.WHITE);
            font.getData().setScale(1.5f);

            String winnerText;
            if (scoreP1 > scoreP2)
                winnerText = "PLAYER 1 WINS!";
            else if (scoreP2 > scoreP1)
                winnerText = "PLAYER 2 WINS!";
            else
                winnerText = "IT'S A TIE!";

            GlyphLayout winnerLayout = new GlyphLayout(font, winnerText);
            // Center the subtitle exactly below the main title
            font.draw(batch, winnerText, centerX - (winnerLayout.width / 2f), centerY - 10);

            // Reset the scale back to default so the scores aren't huge on the next frame
            font.getData().setScale(1f);
        }
    }

    /**
     * Frees the memory used by the font.
     */
    public void dispose() {
        font.dispose();
    }
}
