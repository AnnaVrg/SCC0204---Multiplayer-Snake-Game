package com.scc0204.snake;

import com.badlogic.gdx.graphics.Color;
import java.util.LinkedList;

/**
 * Represents a playable Snake in the game.
 * Manages its own body segments, movement timer, and current direction.
 */
public class Snake extends Entity {
    // LinkedList is optimal here because we frequently add to the head (addFirst)
    // and remove from the tail (removeLast) during movement.
    private LinkedList<SnakeSegment> body;
    private Color color;
    private Direction currentDirection;

    // Timer to control movement speed (grid-based movement, not pixel-by-pixel)
    private float moveTimer = 0;
    private float currentMoveTime = 0.15f; // Start at default speed

    // Flag to check if the snake just consumed food
    private boolean justAte = false;

    // Flag to check if the snake has collided with itself
    private boolean isDead = false;

    // World Boundaries
    private WorldBounds bounds; // MEXI AQUII ANNNA

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * Inner class representing a single grid block of the snake's body.
     */
    public static class SnakeSegment {
        public int x, y;

        public SnakeSegment(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public Snake(int startX, int startY, Color color, WorldBounds bounds, Direction startDir) {
        super(startX, startY);
        this.color = color;
        this.body = new LinkedList<>();
        this.bounds = bounds; // MEXI AQUI ANNAAAA
        this.body.add(new SnakeSegment(startX, startY));

        // Position the initial tail based on the starting direction
        switch (startDir) {
            case RIGHT:
                this.body.add(new SnakeSegment(startX - 1, startY));
                break;
            case LEFT:
                this.body.add(new SnakeSegment(startX + 1, startY));
                break;
            case UP:
                this.body.add(new SnakeSegment(startX, startY - 1));
                break;
            case DOWN:
                this.body.add(new SnakeSegment(startX, startY + 1));
                break;
        }

        this.currentDirection = startDir;
    }

    /**
     * Updates the snake's direction, preventing 180-degree turns
     * (e.g., the snake cannot move LEFT if it is currently moving RIGHT).
     */
    public void setDirection(Direction direction) {
        if (this.currentDirection == Direction.RIGHT && direction == Direction.LEFT)
            return;
        if (this.currentDirection == Direction.LEFT && direction == Direction.RIGHT)
            return;
        if (this.currentDirection == Direction.UP && direction == Direction.DOWN)
            return;
        if (this.currentDirection == Direction.DOWN && direction == Direction.UP)
            return;

        this.currentDirection = direction;
    }

    @Override
    public void update(float deltaTime) {

        // Halt all logic if the snake is dead
        if (isDead)
            return;
        moveTimer += deltaTime;

        // Only trigger the movement logic when the timer reaches the threshold
        if (moveTimer >= currentMoveTime) {
            moveTimer = 0;
            move();
        }
    }

    /**
     * Executes the grid-based movement logic.
     * Calculates the next position, adds a new head, and removes the tail.
     */
    private void move() {
        SnakeSegment head = body.getFirst();
        int nextX = head.x;
        int nextY = head.y;

        // Determine the next coordinates based on the current direction
        switch (currentDirection) {
            case UP:
                nextY += 1;
                break;
            case DOWN:
                nextY -= 1;
                break;
            case LEFT:
                nextX -= 1;
                break;
            case RIGHT:
                nextX += 1;
                break;
        }

        // MEXI AQUI TAMBÉM
        nextX = bounds.wrapX(nextX);
        nextY = bounds.wrapY(nextY);

        // If the calculated next position matches any current body segment, the snake
        // dies.
        for (SnakeSegment segment : body) {
            if (segment.x == nextX && segment.y == nextY) {
                this.isDead = true;
                return; // Interrupt the movement execution
            }
        }

        // Add the new segment at the calculated position (this becomes the new head)
        body.addFirst(new SnakeSegment(nextX, nextY));

        if (!justAte) {
            // If it didn't eat, remove the tail to maintain the same length (normal
            // movement)
            body.removeLast();
        } else {
            // If it ate, do not remove the tail (the snake grows!) and reset the flag
            justAte = false;
        }
    }

    /**
     * Called when the snake's head collides with the food.
     * Triggers growth and slightly increases movement speed.
     */
    public void eat() {
        this.justAte = true;

        // Increases speed by reducing the time between movements (capped at a minimum
        // of 0.05f)
        if (this.currentMoveTime > 0.05f) {
            this.currentMoveTime -= 0.005f;
        }
    }

    // Allows the GameScreen to explicitly kill this snake (e.g., cross-collision)
    public void kill() {
        this.isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }

    public LinkedList<SnakeSegment> getBody() {
        return body;
    }

    public Color getColor() {
        return color;
    }

    // Needed for the GameScreen to rotate the head sprite properly
    public Direction getCurrentDirection() {
        return currentDirection;
    }
}
