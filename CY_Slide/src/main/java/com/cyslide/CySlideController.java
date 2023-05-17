package com.cyslide;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CySlideController {
    @FXML
    private Button StartPage_Button;

    @FXML
    private TextField StartPage_TextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label menuView_Pseudo;

    @FXML
    protected void OnStartPage_ButtonClick() {
        String pseudo = StartPage_TextField.getText();
        if (pseudo.isEmpty()) {
            Label errorLabel = (Label) StartPage_TextField.getScene().lookup("#errorLabel");
            errorLabel.setText("Please enter a username");
        } else {
            // We move to the menu-view.fxml page
            try {
                Parent root = FXMLLoader.load(getClass().getResource("menu-view.fxml"));
                Stage stage = (Stage) StartPage_Button.getScene().getWindow();
                Scene scene = new Scene(root, 800, 450);
                // We set the pseudo in the menu-view
                Label pseudoLabel = (Label) scene.lookup("#menuView_Pseudo");
                pseudoLabel.setText(pseudo);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}