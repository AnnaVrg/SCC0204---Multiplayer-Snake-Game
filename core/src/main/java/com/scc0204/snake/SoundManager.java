package com.scc0204.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private Music backgroundMusic;
    private Sound biteSound;
    private Sound deathSound;

    public SoundManager() {
        // Carrega os sons para a memória apenas uma vez
        try {
            if (Gdx.files.internal("AppleBite.WAV").exists()) {
                biteSound = Gdx.audio.newSound(Gdx.files.internal("AppleBite.WAV"));
            }
            if (Gdx.files.internal("Death.WAV").exists()) {
                deathSound = Gdx.audio.newSound(Gdx.files.internal("Death.WAV"));
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar os efeitos sonoros: " + e.getMessage());
        }
    }

    public void playBiteSound() {
        if (biteSound != null)
            biteSound.play();
    }

    public void playDeathSound() {
        if (deathSound != null)
            deathSound.play();
    }

    public void playBackgroundMusic(String filePath) {
        try {
            if (Gdx.files.internal(filePath).exists()) {
                backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(filePath));
                backgroundMusic.setLooping(true);
                backgroundMusic.play();
            }
        } catch (Exception e) {
            System.err.println("Erro ao tocar música de fundo: " + e.getMessage());
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        }
    }

    // Método super importante para liberar a RAM quando o jogo fechar
    public void dispose() {
        if (biteSound != null)
            biteSound.dispose();
        if (deathSound != null)
            deathSound.dispose();
        if (backgroundMusic != null)
            backgroundMusic.dispose();
    }
}
