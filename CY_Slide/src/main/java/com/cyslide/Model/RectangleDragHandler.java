package com.cyslide.Model;

import com.cyslide.Model.RectangleDragHandler;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * A class that handles dragging and swapping of rectangles in a game.
 * This class is responsible for handling mouse events, keyboard events, and managing the movement and swapping of rectangles.
 * 
 * @author @Ymasuu
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

    private boolean SwitchMode=false;

    /**
     * Constructs a RectangleDragHandler object with the specified rectangles and level.
     * 
     * @param rectangles The 2D array of rectangles representing the game board.
     * @param level      The level object associated with the game.
     */
    public RectangleDragHandler(RectangleWithLabel[][] rectangles,Level level) {
        this.rectangles = rectangles;
        this.level=level;
    }

    // This part is for the mouse events //

    /**
     * Creates an event handler for the mouse pressed event on a rectangle.
     * 
     * @param rectangle The rectangle associated with the event handler.
     * @return The event handler for the mouse pressed event.
     */
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

    /**
     * Creates an event handler for the mouse dragged event on a rectangle.
     * 
     * @param rectangle The rectangle associated with the event handler.
     * @return The event handler for the mouse dragged event.
     */
    public EventHandler<MouseEvent> createOnMouseDraggedHandler(RectangleWithLabel rectangle) {
        return event -> {
            if(rectangle.GetTile().getType()==1 && level.getRandomized()){
                double deltaX = event.getSceneX() - mouseAnchorX;
                double deltaY = event.getSceneY() - mouseAnchorY;
                rectangle.setTranslateX(initialTranslateX + deltaX);
                rectangle.setTranslateY(initialTranslateY + deltaY);
            }
            
        };
    }

    /**
     * Creates an event handler for the mouse released event on a rectangle.
     * 
     * @param rectangle The rectangle associated with the event handler.
     * @return The event handler for the mouse released event.
     */
    public EventHandler<MouseEvent> createOnMouseReleasedHandler(RectangleWithLabel rectangle) {
        return event -> {
            boolean isSwapped = false;
            RectangleWithLabel targetRectangle = null;

            for (int i = 0; i < rectangles.length; i++) {
                for (int j = 0; j < rectangles[i].length; j++) {
                    RectangleWithLabel currentRectangle = rectangles[i][j];
                    if (currentRectangle != rectangle && isMouseInside(currentRectangle, event) && level.getRandomized()) {
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
            }
            draggedRectangle = null;
        };
    }

    /**
     * Checks if the mouse is inside a given rectangle.
     * 
     * @param rectangle The rectangle to check.
     * @param event     The mouse event.
     * @return true if the mouse is inside the rectangle, false otherwise.
     */
    private boolean isMouseInside(RectangleWithLabel rectangle, MouseEvent event) {
        double mouseX = event.getSceneX();
        double mouseY = event.getSceneY();
        return mouseX >= rectangle.getBoundsInParent().getMinX()
                && mouseX <= rectangle.getBoundsInParent().getMaxX()
                && mouseY >= rectangle.getBoundsInParent().getMinY()
                && mouseY <= rectangle.getBoundsInParent().getMaxY();
    }

    /**
     * Swaps the positions of two rectangles.
     * 
     * @param rectangle1 The first rectangle to swap.
     * @param rectangle2 The second rectangle to swap.
     */
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

    // That part is for the keyboard control //

    /**
     * Handles the key press event.
     * 
     * @param event The key event.
     */
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.E) {
            // Activate or deactivate the rectangle swapping mode
            if (draggedRectangle != null) {
                // If a rectangle is already selected, deselect it
                draggedRectangle.setEffect(null);
                draggedRectangle = null;
            } else {
                // Select the top-left rectangle for dragging
                draggedRectangle = rectangles[0][0];
                initialTranslateX = draggedRectangle.getTranslateX();
                initialTranslateY = draggedRectangle.getTranslateY();
                initialLayoutX = draggedRectangle.getLayoutX();
                initialLayoutY = draggedRectangle.getLayoutY();
                draggedRectangle.setEffect(new Glow()); // Add a red glow to indicate the selection
            }
        } else if (draggedRectangle != null) {
            // If a rectangle is selected, move it in the specified direction
            initialLayoutX = draggedRectangle.getLayoutX();
            initialLayoutY = draggedRectangle.getLayoutY();
            if (event.getCode() == KeyCode.R) {
                if(SwitchMode){
                    SwitchMode=false;
                }else{
                    SwitchMode=true;
                }
            }
            if (event.getCode() == KeyCode.Z) {
                moveRectangle(draggedRectangle, Direction.UP,SwitchMode);
                //draggedRectangle.setEffect(new Glow());
            } else if (event.getCode() == KeyCode.S) {
                moveRectangle(draggedRectangle, Direction.DOWN,SwitchMode);
                //draggedRectangle.setEffect(new Glow());
            } else if (event.getCode() == KeyCode.Q) {
                moveRectangle(draggedRectangle, Direction.LEFT,SwitchMode);
                //draggedRectangle.setEffect(new Glow());
            } else if (event.getCode() == KeyCode.D) {
                moveRectangle(draggedRectangle, Direction.RIGHT,SwitchMode);
                //draggedRectangle.setEffect(new Glow());
            }
        }
    }

    /**
     * Moves the specified rectangle in the given direction.
     * 
     * @param rectangle   The rectangle to move.
     * @param direction   The direction to move in.
     * @param switchMode  The switch mode flag.
     */
    private void moveRectangle(RectangleWithLabel rectangle, Direction direction,boolean SwitchMode) {
        int deltaX = 0;
        int deltaY = 0;
        String directionsString=null;
    
        switch (direction) {
            case UP:
                deltaX = -1;
                directionsString="UP";
                break;
            case DOWN:
                deltaX = 1;
                directionsString="DOWN";
                break;
            case LEFT:
                deltaY = -1;
                directionsString="LEFT";
                break;
            case RIGHT:
                deltaY = 1;
                directionsString="RIGHT";
                break;
        }
        initialTranslateX = rectangle.getTranslateX();
        initialTranslateY = rectangle.getTranslateY();
        initialLayoutX = rectangle.getLayoutX();
        initialLayoutY = rectangle.getLayoutY();
        int currentX = rectangle.GetTile().getPosX();
        int currentY = rectangle.GetTile().getPosY();
        int targetX = currentX + deltaX;
        int targetY = currentY + deltaY;
        boolean isSwapped=false;
    
        // Ensure that the destination coordinates are valid
        if (isValidCoordinate(targetX, targetY)) {
            RectangleWithLabel targetRectangle = rectangles[targetX][targetY];
            System.out.println(SwitchMode);
            if(SwitchMode){
                if(level.moveTile(currentX, currentY, directionsString, rectangles)){
                    isSwapped=true;
                }
                if (isSwapped && targetRectangle != null) {
                    swapRectangles(rectangle, targetRectangle);
                    draggedRectangle.setEffect(null); // Deselect the rectangle after moving
                    rectangle.setEffect(new Glow());
                    draggedRectangle=rectangle;
                }
            }else{
                // Movement mode
                rectangle.setEffect(null);
                targetRectangle.setEffect(new Glow());
                draggedRectangle=targetRectangle;
            }
        }
    }

    /**
     * Checks if the given coordinates are valid.
     * 
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @return true if the coordinates are valid, false otherwise.
     */
    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < rectangles[0].length && y >= 0 && y < rectangles.length;
    }

    /**
     * The direction in which a rectangle can be moved.
     */
    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }
}
