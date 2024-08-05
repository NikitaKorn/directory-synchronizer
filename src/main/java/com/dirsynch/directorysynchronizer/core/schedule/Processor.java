package com.dirsynch.directorysynchronizer.core.schedule;


import com.dirsynch.directorysynchronizer.core.GlobalProperties;
import com.dirsynch.directorysynchronizer.core.archiver.Zipper;
import com.dirsynch.directorysynchronizer.core.sender.Sender;
import com.dirsynch.directorysynchronizer.core.status.Status;
import com.dirsynch.directorysynchronizer.core.status.StatusConstants;
import com.dirsynch.directorysynchronizer.di.context.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Processor implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Processor.class);
    private final Zipper zipper;
    private final Sender sender;
    private final Status status;

    public Processor(ApplicationContext context) {
        this.zipper = context.getBean(Zipper.class);
        this.sender = context.getBean(Sender.class);
        this.status = context.getBean(Status.class);
    }

    @Override
    public void run() {
        log.info("Trying to archive directory");
        try {
            zipper.zip(new File(GlobalProperties.DIR_PATH), new File(GlobalProperties.getArchiveFileName()), GlobalProperties.PASSWORD);
        } catch (IOException e) {
            log.error("Error while archiving a directory");
            status.setStatus(StatusConstants.ERROR);
            return;
        }
        log.info("The directory has been archived");

        log.info("Trying to send archive to backend");
        try {
            sender.send();
        } catch (IOException e) {
            log.error("Error sending archive to backend");
            status.setStatus(StatusConstants.ERROR);
            return;
        }
        log.info("The archive was sent");
        status.setStatus(StatusConstants.OK);
    }
}
