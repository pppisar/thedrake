package cz.cvut.fit.thedrakefx.ui;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import cz.cvut.fit.thedrakefx.logic.*;

import java.io.InputStream;

public class TileBackgrounds {

    public static final Background EMPTY_BG = new Background(
            new BackgroundFill(new Color(0.9, 0.9, 0.9, 1), null, null));
    private final Background mountainBg;

    public TileBackgrounds() {
        Image img = new Image(getClass().getResourceAsStream("/cz/cvut/fit/images/mountain.png"));
        this.mountainBg = new Background(
                new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                         BackgroundPosition.CENTER,
                                         new BackgroundSize(1.0, 1.0, true, true, false, true)));
    }

    public Background get(Tile tile) {
        if (tile.hasTroop()) {
            TroopTile armyTile = ((TroopTile) tile);
            return getTroop(armyTile.troop(), armyTile.side(), armyTile.face());
        }

        if (tile == BoardTile.MOUNTAIN) {
            return mountainBg;
        }

        return EMPTY_BG;
    }

    public Background getTroop(Troop info, PlayingSide side, TroopFace face) {
        TroopImageSet images = new TroopImageSet(info.name());
        BackgroundImage bgImage = new BackgroundImage( images.get(side, face),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, true));

        return new Background(bgImage);
    }
}
