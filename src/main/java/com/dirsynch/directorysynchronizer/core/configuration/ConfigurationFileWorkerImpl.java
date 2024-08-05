package com.dirsynch.directorysynchronizer.core.configuration;


import com.dirsynch.directorysynchronizer.di.annotation.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

@Component
public class ConfigurationFileWorkerImpl implements ConfigurationFileWorker {
    private static final Logger log = LoggerFactory.getLogger(ConfigurationFileWorkerImpl.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T readConfig(File configFileDir, Class<T> cClass) {
        try {
            return mapper.readValue(configFileDir, cClass);
        } catch (IOException e) {
            log.error("Error when trying to read the configuration file");
            throw new RuntimeException("Error when trying to read the configuration file");
        }
    }

    @Override
    public <T> void writeConfig(File configFileDir, T obj) {
        try {
            mapper.writeValue(configFileDir, obj);
        } catch (IOException e) {
            log.error("Error when trying to write the configuration file");
        }
    }
}
