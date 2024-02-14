package cz.cvut.fit.thedrakefx.logic;

import java.io.PrintWriter;

public interface JSONSerializable {
    public void toJSON(PrintWriter writer);
}
