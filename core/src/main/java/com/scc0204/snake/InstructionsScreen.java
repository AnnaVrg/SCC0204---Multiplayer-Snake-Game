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
 * Displays the game instructions and rules to the player.
 * Allows navigation back to the main menu.
 */
public class InstructionsScreen implements Screen {
    private final SnakeGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont fontTitle;
    private BitmapFont fontText;

    private GlyphLayout titleLayout, promptLayout;
    private GlyphLayout[] linesLayouts;
    private final String title = "HOW TO PLAY";
    private final String prompt = "Press BACKSPACE or ESC to return";
    private final String[] lines = {
            "--- CONTROLS ---",
            "PLAYER 1 (Green): Arrow Keys",
            "PLAYER 2 (Blue): W A S D",
            "",
            "--- RULES ---",
            "1. Eat apples to grow and score points.",
            "2. Do not hit your own body.",
            "3. If you hit the other snake's body, you die.",
            "4. Going off-screen wraps you to the other side.",
            "5. The player with the highest score wins!",
    };

    /**
     * Initializes the instructions screen and pre-calculates UI layouts.
     *
     * @param game Reference to the main game instance.
     */
    public InstructionsScreen(SnakeGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(GameScreen.V_WIDTH, GameScreen.V_HEIGHT, camera);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Kenney Pixel.ttf"));

        // Configure Title Font
        FreeTypeFontParameter paramTitle = new FreeTypeFontParameter();
        paramTitle.size = 80;
        paramTitle.color = Color.YELLOW;
        fontTitle = generator.generateFont(paramTitle);

        // Configure Text Font
        FreeTypeFontParameter paramText = new FreeTypeFontParameter();
        paramText.size = 35;
        paramText.color = Color.WHITE;
        fontText = generator.generateFont(paramText);

        generator.dispose();

        // Pre-calculate layouts for performance optimization
        titleLayout = new GlyphLayout(fontTitle, title);
        promptLayout = new GlyphLayout(fontText, prompt);

        linesLayouts = new GlyphLayout[lines.length];
        for (int i = 0; i < lines.length; i++) {
            linesLayouts[i] = new GlyphLayout(fontText, lines[i]);
        }
    }

    /**
     * {@inheritDoc}
     * Renders instructions and processes return navigation.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        float centerX = GameScreen.V_WIDTH / 2f;
        float startY = GameScreen.V_HEIGHT - 60;

        // Render Header
        fontTitle.draw(game.batch, title, centerX - (titleLayout.width / 2f), startY);

        // Render Rules and Controls
        float textY = startY - 80;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].startsWith("---"))
                fontText.setColor(Color.YELLOW);
            else
                fontText.setColor(Color.WHITE);

            fontText.draw(game.batch, lines[i], centerX - (linesLayouts[i].width / 2f), textY);
            textY -= 40;
        }

        // Render Footer Prompt
        fontText.setColor(Color.GRAY);
        fontText.draw(game.batch, prompt, centerX - (promptLayout.width / 2f), 50);

        game.batch.end();

        // Handle return navigation
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
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
