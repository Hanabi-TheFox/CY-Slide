package com.cyslide;

import javafx.application.Platform;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import com.cyslide.Model.*;
import com.cyslide.Model.Player.PlayerPseudoException;

/**
 * Main Controller Class responsible for managing all the views
 * and game mouvements
 */

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
        /**
 * Static Attribut to save the context of the currentLevel that is being played
 */
    private static Level currentLevel;
    private static Stage currentStage;
    private static Parent currentRoot;
    private CySlideApplication app;
    private String viewName="";
    private GridPane gridPane;
    private static RectangleWithLabel currentRectangles[][];
        /**
 * Static Matrix to save the context of the currentLevel that is being played
 */
    private static Tile[][] currentTable;
    private static boolean playButtonIsPressed = false;
    //If resolve button is pressed, we cant press any other button
    private static boolean resolveButtonIsPressed = false;

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

    public void setResolveButtonIsPressed(boolean b) {
        CySlideController.resolveButtonIsPressed = b;
    }

    public void setCurrentRectangles(RectangleWithLabel r[][]){
    CySlideController.currentRectangles= r;
    }
    
    
    @FXML
        /**
 * When Start Button is pressed, we load the player's pseudo and
 * show the menu view
 */
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
        /**
 * If back button pressed, we return to the first view
 */
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
        /**
 * The corresponding level view is shown
 */
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

