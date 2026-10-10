/**
 * @author: Andrés Gerardo González
 * */
// Package
package com.project2.project2fpoe.controller;

// Imports
import com.project2.project2fpoe.model.Difficulty;
import com.project2.project2fpoe.model.Sudoku;
import com.project2.project2fpoe.view.StageStart;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * SudokuController class to handle the Sudoku game logic and UI interactions.
 * This class initializes the Sudoku board, handles user input, and provides hints.
 * It also manages the game state and displays messages to the user.
 * */
public class SudokuController {

    /**
     * FXML elements for the Sudoku game.
     * These elements are defined in the FXML file and are used to create the Sudoku UI.
     * */
    @FXML
    private GridPane sudokuGrid; // GridPane to hold the Sudoku cells

    @FXML
    private Button helpButton; // Button to provide hints

    @FXML
    private Button menuButton; // Button to go back to the start screen

    @FXML
    private Label bottomLabel; // Label to display messages to the user

    private Sudoku sudoku; // Sudoku game logic

    /**
     * Starts the Sudoku game by generating a playable board and setting up the UI.
     * This method is called by the stage after the FXML file is loaded.
     *
     * @param difficulty the difficulty that defines how many numbers are predefined
     * @param hintsEnabled true to show the hint button, false to hide it
     * */
    public void startGame(Difficulty difficulty, boolean hintsEnabled) {
        sudoku = new Sudoku(difficulty); // Create a new Sudoku instance
        sudoku.generatePlayableBoard(); // Generate a playable Sudoku board
        createGrid(); // Create the grid of TextFields for the Sudoku cells
        helpButton.setOnAction(e -> showHint()); // Set up the hint button action
        helpButton.setVisible(hintsEnabled); // Hide the hint button if hints are disabled
        helpButton.setManaged(hintsEnabled); // Do not reserve space for a hidden button
        menuButton.setOnAction(e -> backToMenu()); // Set up the menu button action
    }

    /**
     * Opens the start screen and closes the current game, which is discarded.
     * This method is called when the user clicks the menu button.
     * */
    private void backToMenu() {
        try {
            new StageStart(); // Open the start screen
            ((Stage) menuButton.getScene().getWindow()).close(); // Close the game
        } catch (IOException e) {
            throw new RuntimeException("No se pudo abrir el menú", e);
        }
    }

    /**
     * Creates the grid of TextFields for the Sudoku cells.
     * Each cell is represented by a TextField, and listeners are added to handle user input.
     * */
    private void createGrid() {
        int size = sudoku.getSize();
        for (int row = 0; row < size; row++) { // Loop through rows
            for (int col = 0; col < size; col++) { // Loop through columns

                // Create a TextField for each cell
                TextField cell = new TextField();
                cell.setPrefSize(50, 50);
                cell.getStyleClass().add("sudokuCell");

                // Add extra space before each cell that starts a new block
                boolean startsBlockRow = row != 0 && row % sudoku.getBoxRows() == 0;
                boolean startsBlockCol = col != 0 && col % sudoku.getBoxCols() == 0;
                GridPane.setMargin(cell, new Insets(startsBlockRow ? 6 : 0, 0, 0, startsBlockCol ? 6 : 0));

                // Set the cell's number from the original board
                int finalRow = row;
                int finalCol = col;
                int cellValue = sudoku.getBoard().get(row).get(col);

                // Check if the cell is predefined (not empty)
                if (cellValue != 0) {
                    // Set the cell's text and make it non-editable
                    cell.setText(String.valueOf(cellValue));
                    cell.setEditable(false);
                    cell.getStyleClass().add("predefined");
                } else {
                    // Set the cell's text to empty and add a listener for user input
                    cell.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue.matches("[1-9]?")) { // Check if the input is valid (1-9)
                            if (!newValue.isEmpty()) { // If not empty, parse the value
                                int value = Integer.parseInt(newValue); // Parse the input value
                                if (!sudoku.setCell(finalRow, finalCol, value)) { // Check if the move is valid
                                    cell.setStyle("-fx-background-color: #7b1e3a; -fx-border-color: #7b1e3a;"); // Set error style
                                    bottomLabel.setText("Número inválido o no correspondiente en el lugar"); // Display error message
                                    bottomLabel.setTextFill(Color.web("#c0476a")); // Set the label color to wine
                                } else {
                                    cell.setStyle("-fx-background-color: #b99cff;"); // Set valid style
                                    bottomLabel.setText("Juega hasta completar el sudoku"); // Reset to default message
                                    bottomLabel.setTextFill(Color.web("#9b6bff")); // Set the label color to default
                                    if (sudoku.isSolved()) { // Check if the Sudoku is solved
                                        disableSudoku(); // Disable the Sudoku board
                                    }
                                }
                            } else {
                                sudoku.getBoard().get(finalRow).set(finalCol, 0); // If empty, reset the cell
                            }
                        } else {
                            cell.setText(""); // If invalid input, clear the cell
                        }
                    });
                }

