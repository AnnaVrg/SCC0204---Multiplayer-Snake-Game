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

/**
 * Handles the game settings menu, allowing players to configure
 * grid dimensions and movement speed.
 */
public class SettingsScreen implements Screen {
    private final SnakeGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont fontTitle;
    private BitmapFont fontText;

    private GlyphLayout titleLayout, promptLayout, dynamicLayout;
    private final String title = "SETTINGS";
    private final String prompt = "Press BACKSPACE to return";

    /**
     * Initializes the settings screen and pre-loads UI assets.
     *
     * @param game Reference to the main game instance.
     */
    public SettingsScreen(SnakeGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameScreen.V_WIDTH, GameScreen.V_HEIGHT, camera);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Kenney Pixel.ttf"));

        // Configure Title Font
        FreeTypeFontParameter paramTitle = new FreeTypeFontParameter();
        paramTitle.size = 80;
        paramTitle.color = Color.YELLOW;
        fontTitle = generator.generateFont(paramTitle);

        // Configure Menu Text Font
        FreeTypeFontParameter paramText = new FreeTypeFontParameter();
        paramText.size = 45;
        paramText.color = Color.WHITE;
        fontText = generator.generateFont(paramText);

        generator.dispose();

        titleLayout = new GlyphLayout(fontTitle, title);
        promptLayout = new GlyphLayout(fontText, prompt);
        dynamicLayout = new GlyphLayout();
    }

    /**
     * {@inheritDoc}
     * Renders configuration options and processes setting changes.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        float centerX = GameScreen.V_WIDTH / 2f;
        float startY = GameScreen.V_HEIGHT - 100;

        fontTitle.draw(game.batch, title, centerX - (titleLayout.width / 2f), startY);

        String opt1 = "[1] Grid Size: " + game.settings.getGridSizeName();
        String opt2 = "[2] Speed: " + game.settings.getSpeedName();

        // Atualiza e desenha a opção 1
        dynamicLayout.setText(fontText, opt1);
        fontText.draw(game.batch, opt1, centerX - (dynamicLayout.width / 2f), startY - 150);

        // Atualiza e desenha a opção 2
        dynamicLayout.setText(fontText, opt2);
        fontText.draw(game.batch, opt2, centerX - (dynamicLayout.width / 2f), startY - 230);

        fontText.setColor(Color.GRAY);
        fontText.draw(game.batch, prompt, centerX - (promptLayout.width / 2f), 80);
        fontText.setColor(Color.WHITE);

        game.batch.end();

        // Handles navigation input
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
            game.settings.toggleGridSize();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2)) {
            game.settings.toggleSpeed();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void resize(int width, int height) {
        if (viewport != null)
            viewport.update(width, height, true);
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

    /** {@inheritDoc} */
    @Override
    public void dispose() {
        fontTitle.dispose();
        fontText.dispose();
    }
}
