package com.scc0204.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class MainMenuScreen implements Screen {
    // Reference to the main game class to allow screen switching
    private final SnakeGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont fontTitle;
    private BitmapFont fontPrompt;

    public MainMenuScreen(SnakeGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        // Use the same virtual resolution as the GameScreen for consistency
        viewport = new FitViewport(GameScreen.V_WIDTH, GameScreen.V_HEIGHT, camera);

        // Generate the arcade font for the title
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Kenney Pixel.ttf"));

        FreeTypeFontParameter paramTitle = new FreeTypeFontParameter();
        paramTitle.size = 80;
        paramTitle.color = Color.GREEN;
        fontTitle = generator.generateFont(paramTitle);

        // Generate a smaller font for the "Press Space" prompt
        FreeTypeFontParameter paramPrompt = new FreeTypeFontParameter();
        paramPrompt.size = 40;
        paramPrompt.color = Color.WHITE;
        fontPrompt = generator.generateFont(paramPrompt);

        generator.dispose();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        // Draw Title
        String title = "MULTIPLAYER SNAKE";
        GlyphLayout titleLayout = new GlyphLayout(fontTitle, title);
        float titleX = (GameScreen.V_WIDTH - titleLayout.width) / 2f;
        fontTitle.draw(game.batch, title, titleX, GameScreen.V_HEIGHT / 1.5f);

        // Draw Menu Options
        fontPrompt.setColor(Color.WHITE);
        String opt1 = "[1] New Game";
        String opt2 = "[2] High Scores";
        String opt3 = "[3] Exit";

        // Aligning everything to the center
        fontPrompt.draw(game.batch, opt1, (GameScreen.V_WIDTH - new GlyphLayout(fontPrompt, opt1).width) / 2f,
                GameScreen.V_HEIGHT / 2.5f);
        fontPrompt.draw(game.batch, opt2, (GameScreen.V_WIDTH - new GlyphLayout(fontPrompt, opt2).width) / 2f,
                GameScreen.V_HEIGHT / 2.5f - 60);
        fontPrompt.draw(game.batch, opt3, (GameScreen.V_WIDTH - new GlyphLayout(fontPrompt, opt3).width) / 2f,
                GameScreen.V_HEIGHT / 2.5f - 120);

        game.batch.end();

        // --- INPUT HANDLING ---
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
            game.setScreen(new GameScreen(game));
            dispose();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2)) {
            // Placeholder: We will create the HighScoreScreen next
            System.out.println("Switching to High Scores...");
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_3)) {
            Gdx.app.exit(); // Closes the application gracefully
        }
    }

    @Override
    public void resize(int width, int height) {
        if (viewport != null) {
            viewport.update(width, height, true);
        }
    }

    @Override
    public void show() {
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
        fontTitle.dispose();
        fontPrompt.dispose();
    }
}
