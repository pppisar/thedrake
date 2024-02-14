package cz.cvut.fit.thedrakefx.logic;

import java.io.PrintWriter;

public enum PlayingSide implements JSONSerializable {
    ORANGE, BLUE;

    @Override
    public void toJSON(PrintWriter writer) {
        if (this == ORANGE) {
            writer.print("\"ORANGE\"");
        } else {
            writer.print("\"BLUE\"");
        }
    }
}
