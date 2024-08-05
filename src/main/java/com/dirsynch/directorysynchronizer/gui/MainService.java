package com.dirsynch.directorysynchronizer.gui;


import com.dirsynch.directorysynchronizer.core.GlobalProperties;
import com.dirsynch.directorysynchronizer.core.archiver.Zipper;
import com.dirsynch.directorysynchronizer.core.configuration.ConfigurationFileWorker;
import com.dirsynch.directorysynchronizer.core.exception.ValidException;
import com.dirsynch.directorysynchronizer.core.model.ConfigurationFile;
import com.dirsynch.directorysynchronizer.core.schedule.Processor;
import com.dirsynch.directorysynchronizer.core.schedule.Scheduler;
import com.dirsynch.directorysynchronizer.core.sender.Sender;
import com.dirsynch.directorysynchronizer.core.status.Status;
import com.dirsynch.directorysynchronizer.core.validator.Validator;
import com.dirsynch.directorysynchronizer.di.context.ApplicationContext;
import com.dirsynch.directorysynchronizer.gui.updater.GuiUpdater;
import com.dirsynch.directorysynchronizer.gui.updater.StartupUpdater;
import javafx.scene.control.TextField;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Timer;


/**
 * !!!!!    SINGLETON   !!!!!
 * Main service for MainController processing to link the GUI and the business logic of the application.
 * To access the service from inside the controller and at the same time initialize the service in the main method, a singleton pattern is used.
 * P.S. Probably, not the best solution, but it works.
 */
public class MainService {
    private final int UPDATER_PERIOD_IN_MS = 800;
    private ApplicationContext context;
    @Getter
    private ConfigurationFileWorker configurationFileWorker;
    private Sender sender;
    private Zipper archiver;
    private Scheduler scheduler;
    private Validator validator;
    private Status status;
    private static final MainService SERVICE = new MainService();
    private static final Logger log = LoggerFactory.getLogger(MainService.class);
    private final Timer refreshTimer = new Timer();
    private final Timer startupTimer = new Timer();

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    private MainService() {
    }

    public static MainService getInstance() {
        return SERVICE;
    }

    public void init() {
        this.configurationFileWorker = context.getBean(ConfigurationFileWorker.class);
        this.archiver = context.getBean(Zipper.class);
        this.sender = context.getBean(Sender.class);
        this.validator = context.getBean(Validator.class);
        this.status = context.getBean(Status.class);
        this.scheduler = new Scheduler(new Processor(context));

        try {
            loadConfig();
            if (GlobalProperties.IS_AUTO_SYNCH_ENABLED && GlobalProperties.SYNCH_TIME != null) {
                scheduler.run(GlobalProperties.SYNCH_TIME);
            }
        } catch (Exception e) {
        }
    }

    public void validAndRefreshGlobalProperties(TextField chatID, TextField dir, TextField password, TextField time) {
        try {
            String codeVal = chatID.getText();
            String dirVal = dir.getText();
            String passwordVal = password.getText();
            String timeVal = time.getText();
            validator.isNull(codeVal);
            validator.isNull(dirVal);
            validator.isNull(passwordVal);
            validator.isEmpty(codeVal);
            validator.isEmpty(dirVal);
            validator.isEmpty(passwordVal);
            validator.isNumber(codeVal);
            validator.minMaxPasswordLength(passwordVal);
            validator.isNumber(timeVal);
            GlobalProperties.CHAT_ID = codeVal;
            GlobalProperties.DIR_PATH = dirVal;
            GlobalProperties.PASSWORD = passwordVal;

            if (GlobalProperties.IS_AUTO_SYNCH_ENABLED && !Long.valueOf(timeVal).equals(GlobalProperties.SYNCH_TIME)) {
                GlobalProperties.SYNCH_TIME = Long.valueOf(timeVal);
                resetScheduler();
            }
            GlobalProperties.SYNCH_TIME = Long.valueOf(timeVal);
            writeConfig();

            Alerts.INFO.setTextAndShow("Fields was saved");
        } catch (ValidException e) {
            resetFields(chatID, dir, password, time);
            Alerts.ERROR.setTextAndShow("Valid error", e.getMessage());
        }
    }

