/**
 * @author: Andrés Gerardo González
 * */
// Package
package com.project2.project2fpoe.model;

// Imports
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sudoku class to represent the Sudoku game logic.
 * This class handles the generation of the Sudoku board, validation of moves,
 * and checking if the Sudoku is solved.
 */
public class Sudoku {
    // Attributes
    private final List<List<Integer>> board;
    private List<List<Integer>> boardCopy;
    private static final int SIZE = 9;
    private static final int BOX_ROWS = 3; // Rows per block
    private static final int BOX_COLS = 3; // Columns per block
    private static final int CLUES_PER_BLOCK = 4; // Predefined numbers per block
    private static final int MAX_PLACEMENT_ATTEMPTS = 1000; // Attempts before restarting a generation

    /**
     * Constructor to initialize the Sudoku board.
     * The board is a 9x9 grid initialized with zeros.
     */
    public Sudoku() {
        // Initialize the board
        board = new ArrayList<>();
        // Fill the board with zeros
        for (int i = 0; i < SIZE; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                row.add(0);
            }
            board.add(row);
        }
    }

    /**
     * Method to generate a playable Sudoku board.
     * This method fills the board with numbers and checks if the board is resolvable.
     */
    public void generatePlayableBoard() {
        do {
            clearBoard(); // Clear the board before filling
        } while (!fillBlocksWithClues() || !isResolvable()); // Ensure the board was filled and is resolvable

        // Print the generated board
        //printBoard();
        // Print the copy of the board that will be used for solving
        //printBoardCopy();
    }

    /**
     * Method to fill blocks of the Sudoku board with clues.
     * This method randomly selects CLUES_PER_BLOCK cells in each block and fills them with numbers.
     *
     * @return true if every block was filled, false if it got stuck and the generation must restart
     */
    private boolean fillBlocksWithClues() {
        // Iterate through each block in the grid
        for (int blockRow = 0; blockRow < SIZE; blockRow += BOX_ROWS) { // Block rows
            for (int blockCol = 0; blockCol < SIZE; blockCol += BOX_COLS) { // Block columns
                int numbersToPlace = CLUES_PER_BLOCK; // Number of cells to fill in the block
                int attempts = 0; // Attempts made in this block
                while (numbersToPlace > 0) { // While there are numbers to place
                    if (++attempts > MAX_PLACEMENT_ATTEMPTS) { // The block may have no valid placements left
                        return false;
                    }
                    int row = blockRow + (int) (Math.random() * BOX_ROWS); // Random row in the block
                    int col = blockCol + (int) (Math.random() * BOX_COLS); // Random column in the block
                    // Check if the cell is empty and if the number can be placed
                    if (board.get(row).get(col) == 0) {
                        int value = (int) (Math.random() * SIZE) + 1; // Random number between 1 and SIZE
                        if (isValidMove(board ,row, col, value)) { // Check if the move is valid
                            board.get(row).set(col, value); // Place the number
                            numbersToPlace--; // Decrease the count of numbers to place
                        }
                    }
                }
            }
        }
        return true; // All blocks were filled
    }

    /**
     * Method to check if the Sudoku board is resolvable.
     * This method creates a copy of the board and attempts to solve it.
     *
     * @return true if the board is resolvable, false otherwise
     */
    private boolean isResolvable() {
        boardCopy = new ArrayList<>(); // Create a copy of the board for solving without modifying the original
        for (List<Integer> row : board) { // Copy each row of the original board
            List<Integer> newRow = new ArrayList<>(row); // Copy the row
            boardCopy.add(newRow); // Add the copied row to the new board
        }
        return solve();// Attempt to solve the copied board
    }

    /**
     * Method to find a hint for the current state of the board.
     * This method tries every valid move and only accepts one that keeps the board resolvable.
     * If no move does, it checks one last time whether the board is resolvable at all.
     *
     * @return an array {row, col, value} with the hint, or null if the current board has no solution
     */
    public int[] findHint() {
        for (int row = 0; row < SIZE; row++) { // Iterate through each row
            for (int col = 0; col < SIZE; col++) { // Iterate through each column
                if (board.get(row).get(col) == 0) { // Check if the cell is empty
                    for (int value = 1; value <= SIZE; value++) { // Try each value from 1 to SIZE
                        // Accept the move only if it is valid and the board stays resolvable after it
                        if (isValidMove(board, row, col, value) && isResolvableWith(row, col, value)) {
                            return new int[]{row, col, value};
                        }
                    }
                }
            }
        }

        // No move kept the board resolvable: check one last time the board itself
        if (isResolvable()) {
            for (int row = 0; row < SIZE; row++) { // Iterate through each row
                for (int col = 0; col < SIZE; col++) { // Iterate through each column
                    if (board.get(row).get(col) == 0) { // Check if the cell is empty
                        return new int[]{row, col, boardCopy.get(row).get(col)}; // Value from the solution
                    }
                }
            }
        }
        return null; // The numbers placed so far lead to a dead end
    }

    /**
     * Method to check if the board would stay resolvable after a move.
     * The move is placed temporarily and removed after the check.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param value the value to try in the cell
     * @return true if the board is resolvable with the move, false otherwise
     */
    private boolean isResolvableWith(int row, int col, int value) {
        board.get(row).set(col, value); // Place the move temporarily
        boolean resolvable = isResolvable(); // Try to solve the board with the move
        board.get(row).set(col, 0); // Remove the move
        return resolvable;
    }

    /**
     * Method to clear the Sudoku board.
     * This method sets all cells in the board to zero.
     */
    private void clearBoard() {
        for (List<Integer> row : board) { // Clear each row
            Collections.fill(row, 0); // Fill the row with zeros
        }
    }

    public List<List<Integer>> getBoard() {
        return board; // Return the original board
    }

    public int getSize() {
        return SIZE; // Return the number of rows and columns
    }

    public int getBoxRows() {
        return BOX_ROWS; // Return the number of rows per block
    }

    public int getBoxCols() {
        return BOX_COLS; // Return the number of columns per block
    }

    /**
     * Method to set a cell in the Sudoku board.
     * This method checks if the move is valid before placing the number.
     *
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param value the value to place in the cell
     * @return true if the move is valid and successful, false otherwise
     */
    public boolean setCell(int row, int col, int value) {
        if (isValidMove(board ,row, col, value)) { // Check if the move is valid
            board.get(row).set(col, value); // Place the number in the cell
            return true; // Move successful
        }
        return false; // Move failed
    }

    /**
     * Method to check if a move is valid in the Sudoku board.
     * This method checks if the number can be placed in the specified cell without violating Sudoku rules.
     *
     * @param board the Sudoku board
     * @param row the row index of the cell
     * @param col the column index of the cell
     * @param value the value to place in the cell
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(List<List<Integer>> board, int row, int col, int value) {
        // Check if the row and column indices are within bounds
        if (value < 1 || value > SIZE || board.get(row).get(col) != 0) {
            return false; // Invalid value or cell already filled
        }

        // Check if the value already exists in the row, column, or block
        if (board.get(row).contains(value)) {
            return false; // Value already exists in the row
        }
        // Check if the value already exists in the column
        for (int i = 0; i < SIZE; i++) {
            if (board.get(i).get(col) == value) {
                return false;// Value already exists in the column
            }
        }

        // Check if the value already exists in the block
        int blockRowStart = (row / BOX_ROWS) * BOX_ROWS;
        int blockColStart = (col / BOX_COLS) * BOX_COLS;
        for (int i = blockRowStart; i < blockRowStart + BOX_ROWS; i++) {
            for (int j = blockColStart; j < blockColStart + BOX_COLS; j++) {
                if (board.get(i).get(j) == value) {
                    return false; // Value already exists in the block
                }
            }
        }

        return true; // Move is valid
    }

    /**
     * Method to check if the Sudoku board is solved.
     * This method checks if all cells are filled and if the Sudoku rules are satisfied.
     *
     * @return true if the Sudoku is solved, false otherwise
     */
    public boolean isSolved() {
        // Check if all rows are valid
        for (int row = 0; row < SIZE; row++) { // Iterate through each row
            boolean[] seen = new boolean[SIZE + 1]; // Create an array to track seen values
            for (int col = 0; col < SIZE; col++) { // Iterate through each column
                int value = board.get(row).get(col); // Get the value in the cell
                if (value == 0 || seen[value]) { // Check if the value is zero or already seen
                    return false; // Invalid Sudoku
                }
                seen[value] = true; // Mark the value as seen
            }
        }

        // Check if all columns are valid
        for (int col = 0; col < SIZE; col++) { // Iterate through each column
            boolean[] seen = new boolean[SIZE + 1]; // Create an array to track seen values
            for (int row = 0; row < SIZE; row++) { // Iterate through each row
                int value = board.get(row).get(col);// Get the value in the cell
                if (value == 0 || seen[value]) { // Check if the value is zero or already seen
                    return false; // Invalid Sudoku
                }
                seen[value] = true; // Mark the value as seen
            }
        }

        // Check if all blocks are valid
        for (int blockRow = 0; blockRow < SIZE; blockRow += BOX_ROWS) { // Iterate through each block
            for (int blockCol = 0; blockCol < SIZE; blockCol += BOX_COLS) { // Iterate through each block
                boolean[] seen = new boolean[SIZE + 1]; // Create an array to track seen values
                for (int row = blockRow; row < blockRow + BOX_ROWS; row++) { // Iterate through each row in the block
                    for (int col = blockCol; col < blockCol + BOX_COLS; col++) { // Iterate through each column in the block
                        int value = board.get(row).get(col); // Get the value in the cell
                        if (value == 0 || seen[value]) { // Check if the value is zero or already seen
                            return false; // Invalid Sudoku
                        }
                        seen[value] = true; // Mark the value as seen
                    }
                }
            }
        }

        return true; // Sudoku is solved
    }

    /**
     * Method to print the Sudoku board.
     * This method prints the board to the console for debugging purposes.
     */
    public void printBoard() {
        for (List<Integer> row : board) { // Iterate through each row
            for (Integer cell : row) { // Iterate through each cell in the row
                System.out.print(cell + " "); // Print the cell value
            }
            System.out.println(); // Print a new line after each row
        }
    }

    /**
     * Method to print the copied Sudoku board.
     * This method prints the copied board to the console for debugging purposes.
     */
    public void printBoardCopy() {
        for (List<Integer> row : boardCopy) { // Iterate through each row
            for (Integer cell : row) { // Iterate through each cell in the row
                System.out.print(cell + " "); // Print the cell value
            }
            System.out.println(); // Print a new line after each row
        }
    }

    /**
     * Method to solve the Sudoku board using backtracking.
     * This method attempts to fill the board with numbers recursively.
     *
     * @return true if the Sudoku is solved, false otherwise
     */
    public boolean solve() {
        for (int row = 0; row < SIZE; row++) { // Iterate through each row
            for (int col = 0; col < SIZE; col++) { // Iterate through each column
                if (boardCopy.get(row).get(col) == 0) { // Check if the cell is empty
                    for (int value = 1; value <= SIZE; value++) { // Try each value from 1 to SIZE
                        if (isValidMove(boardCopy ,row, col, value)) { // Check if the move is valid
                            boardCopy.get(row).set(col, value); // Place the number
                            if (solve()) { // Recursively attempt to solve the board
                                return true; // If solved, return true
                            }
                            boardCopy.get(row).set(col, 0); // Reset the cell if not solved
                        }
                    }
                    return false; // If no valid number can be placed, return false
                }
            }
        }
        return true; // Sudoku is solved
    }
}