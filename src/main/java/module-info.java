module org.tek.module3_group_assignment {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.tek.module3_group_assignment to javafx.fxml;
    exports org.tek.module3_group_assignment;
}