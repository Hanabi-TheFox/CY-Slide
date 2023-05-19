package com.cyslide.Model;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;


public class RectangleWithLabel extends StackPane {
    private Tile tile;
    public RectangleWithLabel(double width, double height, String labelText, Tile t1) {
        Rectangle rectangle = new Rectangle(width, height);
        if (t1.getType() == 1){ // if it's a number tile 
            rectangle.setFill(Color.DODGERBLUE);
        }else if (t1.getType() == 0){ // if it's an indestructible tile
            rectangle.setFill(Color.BLACK);
        }else { // if it's an empty tile
            rectangle.setFill(Color.WHITE);
        }
        this.tile=t1;
        rectangle.setStroke(Color.BLACK);

        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 20px;");

        getChildren().addAll(rectangle, label);
    }
    public Tile GetTile(){
        return this.tile;
    }
}
