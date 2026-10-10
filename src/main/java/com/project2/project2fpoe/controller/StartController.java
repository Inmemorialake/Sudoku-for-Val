/**
 * @author: Andrés Gerardo González
 * */
// Package
package com.project2.project2fpoe.controller;

// Imports
import com.project2.project2fpoe.model.Difficulty;
import com.project2.project2fpoe.view.StageSudoku;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * StartController class to handle the start screen of the game.
 * This class reads the chosen difficulty and hints option and opens the Sudoku game.
 * */
public class StartController implements Initializable {

    /**
     * FXML elements for the start screen.
     * These elements are defined in the FXML file.
     * */
    @FXML
    private ToggleGroup difficultyGroup; // Group that keeps only one difficulty selected

    @FXML
    private ToggleButton easyButton; // Button to choose the easy difficulty

    @FXML
    private ToggleButton hardButton; // Button to choose the hard difficulty

    @FXML
    private CheckBox hintsCheckBox; // CheckBox to enable or disable the hints

    @FXML
    private Button playButton; // Button to start the game

    /**
     * Initializes the start screen.
     * This method is called when the FXML file is loaded.
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Keep a difficulty always selected, even if the selected button is clicked again
        difficultyGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                difficultyGroup.selectToggle(oldValue);
            }
        });
        playButton.setOnAction(e -> startGame()); // Set up the play button action
    }

    /**
     * Opens the Sudoku game with the chosen settings and closes the start screen.
     * This method is called when the user clicks the play button.
     * */
    private void startGame() {
        // Read the chosen difficulty
        Difficulty difficulty = Difficulty.MEDIUM;
        if (easyButton.isSelected()) {
            difficulty = Difficulty.EASY;
        } else if (hardButton.isSelected()) {
            difficulty = Difficulty.HARD;
        }

        try {
            new StageSudoku(difficulty, hintsCheckBox.isSelected()); // Open the game
            ((Stage) playButton.getScene().getWindow()).close(); // Close the start screen
        } catch (IOException e) {
            throw new RuntimeException("No se pudo abrir el juego", e);
        }
    }
}
