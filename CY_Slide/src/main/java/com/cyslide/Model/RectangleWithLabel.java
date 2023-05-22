package com.cyslide.Model;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;


public class RectangleWithLabel extends StackPane {
    private Tile tile;
    public RectangleWithLabel(double width, double height, String labelText, Tile t1, int size) {
        Rectangle rectangle = new Rectangle(width-1, height-1);
        rectangle.setStroke(Color.BLACK);
        if (t1.getType() == 1){ // if it's a number tile 
            rectangle.setFill(Color.DODGERBLUE);
        }else if (t1.getType() == 0){ // if it's an indestructible tile
            rectangle.setFill(null);
            rectangle.setStroke(null);
            rectangle.setWidth(width);
            rectangle.setHeight(height);
        }else { // if it's an empty tile
            rectangle.setFill(Color.DARKGRAY);
        }
        this.tile=t1;
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size:"+120/size +"px;");
        getChildren().addAll(rectangle, label);
    }
    public Tile GetTile(){
        return this.tile;
    }
}
