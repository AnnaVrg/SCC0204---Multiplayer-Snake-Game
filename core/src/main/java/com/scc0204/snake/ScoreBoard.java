package com.scc0204.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * Handles the Heads-Up Display (HUD).
 * Responsible for rendering the scores and the Game Over screen.
 */
public class ScoreBoard {
  private BitmapFont font;

  public ScoreBoard() {
    // 1. Carrega o arquivo .ttf da pasta assets (coloque o nome exato do arquivo
    // aqui)
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Kenney Pixel.ttf"));

    // 2. Configura os parâmetros (como o tamanho em pixels)
    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
    parameter.size = 32; // Tamanho ideal para placares arcade
    parameter.color = Color.WHITE; // Cor base

    // 3. Gera a fonte perfeitamente nítida
    font = generator.generateFont(parameter);

    // 4. Limpa o gerador da memória (importante!)
    generator.dispose();
  }

  /**
   * Draws the current scores and the end-game state if applicable.
   */
  public void draw(SpriteBatch batch, int scoreP1, int scoreP2, boolean gameOver) {
    // Player 1 Score (White, Left aligned)
    font.setColor(Color.WHITE);
    font.draw(batch, "P1 Score: " + scoreP1, 20, Gdx.graphics.getHeight() - 20);

    // Player 2 Score (Light Blue, Right aligned)
    font.setColor(Color.WHITE);
    font.draw(batch, "P2 Score: " + scoreP2, Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 20);

    // Game Over Overlay
    if (gameOver) {
      font.setColor(Color.WHITE);
      float centerX = Gdx.graphics.getWidth() / 2f;
      float centerY = Gdx.graphics.getHeight() / 2f;

      font.getData().setScale(2.5f);
      font.draw(batch, "GAME OVER", centerX - 100, centerY + 50);

      font.getData().setScale(1.5f);
      font.setColor(Color.WHITE);

      String winnerText;
      if (scoreP1 > scoreP2)
        winnerText = "PLAYER 1 WINS!";
      else if (scoreP2 > scoreP1)
        winnerText = "PLAYER 2 WINS!";
      else
        winnerText = "IT'S A TIE!";

      font.draw(batch, winnerText, centerX - 80, centerY);
    }
  }

  /**
   * Frees the memory used by the font.
   */
  public void dispose() {
    font.dispose();
  }
}
