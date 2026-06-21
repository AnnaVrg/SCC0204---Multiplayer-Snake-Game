package com.scc0204.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Manages the audio playback using LibGDX native audio system.
 * Includes safety checks to prevent crashes if files are missing.
 */
public class SoundManager {

    private Music backgroundMusic;

    /**
     * Plays a sound effect exactly once. 
     * @param filePath Name of the file inside the assets folder (e.g., "bite.wav")
     */
    public void playBiteSound(String filePath) {
        try {
            // SAFETY CHECK: Only tries to load the file if it exists in the assets folder
            if (Gdx.files.internal(filePath).exists()) {
                Sound biteSound = Gdx.audio.newSound(Gdx.files.internal(filePath));
                biteSound.play();
            } else {
                System.out.println("Audio file missing, skipping: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("Error playing sound effect: " + e.getMessage());
        }
    }

    /**
     * Starts playing the background music in a continuous loop.
     * @param filePath Name of the file inside the assets folder (e.g., "bgm.wav")
     */
    public void playBackgroundMusic(String filePath) {
        try {
            // SAFETY CHECK: Only tries to load the file if it exists
            if (Gdx.files.internal(filePath).exists()) {
                backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(filePath));
                backgroundMusic.setLooping(true); // Loop indefinitely
                backgroundMusic.play();
            } else {
                System.out.println("Music file missing, skipping: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("Error playing background music: " + e.getMessage());
        }
    }

    /**
     * Stops and disposes of the background music.
     */
    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
            backgroundMusic.dispose(); 
        }
    }
}