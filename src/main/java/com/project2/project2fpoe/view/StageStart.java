/**
 * @author Andrés Gerardo González
 * */
// Package
package com.project2.project2fpoe.view;

// Imports
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * StageStart class to create the start screen of the Sudoku application.
 * This class sets up the stage where the player chooses the difficulty and the hints.
 */
public class StageStart extends Stage {

    /**
     * Constructor to initialize the start stage.
     *
     * @throws IOException if an I/O error occurs while loading the FXML file
     */
    public StageStart() throws IOException {
        // Load the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/project2/project2fpoe/StartScreen.fxml"));
        Parent root = fxmlLoader.load();

        // Set the icon of the app
        getIcons().add(new Image(getClass().getResourceAsStream("/com/project2/project2fpoe/Sudoku icon.png")));

        // Set the title and scene of the stage
        Scene scene = new Scene(root);
        setTitle("Val's Sudoku");
        setScene(scene);

        // Set the size and position of the stage
        setResizable(false);

        // Show the stage
        show();
    }
}
