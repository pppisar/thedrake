module cz.cvut.fit.thedrakefx {
    requires javafx.controls;
    requires javafx.fxml;


    opens cz.cvut.fit.thedrakefx to javafx.fxml;
    exports cz.cvut.fit.thedrakefx;
    exports cz.cvut.fit.thedrakefx.ui;
    opens cz.cvut.fit.thedrakefx.ui to javafx.fxml;
    exports cz.cvut.fit.thedrakefx.logic;
    opens cz.cvut.fit.thedrakefx.logic to javafx.fxml;
}