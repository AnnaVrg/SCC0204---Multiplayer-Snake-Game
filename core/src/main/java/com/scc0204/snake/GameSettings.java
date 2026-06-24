package com.scc0204.snake;

/**
 * Global settings configuration for the game.
 * Now completely instantiable to ensure testability and prevent global state
 * leaks.
 */
public class GameSettings {

    // Variáveis agora são privadas e instanciáveis (não estáticas)
    private int tileSize = 32;
    private float startingSpeed = 0.15f;

    // Getters para acessar os valores com segurança
    public int getTileSize() {
        return tileSize;
    }

    public float getStartingSpeed() {
        return startingSpeed;
    }

    // Os métodos deixam de ser static
    public void toggleGridSize() {
        if (tileSize == 32)
            tileSize = 16;
        else if (tileSize == 16)
            tileSize = 40;
        else
            tileSize = 32;
    }

    public void toggleSpeed() {
        if (startingSpeed == 0.15f)
            startingSpeed = 0.10f;
        else if (startingSpeed == 0.10f)
            startingSpeed = 0.20f;
        else
            startingSpeed = 0.15f;
    }

    public String getGridSizeName() {
        if (tileSize == 40)
            return "SMALL";
        if (tileSize == 32)
            return "NORMAL";
        return "LARGE";
    }

    public String getSpeedName() {
        if (startingSpeed == 0.20f)
            return "SLOW";
        if (startingSpeed == 0.15f)
            return "NORMAL";
        return "FAST";
    }

    // Método extra muito útil para testes unitários: reseta o estado
    public void resetToDefaults() {
        tileSize = 32;
        startingSpeed = 0.15f;
    }
}
