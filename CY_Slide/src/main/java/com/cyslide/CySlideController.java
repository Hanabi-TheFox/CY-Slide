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
    private Label StartPage_ErrorLabel;
    @FXML
    private Label LevelMenu_Pseudo;
    @FXML
    protected void OnStartPage_ButtonClick() {
        String pseudo = StartPage_TextField.getText();
        if (pseudo.isEmpty()) {
            Label StartPage_ErrorLabel = (Label) StartPage_TextField.getScene().lookup("#StartPage_ErrorLabel");
            StartPage_ErrorLabel.setText("Please enter a username");
        } else {
            // We move to the menu-view.fxml page
            try {
                Parent root = FXMLLoader.load(getClass().getResource("LevelMenu.fxml"));
                Stage stage = (Stage) StartPage_Button.getScene().getWindow();
                Scene scene = new Scene(root, 800, 450);
                // We set the pseudo in the menu-view
                Label LevelMenu_Pseudo = (Label) scene.lookup("#LevelMenu_Pseudo");
                LevelMenu_Pseudo.setText(pseudo);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    private Button LevelMenu_BackButton;
    @FXML
    protected void OnLevelMenu_BackButtonClick() {
    
    }

    @FXML
    private Button LevelMenu_1;
    @FXML
    protected void OnLevelMenu_1ButtonClick() {
    }

    @FXML
    private Button LevelMenu_2;
    @FXML
    protected void OnLevelMenu_2ButtonClick() {
    }

    @FXML
    private Button LevelMenu_3;
    @FXML
    protected void OnLevelMenu_3ButtonClick() {
    }

    @FXML
    private Button LevelMenu_4;
    @FXML
    protected void OnLevelMenu_4ButtonClick() {
    }

    @FXML
    private Button LevelMenu_5;
    @FXML
    protected void OnLevelMenu_5ButtonClick() {
    }

    @FXML
    private Button LevelMenu_6;
    @FXML
    protected void OnLevelMenu_6ButtonClick() {
    }

    @FXML
    private Button LevelMenu_7;
    @FXML
    protected void OnLevelMenu_7ButtonClick() {
    }

    @FXML
    private Button LevelMenu_8;
    @FXML
    protected void OnLevelMenu_8ButtonClick() {
    }

    @FXML
    private Button LevelMenu_9;
    @FXML
    protected void OnLevelMenu_9ButtonClick() {
    }

    @FXML
    private Button LevelMenu_10;
    @FXML
    protected void OnLevelMenu_10ButtonClick() {
    }
}