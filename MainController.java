package application;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

public class MainController {
	@FXML
    private Rectangle draggableRectangle1;
	@FXML
    private Rectangle draggableRectangle2;
	@FXML
    private Rectangle draggableRectangle3;
	@FXML
    private Rectangle draggableRectangle4;
	@FXML
    private Rectangle draggableRectangle5;
	@FXML
    private Rectangle draggableRectangle6;
	@FXML
    private Rectangle draggableRectangle7;
	@FXML
    private Rectangle draggableRectangle8;
	@FXML
    private Rectangle draggableRectangle9;
	
	private Rectangle[][] rectangles;

    public void initialize() {
//        RectangleDragHandler dragHandler = new RectangleDragHandler();
//        draggableRectangle.setOnMousePressed(dragHandler.createOnMousePressedHandler(draggableRectangle));
//        draggableRectangle.setOnMouseDragged(dragHandler.createOnMouseDraggedHandler(draggableRectangle));
    	rectangles = new Rectangle[][] {
            { draggableRectangle1, draggableRectangle2, draggableRectangle3 },
            { draggableRectangle4, draggableRectangle5, draggableRectangle6 },
            { draggableRectangle7, draggableRectangle8, draggableRectangle9 },
            
        };

        
        RectangleDragHandler rectangleDragHandler = new RectangleDragHandler(rectangles);
        for (int i = 0; i < rectangles.length; i++) {
            for (int j = 0; j < rectangles[i].length; j++) {
                Rectangle rectangle = rectangles[i][j];
                rectangle.setOnMousePressed(rectangleDragHandler.createOnMousePressedHandler(rectangle));
                rectangle.setOnMouseDragged(rectangleDragHandler.createOnMouseDraggedHandler(rectangle));
                rectangle.setOnMouseReleased(rectangleDragHandler.createOnMouseReleasedHandler(rectangle));
            }
        }
        
        
    }
}
