package com.cyslide;
import com.cyslide.Model.RectangleDragHandler;
import com.cyslide.Model.RectangleWithLabel;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

public class GameController {
	@FXML
    private RectangleWithLabel draggableRectangle1;
	@FXML
    private RectangleWithLabel draggableRectangle2;
	@FXML
    private RectangleWithLabel draggableRectangle3;
	@FXML
    private RectangleWithLabel draggableRectangle4;
	@FXML
    private RectangleWithLabel draggableRectangle5;
	@FXML
    private RectangleWithLabel draggableRectangle6;
	@FXML
    private RectangleWithLabel draggableRectangle7;
	@FXML
    private RectangleWithLabel draggableRectangle8;
	@FXML
    private RectangleWithLabel draggableRectangle9;
	
	private RectangleWithLabel[][] rectangles;

    public void initialize() {
//        RectangleDragHandler dragHandler = new RectangleDragHandler();
//        draggableRectangle.setOnMousePressed(dragHandler.createOnMousePressedHandler(draggableRectangle));
//        draggableRectangle.setOnMouseDragged(dragHandler.createOnMouseDraggedHandler(draggableRectangle));
    	rectangles = new RectangleWithLabel[][] {
            { draggableRectangle1, draggableRectangle2, draggableRectangle3 },
            { draggableRectangle4, draggableRectangle5, draggableRectangle6 },
            { draggableRectangle7, draggableRectangle8, draggableRectangle9 },
            
        };

        
        RectangleDragHandler rectangleDragHandler = new RectangleDragHandler(rectangles);
        for (int i = 0; i < rectangles.length; i++) {
            for (int j = 0; j < rectangles[i].length; j++) {
                RectangleWithLabel rectangle = rectangles[i][j];
                rectangle.setOnMousePressed(rectangleDragHandler.createOnMousePressedHandler(rectangle));
                rectangle.setOnMouseDragged(rectangleDragHandler.createOnMouseDraggedHandler(rectangle));
                rectangle.setOnMouseReleased(rectangleDragHandler.createOnMouseReleasedHandler(rectangle));
            }
        }
        
        
    }
}
