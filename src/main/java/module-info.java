module org.example.tap2024b {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tap2024b to javafx.fxml;
    exports org.example.tap2024b;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;
    requires java.sql;
    opens org.example.tap2024b.models;
}