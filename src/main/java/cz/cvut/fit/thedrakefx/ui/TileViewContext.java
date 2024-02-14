package cz.cvut.fit.thedrakefx.ui;

import cz.cvut.fit.thedrakefx.logic.BoardPos;
import cz.cvut.fit.thedrakefx.logic.Move;

public interface TileViewContext {

    void tileViewSelected(TileView tileView);

    void troopViewSelected();

    void executeMove(Move move);

    void executePlace(BoardPos position);

    boolean isStackSelected();

}