/**
An important method for printing the game table on the level view.
It utilizes RectangleWithLabel and RectangleDragHandler for
printing and movement of the tiles.
*/
    protected void setLevel(Parent root,Stage stage,Level level){
        // Creation of RectangleWithLabel with predefined positions
        Tile [][] table = level.getTable();
        Pane pane = new Pane();
        pane.getChildren().add(root);
        int OffsetRight = 400;
        int OffsetUp = 75;
        int longeurRectangle = 300 / table.length;
        RectangleWithLabel[][] rectangles;
        
        rectangles = new RectangleWithLabel[table.length][table.length];
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
        /**
        If the Quit button is pressed, we go back to the menu selection view.
        However, we need to wait until the level is resolved if the Resolve button was pressed.
        */
    protected void OnLevelMenu_QuitButtonClick() {
        if (CySlideController.resolveButtonIsPressed == false) {
        try {
            playButtonIsPressed = false;
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
    else {
        System.out.println("You cant quit while the level is being resolved!");
    }
    }   

    @FXML
    Button play_button;
    @FXML
        /**
        Button allowing the unlocking of the game table and starting the game
        with a randomized table.
        */
    protected void playButtonClicked() {
        if(CySlideController.resolveButtonIsPressed == false){
            setPlayButtonIsPressed(true);// The game startedHanabi
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
    }




    @FXML
    Button idResolve;
    //If we play resolve the automatic resolution will solve it step by step with waiting time between each step
    @FXML
        /**
        Allows automatic resolution of the current level if the Resolve Button is pressed.
        It blocks any other button until the game is completely resolved.
        */
    public void OnLevelX_ResolveButtonClick(){
        if (CySlideController.playButtonIsPressed == true && CySlideController.resolveButtonIsPressed == false) {
            setResolveButtonIsPressed(true);
            System.out.println("RESOLVE STARTED!");
            Level level = CySlideController.currentLevel;
    
            System.out.println("Initial State :");
            AStarAlgo.printState(level);
    
            List<Level> solution = AStarAlgo.astar(level);
            // we reverse the list
            Collections.reverse(solution);
            
            if (solution != null) {
                System.out.println("Voici les mouvements que vous devez faire un par un.\n");
                int i = 0;
                List<Level> steps = new ArrayList<>();
    
                for (Level state : solution) {
                    if(state!=null){
                        // try{
                        //     System.out.println("nombre de coups : " + i);
                        //     i++;
                        //     AStarAlgo.printState(state);
                        //     Thread.sleep(2000);
                        //     //setResolveStage(currentRoot, currentStage, state);
                        // }catch (InterruptedException e){
                        //     e.printStackTrace();
                        // }
                        System.out.println("nombre de coups : " + i);
                        i++;
                        AStarAlgo.printState(state);
                        steps.add(state);
                        
                        
                    } 
                }
                displaySteps(steps);
                CySlideController.playButtonIsPressed = false;
                //setResolveStage(currentRoot, currentStage, solution.get(solution.size() - 1));
            } else {
                System.out.println("No solution found.");
            }
        }

        else {
            System.out.println("Play Button is not already pressed!");
        }
       
    }

        /**
        @param none
        Allows printing each tile movement step by step, allowing the player
        to keep track of which tile is being moved.
        */
    private void displaySteps(List<Level> steps) {
        Timer timer = new Timer();
        int delay = 50; // Délai en millisecondes entre chaque étape
        final int[] currentIndex = {0}; // Utilisation d'un tableau d'entiers pour contourner la limitation
    
        TimerTask task = new TimerTask() {
            public void run() {
                if (currentIndex[0] < steps.size()-1) {
                    Platform.runLater(() -> {
                        setResolveStage(currentRoot, currentStage, steps.get(currentIndex[0]));
                    });
                    currentIndex[0]++;
                } else {
                    timer.cancel(); // Arrêter le timer lorsque toutes les étapes ont été affichées
                }
            }
        };
    
        timer.scheduleAtFixedRate(task, delay, delay);
    }
    
    


        /**
        Method that prints the game table whenever a tile is moved by the
        AStar algorithm.
        */
    public void setResolveStage( Parent root,Stage stage,Level level){
            // Creation of RectangleWithLabel with predefined positions
            Tile [][] table = level.getTable();
            Pane pane = new Pane();
            pane.getChildren().add(root);
            int OffsetRight = 400;
            int OffsetUp = 75;
            int longeurRectangle = 300 / table.length;
            RectangleWithLabel[][] rectangles;
            
            rectangles = new RectangleWithLabel[table.length][table.length];
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
        CySlideController.currentLevel.setMoveCounter(CySlideController.currentLevel.getMoveCounter()+1);
        LevelX_NBTurns.setText(Integer.toString(CySlideController.currentLevel.getMoveCounter()));   
            
            // RectangleDragHandler rectangleDragHandler = new RectangleDragHandler(rectangles,level);
            // for (RectangleWithLabel[] row : rectangles) {
            //     for (RectangleWithLabel rectangle : row) {
            //         rectangle.setOnMousePressed(rectangleDragHandler.createOnMousePressedHandler(rectangle));
            //         rectangle.setOnMouseDragged(rectangleDragHandler.createOnMouseDraggedHandler(rectangle));
            //         rectangle.setOnMouseReleased(rectangleDragHandler.createOnMouseReleasedHandler(rectangle));
            //     }
            // }
            Scene scene = new Scene(pane, 800, 450);
    
            //scene.setOnKeyPressed(rectangleDragHandler::handleKeyPress);
            setCurrentRectangles(rectangles);

            int[][] tableTmp = RectangleWithLabelToTable(CySlideController.currentRectangles);
            if (CySlideController.currentLevel.isCompleted(tableTmp) == true) {
                System.out.println("RESOLVE FINISHED!");
                //We finished resolve!
                setResolveButtonIsPressed(false);
            }
    
            this.setViewName("game-view.fxml");
            stage.setScene(scene);
            stage.show();

    }



    public String getViewName() {
        return this.viewName;
    }

        /**
        @param none
        A very important method for verifying, after each player's movement,
        if the game has been successfully completed.
        */
    public void handleMoveTileEvent(){
        LevelX_NBTurns = (Label) CySlideController.currentRoot.getScene().lookup("#LevelX_NBTurns");
        LevelX_NBTurns.setText(Integer.toString(CySlideController.currentLevel.getMoveCounter()));
        //We print the matrix in the console
        System.out.println("Table values ---------------");
        int[][] table = RectangleWithLabelToTable(CySlideController.currentRectangles);
        CySlideController.currentLevel.MatrixToLevel(table);
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
                CySlideController.currentLevel.setRandomized(false);
                LevelX_labelFinished = (Label) CySlideController.currentRoot.getScene().lookup("#LevelX_labelFinished");
                LevelX_labelFinished.setText("Level is Completed!");
                setPlayButtonIsPressed(false);
            }
        }
    }

       /**
        * This method converts a matrix of RectangleWithLabels to simple matrix Table[][]
        * @param rectanglesWithLabels
        * @return int[][]
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