                // Add a listener for key events to navigate through the grid
                cell.setOnKeyPressed(event -> {
                    switch (event.getCode()) { // Handle key events
                        case UP -> moveFocus(finalRow - 1, finalCol); // Move focus up
                        case DOWN -> moveFocus(finalRow + 1, finalCol); // Move focus down
                        case LEFT -> moveFocus(finalRow, finalCol - 1); // Move focus left
                        case RIGHT -> moveFocus(finalRow, finalCol + 1); // Move focus right
                    }
                });

                sudokuGrid.add(cell, col, row); // Add the cell to the grid
            }
        }
    }

    /**
     * Moves the focus to the specified cell in the Sudoku grid.
     * This method is called when the user presses arrow keys to navigate through the grid.
     *
     * @param row the row index of the cell to focus on
     * @param col the column index of the cell to focus on
     */
    private void moveFocus(int row, int col) {
        int size = sudoku.getSize();
        if (row >= 0 && row < size && col >= 0 && col < size) { // Check if the indices are within bounds
            for (javafx.scene.Node node : sudokuGrid.getChildren()) { // Loop through the grid nodes
                // Check if the node is a TextField and matches the specified row and column
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof TextField textField) {
                    textField.requestFocus(); // Request focus on the specified cell
                    break; // Exit the loop once the cell is found
                }
            }
        }
    }

    /**
     * Disables the Sudoku board and displays a success message.
     * This method is called when the Sudoku is solved.
     * */
    public void disableSudoku() {
        for (javafx.scene.Node node : sudokuGrid.getChildren()) { // Loop through the grid nodes
            if (node instanceof TextField textField) { // Check if the node is a TextField
                textField.setEditable(false); // Make the cell non-editable
                textField.setStyle("-fx-background-color: #b99cff; -fx-text-fill: white;"); // Set the cell style
                helpButton.setDisable(true); // Disable the hint button
            }
        }
        bottomLabel.setText("¡Felicidades! Has resuelto el Sudoku."); // Display success message
        bottomLabel.setTextFill(Color.LIGHTGREEN); // Set the label color to light green
    }

    /**
     * Provides a hint for the Sudoku game by filling an empty cell with a move that keeps the board
     * resolvable. If the numbers placed so far have no solution, a message is shown instead.
     * This method is called when the user clicks the hint button.
     * */
    private void showHint() {
        int[] hint = sudoku.findHint(); // Get {row, col, value} of a move that keeps the board resolvable
        if (hint == null) { // The current board has no solution
            bottomLabel.setText("No hay solución con estos números, borra alguno"); // Display warning message
            bottomLabel.setTextFill(Color.web("#c0476a")); // Set the label color to wine
            return;
        }

        int row = hint[0];
        int col = hint[1];
        for (javafx.scene.Node node : sudokuGrid.getChildren()) { // Loop through the grid nodes
            // Check if the node is a TextField and matches the hint's row and column
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof TextField textField) {
                textField.setText(String.valueOf(hint[2])); // Set the cell's text to the hint value
                if (!sudoku.isSolved()) { // Keep the solved style if the hint completed the Sudoku
                    textField.setStyle("-fx-background-color: #a57dff; -fx-text-fill: white;"); // Set the cell style
                }
                return; // Exit the method after providing a hint
            }
        }
    }
}