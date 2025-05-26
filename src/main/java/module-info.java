module finalproject.glamourfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens finalproject.glamourfx to javafx.fxml;
    exports finalproject.glamourfx.controllers;
    opens finalproject.glamourfx.controllers to javafx.fxml;
    exports finalproject.glamourfx.main;
    opens finalproject.glamourfx.main to javafx.fxml;
    exports finalproject.glamourfx.data;
}