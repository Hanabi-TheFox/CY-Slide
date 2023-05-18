package com.cyslide;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.cyslide.Model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class CySlideController {
    @FXML
    private Button StartPage_Button;
    @FXML
    private TextField StartPage_TextField;
    @FXML
    private Label StartPage_ErrorLabel;
    @FXML
    private Label LevelMenu_Pseudo;
    private CySlideApplication app;
    private String viewName="";

    /*@FXML
    private void initialize() {
        // Register a key press event handler on the StartPage_TextField
        StartPage_TextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                OnStartPage_ButtonClick();
            }
        });
    } */

    @FXML
    protected void OnStartPage_ButtonClick() {
        String pseudo = StartPage_TextField.getText();
        if (pseudo.isEmpty()) {
            Label StartPage_ErrorLabel = (Label) StartPage_TextField.getScene().lookup("#StartPage_ErrorLabel");
            StartPage_ErrorLabel.setText("Please enter a username");
        } else {
            // Check if the player exists in the player.csv file
            boolean playerExists = checkPlayerExists(pseudo);
            
            if (!playerExists) {
                // Create a new player entry in the player.csv file
                createNewPlayer(pseudo);
            }
            // We move to the menu-view.fxml page
            try {
                this.setViewName("LevelMenu.fxml");
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

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    private boolean checkPlayerExists(String pseudo) {
        String pathFile = "CY_Slide/src/main/java/com/cyslide/Data/Player.csv";
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
            while ((line = br.readLine()) != null) {
                    String[] rowValues = line.split(";");
                    if (rowValues[0].equals(pseudo)) {
                        return true;
                    }  
            }
            System.out.println("File Found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading file");
        }
        return false;
    }

    private void createNewPlayer(String pseudo) {
        String pathFile = "CY_Slide/src/main/java/com/cyslide/Data/Player.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile, true))) {
            // Write the new data at the end of the file
            writer.write(pseudo + ";0");
            writer.newLine();
            System.out.println("Success in writing file");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in writing the file");
        }
    }
    

    @FXML
    private Button LevelMenu_BackButton;
    @FXML
    protected void OnLevelMenu_BackButtonClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
            Stage stage = (Stage) LevelMenu_BackButton.getScene().getWindow();
            Scene scene = new Scene(root, 800, 450);

            this.setViewName("StartPage.fxml");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private Button LevelMenu_1;
    @FXML
    protected void OnLevelMenu_1ButtonClick() {
         // We move to the game-view.fxml page
         try {
            // Parent root = FXMLLoader.load(getClass().getResource("game-view.fxml"));
            Stage stage = (Stage) LevelMenu_1.getScene().getWindow();
            // creation of level 1
            Level level = new Level(1);
            // Création des RectangleWithLabel avec des positions prédéfinies
            Tile [][] table = level.getTable();
            for (int i=0; i < table.length; i++){
                for (int j=0; j < table.length; j++){
                    RectangleWithLabel rectangleWithLabel1 = new RectangleWithLabel(100, 100, "1", table[i][j]);
                }
            }
            Tile t1 = new NumberTile(1, 0, 0);
	        RectangleWithLabel rectangleWithLabel1 = new RectangleWithLabel(100, 100, "1", t1);
	        RectangleWithLabel rectangleWithLabel2 = new RectangleWithLabel(100, 100, "2", t1);
	        RectangleWithLabel rectangleWithLabel3 = new RectangleWithLabel(100, 100, "3", t1);

	        // Définir les positions des RectangleWithLabel
	        rectangleWithLabel1.setLayoutX(100);
	        rectangleWithLabel1.setLayoutY(100);
	        rectangleWithLabel2.setLayoutX(200);
	        rectangleWithLabel2.setLayoutY(200);
	        rectangleWithLabel3.setLayoutX(300);
	        rectangleWithLabel3.setLayoutY(300);

	        // Création de la scène
	        Pane pane = new Pane(rectangleWithLabel1, rectangleWithLabel2, rectangleWithLabel3);

	        RectangleWithLabel[][] rectangles = {
	                { rectangleWithLabel1, rectangleWithLabel2, rectangleWithLabel3 }
	            };

	            RectangleDragHandler rectangleDragHandler = new RectangleDragHandler(rectangles);
	            for (RectangleWithLabel[] row : rectangles) {
	                for (RectangleWithLabel rectangle : row) {
	                    rectangle.setOnMousePressed(rectangleDragHandler.createOnMousePressedHandler(rectangle));
	                    rectangle.setOnMouseDragged(rectangleDragHandler.createOnMouseDraggedHandler(rectangle));
	                    rectangle.setOnMouseReleased(rectangleDragHandler.createOnMouseReleasedHandler(rectangle));
	                }
	            }
            Scene scene = new Scene(pane, 800, 450);

             this.setViewName("game-view.fxml");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
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