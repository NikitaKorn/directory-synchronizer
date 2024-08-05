package com.dirsynch.directorysynchronizer.gui;

import javafx.scene.control.Alert;

public enum Alerts {
    INFO(Alert.AlertType.INFORMATION),
    WARN(Alert.AlertType.WARNING),
    ERROR(Alert.AlertType.ERROR);

    private final Alert alert;

    Alerts(Alert.AlertType alertType) {
        this.alert = new Alert(alertType);
    }

    public void setTextAndShow(String header) {
        alert.setHeaderText(header);
        alert.show();
    }

    public void setTextAndShow(String header, String content) {
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
}
