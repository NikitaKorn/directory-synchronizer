package com.dirsynch.directorysynchronizer.core.sender;


import com.dirsynch.directorysynchronizer.core.GlobalProperties;
import com.dirsynch.directorysynchronizer.core.cleaner.DirCleaner;
import com.dirsynch.directorysynchronizer.di.annotation.Component;
import com.dirsynch.directorysynchronizer.di.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Component
public class BackendSender implements Sender {
    private static final Logger log = LoggerFactory.getLogger(BackendSender.class);
    @Inject
    private DirCleaner cleaner;

    @Override
    synchronized public void send() throws IOException {
        HttpURLConnection connection = configureConnectionToBackend("http://176.109.105.37:30100/receive/" + GlobalProperties.CHAT_ID);
        assert connection != null;
        File file = new File(GlobalProperties.getArchiveFileName());
        connection.setFixedLengthStreamingMode(file.length());
        byte[] bytes = readFile(file);
        assert bytes != null;
        setContentInRequest(connection, bytes);
        connect(connection);
    }

    private HttpURLConnection configureConnectionToBackend(String address) {
        try {
            URL url = new URI(address).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            return connection;
        } catch (IOException | URISyntaxException e) {
            log.error("FIXME");
            return null;
        }
    }

    private void setContentInRequest(HttpURLConnection connection, byte[] content) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream())) {
            out.write(content);
            out.flush();
        }
    }

    private void connect(HttpURLConnection connection) throws IOException {
        connection.connect();
    }

    private byte[] readFile(File file) {
        try (FileInputStream fis = new FileInputStream(file.getPath())) {
            return fis.readAllBytes();
        } catch (IOException e) {
            log.error("Error while reading the file");
            return null;
        } finally {
            cleaner.clean(GlobalProperties.getArchiveFileName());
        }
    }
}
