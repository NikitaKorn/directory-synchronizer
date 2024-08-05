package com.dirsynch.directorysynchronizer.core.configuration;

import java.io.File;

public interface ConfigurationFileWorker {
    <T> T readConfig(File configFile, Class<T> cClass);
    <T> void writeConfig(File configFile, T obj);
}
