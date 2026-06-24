package com.scc0204.snake;

import com.badlogic.gdx.graphics.Color;
import java.util.LinkedList;

/**
 * Represents a playable snake in the game.
 * Manages body segments, movement timing, direction state, and growth/shrink
 * logic.
 */
public class Snake extends Entity {

    private LinkedList<SnakeSegment> body;
    private Color color;
    private Direction currentDirection;

    private float moveTimer = 0;
    private float currentMoveTime;
    private int pendingGrowth = 0;

    private boolean isDead = false;
    private boolean diedBySuicide = false;

    private WorldBounds bounds;

    /**
     * Defines the cardinal movement directions.
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * Represents a single segment of the snake's body on the grid.
     */
    public static class SnakeSegment {
        public int x, y;

        public SnakeSegment(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Constructs a new Snake instance.
     *
     * @param startX        Initial grid X coordinate.
     * @param startY        Initial grid Y coordinate.
     * @param color         Snake visual color.
     * @param bounds        Grid boundaries for movement wrapping.
     * @param startDir      Initial starting direction.
     * @param startingSpeed Time interval between moves in seconds.
     */
    public Snake(int startX, int startY, Color color, WorldBounds bounds, Direction startDir, float startingSpeed) {
        super(startX, startY);
        this.color = color;
        this.body = new LinkedList<>();
        this.bounds = bounds;
        this.body.add(new SnakeSegment(startX, startY));
        this.currentMoveTime = startingSpeed;

        // Initialize second segment based on starting direction
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
     * Updates the snake direction, preventing 180-degree turns.
     *
     * @param direction The new desired direction.
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

    /**
     * {@inheritDoc}
     * Processes movement based on the elapsed time and movement timer.
     */
    @Override
    public void update(float deltaTime) {
        if (isDead)
            return;
        moveTimer += deltaTime;

        if (moveTimer >= currentMoveTime) {
            moveTimer = 0;
            move();
        }
    }

    /**
     * Calculates the next position, checks for collisions, and updates body
     * segments.
     */
    private void move() {
        SnakeSegment head = body.getFirst();
        int nextX = head.x;
        int nextY = head.y;

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

        nextX = bounds.wrapX(nextX);
        nextY = bounds.wrapY(nextY);

        // Check for self-collision
        for (SnakeSegment segment : body) {
            if (segment.x == nextX && segment.y == nextY) {
                this.isDead = true;
                this.diedBySuicide = true;
                return;
            }
        }

        body.addFirst(new SnakeSegment(nextX, nextY));

        // Process growth/shrink queue
        if (pendingGrowth > 0) {
            pendingGrowth--;
        } else if (pendingGrowth < 0) {
            body.removeLast();
            if (body.size() > 1) {
                body.removeLast();
            } else {
                this.isDead = true;
            }
            pendingGrowth++;
        } else {
            body.removeLast();
        }
    }

    /**
     * Modifies the snake's size and adjusts speed when food is consumed.
     *
     * @param sizeChange Positive value to grow, negative to shrink.
     */
    public void modifySize(int sizeChange) {
        this.pendingGrowth += sizeChange;

        if (sizeChange > 0 && this.currentMoveTime > 0.05f) {
            this.currentMoveTime -= 0.005f;
        }
    }

    /** Forces the snake into the dead state. */
    public void kill() {
        this.isDead = true;
    }

    /** @return True if the snake is currently in the dead state. */
    public boolean isDead() {
        return isDead;
    }

    /** @return The current list of body segments. */
    public LinkedList<SnakeSegment> getBody() {
        return body;
    }

    /** @return The snake's color. */
    public Color getColor() {
        return color;
    }

    /** @return The current movement direction. */
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    /** @return True if the snake died by colliding with its own body. */
    public boolean didDieBySuicide() {
        return diedBySuicide;
    }

}
