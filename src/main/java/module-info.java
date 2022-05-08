module com.goindol.teamtalk {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.goindol.teamtalk to javafx.fxml;
    exports com.goindol.teamtalk;
}