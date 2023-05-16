package com.cyslide;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CySlideController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}