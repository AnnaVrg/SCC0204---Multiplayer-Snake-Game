package com.scc0204.snake;

/**
 * Global settings configuration for the game.
 * Uses static variables so they can be accessed from any screen.
 */
public class GameSettings {

  // Grid Options: 40 (Small 20x16), 32 (Normal 25x20), 16 (Large 50x40)
  public static int TILE_SIZE = 32;

  // Speed Options: 0.20f (Slow), 0.15f (Normal), 0.10f (Fast)
  public static float STARTING_SPEED = 0.15f;

  // Helper methods to cycle through settings in the menu
  public static void toggleGridSize() {
    if (TILE_SIZE == 32)
      TILE_SIZE = 16;
    else if (TILE_SIZE == 16)
      TILE_SIZE = 40;
    else
      TILE_SIZE = 32;
  }

  public static void toggleSpeed() {
    if (STARTING_SPEED == 0.15f)
      STARTING_SPEED = 0.10f;
    else if (STARTING_SPEED == 0.10f)
      STARTING_SPEED = 0.20f;
    else
      STARTING_SPEED = 0.15f;
  }

  // Helper methods to display current settings as text
  public static String getGridSizeName() {
    if (TILE_SIZE == 40)
      return "SMALL";
    if (TILE_SIZE == 32)
      return "NORMAL";
    return "LARGE";
  }

  public static String getSpeedName() {
    if (STARTING_SPEED == 0.20f)
      return "SLOW";
    if (STARTING_SPEED == 0.15f)
      return "NORMAL";
    return "FAST";
  }
}
