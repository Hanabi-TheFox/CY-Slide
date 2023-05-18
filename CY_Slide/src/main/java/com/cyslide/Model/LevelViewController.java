/*package com.cyslide.Model;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class LevelViewController {
    @FXML
    private GridPane gridPane;

    @FXML
    private Tile[][] matrix; // Matrix of tiles

    private int counter = 1; // Compter for tiles
    
    
    @FXML
    protected void initialize() {
        //TODO

    }

    // Method for creating the table of the game
    private void initializeGridPane() {
        int size = matrix.length; // Matrix Size

        gridPane.getChildren().clear(); // clean the gridpane

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                //int tileValue = matrix[row][col];

                Label label = new Label(); // New Label creation for the tile
                label.getStyleClass().add("tile"); // Appliquer les styles CSS pour la tuile

                if (tileValue > 0) {
                    // La tuile est numérotée, lui attribuer la valeur et incrémenter le compteur
                    label.setText(String.valueOf(counter));
                    counter++;
                } else {
                    // La tuile est vide, lui attribuer une chaîne vide
                    label.setText("");
                }

                // Ajouter le label à la grille à la position (row, col)
                gridPane.add(label, col, row);
            }
        }
    }
}*/
