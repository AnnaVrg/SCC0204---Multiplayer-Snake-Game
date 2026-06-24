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

import java.util.ArrayList;

public class HighScoreScreen implements Screen {
    private final SnakeGame game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private BitmapFont fontTitle;
    private BitmapFont fontScore;
    private ArrayList<HighScoreManager.ScoreEntry> topScores;

    private GlyphLayout titleLayout, emptyLayout, promptLayout;
    private GlyphLayout[] scoreLayouts;
    private String[] scoreTexts;

    private final String title = "TOP 5 SCORES";
    private final String emptyText = "No scores yet!";
    private final String prompt = "Press BACKSPACE or ESC to return";

    public HighScoreScreen(SnakeGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(GameScreen.V_WIDTH, GameScreen.V_HEIGHT, camera);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Kenney Pixel.ttf"));

        // Font for the Title
        FreeTypeFontParameter paramTitle = new FreeTypeFontParameter();
        paramTitle.size = 80;
        paramTitle.color = Color.YELLOW;
        fontTitle = generator.generateFont(paramTitle);

        // Font for the Scores
        FreeTypeFontParameter paramScore = new FreeTypeFontParameter();
        paramScore.size = 50;
        paramScore.color = Color.WHITE;
        fontScore = generator.generateFont(paramScore);

        generator.dispose();

        // Load the scores from our manager class when the screen is created
        topScores = HighScoreManager.getHighScores();

        // Calculando os layouts fixos
        titleLayout = new GlyphLayout(fontTitle, title);
        emptyLayout = new GlyphLayout(fontScore, emptyText);

        // Cuidado especial com a escala do prompt
        fontScore.getData().setScale(0.7f);
        promptLayout = new GlyphLayout(fontScore, prompt);
        fontScore.getData().setScale(1f); // Reseta a escala logo após

        // Pre-calculando as strings de pontuação e seus tamanhos
        if (!topScores.isEmpty()) {
            scoreTexts = new String[topScores.size()];
            scoreLayouts = new GlyphLayout[topScores.size()];
            for (int i = 0; i < topScores.size(); i++) {
                HighScoreManager.ScoreEntry entry = topScores.get(i);
                scoreTexts[i] = (i + 1) + ". " + entry.name + " - " + entry.score + " PTS (" + entry.date + ")";
                scoreLayouts[i] = new GlyphLayout(fontScore, scoreTexts[i]);
            }
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        float centerX = GameScreen.V_WIDTH / 2f;
        float startY = GameScreen.V_HEIGHT - 100;

        fontTitle.draw(game.batch, title, centerX - (titleLayout.width / 2f), startY);

        float scoreY = startY - 120;
        if (topScores.isEmpty()) {
            fontScore.draw(game.batch, emptyText, centerX - (emptyLayout.width / 2f), scoreY);
        } else {
            for (int i = 0; i < topScores.size(); i++) {
                fontScore.draw(game.batch, scoreTexts[i], centerX - (scoreLayouts[i].width / 2f), scoreY);
                scoreY -= 60;
            }
        }

        fontScore.getData().setScale(0.7f);
        fontScore.draw(game.batch, prompt, centerX - (promptLayout.width / 2f), 80);
        fontScore.getData().setScale(1f);
        fontScore.getData().setScale(1f); // Reset scale

        game.batch.end();

        // --- INPUT HANDLING ---
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
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
        fontTitle.dispose();
        fontScore.dispose();
    }
}
