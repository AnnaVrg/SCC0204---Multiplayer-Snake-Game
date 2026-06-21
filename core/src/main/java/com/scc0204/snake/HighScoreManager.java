package com.scc0204.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Utility class to handle File I/O for high scores.
 * Now supports Score, Name, and Date separated by commas.
 */
public class HighScoreManager {

  private static final String FILE_NAME = "highscores.txt";
  private static final int MAX_SCORES = 5;

  /**
   * Inner class representing a single high score record.
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

    @Override
    public int compareTo(ScoreEntry other) {
      // Sort descending (highest score first)
      return Integer.compare(other.score, this.score);
    }
  }

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
   * Checks if a given score is high enough to enter the Top 5.
   */
  public static boolean isHighScore(int score) {
    if (score <= 0)
      return false;

    ArrayList<ScoreEntry> scores = getHighScores();
    if (scores.size() < MAX_SCORES)
      return true; // Board is not full yet

    // Return true if the score beats the lowest score on the board
    return score > scores.get(scores.size() - 1).score;
  }

  /**
   * Formats the current date and saves the new record to the file.
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

    // Keep only top 5
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
