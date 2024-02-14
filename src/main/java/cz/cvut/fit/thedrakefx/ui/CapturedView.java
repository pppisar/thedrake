package cz.cvut.fit.thedrakefx.ui;

import cz.cvut.fit.thedrakefx.logic.GameState;
import cz.cvut.fit.thedrakefx.logic.PlayingSide;
import cz.cvut.fit.thedrakefx.logic.Troop;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

public class CapturedView extends VBox {

    private GameState gameState;

    private final PlayingSide playingSide;
    private final PlayingSide opositeSide;

    public CapturedView(GameState gameState, PlayingSide playingSide) {
        this.gameState = gameState;
        this.playingSide = playingSide;

        if (this.playingSide == PlayingSide.BLUE) {
            opositeSide = PlayingSide.ORANGE;
        } else {
            opositeSide = PlayingSide.BLUE;
        }

        for (Troop troop: this.gameState.army(this.playingSide).captured()) {
            getChildren().add(new TroopView(troop, this.opositeSide, this.gameState));
        }

        setPrefSize(100, 700);
        setAlignment(Pos.CENTER);
    }
    public void updateGameState(GameState gameState) {
        this.gameState = gameState;

        getChildren().clear();

        for (Troop troop: this.gameState.army(this.playingSide).captured()) {
            getChildren().add(new TroopView(troop, this.opositeSide, this.gameState));
        }
    }
}