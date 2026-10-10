/**
 * @author: Andrés Gerardo González
 * */
// Package
package com.project2.project2fpoe.model;

/**
 * Difficulty enum to represent the difficulty levels of the Sudoku game.
 * Each level defines how many predefined numbers are placed in every block.
 */
public enum Difficulty {
    EASY("Fácil", 5),
    MEDIUM("Medio", 4),
    HARD("Difícil", 3);

    // Attributes
    private final String label; // Name shown to the user
    private final int cluesPerBlock; // Predefined numbers per block

    Difficulty(String label, int cluesPerBlock) {
        this.label = label;
        this.cluesPerBlock = cluesPerBlock;
    }

    public String getLabel() {
        return label; // Return the name shown to the user
    }

    public int getCluesPerBlock() {
        return cluesPerBlock; // Return the predefined numbers per block
    }
}
