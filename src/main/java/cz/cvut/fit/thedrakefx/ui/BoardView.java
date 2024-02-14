package cz.cvut.fit.thedrakefx.ui;

import java.util.List;

import cz.cvut.fit.thedrakefx.GameBoardController;
import cz.cvut.fit.thedrakefx.logic.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;

public class BoardView extends GridPane implements TileViewContext {

    private GameState gameState;

    private TileView selected;

    private ValidMoves validMoves;

    private StackView blueStack;

    private CapturedView blueCaptured;

    private StackView orangeStack;

    private CapturedView orangeCaptured;

    private GameBoardController gameBoardController;

    public BoardView(GameState gameState, GameBoardController gameBoardController) {
        this.gameState = gameState;
        this.blueStack = new StackView(gameState, PlayingSide.BLUE, this);
        this.blueCaptured = new CapturedView(gameState, PlayingSide.BLUE);
        this.orangeStack = new StackView(gameState, PlayingSide.ORANGE, this);
        this.orangeCaptured = new CapturedView(gameState, PlayingSide.ORANGE);
        this.gameBoardController = gameBoardController;
        validMoves = new ValidMoves(this.gameState);

        PositionFactory positionFactory = gameState.board().positionFactory();

        for (int j= 0; j < positionFactory.dimension(); j++) {
            for (int i = 0; i < positionFactory.dimension(); i++) {
                BoardPos position = positionFactory.pos(i, positionFactory.dimension() - j - 1);
                add(new TileView(gameState.tileAt(position), position, gameState, this), i, j);
            }
        }

        setHgap(5);
        setVgap(5);
        setPrefSize(415, 415);
        setAlignment(Pos.CENTER);
    }

    public void unselectTile() {
        if (selected != null) {
            selected.unselect();
        }
        clearMoves();
    }

    @Override
    public void tileViewSelected(TileView tileView) {
        if (selected != null && selected != tileView) {
            selected.unselect();
        }

        unselectTroop();

        clearMoves();
        selected = tileView;
        showMoves(validMoves.boardMoves(tileView.position()));
    }

    @Override
    public void troopViewSelected() {
        if (selected != null) {
            selected.unselect();
            selected = null;
        }
        clearMoves();

        showMoves(validMoves.movesFromStack());
    }

    @Override
    public void executeMove(Move move) {
        unselectTile();

        gameState = move.execute(gameState);
        validMoves = new ValidMoves(gameState);

        if (gameState.result() != GameResult.IN_PLAY) {
            this.gameState = this.gameState.changePlayingSide();
        }

        if (validMoves.allMoves().isEmpty()) {
            this.gameState = gameState.resign();
        }

        updateTiles();
        updateArmy();

        if (gameState.result() != GameResult.IN_PLAY) {
            gameBoardController.displayResult(gameState);
        }
    }

    public void executePlace(BoardPos position) {
        try {
            gameState = gameState.placeFromStack(position);
            validMoves = new ValidMoves(gameState);
            unselectTile();

            if (gameState.result() != GameResult.IN_PLAY) {
                this.gameState = this.gameState.changePlayingSide();
            }

            if (validMoves.allMoves().isEmpty()) {
                this.gameState = gameState.resign();
            }

            unselectTroop();
            updateTiles();
            updateArmy();

            if (gameState.result() != GameResult.IN_PLAY) {
                gameBoardController.displayResult(gameState);
            }
        } catch (IllegalArgumentException ignored) { }

    }

    private void updateArmy() {
        blueStack.updateGameState(gameState);
        blueCaptured.updateGameState(gameState);
        orangeStack.updateGameState(gameState);
        orangeCaptured.updateGameState(gameState);
    }

    @Override
    public boolean isStackSelected() {
        if (gameState.sideOnTurn() == PlayingSide.BLUE) {
            return blueStack.isSelected();
        } else {
            return orangeStack.isSelected();
        }
    }

    private void unselectTroop() {
        blueStack.unselect();
        orangeStack.unselect();
    }

    private void updateTiles() {
        for (Node node: getChildren()) {
            TileView tileView = (TileView) node;
            tileView.setTile(gameState);
        }
    }

    private void clearMoves() {
        for (Node node: getChildren()) {
            TileView tileView = (TileView) node;
            tileView.clearMove();
        }
    }

    private void showMoves(List<Move> moveList) {
        for (Move move: moveList) {
            tileViewAt(move.target()).setMove(move);
        }
    }

    private TileView tileViewAt(BoardPos target) {
        int index = (gameState.board().dimension() - target.j() - 1) * 4 + target.i();
        return (TileView) getChildren().get(index);
    }

    public StackView getBlueStack() {
        return this.blueStack;
    }

    public CapturedView getBlueCaptured() {
        return this.blueCaptured;
    }

    public StackView getOrangeStack() {
        return this.orangeStack;
    }

    public CapturedView getOrangeCaptured() {
        return this.orangeCaptured;
    }
}
