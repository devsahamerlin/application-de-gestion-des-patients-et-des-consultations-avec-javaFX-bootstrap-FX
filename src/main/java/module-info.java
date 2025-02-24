module com.merlin.gestionconsultation {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.merlin.gestionconsultation.controllers to javafx.fxml;
    opens com.merlin.gestionconsultation.entities to javafx.base;
    exports com.merlin.gestionconsultation;
}