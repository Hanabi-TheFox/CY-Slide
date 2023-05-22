package snippet;

public class Snippet {
	<?xml version="1.0" encoding="UTF-8"?>
	
	<?import javafx.geometry.Insets?>
	<?import javafx.scene.control.Button?>
	<?import javafx.scene.control.Label?>
	<?import javafx.scene.layout.AnchorPane?>
	<?import javafx.scene.layout.ColumnConstraints?>
	<?import javafx.scene.layout.GridPane?>
	<?import javafx.scene.layout.HBox?>
	<?import javafx.scene.layout.RowConstraints?>
	<?import javafx.scene.layout.VBox?>
	<?import javafx.scene.text.Font?>
	
	<AnchorPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="450.0" prefWidth="800.0" xmlns="http://javafx.com/javafx/19" xmlns:fx="http://javafx.com/fxml/1" fx:controller="com.cyslide.CySlideController">
	   <children>
	      <HBox fx:id="LevelX_HBox" alignment="CENTER" maxHeight="1.7976931348623157E308" maxWidth="1.7976931348623157E308" prefHeight="450.0" prefWidth="800.0" spacing="50.0">
	         <children>
	            <AnchorPane maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="420.0" prefWidth="300.0" style="-fx-background-color: #0066ff;" translateX="-55.0" HBox.hgrow="ALWAYS">
	               <children>
	                  <HBox alignment="CENTER" layoutX="78.0" layoutY="14.0" spacing="10.0">
	                     <children>
	                        <Label alignment="CENTER" contentDisplay="CENTER" text="LEVEL" textAlignment="CENTER" textFill="WHITE">
	                           <font>
	                              <Font name="Arial Bold" size="35.0" />
	                           </font>
	                        </Label>
	                        <Label alignment="CENTER" contentDisplay="CENTER" text="X" textAlignment="CENTER" textFill="WHITE">
	                           <font>
	                              <Font name="Arial" size="35.0" />
	                           </font>
	                        </Label>
	                     </children>
	                  </HBox>
	                  <VBox alignment="CENTER" layoutX="55.0" layoutY="115.0">
	                     <children>
	                        <Label alignment="CENTER" contentDisplay="CENTER" text="NB TURNS" textAlignment="CENTER" textFill="WHITE">
	                           <font>
	                              <Font size="30.0" />
	                           </font>
	                        </Label>
	                        <Label alignment="CENTER" contentDisplay="CENTER" text="X" textAlignment="CENTER" textFill="WHITE">
	                           <font>
	                              <Font size="30.0" />
	                           </font>
	                        </Label>
	                        <Label alignment="CENTER" contentDisplay="CENTER" text="RECORD" textAlignment="CENTER" textFill="WHITE">
	                           <font>
	                              <Font size="30.0" />
	                           </font>
	                        </Label>
	                        <Label alignment="CENTER" contentDisplay="CENTER" text="X" textAlignment="CENTER" textFill="WHITE">
	                           <font>
	                              <Font size="30.0" />
	                           </font>
	                        </Label>
	                        <HBox alignment="CENTER" spacing="40.0">
	                           <children>
	                              <Button id="idResolve" mnemonicParsing="false" prefHeight="80.0" prefWidth="80.0" text="Resolve">
	                                 <font>
	                                    <Font name="Arial" size="16.0" />
	                                 </font>
	                              </Button>
	                              <Button id="idQuit" fx:id="quitButton" mnemonicParsing="false" onAction="#OnLevelMenu_QuitButtonClick" prefHeight="80.0" prefWidth="80.0" text="Quit">
	                                 <font>
	                                    <Font name="Arial" size="16.0" />
	                                 </font>
	                              </Button>
	                           </children>
	                           <opaqueInsets>
	                              <Insets />
	                           </opaqueInsets>
	                        </HBox>
	                     </children>
	                  </VBox>
	                  <Button id="play_button" layoutX="93.0" layoutY="65.0" mnemonicParsing="false" prefHeight="42.0" prefWidth="114.0" text="Play" onAction="#playButtonClicked">
	                        <font>
	                           <Font name="Arial" size="16.0" />
	                        </font>
	                     </Button>
	
	               </children>
	               <HBox.margin>
	                  <Insets />
	               </HBox.margin>
	            </AnchorPane>
	         </children>
	         <padding>
	            <Insets bottom="20.0" right="20.0" top="20.0" />
	         </padding>
	      </HBox>
	   </children>
	</AnchorPane>
}

