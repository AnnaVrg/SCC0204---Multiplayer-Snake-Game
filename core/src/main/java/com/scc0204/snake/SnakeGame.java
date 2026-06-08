package com.scc0204.snake;

import com.badlogic.gdx.Game;

public class SnakeGame extends Game {
    @Override
    public void create() {
        // Ao iniciar o jogo, chamamos direto a GameScreen para testes
        setScreen(new GameScreen());
    }
}
