/**
 * @author Andrés Gerardo González
 */
// package
package com.project2.project2fpoe;

// Imports
import com.project2.project2fpoe.view.StageSudoku;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main class to launch the Sudoku application.
 * This class initializes the JavaFX application and sets up the main stage.
 */
public class Main extends Application {

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start method to set up the main stage of the application.
     *
     * @param primaryStage the primary stage for this application
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Create an instance of StageSudoku to set up the main stage
        new StageSudoku();
    }
}