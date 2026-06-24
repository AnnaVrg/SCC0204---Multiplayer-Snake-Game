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

    // Otimização: Variáveis estáticas de layout e texto
    private GlyphLayout titleLayout, opt1Layout, opt2Layout, opt3Layout, opt4Layout, opt5Layout;
    private final String titleText = "MULTIPLAYER SNAKE";
    private final String opt1 = "[1] New Game";
    private final String opt2 = "[2] High Scores";
    private final String opt3 = "[3] Instructions";
    private final String opt4 = "[4] Settings";
    private final String opt5 = "[5] Exit";

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

        titleLayout = new GlyphLayout(fontTitle, titleText);
        opt1Layout = new GlyphLayout(fontPrompt, opt1);
        opt2Layout = new GlyphLayout(fontPrompt, opt2);
        opt3Layout = new GlyphLayout(fontPrompt, opt3);
        opt4Layout = new GlyphLayout(fontPrompt, opt4);
        opt5Layout = new GlyphLayout(fontPrompt, opt5);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        float centerX = GameScreen.V_WIDTH / 2f;

        fontTitle.draw(game.batch, titleText, centerX - (titleLayout.width / 2f), GameScreen.V_HEIGHT / 1.5f);

        fontPrompt.setColor(Color.WHITE);
        fontPrompt.draw(game.batch, opt1, centerX - (opt1Layout.width / 2f), GameScreen.V_HEIGHT / 2.5f + 20);
        fontPrompt.draw(game.batch, opt2, centerX - (opt2Layout.width / 2f), GameScreen.V_HEIGHT / 2.5f - 40);
        fontPrompt.draw(game.batch, opt3, centerX - (opt3Layout.width / 2f), GameScreen.V_HEIGHT / 2.5f - 100);
        fontPrompt.draw(game.batch, opt4, centerX - (opt4Layout.width / 2f), GameScreen.V_HEIGHT / 2.5f - 160);
        fontPrompt.draw(game.batch, opt5, centerX - (opt5Layout.width / 2f), GameScreen.V_HEIGHT / 2.5f - 220);
        ;

        game.batch.end();

        // --- INPUT HANDLING ---
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
            game.setScreen(new GameScreen(game));
            dispose();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2)) {
            game.setScreen(new HighScoreScreen(game));
            dispose();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_3)) {
            game.setScreen(new InstructionsScreen(game));
            dispose();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_4)) {
            game.setScreen(new SettingsScreen(game));
            dispose();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_5)) {
            Gdx.app.exit();
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
