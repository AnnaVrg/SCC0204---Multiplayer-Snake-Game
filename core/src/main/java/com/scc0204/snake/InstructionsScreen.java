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

public class InstructionsScreen implements Screen {
  private final SnakeGame game;
  private OrthographicCamera camera;
  private Viewport viewport;
  private BitmapFont fontTitle;
  private BitmapFont fontText;

  public InstructionsScreen(SnakeGame game) {
    this.game = game;

    camera = new OrthographicCamera();
    viewport = new FitViewport(GameScreen.V_WIDTH, GameScreen.V_HEIGHT, camera);

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Kenney Pixel.ttf"));

    // Font for the Title
    FreeTypeFontParameter paramTitle = new FreeTypeFontParameter();
    paramTitle.size = 80;
    paramTitle.color = Color.YELLOW;
    fontTitle = generator.generateFont(paramTitle);

    // Font for the Rules Text
    FreeTypeFontParameter paramText = new FreeTypeFontParameter();
    paramText.size = 35;
    paramText.color = Color.WHITE;
    fontText = generator.generateFont(paramText);

    generator.dispose();
  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(0, 0, 0, 1);
    camera.update();
    game.batch.setProjectionMatrix(camera.combined);
    game.batch.begin();

    float centerX = GameScreen.V_WIDTH / 2f;
    float startY = GameScreen.V_HEIGHT - 60;

    // Draw Title
    String title = "HOW TO PLAY";
    GlyphLayout titleLayout = new GlyphLayout(fontTitle, title);
    fontTitle.draw(game.batch, title, centerX - (titleLayout.width / 2f), startY);

    // Array of instruction lines
    String[] lines = {
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

    // Draw Instructions Iteratively
    float textY = startY - 80;
    for (String line : lines) {
      // Highlight section headers in yellow
      if (line.startsWith("---"))
        fontText.setColor(Color.YELLOW);
      else
        fontText.setColor(Color.WHITE);

      GlyphLayout lineLayout = new GlyphLayout(fontText, line);
      fontText.draw(game.batch, line, centerX - (lineLayout.width / 2f), textY);
      textY -= 40; // Spacing between lines
    }

    // Draw Exit Prompt
    fontText.setColor(Color.GRAY);
    String prompt = "Press BACKSPACE or ESC to return";
    GlyphLayout promptLayout = new GlyphLayout(fontText, prompt);
    fontText.draw(game.batch, prompt, centerX - (promptLayout.width / 2f), 50);

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
    fontText.dispose();
  }
}
