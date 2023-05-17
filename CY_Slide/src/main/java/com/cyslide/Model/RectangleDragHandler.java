package com.cyslide.Model;

import com.cyslide.Model.RectangleDragHandler;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
public class RectangleDragHandler {

    private double mouseAnchorX;
    private double mouseAnchorY;
    private double initialTranslateX;
    private double initialTranslateY;
    private double initialLayoutX;
    private double initialLayoutY;
    private Rectangle[][] rectangles;
    private Rectangle draggedRectangle;

    public RectangleDragHandler(Rectangle[][] rectangles) {
        this.rectangles = rectangles;
    }

    public EventHandler<MouseEvent> createOnMousePressedHandler(Rectangle rectangle) {
        return event -> {
            mouseAnchorX = event.getSceneX();
            mouseAnchorY = event.getSceneY();
            initialTranslateX = rectangle.getTranslateX();
            initialTranslateY = rectangle.getTranslateY();
            draggedRectangle = rectangle;
            initialLayoutX = rectangle.getLayoutX();
            initialLayoutY = rectangle.getLayoutY();
        };
    }

    public EventHandler<MouseEvent> createOnMouseDraggedHandler(Rectangle rectangle) {
        return event -> {
            double deltaX = event.getSceneX() - mouseAnchorX;
            double deltaY = event.getSceneY() - mouseAnchorY;
            rectangle.setTranslateX(initialTranslateX + deltaX);
            rectangle.setTranslateY(initialTranslateY + deltaY);
        };
    }

    public EventHandler<MouseEvent> createOnMouseReleasedHandler(Rectangle rectangle) {
        return event -> {
            boolean isSwapped = false;
            Rectangle targetRectangle = null;

            for (int i = 0; i < rectangles.length; i++) {
                for (int j = 0; j < rectangles[i].length; j++) {
                    Rectangle currentRectangle = rectangles[i][j];
                    if (currentRectangle != rectangle && isMouseInside(currentRectangle, event)) {
                        targetRectangle = currentRectangle;
                        isSwapped = true;
                        break;
                    }
                }
                if (isSwapped) {
                    break;
                }
            }

            if (isSwapped && targetRectangle != null) {
                swapRectangles(rectangle, targetRectangle);
            } else {
                rectangle.setTranslateX(initialTranslateX);
                rectangle.setTranslateY(initialTranslateY);
            }
            draggedRectangle = null;
        };
    }

    private boolean isMouseInside(Rectangle rectangle, MouseEvent event) {
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();
        return mouseX >= rectangle.getBoundsInParent().getMinX()
                && mouseX <= rectangle.getBoundsInParent().getMaxX()
                && mouseY >= rectangle.getBoundsInParent().getMinY()
                && mouseY <= rectangle.getBoundsInParent().getMaxY();
    }

    private void swapRectangles(Rectangle rectangle1, Rectangle rectangle2) {
        double tempLayoutX = rectangle2.getLayoutX();
        double tempLayoutY = rectangle2.getLayoutY();
        rectangle1.setTranslateX(initialTranslateX);
        rectangle1.setTranslateY(initialTranslateY);
        rectangle1.setLayoutX(tempLayoutX);
        rectangle1.setLayoutY(tempLayoutY);
        rectangle2.setLayoutX(initialLayoutX);
        rectangle2.setLayoutY(initialLayoutY);
    }
}