    private void resetFields(TextField code, TextField dir, TextField password, TextField time){
        code.setText(GlobalProperties.CHAT_ID);
        dir.setText(GlobalProperties.DIR_PATH);
        password.setText(GlobalProperties.PASSWORD);
        time.setText(String.valueOf(GlobalProperties.SYNCH_TIME));
    }

    public void runRefreshTimer(MainController mainController) {
        GuiUpdater startupUpdater = new GuiUpdater(mainController);
        refreshTimer.schedule(startupUpdater, 0, UPDATER_PERIOD_IN_MS);
    }

    public void autoSynchronizeProcessor() {
        GlobalProperties.IS_AUTO_SYNCH_ENABLED = !GlobalProperties.IS_AUTO_SYNCH_ENABLED;
        if(GlobalProperties.IS_AUTO_SYNCH_ENABLED) {
            resetScheduler();
        } else {
            cancelScheduler();
        }
        writeConfig();
    }

    public void synchronizeProcessor() {
        try {
            archiver.zip(
                    new File(GlobalProperties.DIR_PATH),
                    new File(GlobalProperties.getArchiveFileName()),
                    GlobalProperties.PASSWORD);
        } catch (IOException e) {
            log.error("Error while archiving a directory");
            Alerts.ERROR.setTextAndShow("Error while archiving a directory");
            return;
        }

        try {
            sender.send();
        } catch (IOException e) {
            log.error("Error sending archive to backend");
            Alerts.ERROR.setTextAndShow("Directory can't be send");
            return;
        }
        Alerts.INFO.setTextAndShow("Directory was synchronized");
    }

    private void loadConfig() {
        ConfigurationFile configurationFile = configurationFileWorker.readConfig(new File(GlobalProperties.getConfigurationFileName()), ConfigurationFile.class);
        GlobalProperties.CHAT_ID = configurationFile.getChatID();
        GlobalProperties.DIR_PATH = configurationFile.getDirPath();
        GlobalProperties.PASSWORD = configurationFile.getPassword();
        GlobalProperties.SYNCH_TIME = Long.valueOf(configurationFile.getSynchTime());
        GlobalProperties.IS_AUTO_SYNCH_ENABLED = configurationFile.isAutoSynchEnabled();
    }

    private void writeConfig() {
        ConfigurationFile configurationFile = new ConfigurationFile();
        configurationFile.setPassword(GlobalProperties.PASSWORD);
        configurationFile.setChatID(GlobalProperties.CHAT_ID);
        configurationFile.setDirPath(GlobalProperties.DIR_PATH);
        configurationFile.setSynchTime(String.valueOf(GlobalProperties.SYNCH_TIME));
        configurationFile.setAutoSynchEnabled(GlobalProperties.IS_AUTO_SYNCH_ENABLED);
        configurationFileWorker.writeConfig(new File(GlobalProperties.getConfigurationFileName()), configurationFile);
    }

    public long getDelay() {
        return scheduler.getDelay();
    }

    public String getStatus() {
        return status.getCurrentStatus();
    }

    public void runStartupTimer(MainController mainController) {
        StartupUpdater startupUpdater = new StartupUpdater(mainController);
        startupTimer.schedule(startupUpdater, 0, 2000);
    }

    public void cancelStartupTimer() {
        startupTimer.cancel();
    }

    private void resetScheduler(){
        cancelScheduler();
        scheduler.run(GlobalProperties.SYNCH_TIME);
    }

    private void cancelScheduler(){
        if (scheduler.getScheduledFuture() != null) {
            scheduler.cancel();
        }
    }
}
