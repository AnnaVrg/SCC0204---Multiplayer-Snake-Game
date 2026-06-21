package com.scc0204.snake;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.graphics.Color;

public class SnakeGameLogicTests {

    @Test
    public void testWorldBoundsWrapping() {
        // Creates a 20x20 grid (valid indices from 0 to 19)
        WorldBounds bounds = new WorldBounds(20, 20);

        // Tests crossing the right boundary
        assertEquals(0, bounds.wrapX(20), "Coordinate X = 20 should wrap around to index 0");

        // Tests crossing the left boundary
        assertEquals(19, bounds.wrapX(-1), "Coordinate X = -1 should wrap around to index 19");

        // Tests vertical boundaries (Y)
        assertEquals(0, bounds.wrapY(20), "Coordinate Y = 20 should wrap around to index 0");
        assertEquals(15, bounds.wrapY(15), "Coordinates within the grid should not be altered");
    }

    @Test
    public void testFoodTypesAndModifiers() {
        Food apple = new Food(20, 20);

        // Tests the Golden Apple
        apple.respawnAs(Food.AppleType.GOLDEN);
        assertEquals(10, apple.getPoints(), "Golden Apple should be worth 10 points");
        assertEquals(2, apple.getSizeChange(), "Golden Apple should increase size by 2 segments");

        // Tests the Rotten Apple
        apple.respawnAs(Food.AppleType.ROTTEN);
        assertEquals(-5, apple.getPoints(), "Rotten Apple should penalize 5 points");
        assertEquals(-1, apple.getSizeChange(), "Rotten Apple should remove 1 segment");
    }

    @Test
    public void testSnakeDigestionLogic() {
        WorldBounds bounds = new WorldBounds(20, 20);
        Snake snake = new Snake(10, 10, Color.WHITE, bounds, Snake.Direction.RIGHT);

        // The constructor initializes the snake with 2 segments (head + 1 tail)
        int initialSize = snake.getBody().size();
        assertEquals(2, initialSize);

        // Forces the snake to eat a rotten apple (sizeChange = -1)
        snake.modifySize(-1);

        // Since it only has 2 segments and the method prevents it from disappearing
        // (body.size() > 2),
        // the size should not drop to 1.
        assertEquals(2, snake.getBody().size(), "The snake cannot shrink to less than 2 segments.");
    }
}
