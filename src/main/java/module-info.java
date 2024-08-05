module com.dirsynch.directorysynchronizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires org.slf4j;
    requires static lombok;
    requires org.reflections;


    opens com.dirsynch.directorysynchronizer to javafx.fxml;
    exports com.dirsynch.directorysynchronizer;
    exports com.dirsynch.directorysynchronizer.core.model;
    opens com.dirsynch.directorysynchronizer.core.model to javafx.fxml;
    exports com.dirsynch.directorysynchronizer.core.archiver;
    opens com.dirsynch.directorysynchronizer.core.archiver to javafx.fxml;
    exports com.dirsynch.directorysynchronizer.core.sender;
    opens com.dirsynch.directorysynchronizer.core.sender to javafx.fxml;
    exports com.dirsynch.directorysynchronizer.core.schedule;
    opens com.dirsynch.directorysynchronizer.core.schedule to javafx.fxml;
    exports com.dirsynch.directorysynchronizer.gui;
    opens com.dirsynch.directorysynchronizer.gui to javafx.fxml;
    exports com.dirsynch.directorysynchronizer.core;
    opens com.dirsynch.directorysynchronizer.core to javafx.fxml;
    exports com.dirsynch.directorysynchronizer.core.configuration;
    opens com.dirsynch.directorysynchronizer.core.configuration to javafx.fxml;
    exports com.dirsynch.directorysynchronizer.core.cleaner;
    opens com.dirsynch.directorysynchronizer.core.cleaner to javafx.fxml;
    exports com.dirsynch.directorysynchronizer.gui.updater;
    opens com.dirsynch.directorysynchronizer.gui.updater to javafx.fxml;
    exports com.dirsynch.directorysynchronizer.core.status;
    opens com.dirsynch.directorysynchronizer.core.status to javafx.fxml;
}