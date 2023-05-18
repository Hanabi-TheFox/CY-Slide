package com.cyslide.Model;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.StackPane;


public class RectangleWithLabel extends StackPane {
    public RectangleWithLabel(double width, double height, String labelText) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(Color.DODGERBLUE);
        rectangle.setStroke(Color.BLACK);

        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 20px;");

        getChildren().addAll(rectangle, label);
    }
}
