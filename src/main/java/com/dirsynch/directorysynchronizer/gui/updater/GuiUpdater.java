package com.dirsynch.directorysynchronizer.gui.updater;

import com.dirsynch.directorysynchronizer.gui.MainController;
import javafx.application.Platform;

import java.util.TimerTask;

public class GuiUpdater extends TimerTask {
    private final MainController controller;

    public GuiUpdater(MainController controller){
        this.controller = controller;
    }

    @Override
    public void run(){
        Platform.runLater(controller::refresh);
    }
}
