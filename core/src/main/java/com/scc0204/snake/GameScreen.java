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
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Manages the core gameplay loop, including input handling, entity updates,
 * collision detection, and rendering the game world.
 */
public class GameScreen extends ScreenAdapter {

    private final SnakeGame game;
    public static final float V_WIDTH = 800;
    public static final float V_HEIGHT = 640;

    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;

    private Texture headTex, bodyTex, tailTex, cornerTex, fruitTex, goldenTex, rottenTex, tileTex1, tileTex2;
    private TextureRegion headRegion, bodyRegion, tailRegion, cornerRegion;

    private Snake player1;
    private Snake player2;
    private Food apple;
    private SoundManager soundManager;

    private ScoreBoard scoreBoard;
    private PauseMenu pauseMenu;

    private static final int CHANCE_GOLDEN = 10; // 10% de chance
    private static final int CHANCE_ROTTEN = 20; // 20% de chance
    private static final int PENALTY_SUICIDE = 10;

    private final java.util.Random random = new java.util.Random();

    /**
     * Initializes gameplay resources, UI components, and game entities.
     *
     * @param game Reference to the main game instance.
     */
    public GameScreen(SnakeGame game) {
        this.game = game;
        this.batch = game.batch;

        camera = new OrthographicCamera();
        viewport = new FitViewport(V_WIDTH, V_HEIGHT, camera);

        // Loading the textures
        headTex = new Texture("head.png");
        bodyTex = new Texture("body.png");
        tailTex = new Texture("tail.png");
        cornerTex = new Texture("corner.png");
        fruitTex = new Texture("fruit.png");
        goldenTex = new Texture("golden_apple.png");
        rottenTex = new Texture("rotten_apple.png");
        tileTex1 = new Texture("tile1.png");
        tileTex2 = new Texture("tile2.png");

        headRegion = new TextureRegion(headTex);
        bodyRegion = new TextureRegion(bodyTex);
        tailRegion = new TextureRegion(tailTex);
        cornerRegion = new TextureRegion(cornerTex);

        scoreBoard = new ScoreBoard();
        pauseMenu = new PauseMenu();

        int gridWidth = (int) (V_WIDTH / game.settings.getTileSize());
        int gridHeight = (int) (V_HEIGHT / game.settings.getTileSize());

        WorldBounds bounds = new WorldBounds(gridWidth, gridHeight);

        int spawnY = gridHeight / 2;

        player1 = new Snake(5, spawnY, Color.WHITE, bounds, Snake.Direction.RIGHT, game.settings.getStartingSpeed());
        player2 = new Snake(gridWidth - 5, spawnY, new Color(0.5f, 0.7f, 1f, 1f), bounds, Snake.Direction.LEFT,
                game.settings.getStartingSpeed());

        apple = new Food(gridWidth, gridHeight);

        soundManager = new SoundManager();
        soundManager.playBackgroundMusic("GameMusic.WAV");
    }

