package com.scc0204.snake;

import com.badlogic.gdx.graphics.Color;
import java.util.LinkedList;

/**
 * Represents a playable Snake in the game.
 * Manages its own body segments, movement timer, and current direction.
 */
public class Snake extends Entity {

    private LinkedList<SnakeSegment> body;
    private Color color;
    private Direction currentDirection;

    private float moveTimer = 0;
    private float currentMoveTime;
    // REPLACED: Swapped 'justAte' boolean flag for an integer counter for pending
    // segment changes
    private int pendingGrowth = 0;

    private boolean isDead = false;
    private boolean diedBySuicide = false;

    // World Boundaries
    private WorldBounds bounds;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static class SnakeSegment {
        public int x, y;

        public SnakeSegment(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public Snake(int startX, int startY, Color color, WorldBounds bounds, Direction startDir, float startingSpeed) {
        super(startX, startY);
        this.color = color;
        this.body = new LinkedList<>();
        this.bounds = bounds;
        this.body.add(new SnakeSegment(startX, startY));
        this.currentMoveTime = startingSpeed;

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
        if (isDead)
            return;
        moveTimer += deltaTime;

        if (moveTimer >= currentMoveTime) {
            moveTimer = 0;
            move();
        }
    }

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

        for (SnakeSegment segment : body) {
            if (segment.x == nextX && segment.y == nextY) {
                this.isDead = true;
                this.diedBySuicide = true;
                return;
            }
        }

        body.addFirst(new SnakeSegment(nextX, nextY));

        // NEW GROWTH/SHRINK LOGIC: Processing the growth/shrink queue
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
     * Handles dynamic snake sizing when special food is consumed.
     */
    public void modifySize(int sizeChange) {
        this.pendingGrowth += sizeChange;

        if (sizeChange > 0 && this.currentMoveTime > 0.05f) {
            this.currentMoveTime -= 0.005f;
        }
    }

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

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public boolean didDieBySuicide() {
        return diedBySuicide;
    }
}

