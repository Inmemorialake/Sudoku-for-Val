/**
 * @author: Andrés Gerardo González
 * */
// Package
package com.project2.project2fpoe.controller;

// Imports
import com.project2.project2fpoe.model.Sudoku;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * SudokuController class to handle the Sudoku game logic and UI interactions.
 * This class initializes the Sudoku board, handles user input, and provides hints.
 * It also manages the game state and displays messages to the user.
 * */
public class SudokuController implements Initializable {

    /**
     * FXML elements for the Sudoku game.
     * These elements are defined in the FXML file and are used to create the Sudoku UI.
     * */
    @FXML
    private GridPane sudokuGrid; // GridPane to hold the Sudoku cells

    @FXML
    private Button helpButton; // Button to provide hints

    @FXML
    private Label bottomLabel; // Label to display messages to the user

    private Sudoku sudoku; // Sudoku game logic

    /**
     * Initializes the Sudoku game by generating a playable board and setting up the UI.
     * This method is called when the FXML file is loaded.
     * */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sudoku = new Sudoku(); // Create a new Sudoku instance
        sudoku.generatePlayableBoard(); // Generate a playable Sudoku board
        createGrid(); // Create the grid of TextFields for the Sudoku cells
        helpButton.setOnAction(e -> showHint()); // Set up the hint button action
    }

    /**
     * Creates the grid of TextFields for the Sudoku cells.
     * Each cell is represented by a TextField, and listeners are added to handle user input.
     * */
    private void createGrid() {
        for (int row = 0; row < 6; row++) { // Loop through rows
            for (int col = 0; col < 6; col++) { // Loop through columns

                // Create a TextField for each cell
                TextField cell = new TextField();
                cell.setPrefSize(60, 60);
                cell.getStyleClass().add("sudokuCell");

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
                        if (newValue.matches("[1-6]?")) { // Check if the input is valid (1-6)
                            if (!newValue.isEmpty()) { // If not empty, parse the value
                                int value = Integer.parseInt(newValue); // Parse the input value
                                if (!sudoku.setCell(finalRow, finalCol, value)) { // Check if the move is valid
                                    cell.setStyle("-fx-background-color: #ff0000; -fx-border-color: #ff0000;"); // Set error style
                                    bottomLabel.setText("Número inválido o no correspondiente en el lugar"); // Display error message
                                    bottomLabel.setTextFill(Color.RED); // Set the label color to red
                                } else {
                                    cell.setStyle("-fx-background-color: #90cbff;"); // Set valid style
                                    bottomLabel.setText("Juega hasta completar el sudoku"); // Reset to default message
                                    bottomLabel.setTextFill(Color.web("#358bfc")); // Set the label color to default
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
        if (row >= 0 && row < 6 && col >= 0 && col < 6) { // Check if the indices are within bounds
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
                textField.setStyle("-fx-background-color: #90cbff; -fx-text-fill: white;"); // Set the cell style
                helpButton.setDisable(true); // Disable the hint button
            }
        }
        bottomLabel.setText("¡Felicidades! Has resuelto el Sudoku."); // Display success message
        bottomLabel.setTextFill(Color.LIGHTGREEN); // Set the label color to light green
    }

    /**
     * Provides a hint for the Sudoku game by filling in a valid number in an empty cell.
     * This method is called when the user clicks the hint button.
     * */
    private void showHint() {
        for (int row = 0; row < 6; row++) { // Loop through rows
            for (int col = 0; col < 6; col++) { // Loop through columns
                if (sudoku.getBoard().get(row).get(col) == 0) { // Check if the cell is empty
                    for (int value = 1; value <= 6; value++) { // Loop through possible values (1-6)
                        if (sudoku.isValidMove(sudoku.getBoard() ,row, col, value)) { // Check if the move is valid
                            for (javafx.scene.Node node : sudokuGrid.getChildren()) { // Loop through the grid nodes
                                // Check if the node is a TextField and matches the specified row and column
                                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof TextField textField) {
                                    textField.setText(String.valueOf(value)); // Set the cell's text to the hint value
                                    textField.setStyle("-fx-background-color: #4da6ff; -fx-text-fill: white;"); // Set the cell style
                                }
                            }
                            return; // Exit the method after providing a hint
                        }
                    }
                }
            }
        }
    }
}