    /**
     * Handles players inputs
     */
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player1.setDirection(Snake.Direction.UP);
        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            player1.setDirection(Snake.Direction.DOWN);
        else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
            player1.setDirection(Snake.Direction.LEFT);
        else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
            player1.setDirection(Snake.Direction.RIGHT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.W))
            player2.setDirection(Snake.Direction.UP);
        else if (Gdx.input.isKeyJustPressed(Input.Keys.S))
            player2.setDirection(Snake.Direction.DOWN);
        else if (Gdx.input.isKeyJustPressed(Input.Keys.A))
            player2.setDirection(Snake.Direction.LEFT);
        else if (Gdx.input.isKeyJustPressed(Input.Keys.D))
            player2.setDirection(Snake.Direction.RIGHT);
    }

    /**
     * {@inheritDoc}
     * Executes the gameplay logic loop, collision checks, and render calls.
     */
    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.P) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pauseMenu.togglePause();
        }

        handleInput();

        if (!pauseMenu.isPaused()) {
            if (!player1.isDead() && !player2.isDead()) {

                player1.update(delta);
                player2.update(delta);
                apple.update(delta);

                // Cross-snake collision
                Snake.SnakeSegment p1Head = player1.getBody().getFirst();
                Snake.SnakeSegment p2Head = player2.getBody().getFirst();

                if (p1Head.x == p2Head.x && p1Head.y == p2Head.y) {
                    player1.kill();
                    player2.kill();
                } else {
                    for (Snake.SnakeSegment segment : player2.getBody()) {
                        if (p1Head.x == segment.x && p1Head.y == segment.y)
                            player1.kill();
                    }
                    for (Snake.SnakeSegment segment : player1.getBody()) {
                        if (p2Head.x == segment.x && p2Head.y == segment.y)
                            player2.kill();
                    }
                }

                // Food collision logic
                if (!player1.isDead()) {
                    if (p1Head.x == apple.getX() && p1Head.y == apple.getY()) {
                        soundManager.playBiteSound();
                        player1.modifySize(apple.getSizeChange());

                        scoreBoard.addScoreP1(apple.getPoints());

                        spawnNewApple();
                    }
                }

                if (!player2.isDead()) {
                    if (p2Head.x == apple.getX() && p2Head.y == apple.getY()) {
                        soundManager.playBiteSound();
                        player2.modifySize(apple.getSizeChange());

                        // Locks score at 0 minimum
                        scoreBoard.addScoreP2(apple.getPoints());

                        spawnNewApple();
                    }
                }

            } else {
                // Game Over State
                soundManager.stopBackgroundMusic();

                if (player1.didDieBySuicide()) {
                    scoreBoard.addScoreP1(-PENALTY_SUICIDE);
                }
                if (player2.didDieBySuicide()) {
                    scoreBoard.addScoreP2(-PENALTY_SUICIDE);
                }
                game.setScreen(new GameOverScreen(game, scoreBoard.getScoreP1(), scoreBoard.getScoreP2()));
                dispose();
                return;
            }
        }

        // Rendering
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.setColor(Color.WHITE);

        int gridWidth = (int) (V_WIDTH / game.settings.getTileSize());
        int gridHeight = (int) (V_HEIGHT / game.settings.getTileSize());

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if ((x + y) % 2 == 0) {
                    batch.draw(tileTex1, x * game.settings.getTileSize(), y * game.settings.getTileSize(),
                            game.settings.getTileSize(),
                            game.settings.getTileSize());
                } else {
                    batch.draw(tileTex2, x * game.settings.getTileSize(), y * game.settings.getTileSize(),
                            game.settings.getTileSize(),
                            game.settings.getTileSize());
                }
            }
        }
        // Chooses the correct texture based on the apple type
        Texture currentAppleTex;
        switch (apple.getType()) {
            case GOLDEN:
                currentAppleTex = goldenTex;
                break;
            case ROTTEN:
                currentAppleTex = rottenTex;
                break;
            case NORMAL:
            default:
                currentAppleTex = fruitTex;
                break;
        }

        // Draws the selected texture
        batch.draw(currentAppleTex, apple.getX() * game.settings.getTileSize(),
                apple.getY() * game.settings.getTileSize(),
                game.settings.getTileSize(), game.settings.getTileSize());

        batch.setColor(Color.WHITE);

        drawSnake(player1);
        drawSnake(player2);
        scoreBoard.draw(batch);
        boolean wantsToQuit = pauseMenu.updateAndDraw(batch, V_WIDTH, V_HEIGHT);

        batch.end();

        if (wantsToQuit) {
            soundManager.stopBackgroundMusic();
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    /**
     * Spawns a new fruit on the grid, applying probabilistic logic for special
     * apple types.
     */
    private void spawnNewApple() {
        // Roll a random number between 0 and 99
        int chance = random.nextInt(100);

        // Apply probability logic to determine apple type
        if (chance < CHANCE_GOLDEN) {
            apple.respawnAs(Food.AppleType.GOLDEN);
        } else if (chance < (CHANCE_GOLDEN + CHANCE_ROTTEN)) {
            apple.respawnAs(Food.AppleType.ROTTEN);
        } else {
            apple.respawn();
        }
    }

    /**
     * Calculates the rotation angle required for the snake's tail based on its
     * preceding segment.
     *
     * @param from The current segment (tail).
     * @param to   The segment immediately preceding the tail.
     * @return The rotation angle in degrees.
     */
    private float getDirectionRotation(Snake.SnakeSegment from, Snake.SnakeSegment to) {
        if (to.x > from.x)
            return 0f;
        if (to.x < from.x)
            return 180f;
        if (to.y > from.y)
            return 90f;
        if (to.y < from.y)
            return 270f;
        return 0f;
    }

    /**
     * Calculates the rotation angle for snake corner segments to ensure smooth
     * visuals.
     * * @param front The segment in front of the corner.
     *
     * @param current The corner segment itself.
     * @param back    The segment behind the corner.
     * @return The rotation angle in degrees.
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
     * Renders the entire snake body, including the head, tail, and corner segments.
     *
     * @param player The Snake instance to render.
     */

    private void drawSnake(Snake player) {
        batch.setColor(player.getColor());
        LinkedList<Snake.SnakeSegment> body = player.getBody();

        for (int i = 0; i < body.size(); i++) {
            Snake.SnakeSegment segment = body.get(i);
            float drawX = segment.x * game.settings.getTileSize();
            float drawY = segment.y * game.settings.getTileSize();

            TextureRegion regionToDraw;
            float rotation = 0f;
            // Handle Head segment
            if (i == 0) {
                regionToDraw = headRegion;
                Snake.SnakeSegment head = body.get(0);

                // Fixes rendering crash when snake size is 1
                if (body.size() > 1) {
                    Snake.SnakeSegment next = body.get(1);
                    if (next.x > head.x)
                        rotation = 180f;
                    else if (next.x < head.x)
                        rotation = 0f;
                    else if (next.y > head.y)
                        rotation = 270f;
                    else
                        rotation = 90f;
                } else {
                    switch (player.getCurrentDirection()) {
                        case RIGHT:
                            rotation = 0f;
                            break;
                        case LEFT:
                            rotation = 180f;
                            break;
                        case UP:
                            rotation = 90f;
                            break;
                        case DOWN:
                            rotation = 270f;
                            break;
                    }
                }

                // Handle Tail segment
            } else if (i == body.size() - 1) {
                regionToDraw = tailRegion;
                Snake.SnakeSegment front = body.get(i - 1);
                rotation = getDirectionRotation(segment, front);
            }

            // Handle Body and Corner segments
            else {
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

            batch.draw(regionToDraw, drawX, drawY, game.settings.getTileSize() / 2f, game.settings.getTileSize() / 2f,
                    game.settings.getTileSize(), game.settings.getTileSize(), 1f, 1f, rotation);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    /**
     * {@inheritDoc}
     * Disposes of texture resources and dependencies to free system memory.
     */
    @Override
    public void dispose() {
        headTex.dispose();
        bodyTex.dispose();
        tailTex.dispose();
        cornerTex.dispose();
        fruitTex.dispose();
        goldenTex.dispose();
        rottenTex.dispose();
        tileTex1.dispose();
        tileTex2.dispose();
        scoreBoard.dispose();
        pauseMenu.dispose();

        if (soundManager != null) {
            soundManager.dispose();
        }

    }
}
