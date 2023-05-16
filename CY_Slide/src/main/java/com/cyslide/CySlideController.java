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
    private Label menuView_Pseudo;

    @FXML
    protected void OnStartPage_ButtonClick() {
        // écrire le contenue de la textfield dans le label de la page menu-view.fxml
        menuView_Pseudo.setText(StartPage_TextField.getText());
        // On se rend à la page menu-view.fxml
        try {
            Parent root = FXMLLoader.load(getClass().getResource("menu-view.fxml"));
            Scene scene = new Scene(root, 800, 450);
            Stage stage = (Stage) StartPage_Button.getScene().getWindow();
            stage.setScene(scene);
            StartPage_Button.getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}