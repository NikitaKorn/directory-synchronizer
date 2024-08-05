package com.dirsynch.directorysynchronizer.gui;

import com.dirsynch.directorysynchronizer.core.GlobalProperties;
import com.dirsynch.directorysynchronizer.core.status.StatusConstants;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController {
    private final MainService service;
    @FXML
    private TextField fileText;
    @FXML
    private TextField idText;
    @FXML
    private TextField passwordText;
    @FXML
    private TextField synchTimeText;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Text statusText;
    @FXML
    private Circle statusShape;
    @FXML
    private Text timeText;
    @FXML
    private RadioButton autoSynchEnableButton;
    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    public MainController() throws InterruptedException {
        service = MainService.getInstance();
        service.runStartupTimer(this);
        // Wait for startup configuration
        Thread.sleep(1000);
        service.runRefreshTimer(this);
    }

    @FXML
    protected void onSynchButtonClick() {
        service.synchronizeProcessor();
    }

    @FXML
    private void onAutoSynchEnableClick() {
        service.autoSynchronizeProcessor();
    }

    @FXML
    public void onSaveButtonClick() {
        service.validAndRefreshGlobalProperties(idText, fileText, passwordText, synchTimeText);
    }

    @FXML
    public void refresh() {
        refreshProgressBarStatus();
        refreshStatusShapeStatus();
    }

    @FXML
    public void startupRefresh() {
        if (GlobalProperties.CHAT_ID == null || GlobalProperties.DIR_PATH == null) {
            Alerts.WARN.setTextAndShow("Fill CHAT ID and DIR PATH fields!");
        }
        autoSynchEnableButton.setSelected(GlobalProperties.IS_AUTO_SYNCH_ENABLED);
        idText.setText(GlobalProperties.CHAT_ID);
        fileText.setText(GlobalProperties.DIR_PATH);
        passwordText.setText(GlobalProperties.PASSWORD);
        synchTimeText.setText(String.valueOf(GlobalProperties.SYNCH_TIME));
        service.cancelStartupTimer();
    }

    private void refreshProgressBarStatus(){
        if (GlobalProperties.IS_AUTO_SYNCH_ENABLED) {
            long delay = service.getDelay();
            long res = Math.abs(delay - GlobalProperties.SYNCH_TIME);
            progressBar.setProgress((double) res / GlobalProperties.SYNCH_TIME);
            timeText.setText(String.valueOf(service.getDelay()));
        } else {
            progressBar.setProgress(0);
            timeText.setText("0");
        }
    }

    private void refreshStatusShapeStatus() {
        String status = service.getStatus();
        if (status.equals(StatusConstants.OK.getTitle())) {
            statusShape.setFill(Color.GREEN);
        } else if (status.equals(StatusConstants.UNDEFINED.getTitle())) {
            statusShape.setFill(Color.GREY);
        } else {
            statusShape.setFill(Color.RED);
        }
        statusText.setText(status);
    }
}