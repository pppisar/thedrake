package cz.cvut.fit.thedrakefx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private Button localButton;

    @FXML
    void onExitClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.exitButton.getScene().getWindow();
        stage.close();
        System.exit(0);
    }

    @FXML
    void onLocalClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) this.localButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("game-board.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 720);
        stage.setTitle("TheDrake");
        stage.setScene(scene);
        stage.show();
        GameBoard gameBoard = new GameBoard(stage,scene);
        GameBoardController gameBoardController = (GameBoardController) fxmlLoader.getController();
        gameBoard.setup(gameBoardController);
    }

    @FXML
    void initialize() {

    }

}
