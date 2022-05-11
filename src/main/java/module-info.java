module com.goindol.teamtalk {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;


    opens com.goindol.teamtalk to javafx.fxml;
    exports com.goindol.teamtalk;
}