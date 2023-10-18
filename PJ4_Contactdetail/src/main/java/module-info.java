module main_code.pj4_contactdetail {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires itextpdf;

    opens main_code to javafx.fxml;
    exports main_code;
    exports Function;
    opens Function to javafx.fxml;
}