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
     * Plays the food eating sound effect exactly once. 
     * @param filePath Name of the file inside the assets folder
     */
    public void playBiteSound(String filePath) {
        try {
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
     * MODIFIED: Plays the death/collision sound exactly once.
     * @param filePath Name of the file inside the assets folder (e.g., "Death.WAV")
     */
    public void playDeathSound(String filePath) {
        try {
            if (Gdx.files.internal(filePath).exists()) {
                Sound deathSound = Gdx.audio.newSound(Gdx.files.internal(filePath));
                deathSound.play();
            } else {
                System.out.println("Death sound file missing, skipping: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("Error playing death sound: " + e.getMessage());
        }
    }

    /**
     * Starts playing the background music in a continuous loop.
     * @param filePath Name of the file inside the assets folder
     */
    public void playBackgroundMusic(String filePath) {
        try {
            if (Gdx.files.internal(filePath).exists()) {
                backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(filePath));
                backgroundMusic.setLooping(true); 
                backgroundMusic.play();
            } else {
                System.out.println("Music file missing, skipping: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("Error playing background music: " + e.getMessage());
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
            backgroundMusic.dispose(); 
        }
    }
}
