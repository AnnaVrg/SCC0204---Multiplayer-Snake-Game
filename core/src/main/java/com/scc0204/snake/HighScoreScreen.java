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
  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(0, 0, 0, 1);
    camera.update();
    game.batch.setProjectionMatrix(camera.combined);
    game.batch.begin();

    float centerX = GameScreen.V_WIDTH / 2f;
    float startY = GameScreen.V_HEIGHT - 100;

    // Draw Title
    String title = "TOP 5 SCORES";
    GlyphLayout titleLayout = new GlyphLayout(fontTitle, title);
    fontTitle.draw(game.batch, title, centerX - (titleLayout.width / 2f), startY);

    // Draw Scores iteratively
    float scoreY = startY - 120;
    if (topScores.isEmpty()) {
      String emptyText = "No scores yet!";
      GlyphLayout emptyLayout = new GlyphLayout(fontScore, emptyText);
      fontScore.draw(game.batch, emptyText, centerX - (emptyLayout.width / 2f), scoreY);
    } else {
      // Loop through the objects to format the string
      for (int i = 0; i < topScores.size(); i++) {
        HighScoreManager.ScoreEntry entry = topScores.get(i);

        // Format: "1. NAME - 15 PTS (21/06/2026)"
        String scoreText = (i + 1) + ". " + entry.name + " - " + entry.score + " PTS (" + entry.date + ")";

        GlyphLayout scoreLayout = new GlyphLayout(fontScore, scoreText);
        fontScore.draw(game.batch, scoreText, centerX - (scoreLayout.width / 2f), scoreY);
        scoreY -= 60;
      }
    }

    // Draw Exit Prompt
    fontScore.getData().setScale(0.7f); // Scale down slightly for the prompt
    String prompt = "Press BACKSPACE or ESC to return";
    GlyphLayout promptLayout = new GlyphLayout(fontScore, prompt);
    fontScore.draw(game.batch, prompt, centerX - (promptLayout.width / 2f), 80);
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
