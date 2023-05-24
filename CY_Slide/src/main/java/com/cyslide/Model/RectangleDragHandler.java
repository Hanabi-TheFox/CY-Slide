package com.cyslide.Model;

import com.cyslide.Model.RectangleDragHandler;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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

    private boolean SwitchMode=false;

    

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
            if(rectangle.GetTile().getType()==1 && level.getRandomized()){
                double deltaX = event.getSceneX() - mouseAnchorX;
                double deltaY = event.getSceneY() - mouseAnchorY;
                rectangle.setTranslateX(initialTranslateX + deltaX);
                rectangle.setTranslateY(initialTranslateY + deltaY);
            }
            
        };
    }

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

    //------------------------------------------------------------------------------------------------------------------------------
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.E) {
            // Activer ou désactiver le mode d'échange de rectangles
            if (draggedRectangle != null) {
                // Si un rectangle est déjà sélectionné, désélectionnez-le
                draggedRectangle.setEffect(null);
                draggedRectangle = null;
            } else {
                // Sélectionnez le rectangle en haut à gauche pour le déplacement
                draggedRectangle = rectangles[0][0];
                initialTranslateX = draggedRectangle.getTranslateX();
                initialTranslateY = draggedRectangle.getTranslateY();
                initialLayoutX = draggedRectangle.getLayoutX();
                initialLayoutY = draggedRectangle.getLayoutY();
                draggedRectangle.setEffect(new Glow()); // Ajoutez un contour rouge pour indiquer la sélection
            }
        } else if (draggedRectangle != null) {
            // Si un rectangle est sélectionné, déplacez-le dans la direction spécifiée
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
    
        // Assurez-vous que les coordonnées de destination sont valides
        if (isValidCoordinate(targetX, targetY)) {
            RectangleWithLabel targetRectangle = rectangles[targetX][targetY];
            System.out.println(SwitchMode);
            if(SwitchMode){
                if(level.moveTile(currentX, currentY, directionsString, rectangles)){
                    isSwapped=true;
                }
                if (isSwapped && targetRectangle != null) {
                    swapRectangles(rectangle, targetRectangle);
                    draggedRectangle.setEffect(null); // Désélectionnez le rectangle après le déplacement
                    rectangle.setEffect(new Glow());
                    draggedRectangle=rectangle;
                    // initialTranslateX = rectangle.getTranslateX();
                    // initialTranslateY = rectangle.getTranslateY();
                    // rectangles[currentX][currentY]=targetRectangle;
                    // rectangles[targetX][targetY]=draggedRectangle;
                }
                //draggedRectangle = null;
            }else{
                // deplacement mode
                rectangle.setEffect(null);
                targetRectangle.setEffect(new Glow());
                draggedRectangle=targetRectangle;
            }
        }
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < rectangles[0].length && y >= 0 && y < rectangles.length;
    }

    // ...
//}

enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}
}
