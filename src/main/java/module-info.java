module org.example.libriary {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.jdi;
    requires java.desktop;
    requires kernel;
    requires layout;


    opens org.example.libriary to javafx.fxml;
    exports org.example.libriary;
}