package com.cyslide.Model;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;

/**
 * A custom JavaFX StackPane that displays a rectangle with a label on top.
 */
public class RectangleWithLabel extends StackPane {
    private Tile tile;

    /**
     * Constructs a RectangleWithLabel object with the specified dimensions, label text, tile, and size.
     *
     * @param width     The width of the rectangle.
     * @param height    The height of the rectangle.
     * @param labelText The text to be displayed on the label.
     * @param tile      The associated tile object.
     * @param size      The size of the matrix used to calculate the label font size.
     */
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

    /**
     * Gets the associated tile object.
     *
     * @return The associated tile object.
     */
    public Tile GetTile(){
        return this.tile;
    }
}
