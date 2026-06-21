package com.scc0204.snake;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.LinkedList;

/**
 * The main screen where the gameplay loop takes place.
 * Uses SpriteBatch and TextureRegion to handle 16x16 pixel art with directional
 * rotations.
 */
public class GameScreen extends ScreenAdapter {
    private SpriteBatch batch;

    // Textures to hold the .png files in memory
    private Texture headTex, bodyTex, tailTex, cornerTex, fruitTex, tileTex1, tileTex2;
    // TextureRegions allow us to easily define a rotation origin for the sprites
    private TextureRegion headRegion, bodyRegion, tailRegion, cornerRegion;

    private Snake player1;
    private Food apple;
    private int score = 0;

    // Scales the 16x16 art up to 32x32 visually on the screen
    private static final int TILE_SIZE = 32;

    public GameScreen() {
        batch = new SpriteBatch();

        // Load the 16x16 pixel art files (make sure these exist in your assets folder)
        headTex = new Texture("head.png");
        bodyTex = new Texture("body.png");
        tailTex = new Texture("tail.png");
        cornerTex = new Texture("corner.png");
        fruitTex = new Texture("fruit.png");
        tileTex1 = new Texture("tile1.png");
        tileTex2 = new Texture("tile2.png");

        // Wrap the directional textures in a TextureRegion
        headRegion = new TextureRegion(headTex);
        bodyRegion = new TextureRegion(bodyTex);
        tailRegion = new TextureRegion(tailTex);
        cornerRegion = new TextureRegion(cornerTex);

        // Initialize Player 1 (Color is kept for structural compatibility)
        int gridWidth = Gdx.graphics.getWidth() / TILE_SIZE;
        int gridHeight = Gdx.graphics.getHeight() / TILE_SIZE;

        WorldBounds bounds = new WorldBounds(gridWidth, gridHeight); // MEXI AQUI

        player1 = new Snake(10, 10, null, bounds); // AQUI TAMBÉM

        apple = new Food(gridWidth, gridHeight);

    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player1.setDirection(Snake.Direction.UP);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player1.setDirection(Snake.Direction.DOWN);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player1.setDirection(Snake.Direction.LEFT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player1.setDirection(Snake.Direction.RIGHT);
        }
    }

    @Override
    public void render(float delta) {
        // Process user input
        handleInput();

        // Update game logic
        player1.update(delta);

        // Check Food Collision
        Snake.SnakeSegment head = player1.getBody().getFirst();
        if (head.x == apple.getX() && head.y == apple.getY()) {
            player1.eat();
            apple.respawn();
            score++;
            System.out.println("Score Player 1: " + score);
        }

        // --- RENDERING ---
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();

        int gridWidth = Gdx.graphics.getWidth() / TILE_SIZE;
        int gridHeight = Gdx.graphics.getHeight() / TILE_SIZE;

        // Draw the tiled background grid with a checkerboard pattern
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {

                // Alternate colors based on even/odd sum of coordinates
                if ((x + y) % 2 == 0) {
                    // Normal color (White tint = original pixel art colors)
                    batch.draw(tileTex1, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else {
                    // Slightly darker tint to create the checkerboard contrast
                    batch.draw(tileTex2, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }

            }
        }

        // Reset the batch color back to pure white before drawing the fruit and the
        // snake
        batch.setColor(1f, 1f, 1f, 1f);

        // Draw the fruit
        batch.draw(fruitTex, apple.getX() * TILE_SIZE, apple.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        // Draw the Snake
        LinkedList<Snake.SnakeSegment> body = player1.getBody();
        for (int i = 0; i < body.size(); i++) {
            Snake.SnakeSegment segment = body.get(i);
            float drawX = segment.x * TILE_SIZE;
            float drawY = segment.y * TILE_SIZE;

            TextureRegion regionToDraw;
            float rotation = 0f;

            if (i == 0) {
                // HEAD: Rotation is purely based on the current direction of the snake
                regionToDraw = headRegion;
                rotation = getHeadRotation(player1.getCurrentDirection());
            } else if (i == body.size() - 1) {
                // TAIL: Looks at the segment immediately in front of it
                regionToDraw = tailRegion;
                Snake.SnakeSegment front = body.get(i - 1);
                rotation = getDirectionRotation(segment, front);
            } else {
                // MIDDLE BODY OR CORNER: Looks at the segment in front and behind
                Snake.SnakeSegment front = body.get(i - 1);
                Snake.SnakeSegment back = body.get(i + 1);

                if (front.x == back.x) {
                    // Vertical straight line
                    regionToDraw = bodyRegion;
                    rotation = 90f;
                } else if (front.y == back.y) {
                    // Horizontal straight line
                    regionToDraw = bodyRegion;
                    rotation = 0f;
                } else {
                    // It's a corner, calculate the specific bend
                    regionToDraw = cornerRegion;
                    rotation = getCornerRotation(front, segment, back);
                }
            }

            // Draw the specific region rotated around its center point
            batch.draw(regionToDraw,
                    drawX, drawY,
                    TILE_SIZE / 2f, TILE_SIZE / 2f, // Origin X and Y for rotation
                    TILE_SIZE, TILE_SIZE,
                    1f, 1f, // Scale X and Y
                    rotation);
        }

        batch.end();
    }

    // --- ROTATION HELPER METHODS ---

    /**
     * Assumes the original 'head.png' points RIGHT.
     */
    private float getHeadRotation(Snake.Direction dir) {
        switch (dir) {
            case UP:
                return 90f;
            case LEFT:
                return 180f;
            case DOWN:
                return 270f;
            case RIGHT:
            default:
                return 0f;
        }
    }

    /**
     * Calculates angle for the tail based on the segment in front of it.
     * Assumes 'tail.png' naturally points LEFT and connects on its RIGHT side.
     */
    private float getDirectionRotation(Snake.SnakeSegment from, Snake.SnakeSegment to) {
        if (to.x > from.x)
            return 0f; // Facing Right
        if (to.x < from.x)
            return 180f; // Facing Left
        if (to.y > from.y)
            return 90f; // Facing Up
        if (to.y < from.y)
            return 270f; // Facing Down
        return 0f;
    }

    /**
     * Calculates corner rotation based on surrounding segments.
     * Assumes 'corner.png' connects LEFT and UP natively (an L-shape pointing
     * bottom-right).
     */
    private float getCornerRotation(Snake.SnakeSegment front, Snake.SnakeSegment current, Snake.SnakeSegment back) {
        boolean up = (front.y > current.y) || (back.y > current.y);
        boolean down = (front.y < current.y) || (back.y < current.y);
        boolean left = (front.x < current.x) || (back.x < current.x);
        boolean right = (front.x > current.x) || (back.x > current.x);

        if (left && up)
            return 0f;
        if (up && right)
            return 270f;
        if (right && down)
            return 180f;
        if (down && left)
            return 90f;

        return 0f;
    }

    @Override
    public void dispose() {
        // ALWAYS clean up textures to prevent VRAM memory leaks
        batch.dispose();
        headTex.dispose();
        bodyTex.dispose();
        tailTex.dispose();
        cornerTex.dispose();
        fruitTex.dispose();
        tileTex1.dispose();
        tileTex2.dispose();
    }
}
