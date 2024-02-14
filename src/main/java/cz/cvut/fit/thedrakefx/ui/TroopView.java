package cz.cvut.fit.thedrakefx.ui;

import cz.cvut.fit.thedrakefx.logic.GameState;
import cz.cvut.fit.thedrakefx.logic.PlayingSide;
import cz.cvut.fit.thedrakefx.logic.Troop;
import cz.cvut.fit.thedrakefx.logic.TroopFace;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class TroopView extends Pane {

    private final Troop troop;

    private final PlayingSide playingSide;

    private GameState gameState;

    private final Border border = new Border(
            new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));

    private TileBackgrounds backgrounds = new TileBackgrounds();

    public TroopView (Troop troop, PlayingSide playingSide, GameState gameState) {
        this.troop = troop;
        this.playingSide = playingSide;
        this.gameState = gameState;

        setPrefSize(100, 100);

        setBackground(backgrounds.getTroop(this.troop, this.playingSide, TroopFace.AVERS));
    }

    public void select() {
        setBorder(border);
    }

    public void unselect() {
        setBorder(null);
    }

    public void updateGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Troop getTroop() {
        return troop;
    }
}
