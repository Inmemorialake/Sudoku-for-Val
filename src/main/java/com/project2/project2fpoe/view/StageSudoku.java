/**
 * @author Andrés Gerardo González
 * */
// Package
package com.project2.project2fpoe.view;

// Imports
import com.project2.project2fpoe.controller.SudokuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * StageSudoku class to create the main stage of the Sudoku application.
 * This class sets up the stage with the Sudoku controller and loads the FXML file.
 */
public class StageSudoku extends Stage {

    /**
     * Constructor to initialize the Sudoku stage.
     *
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    public StageSudoku() throws IOException {
        // Load the FXML file and set up the controller
        FXMLLoader fxmlLoader;
        fxmlLoader = new FXMLLoader(getClass().getResource("/com/project2/project2fpoe/Project2FPOE.fxml"));
        SudokuController controller = fxmlLoader.getController();
        Parent root = fxmlLoader.load();

        // Set the icon of the app
        getIcons().add(new Image(getClass().getResourceAsStream("/com/project2/project2fpoe/Sudoku icon.png")));

        // Set the title and scene of the stage
        Scene scene = new Scene(root);
        setTitle("Sudoku");
        setScene(scene);

        // Set the size and position of the stage
        setResizable(false);

        // Show the stage
        show();
    }
}