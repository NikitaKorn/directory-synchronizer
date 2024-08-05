package com.dirsynch.directorysynchronizer.core.status;

public enum StatusConstants {
    ARCHIVE_ERROR("Error while archiving"),
    SENDING_ERROR("Error while sending"),
    OK("Success"),
    UNDEFINED("Undefined"),
    ERROR("Error");

    private final String title;

    StatusConstants(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
