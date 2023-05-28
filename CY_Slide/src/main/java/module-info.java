module com.cyslide {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;


    opens com.cyslide to javafx.fxml;
    exports com.cyslide;
    exports com.cyslide.Model;
}