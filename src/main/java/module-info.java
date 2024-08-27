module org.example.tap2024b {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tap2024b to javafx.fxml;
    exports org.example.tap2024b;
}