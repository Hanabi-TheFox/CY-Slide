package com.cyslide;

import com.cyslide.Model.Player;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    Button play_button;
    @FXML
    protected void playButtonClicked() {
        //TODO
        /*
         * 
         *  // We call the initiate method on Class Level
    Level level = getCurrentLevel();
    Button levelMenu = getLastLevelButtonPressed();
    // We move randomly each tile from the table
    level.initLevelMove(); //this level object will have know a different table
    //We get stage from the levelMenuButton reference
    Stage stage = (Stage) levelMenu.getScene().getWindow();
    //We print again the playable table
    setLevel(levelMenu, stage, level);
         */
    }

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
            this.player = new Player(pseudo);
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
    private Button LevelMenu_2;
    @FXML
    private Button LevelMenu_3;
    @FXML
    private Button LevelMenu_4;
    @FXML
    private Button LevelMenu_5;
    @FXML
    private Button LevelMenu_6;
    @FXML
    private Button LevelMenu_7;
    @FXML
    private Button LevelMenu_8;
    @FXML
    private Button LevelMenu_9;
    @FXML
    private Button LevelMenu_10;
    @FXML
    protected void OnLevelMenu_XButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String levelNumber = clickedButton.getText();
         try {
            Parent root = FXMLLoader.load(getClass().getResource("LevelX.fxml"));
            Stage stage = (Stage) LevelMenu_1.getScene().getWindow();
            // creation of level X
            Level level = new Level(Integer.parseInt(levelNumber));
            setLevel(root,stage,level);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected void setLevel(Parent root,Stage stage,Level level){
        HBox LevelX_HBox = (HBox) root.lookup("#LevelX_HBox");
        // Création des RectangleWithLabel avec des positions prédéfinies
        Tile [][] table = level.getTable();
        //GridPane gridPane = new GridPane();
        Pane pane = new Pane();
        pane.setPrefSize(300, 300);
        pane.setMinSize(300, 300);
        pane.setMaxSize(300, 300);
        LevelX_HBox.getChildren().add(pane);
        int longeurRectangle = 300 / table.length;

        RectangleWithLabel[][] rectangles = new RectangleWithLabel[table.length][table.length];
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table.length; j++) {
                String label = "";
                if (table[i][j].getType() == 1) { // Number tile
                    NumberTile nb = (NumberTile) table[i][j];
                    label = Integer.toString(nb.getNumber());
                }
                RectangleWithLabel rectangleWithLabel = new RectangleWithLabel(longeurRectangle, longeurRectangle, label, table[i][j], table.length);
                rectangleWithLabel.setLayoutX(longeurRectangle * j);
                rectangleWithLabel.setLayoutY(longeurRectangle * i);
                pane.getChildren().add(rectangleWithLabel);
                rectangles[i][j] = rectangleWithLabel;
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
        Scene scene = new Scene(root, 800, 450);

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