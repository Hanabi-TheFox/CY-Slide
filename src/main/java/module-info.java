module com.cyslide.cyslide {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cyslide.cyslide to javafx.fxml;
    exports com.cyslide.cyslide;
}