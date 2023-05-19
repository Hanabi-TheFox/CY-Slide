package com.cyslide;

import com.cyslide.Model.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
    private Player player;
    private GridPane gridPane;

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
            this.player = new Player(this.LevelMenu_Pseudo.getText());
        } catch (IOException | Player.PlayerPseudoException e) {
            e.printStackTrace();
            System.out.println("Error in writing the file or/and creating player object");
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
         try {
            Parent root = FXMLLoader.load(getClass().getResource("LevelX.fxml"));
            Stage stage = (Stage) LevelMenu_1.getScene().getWindow();
            // creation of level 1
            Level level = new Level(1);
            setLevel(root,stage,level);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private Button LevelMenu_2;
    @FXML
    protected void OnLevelMenu_2ButtonClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LevelX.fxml"));
            Stage stage = (Stage) LevelMenu_2.getScene().getWindow();
            // creation of level 2
            Level level = new Level(2);
            setLevel(root,stage,level);
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }

    @FXML
    private Button LevelMenu_3;
    @FXML
    protected void OnLevelMenu_3ButtonClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LevelX.fxml"));
            Stage stage = (Stage) LevelMenu_3.getScene().getWindow();
            // creation of level 3
            Level level = new Level(3);
            setLevel(root,stage,level);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private Button LevelMenu_4;
    @FXML
    protected void OnLevelMenu_4ButtonClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LevelX.fxml"));
            Stage stage = (Stage) LevelMenu_4.getScene().getWindow();
            // creation of level 4
            Level level = new Level(4);
            setLevel(root,stage,level);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private Button LevelMenu_5;
    @FXML
    protected void OnLevelMenu_5ButtonClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LevelX.fxml"));
            Stage stage = (Stage) LevelMenu_5.getScene().getWindow();
            // creation of level 5
            Level level = new Level(5);
            setLevel(root,stage,level);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private Button LevelMenu_6;
    @FXML
    protected void OnLevelMenu_6ButtonClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LevelX.fxml"));
            Stage stage = (Stage) LevelMenu_6.getScene().getWindow();
            // creation of level 6
            Level level = new Level(6);
            setLevel(root,stage,level);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private Button LevelMenu_7;
    @FXML
    protected void OnLevelMenu_7ButtonClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LevelX.fxml"));
            Stage stage = (Stage) LevelMenu_7.getScene().getWindow();
            // creation of level 7
            Level level = new Level(7);
            setLevel(root,stage,level);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private Button LevelMenu_8;
    @FXML
    protected void OnLevelMenu_8ButtonClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LevelX.fxml"));
            Stage stage = (Stage) LevelMenu_8.getScene().getWindow();
            // creation of level 8
            Level level = new Level(8);
            setLevel(root,stage,level);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private Button LevelMenu_9;
    @FXML
    protected void OnLevelMenu_9ButtonClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LevelX.fxml"));
            Stage stage = (Stage) LevelMenu_9.getScene().getWindow();
            // creation of level 9
            Level level = new Level(9);
            setLevel(root,stage,level);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private Button LevelMenu_10;
    @FXML
    protected void OnLevelMenu_10ButtonClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LevelX.fxml"));
            Stage stage = (Stage) LevelMenu_10.getScene().getWindow();
            // creation of level 10
            Level level = new Level(10);
            setLevel(root,stage,level);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected void setLevel(Parent root,Stage stage,Level level){
        
        // Création des RectangleWithLabel avec des positions prédéfinies
        Tile [][] table = level.getTable();
        //GridPane gridPane = new GridPane();
        Pane pane = new Pane();
        pane.getChildren().add(root);
        int OffsetRight = 400;
        int longeurRectangle = 300 / table.length;

        RectangleWithLabel[][] rectangles = new RectangleWithLabel[table.length][table.length];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                if (table[i][j].getType() == -1) { // Empty tile
                    RectangleWithLabel rectangleWithLabel = new RectangleWithLabel(longeurRectangle, longeurRectangle, "", table[i][j]);
                    rectangleWithLabel.setLayoutX(OffsetRight + longeurRectangle * j);
                    rectangleWithLabel.setLayoutY(longeurRectangle * i);
                    pane.getChildren().add(rectangleWithLabel);
                    rectangles[i][j] = rectangleWithLabel;
                }
                if (table[i][j].getType() == 0) { // Indestructible tile
                    RectangleWithLabel rectangleWithLabel = new RectangleWithLabel(longeurRectangle, longeurRectangle, "", table[i][j]);
                    rectangleWithLabel.setLayoutX(OffsetRight + longeurRectangle * j);
                    rectangleWithLabel.setLayoutY(longeurRectangle * i);
                    pane.getChildren().add(rectangleWithLabel);
                    rectangles[i][j] = rectangleWithLabel;
                }
                if (table[i][j].getType() == 1) { // Number tile
                    NumberTile nb = (NumberTile) table[i][j];
                    RectangleWithLabel rectangleWithLabel = new RectangleWithLabel(longeurRectangle, longeurRectangle, Integer.toString(nb.getNumber()), table[i][j]);
                    rectangleWithLabel.setLayoutX(OffsetRight + longeurRectangle * j);
                    rectangleWithLabel.setLayoutY(longeurRectangle * i);
                    pane.getChildren().add(rectangleWithLabel);
                    rectangles[i][j] = rectangleWithLabel;
                }
            }
        }
                    
        RectangleDragHandler rectangleDragHandler = new RectangleDragHandler(rectangles,level);
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
    }

    //If we press to quit, we go back to the menu view
    @FXML
    private Button quitButton;
    @FXML
    protected void OnLevelMenu_QuitButtonClick() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LevelMenu.fxml"));
            Stage stage = (Stage) quitButton.getScene().getWindow();
            Scene scene = new Scene(root, 800, 450);

            this.setViewName("LevelMenu.fxml");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }   
}