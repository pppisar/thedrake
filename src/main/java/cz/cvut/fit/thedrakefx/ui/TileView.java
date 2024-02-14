package cz.cvut.fit.thedrakefx.ui;

import cz.cvut.fit.thedrakefx.logic.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.InputStream;

public class TileView extends Pane {

    private BoardPos position;

    private Tile tile;

    private GameState gameState;

    private TileBackgrounds backgrounds = new TileBackgrounds();

    private final Border border = new Border(
            new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));

    private final Border selectedBorder = new Border(
            new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));

    private TileViewContext tileViewContext;

    private Move move;

    private final ImageView moveImage;

    public TileView(Tile tile, BoardPos position, GameState gameState, TileViewContext tileViewContext) {
        this.tile = tile;
        this.position = position;
        this.gameState = gameState;
        this.tileViewContext = tileViewContext;

        setPrefSize(100, 100);
        setBorder(border);
        setOnMouseClicked(e -> onClick());

        update();

        moveImage = new ImageView(getClass().getResource("/cz/cvut/fit/images/move.png").toString());
        moveImage.setVisible(false);
        getChildren().add(moveImage);
    }

    private void onClick() {
        if (gameState.result() == GameResult.IN_PLAY) {
            if (move != null && !tileViewContext.isStackSelected()) {
                tileViewContext.executeMove(move);
            } else if (tile.hasTroop()) {
                TroopTile troopTile = (TroopTile) tile;
                if (troopTile.side() == gameState.sideOnTurn()) {
                    select();
                }
            } else if (tileViewContext.isStackSelected()) {
                tileViewContext.executePlace(position);
            }
        }
    }

    private void select() {
        setBorder(selectedBorder);
        tileViewContext.tileViewSelected(this);
    }

    public void unselect() {
        setBorder(border);
    }

    public void update() {
        setBackground(backgrounds.get(tile));
    }

    public void setMove(Move move) {
        this.move = move;
        moveImage.setVisible(true);
    }

    public void clearMove() {
        this.move = null;
        moveImage.setVisible(false);
    }

    public BoardPos position() {
        return position;
    }

    public void setTile(GameState gameState) {
        this.tile = gameState.tileAt(position);
        this.gameState = gameState;
        update();
    }

}
