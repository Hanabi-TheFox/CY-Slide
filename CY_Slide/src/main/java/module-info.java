module com.cyslide {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.cyslide to javafx.fxml;
    exports com.cyslide;
}