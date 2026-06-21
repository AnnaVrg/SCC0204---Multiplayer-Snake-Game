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

public class GameOverScreen implements Screen {
    private final SnakeGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont fontLarge;
    private BitmapFont fontMedium;
    private int scoreP1, scoreP2;

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

        fontMedium.setColor(Color.WHITE);
        String scoreText = "P1: " + scoreP1 + "   -   P2: " + scoreP2;
        GlyphLayout scoreLayout = new GlyphLayout(fontMedium, scoreText);
        fontMedium.draw(game.batch, scoreText, centerX - (scoreLayout.width / 2f), centerY);

        // Draw Menu Options
        String opt1 = "[1] Play Again";
        String opt2 = "[2] Main Menu";
        fontMedium.draw(game.batch, opt1, centerX - (new GlyphLayout(fontMedium, opt1).width / 2f), centerY - 100);
        fontMedium.draw(game.batch, opt2, centerX - (new GlyphLayout(fontMedium, opt2).width / 2f), centerY - 150);

        game.batch.end();

        // --- INPUT HANDLING ---
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
            game.setScreen(new GameScreen(game));
            dispose();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2)) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

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

    @Override
    public void dispose() {
        fontLarge.dispose();
        fontMedium.dispose();
    }
}
