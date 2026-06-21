package com.scc0204.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOverScreen implements Screen {
    private final SnakeGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont fontLarge;
    private BitmapFont fontMedium;
    private int scoreP1, scoreP2;

    private boolean enteringP1Name = false;
    private boolean enteringP2Name = false;
    private String currentName = "";

    public GameOverScreen(SnakeGame game, int scoreP1, int scoreP2) {
        this.game = game;
        this.scoreP1 = scoreP1;
        this.scoreP2 = scoreP2;

        camera = new OrthographicCamera();
        viewport = new FitViewport(GameScreen.V_WIDTH, GameScreen.V_HEIGHT, camera);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Kenney Pixel.ttf"));

        FreeTypeFontParameter paramLarge = new FreeTypeFontParameter();
        paramLarge.size = 80;
        paramLarge.color = Color.WHITE;
        fontLarge = generator.generateFont(paramLarge);

        FreeTypeFontParameter paramMedium = new FreeTypeFontParameter();
        paramMedium.size = 40;
        paramMedium.color = Color.WHITE;
        fontMedium = generator.generateFont(paramMedium);

        generator.dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        float centerX = GameScreen.V_WIDTH / 2f;
        float centerY = GameScreen.V_HEIGHT / 2f;

        // Draw GAME OVER
        String title = "GAME OVER";
        GlyphLayout titleLayout = new GlyphLayout(fontLarge, title);
        fontLarge.draw(game.batch, title, centerX - (titleLayout.width / 2f), centerY + 150);

        // Draw Winner & Scores
        String winnerText = (scoreP1 > scoreP2) ? "PLAYER 1 WINS!"
                : (scoreP2 > scoreP1) ? "PLAYER 2 WINS!" : "IT'S A TIE!";
        fontMedium.setColor(Color.WHITE);
        GlyphLayout winnerLayout = new GlyphLayout(fontMedium, winnerText);
        fontMedium.draw(game.batch, winnerText, centerX - (winnerLayout.width / 2f), centerY + 70);

        String scoreText = "P1: " + scoreP1 + "   -   P2: " + scoreP2;
        GlyphLayout scoreLayout = new GlyphLayout(fontMedium, scoreText);
        fontMedium.draw(game.batch, scoreText, centerX - (scoreLayout.width / 2f), centerY);

        // --- DYNAMIC UI STATE ---
        if (enteringP1Name || enteringP2Name) {
            // Draw Typing Prompt
            fontMedium.setColor(Color.YELLOW);
            String promptText = enteringP1Name ? "NEW RECORD P1! ENTER NAME:" : "NEW RECORD P2! ENTER NAME:";
            GlyphLayout promptLayout = new GlyphLayout(fontMedium, promptText);
            fontMedium.draw(game.batch, promptText, centerX - (promptLayout.width / 2f), centerY - 80);

            // Draw the typed name with a classic blinking cursor effect
            String displayName = currentName + (System.currentTimeMillis() % 1000 < 500 ? "_" : "");
            GlyphLayout nameLayout = new GlyphLayout(fontMedium, displayName);
            fontMedium.draw(game.batch, displayName, centerX - (nameLayout.width / 2f), centerY - 130);

        } else {
            // Draw Menu Options only when not typing
            fontMedium.setColor(Color.WHITE);
            String opt1 = "[1] Play Again";
            String opt2 = "[2] Main Menu";
            fontMedium.draw(game.batch, opt1, centerX - (new GlyphLayout(fontMedium, opt1).width / 2f), centerY - 100);
            fontMedium.draw(game.batch, opt2, centerX - (new GlyphLayout(fontMedium, opt2).width / 2f), centerY - 150);

            // Input handling for menu
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
                game.setScreen(new GameScreen(game));
                dispose();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)
                    || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2)) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        }

        game.batch.end();
    }

    @Override
    public void show() {
        // Check who deserves a high score right when the screen loads
        boolean p1Worthy = HighScoreManager.isHighScore(scoreP1);
        boolean p2Worthy = HighScoreManager.isHighScore(scoreP2);

        if (p1Worthy) {
            enteringP1Name = true;
            setupInputProcessor(p2Worthy);
        } else if (p2Worthy) {
            enteringP2Name = true;
            setupInputProcessor(false);
        }
    }

    /**
     * Sets up a LibGDX InputAdapter to capture raw keystrokes directly on the
     * screen.
     */
    private void setupInputProcessor(final boolean checkP2Next) {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyTyped(char character) {
                // Handle Backspace
                if (character == '\b' && currentName.length() > 0) {
                    currentName = currentName.substring(0, currentName.length() - 1);
                }
                // Handle Enter (Submit)
                else if (character == '\r' || character == '\n') {
                    saveCurrentName(checkP2Next);
                }
                // Handle Letters and Digits (Limit to 10 characters so it fits the screen)
                else if (Character.isLetterOrDigit(character) && currentName.length() < 10) {
                    currentName += character;
                }
                return true; // Return true to indicate the input was processed
            }
        });
    }

    private void saveCurrentName(boolean checkP2Next) {
        if (enteringP1Name) {
            // Automatically convert to uppercase for that arcade aesthetic
            HighScoreManager.addScore(scoreP1, currentName.isEmpty() ? "PLAYER 1" : currentName.toUpperCase());
            enteringP1Name = false;
            currentName = ""; // Clear for the next player

            if (checkP2Next) {
                enteringP2Name = true;
            } else {
                Gdx.input.setInputProcessor(null); // Release keyboard back to the game loop
            }
        } else if (enteringP2Name) {
            HighScoreManager.addScore(scoreP2, currentName.isEmpty() ? "PLAYER 2" : currentName.toUpperCase());
            enteringP2Name = false;
            Gdx.input.setInputProcessor(null); // Release keyboard
        }
    }

    @Override
    public void resize(int width, int height) {
        if (viewport != null)
            viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        fontLarge.dispose();
        fontMedium.dispose();
        // Ensure we don't leave the keyboard locked if the screen is destroyed early
        Gdx.input.setInputProcessor(null);
    }
}
