package com.dirsynch.directorysynchronizer.core.model;

public class ConfigurationFile {
    private String chatID;
    private String dirPath;
    private String password;
    private String synchTime;
    private boolean isAutoSynchEnabled;

    public String getChatID() {
        return chatID;
    }

    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSynchTime() {
        return synchTime;
    }

    public void setSynchTime(String synchTime) {
        this.synchTime = synchTime;
    }

    public boolean isAutoSynchEnabled() {
        return isAutoSynchEnabled;
    }

    public void setAutoSynchEnabled(boolean autoSynchEnabled) {
        isAutoSynchEnabled = autoSynchEnabled;
    }
}
