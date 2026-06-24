package com.scc0204.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Utility class responsible for managing persistent high score data.
 * Handles reading from and writing to external storage, ensuring scores
 * are sorted and limited to a predefined maximum capacity.
 */
public class HighScoreManager {

    private static final String FILE_NAME = "highscores.txt";
    private static final int MAX_SCORES = 5;

    /**
     * Represents a single high score entry, containing the player's name,
     * achieved score, and the date of achievement.
     */
    public static class ScoreEntry implements Comparable<ScoreEntry> {
        public int score;
        public String name;
        public String date;

        public ScoreEntry(int score, String name, String date) {
            this.score = score;
            this.name = name;
            this.date = date;
        }

        /**
         * Compares this entry with another based on score for descending order.
         * {@inheritDoc}
         */
        @Override
        public int compareTo(ScoreEntry other) {
            // Sort descending (highest score first)
            return Integer.compare(other.score, this.score);
        }
    }

    /**
     * Retrieves the current list of high scores from persistent storage.
     *
     * @return An {@link ArrayList} of {@link ScoreEntry} objects, sorted by score.
     */
    public static ArrayList<ScoreEntry> getHighScores() {
        ArrayList<ScoreEntry> entries = new ArrayList<>();
        FileHandle file = Gdx.files.external(FILE_NAME); // Always use external for safety

        if (file.exists()) {
            String text = file.readString();
            String[] lines = text.split("\\r?\\n");

            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(","); // Split the line by commas

                    // Ensure the line has exactly 3 parts: score, name, date
                    if (parts.length == 3) {
                        try {
                            int score = Integer.parseInt(parts[0].trim());
                            entries.add(new ScoreEntry(score, parts[1].trim(), parts[2].trim()));
                        } catch (NumberFormatException e) {
                            // Ignore corrupted lines
                        }
                    }
                }
            }
        }
        return entries;
    }

    /**
     * Determines if a given score qualifies for the top high score board.
     * * @param score The score to evaluate.
     *
     * @return True if the score qualifies for the top 5, false otherwise.
     */
    public static boolean isHighScore(int score) {
        if (score <= 0)
            return false;

        // Board has space for new entries
        ArrayList<ScoreEntry> scores = getHighScores();
        if (scores.size() < MAX_SCORES)
            return true;

        // Return true if the score beats the lowest score on the board
        return score > scores.get(scores.size() - 1).score;
    }

    /**
     * Records a new score, formats the current date, and updates the persistent
     * storage.
     * * @param score The score to be saved.
     *
     * @param name The player's name.
     */
    public static void addScore(int score, String name) {
        if (score <= 0)
            return;

        ArrayList<ScoreEntry> scores = getHighScores();

        // Get today's date formatted as DD/MM/YYYY
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Add new entry and sort
        scores.add(new ScoreEntry(score, name, today));
        Collections.sort(scores);

        // Enforce the maximum score limit
        if (scores.size() > MAX_SCORES) {
            scores = new ArrayList<>(scores.subList(0, MAX_SCORES));
        }

        // Rebuild the file content using commas
        StringBuilder sb = new StringBuilder();
        for (ScoreEntry entry : scores) {
            sb.append(entry.score).append(",")
                    .append(entry.name).append(",")
                    .append(entry.date).append("\n");
        }

        FileHandle file = Gdx.files.external(FILE_NAME);
        file.writeString(sb.toString(), false);
    }
}
