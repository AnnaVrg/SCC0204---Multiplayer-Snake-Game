package com.scc0204.snake;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.LinkedList;
import com.badlogic.gdx.graphics.Color;

/**
 * The main screen where the gameplay loop takes place.
 * Uses SpriteBatch and TextureRegion to handle 16x16 pixel art with directional
 * rotations.
 */
public class GameScreen extends ScreenAdapter {

    private boolean gameOver = false;
    private SpriteBatch batch;

    // Textures to hold the .png files in memory
    private Texture headTex, bodyTex, tailTex, cornerTex, fruitTex, tileTex1, tileTex2;
    // TextureRegions allow us to easily define a rotation origin for the sprites
    private TextureRegion headRegion, bodyRegion, tailRegion, cornerRegion;

    private Snake player1;
    private Snake player2;

    private Food apple;

    private int scoreP1 = 0;
    private int scoreP2 = 0;

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

        // Initialize Player 1 on the left, facing right (White color = original sprite
        // colors)
        player1 = new Snake(5, 10, Color.WHITE, bounds, Snake.Direction.RIGHT);

        // Initialize Player 2 on the right, facing left (Light Blue tint to
        // differentiate)
        player2 = new Snake(gridWidth - 5, 10, new Color(0.5f, 0.7f, 1f, 1f), bounds, Snake.Direction.LEFT);

        apple = new Food(gridWidth, gridHeight);

    }

    private void handleInput() {
        // Player 1 Controls (Arrow Keys)
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            player1.setDirection(Snake.Direction.UP);
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            player1.setDirection(Snake.Direction.DOWN);
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            player1.setDirection(Snake.Direction.LEFT);
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            player1.setDirection(Snake.Direction.RIGHT);

        // Player 2 Controls (W, A, S, D)
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            player2.setDirection(Snake.Direction.UP);
        else if (Gdx.input.isKeyPressed(Input.Keys.S))
            player2.setDirection(Snake.Direction.DOWN);
        else if (Gdx.input.isKeyPressed(Input.Keys.A))
            player2.setDirection(Snake.Direction.LEFT);
        else if (Gdx.input.isKeyPressed(Input.Keys.D))
            player2.setDirection(Snake.Direction.RIGHT);
    }

    @Override
    public void render(float delta) {
        handleInput();

        // --- GAME STATE CHECK ---
        // Only update the game logic if BOTH snakes are alive
        if (!player1.isDead() && !player2.isDead()) {

            player1.update(delta);
            player2.update(delta);

            // --- CROSS-COLLISION CHECK ---
            Snake.SnakeSegment p1Head = player1.getBody().getFirst();
            Snake.SnakeSegment p2Head = player2.getBody().getFirst();

            // Head-to-Head collision (Tie: both die)
            if (p1Head.x == p2Head.x && p1Head.y == p2Head.y) {
                player1.kill();
                player2.kill();
            } else {
                // Did Player 1 hit Player 2's body?
                for (Snake.SnakeSegment segment : player2.getBody()) {
                    if (p1Head.x == segment.x && p1Head.y == segment.y)
                        player1.kill();
                }
                // Did Player 2 hit Player 1's body?
                for (Snake.SnakeSegment segment : player1.getBody()) {
                    if (p2Head.x == segment.x && p2Head.y == segment.y)
                        player2.kill();
                }
            }

            // --- FOOD COLLISION ---
            if (!player1.isDead()) {
                if (p1Head.x == apple.getX() && p1Head.y == apple.getY()) {
                    player1.eat();
                    apple.respawn();
                    scoreP1++;
                    System.out.println("Player 1 Score: " + scoreP1);
                }
            }

            if (!player2.isDead()) {
                if (p2Head.x == apple.getX() && p2Head.y == apple.getY()) {
                    player2.eat();
                    apple.respawn();
                    scoreP2++;
                    System.out.println("Player 2 Score: " + scoreP2);
                }
            }

        } else {
            // --- GAME OVER STATE ---
            // If we enter here, at least one snake has died.
            // We run this block only once using the gameOver flag to prevent console spam.
            if (!gameOver) {
                gameOver = true;
                System.out.println("\n--- GAME OVER ---");
                System.out.println("Final Score - Player 1: " + scoreP1);
                System.out.println("Final Score - Player 2: " + scoreP2);

                // Determine the winner based strictly on the score
                if (scoreP1 > scoreP2) {
                    System.out.println("RESULT: Player 1 Wins!");
                } else if (scoreP2 > scoreP1) {
                    System.out.println("RESULT: Player 2 Wins!");
                } else {
                    System.out.println("RESULT: It's a Tie!");
                }
            }
        }

        // --- RENDERING ---
        // Rendering continues so we can see the frozen game state
        ScreenUtils.clear(0, 0, 0, 1);
        batch.begin();

        batch.setColor(Color.WHITE);

        int gridWidth = Gdx.graphics.getWidth() / TILE_SIZE;
        int gridHeight = Gdx.graphics.getHeight() / TILE_SIZE;

        // Draw the tiled background grid with a checkerboard pattern
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if ((x + y) % 2 == 0) {
                    batch.draw(tileTex1, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                } else {
                    batch.draw(tileTex2, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }

        batch.setColor(1f, 1f, 1f, 1f);
        batch.draw(fruitTex, apple.getX() * TILE_SIZE, apple.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        drawSnake(player1);
        drawSnake(player2);

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

    /**
     * Helper method to render a snake.
     * Applies the snake's specific color tint to differentiate players.
     */
    private void drawSnake(Snake player) {
        // Tints the pixel art based on the player's color
        batch.setColor(player.getColor());

        LinkedList<Snake.SnakeSegment> body = player.getBody();
        for (int i = 0; i < body.size(); i++) {
            Snake.SnakeSegment segment = body.get(i);
            float drawX = segment.x * TILE_SIZE;
            float drawY = segment.y * TILE_SIZE;

            TextureRegion regionToDraw;
            float rotation = 0f;

            if (i == 0) {
                regionToDraw = headRegion;
                rotation = getHeadRotation(player.getCurrentDirection());
            } else if (i == body.size() - 1) {
                regionToDraw = tailRegion;
                Snake.SnakeSegment front = body.get(i - 1);
                rotation = getDirectionRotation(segment, front);
            } else {
                Snake.SnakeSegment front = body.get(i - 1);
                Snake.SnakeSegment back = body.get(i + 1);

                if (front.x == back.x) {
                    regionToDraw = bodyRegion;
                    rotation = 90f;
                } else if (front.y == back.y) {
                    regionToDraw = bodyRegion;
                    rotation = 0f;
                } else {
                    regionToDraw = cornerRegion;
                    rotation = getCornerRotation(front, segment, back);
                }
            }

            batch.draw(regionToDraw,
                    drawX, drawY,
                    TILE_SIZE / 2f, TILE_SIZE / 2f,
                    TILE_SIZE, TILE_SIZE,
                    1f, 1f,
                    rotation);
        }
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
