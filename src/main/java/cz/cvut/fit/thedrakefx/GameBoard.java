package cz.cvut.fit.thedrakefx;

import cz.cvut.fit.thedrakefx.logic.*;
import cz.cvut.fit.thedrakefx.ui.BoardView;
import cz.cvut.fit.thedrakefx.ui.CapturedView;
import cz.cvut.fit.thedrakefx.ui.StackView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameBoard {
    private Scene scene;
    private Stage stage;
    private GameState gameState;
    private Board board;

    public GameBoard(Stage stage, Scene scene) {
        this.scene = scene;
        this.stage = stage;

        this.board = new Board(4);
        this.board = this.board.withTiles(new Board.TileAt(new BoardPos(4,1,2), BoardTile.MOUNTAIN),
                                          new Board.TileAt(new BoardPos(4,3,1), BoardTile.MOUNTAIN));

        StandardDrakeSetup standardDrakeSetup = new StandardDrakeSetup();
        this.gameState = standardDrakeSetup.startState(board);
    }
    public void setup(GameBoardController gameBoardController) {
        BoardView boardView = new BoardView(gameState, gameBoardController);
        gameBoardController.update(boardView);
    }

}