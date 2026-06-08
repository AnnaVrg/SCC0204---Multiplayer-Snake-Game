package com.scc0204.snake;

import java.util.Random;

/**
 * Represents the food (apple) in the game.
 * Inherits coordinates from Entity and handles random respawning.
 */
public class Food extends Entity {
  private Random random;
  private int gridWidth;
  private int gridHeight;

  public Food(int gridWidth, int gridHeight) {
    super(0, 0); // Initial dummy position
    this.gridWidth = gridWidth;
    this.gridHeight = gridHeight;
    this.random = new Random();
    respawn(); // Spawn at a random location immediately
  }

  /**
   * Randomly changes the food's coordinates within the grid bounds.
   */
  public void respawn() {
    this.x = random.nextInt(gridWidth);
    this.y = random.nextInt(gridHeight);
  }

  @Override
  public void update(float deltaTime) {
    // The food doesn't move autonomously, so this stays empty.
  }
}
