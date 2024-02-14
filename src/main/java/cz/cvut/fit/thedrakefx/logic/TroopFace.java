package cz.cvut.fit.thedrakefx.logic;

import java.io.PrintWriter;

public enum TroopFace implements JSONSerializable {
    AVERS, REVERS;

    @Override
    public void toJSON(PrintWriter writer) {
        if (this == AVERS) {
            writer.print("\"AVERS\"");
        } else {
            writer.print("\"REVERS\"");
        }
    }
}
