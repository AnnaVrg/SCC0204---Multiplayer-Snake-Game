package com.scc0204.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Manages all game audio assets, including sound effects and background music.
 * This class ensures that audio resources are loaded into memory efficiently
 * and provides methods to trigger them during gameplay.
 */

public class SoundManager {
    private Music backgroundMusic;
    private Sound biteSound;
    private Sound deathSound;

    /**
     * Initializes the SoundManager by pre-loading sound effects into memory.
     */

    public SoundManager() {
        try {
            if (Gdx.files.internal("AppleBite.WAV").exists()) {
                biteSound = Gdx.audio.newSound(Gdx.files.internal("AppleBite.WAV"));
            }
            if (Gdx.files.internal("Death.WAV").exists()) {
                deathSound = Gdx.audio.newSound(Gdx.files.internal("Death.WAV"));
            }
        } catch (Exception e) {
            System.err.println("Failed to load sound effects: " + e.getMessage());
        }
    }

    /**
     * Plays the bite sound effect when the snake consumes food.
     */
    public void playBiteSound() {
        if (biteSound != null)
            biteSound.play();
    }

    /**
     * Plays the death sound effect when the snake collides with an obstacle.
     */
    public void playDeathSound() {
        if (deathSound != null)
            deathSound.play();
    }

    /**
     * Loads and starts playing a background music track.
     *
     * @param filePath The internal path to the music file.
     */
    public void playBackgroundMusic(String filePath) {
        try {
            if (Gdx.files.internal(filePath).exists()) {
                backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(filePath));
                backgroundMusic.setLooping(true);
                backgroundMusic.play();
            }
        } catch (Exception e) {
            System.err.println("Failed to play background music: " + e.getMessage());
        }
    }

    /**
     * Stops the currently playing background music if it is active.
     */
    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        }
    }

    /**
     * Disposes of all audio assets to free up system memory.
     * Should be called when the game or screen is destroyed.
     */
    public void dispose() {
        if (biteSound != null)
            biteSound.dispose();
        if (deathSound != null)
            deathSound.dispose();
        if (backgroundMusic != null)
            backgroundMusic.dispose();
    }
}
