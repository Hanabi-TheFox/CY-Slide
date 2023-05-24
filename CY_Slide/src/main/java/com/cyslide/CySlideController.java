package com.cyslide;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import com.cyslide.Model.*;
import com.cyslide.Model.Player.PlayerPseudoException;

public class CySlideController implements Initializable {
    @FXML
    private Button StartPage_Button;
    @FXML
    private TextField StartPage_TextField;
    @FXML
    private Label StartPage_ErrorLabel;


    @FXML
    private Label LevelMenu_Pseudo;

    @FXML
    private Label LevelX_LevelNumber;
    @FXML
    private Label LevelX_NBTurns;
    @FXML
    private Label LevelX_Record;
    @FXML
    private Label LevelX_labelFinished;
    @FXML
    private static Player player;
    private static Level currentLevel;
    private static Stage currentStage;
    private static Parent currentRoot;
    private CySlideApplication app;
    private String viewName="";
    private GridPane gridPane;
    private static RectangleWithLabel currentRectangles[][];
    private static Tile[][] currentTable;
    private static boolean playButtonIsPressed = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (StartPage_Button != null) {
            System.out.println("We are in the StartPage.fxml page");
        }
        if (LevelMenu_BackButton != null) {
            System.out.println("We are in the LevelMenu.fxml page");
            LevelMenu_Pseudo.setText(player.getPseudo());
            Button[] levelButtons = {LevelMenu_1,LevelMenu_2,LevelMenu_3,LevelMenu_4,LevelMenu_5,LevelMenu_6,LevelMenu_7,LevelMenu_8,LevelMenu_9,LevelMenu_10};
            int level = 1;
            for (Button button : levelButtons){
                if (level <= player.getLevelResolved()){
                    button.setDisable(false);
                    button.setOpacity(1.0);
                } else {
                    button.setDisable(true);
                    button.setOpacity(0.5);
                }
                level++;
            }
        }
        // if we are in the LevelX.fxml page
        if (quitButton != null){
            System.out.println("We are in the LevelX.fxml page");
            LevelX_Record.setText(Integer.toString(CySlideController.currentLevel.getRecord()));
        }
    }

    public void setCurrentLevel(Level level){
        CySlideController.currentLevel = level;
    }
    public void setCurrentStage(Stage stage){
        CySlideController.currentStage = stage;
    }
    public void setCurrentRoot(Parent root){
        CySlideController.currentRoot = root;
    }
    public void setPlayButtonIsPressed(boolean b) {
        CySlideController.playButtonIsPressed= b;
    }

    public void setCurrentRectangles(RectangleWithLabel r[][]){
    CySlideController.currentRectangles= r;
    }
    
    @FXML
    protected void OnStartPage_ButtonClick() throws PlayerPseudoException {
        String pseudo = StartPage_TextField.getText();
        if (pseudo.isEmpty()) {
            Label StartPage_ErrorLabel = (Label) StartPage_TextField.getScene().lookup("#StartPage_ErrorLabel");
            StartPage_ErrorLabel.setText("Please enter a username");
        } else {
            player = new Player(pseudo);
            // We move to the LevelMenu.fxml page
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
        this.setViewName("LevelX.fxml");
        //The player cant play the next level unless he finishes the previous one
        //Or its level 1
        if (CySlideController.player.getLevelResolved() >= Integer.parseInt(levelNumber)) {
            try {
                    // creation of level X
                Level level = new Level(Integer.parseInt(levelNumber),this);
                setCurrentLevel(level);
                Parent root = FXMLLoader.load(getClass().getResource("LevelX.fxml"));
                setCurrentRoot(root);
                Stage stage = (Stage) LevelMenu_1.getScene().getWindow();
                setLevel(root,stage,level);
                setCurrentStage(stage);
                LevelX_LevelNumber = (Label) root.getScene().lookup("#LevelX_LevelNumber");
                LevelX_LevelNumber.setText(levelNumber);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        //The player cant play the next level
        else {
            System.out.println("Level " + levelNumber + " can't be played. You should finish Level " + CySlideController.player.getLevelResolved() + " first");
        }
    }

    protected void setLevel(Parent root,Stage stage,Level level){
        // Creation of RectangleWithLabel with predefined positions
        Tile [][] table = level.getTable();
        Pane pane = new Pane();
        pane.getChildren().add(root);
        int OffsetRight = 400;
        int OffsetUp = 75;
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
                rectangleWithLabel.setLayoutX(OffsetRight + longeurRectangle * j);
                rectangleWithLabel.setLayoutY(OffsetUp + longeurRectangle * i);
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
        Scene scene = new Scene(pane, 800, 450);

        scene.setOnKeyPressed(rectangleDragHandler::handleKeyPress);
        this.setCurrentRectangles(rectangles);

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

    @FXML
    Button play_button;
    @FXML
    protected void playButtonClicked() {
        setPlayButtonIsPressed(true);// The game started
        LevelX_Record.setText(Integer.toString(CySlideController.currentLevel.getRecord()));
        Level level = CySlideController.currentLevel;
        Level nvLevel = new Level(level.getNumber(),this);
        nvLevel.initLevelMove();
        Stage stage = CySlideController.currentStage;
        Parent root = CySlideController.currentRoot;
        setLevel(root, stage, nvLevel);
        nvLevel.setMoveCounter(0);
        LevelX_NBTurns = (Label) root.getScene().lookup("#LevelX_NBTurns");
        LevelX_NBTurns.setText("0");
        nvLevel.setRandomized(true);
        nvLevel.setCompleted(false);
        LevelX_labelFinished = (Label) root.getScene().lookup("#LevelX_labelFinished");
        LevelX_labelFinished.setText("");
        play_button = (Button) root.getScene().lookup("#play_button");
        play_button.setText("Replay");
        setCurrentLevel(nvLevel);
    }

    public String getViewName() {
        return this.viewName;
    }

    public void handleMoveTileEvent(){
        LevelX_NBTurns = (Label) CySlideController.currentRoot.getScene().lookup("#LevelX_NBTurns");
        LevelX_NBTurns.setText(Integer.toString(CySlideController.currentLevel.getMoveCounter()));
        //We print the matrix in the console
        System.out.println("Table values ---------------");
        int[][] table = RectangleWithLabelToTable(CySlideController.currentRectangles);
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------");
        if ((CySlideController.playButtonIsPressed == true)) {
            //We transform Rectangle matrix to Tile matrix
            if (CySlideController.currentLevel.isCompleted(table)) {
                //The game is finished
                if(CySlideController.currentLevel.getNumber() == CySlideController.player.getLevelResolved())
                    CySlideController.player.setLevelResolved(CySlideController.currentLevel.getNumber()+1);
                if(CySlideController.currentLevel.getRecord() > CySlideController.currentLevel.getMoveCounter())
                    CySlideController.currentLevel.setRecord(CySlideController.currentLevel.getMoveCounter());
                LevelX_labelFinished = (Label) CySlideController.currentRoot.getScene().lookup("#LevelX_labelFinished");
                LevelX_labelFinished.setText("Level is Completed!");
                setPlayButtonIsPressed(false);
            }
        }
    }

       /**
        * This method converts a matrix of RectangleWithLabels to simple matrix Table[][]
        * @param rectanglesWithLabels
        * @return table[][]
        */
        public int[][] RectangleWithLabelToTable(RectangleWithLabel rectanglesWithLabels[][] ) {
            int numRows =rectanglesWithLabels.length;
            int numCols = rectanglesWithLabels[0].length;
        
            // Create the table matrix
            int[][] table = new int[numRows][numCols];
            //Of tiles too
            //Tile[][] tableTile = new Tile[numRows][numCols];
        
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    Tile tempTile = rectanglesWithLabels[i][j].GetTile();

                    if(tempTile.getType() > 0){
                        NumberTile temp2= (NumberTile) tempTile;
                        table[i][j]=temp2.getNumber();
                    }
                    else if (tempTile.getType() == -1 || tempTile.getType() == 0) {
                        table[i][j] =tempTile.getType();
                    }
                }
            }

            return table;
                }
        }

