package cz.cvut.fit.thedrakefx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cz.cvut.fit.thedrakefx.logic.GameResult;
import cz.cvut.fit.thedrakefx.logic.GameState;
import cz.cvut.fit.thedrakefx.logic.PlayingSide;
import cz.cvut.fit.thedrakefx.ui.BoardView;
import cz.cvut.fit.thedrakefx.ui.CapturedView;
import cz.cvut.fit.thedrakefx.ui.StackView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameBoardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox blueArmy;

    @FXML
    private Button exitButton;

    @FXML
    private BorderPane interfacePane;

    @FXML
    private Button menuButton;

    @FXML
    private Button newGameButton;

    @FXML
    private HBox orangeArmy;

    @FXML
    private Label resultLabel;


    @FXML
    void onExitClick(ActionEvent event) {
        Stage stage = (Stage) this.exitButton.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    @FXML
    void onMenuClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.menuButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("main-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 720);
        stage.setTitle("TheDrake");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onNewGameButton(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.newGameButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(GameBoard.class.getResource("game-board.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 720);
        stage.setTitle("TheDrake");
        stage.setScene(scene);
        stage.show();
        GameBoard gameBoard = new GameBoard(stage,scene);
        GameBoardController gameBoardController = (GameBoardController)fxmlLoader.getController();
        gameBoard.setup(gameBoardController);
    }

    @FXML
    void initialize() {

    }

    public void update(BoardView boardView) {
        interfacePane.setCenter(boardView);
        blueArmy.getChildren().add(boardView.getBlueCaptured());
        blueArmy.getChildren().add(boardView.getBlueStack());
        orangeArmy.getChildren().add(boardView.getOrangeStack());
        orangeArmy.getChildren().add(boardView.getOrangeCaptured());
    }

    public void displayResult(GameState gameState) {
        if (gameState.result() == GameResult.DRAW) {
            resultLabel.setText("RESULT: DRAW");
        } else {
            if (gameState.sideOnTurn() == PlayingSide.BLUE) {
                resultLabel.setText("RESULT: BLUE WINS");
            } else {
                resultLabel.setText("RESULT: ORANGE WINS");
            }
        }
        resultLabel.setVisible(true);
        newGameButton.setVisible(true);
        newGameButton.setDisable(false);
    }
}