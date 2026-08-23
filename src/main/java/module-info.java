module com.project2.project2fpoe {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.project2.project2fpoe;
    exports com.project2.project2fpoe.view;

    // 👇 Esta línea es clave para que FXML pueda instanciar el controlador
    opens com.project2.project2fpoe.controller to javafx.fxml;
}