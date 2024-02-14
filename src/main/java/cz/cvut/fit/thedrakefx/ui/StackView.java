package cz.cvut.fit.thedrakefx.ui;

import cz.cvut.fit.thedrakefx.logic.*;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Optional;

public class StackView extends VBox {

    private GameState gameState;

    private final PlayingSide playingSide;

    private TileViewContext tileViewContext;

    TroopView selected;

    private final Border border;

    public StackView(GameState gameState, PlayingSide playingSide, TileViewContext tileViewContext) {
        this.gameState = gameState;
        this.playingSide = playingSide;
        this.tileViewContext = tileViewContext;

        for (Troop troop: this.gameState.army(this.playingSide).stack()) {
            getChildren().add(new TroopView(troop, this.playingSide, this.gameState));
        }

        setPrefSize(100, 700);
        setAlignment(Pos.CENTER);

        setOnMouseClicked(e -> onClick());

        if (this.playingSide == PlayingSide.BLUE) {
            border = new Border(
                    new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2)));
        } else {
            border = new Border(
                    new BorderStroke(Color.ORANGE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2)));
        }
    }

    void onClick() {
        if (gameState.result() == GameResult.IN_PLAY) {
            if (!getChildren().isEmpty() && this.playingSide == gameState.sideOnTurn()) {
                tileViewContext.troopViewSelected();

                TroopView topOfStack = (TroopView) getChildren().get(0);
                topOfStack.select();
                selected = topOfStack;
            }
        }
    }

    public void updateGameState(GameState gameState) {
        this.gameState = gameState;
        selected = null;

        getChildren().clear();

        for (Troop troop: this.gameState.army(this.playingSide).stack()) {
            getChildren().add(new TroopView(troop, this.playingSide, this.gameState));
        }

        if (gameState.sideOnTurn() == playingSide) {
            setBorder(border);
        } else {
            setBorder(null);
        }
    }

    public void unselect() {
        selected = null;

        if (!getChildren().isEmpty()) {
            TroopView topOfStack = (TroopView) getChildren().get(0);
            topOfStack.unselect();
        }
    }

    public boolean isSelected() {
        return selected != null;
    }
}
