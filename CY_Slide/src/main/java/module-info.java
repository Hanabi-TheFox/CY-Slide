module com.cyslide {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cyslide to javafx.fxml;
    exports com.cyslide;
}