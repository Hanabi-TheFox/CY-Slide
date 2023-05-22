package com.cyslide.Model;

import com.cyslide.Model.RectangleDragHandler;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

/**
 * @author @Ymasuu
 *
 */
public class RectangleDragHandler {

    private double mouseAnchorX;
    private double mouseAnchorY;
    private double initialTranslateX;
    private double initialTranslateY;
    private double initialLayoutX;
    private double initialLayoutY;
    private RectangleWithLabel[][] rectangles;
    private Level level;
    private RectangleWithLabel draggedRectangle;

    public RectangleDragHandler(RectangleWithLabel[][] rectangles,Level level) {
        this.rectangles = rectangles;
        this.level=level;
    }

    public EventHandler<MouseEvent> createOnMousePressedHandler(RectangleWithLabel rectangle) {
        return event -> {
            mouseAnchorX = event.getSceneX();
            mouseAnchorY = event.getSceneY();
            initialTranslateX = rectangle.getTranslateX();
            initialTranslateY = rectangle.getTranslateY();
            draggedRectangle = rectangle;
            draggedRectangle.toFront();
            initialLayoutX = rectangle.getLayoutX();
            initialLayoutY = rectangle.getLayoutY();
        };
    }

    public EventHandler<MouseEvent> createOnMouseDraggedHandler(RectangleWithLabel rectangle) {
        return event -> {
            double deltaX = event.getSceneX() - mouseAnchorX;
            double deltaY = event.getSceneY() - mouseAnchorY;
            rectangle.setTranslateX(initialTranslateX + deltaX);
            rectangle.setTranslateY(initialTranslateY + deltaY);
        };
    }

    public EventHandler<MouseEvent> createOnMouseReleasedHandler(RectangleWithLabel rectangle) {
        return event -> {
            boolean isSwapped = false;
            RectangleWithLabel targetRectangle = null;

            for (int i = 0; i < rectangles.length; i++) {
                for (int j = 0; j < rectangles[i].length; j++) {
                    RectangleWithLabel currentRectangle = rectangles[i][j];
                    if (currentRectangle != rectangle && isMouseInside(currentRectangle, event)) {
                        targetRectangle = currentRectangle;
                        int x = rectangle.GetTile().getPosX();
                        int y = rectangle.GetTile().getPosY();
                        if((rectangle.GetTile().getPosX()-targetRectangle.GetTile().getPosX()==1) && (rectangle.GetTile().getPosY()-targetRectangle.GetTile().getPosY()==0) ){
                            String direction="UP";
                            if(level.moveTile(x, y, direction,rectangles)){
                                isSwapped = true;
                                break;
                            }
                        }
                        if((rectangle.GetTile().getPosX()-targetRectangle.GetTile().getPosX()==-1) && (rectangle.GetTile().getPosY()-targetRectangle.GetTile().getPosY()==0) ){
                            String direction="DOWN";
                            if(level.moveTile(x, y, direction,rectangles)){
                                isSwapped = true;
                                break;
                            }
                            
                        }
                        if((rectangle.GetTile().getPosX()-targetRectangle.GetTile().getPosX()==0) && (rectangle.GetTile().getPosY()-targetRectangle.GetTile().getPosY()==1) ){
                            String direction="LEFT";
                            if(level.moveTile(x, y, direction,rectangles)){
                                isSwapped = true;
                                break;
                            }
                            
            
                        }
                        if((rectangle.GetTile().getPosX()-targetRectangle.GetTile().getPosX()==0) && (rectangle.GetTile().getPosY()-targetRectangle.GetTile().getPosY()==-1) ){
                            String direction="RIGHT";
                            if(level.moveTile(x, y, direction,rectangles)){
                                isSwapped = true;
                                break;
                            }
                        }
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
                // rectangle.setLayoutX(initialTranslateX);
                // rectangle.setLayoutY(initialTranslateY);
            }
            draggedRectangle = null;
        };
    }


    private boolean isMouseInside(RectangleWithLabel rectangle, MouseEvent event) {
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();
        return mouseX >= rectangle.getBoundsInParent().getMinX()
                && mouseX <= rectangle.getBoundsInParent().getMaxX()
                && mouseY >= rectangle.getBoundsInParent().getMinY()
                && mouseY <= rectangle.getBoundsInParent().getMaxY();
    }

    private void swapRectangles(RectangleWithLabel rectangle1, RectangleWithLabel rectangle2) {
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
