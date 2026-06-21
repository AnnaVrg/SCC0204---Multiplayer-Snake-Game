package com.scc0204.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * Handles the Pause Menu Overlay.
 * Encapsulates the fonts, UI drawing, and pause state logic.
 */
public class PauseMenu {
  private BitmapFont fontLarge;
  private BitmapFont fontMedium;
  private boolean isPaused = false;

  public PauseMenu() {
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Kenney Pixel.ttf"));

    FreeTypeFontParameter paramLarge = new FreeTypeFontParameter();
    paramLarge.size = 80;
    paramLarge.color = Color.YELLOW;
    fontLarge = generator.generateFont(paramLarge);

    FreeTypeFontParameter paramMedium = new FreeTypeFontParameter();
    paramMedium.size = 40;
    paramMedium.color = Color.WHITE;
    fontMedium = generator.generateFont(paramMedium);

    generator.dispose();
  }

  public boolean isPaused() {
    return isPaused;
  }

  public void togglePause() {
    this.isPaused = !this.isPaused;
  }

  /**
   * Draws the pause menu and checks for input.
   * 
   * @return true if the user selected "Main Menu" and wants to quit the game.
   */
  public boolean updateAndDraw(SpriteBatch batch, float screenWidth, float screenHeight) {
    if (!isPaused)
      return false;

    float centerX = screenWidth / 2f;
    float centerY = screenHeight / 2f;

    // Draw "PAUSED" text
    String title = "PAUSED";
    GlyphLayout titleLayout = new GlyphLayout(fontLarge, title);
    fontLarge.draw(batch, title, centerX - (titleLayout.width / 2f), centerY + 100);

    // Draw Options
    String opt1 = "[1] Resume";
    String opt2 = "[2] Main Menu";

    GlyphLayout opt1Layout = new GlyphLayout(fontMedium, opt1);
    GlyphLayout opt2Layout = new GlyphLayout(fontMedium, opt2);

    fontMedium.draw(batch, opt1, centerX - (opt1Layout.width / 2f), centerY);
    fontMedium.draw(batch, opt2, centerX - (opt2Layout.width / 2f), centerY - 60);

    // Menu Inputs
    if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_1)) {
      this.isPaused = false;
    } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) || Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2)) {
      return true; // Signal to the GameScreen that we should quit
    }

    return false;
  }

  public void dispose() {
    fontLarge.dispose();
    fontMedium.dispose();
  }
}
