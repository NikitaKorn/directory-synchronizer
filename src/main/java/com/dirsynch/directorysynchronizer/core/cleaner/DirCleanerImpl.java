package com.dirsynch.directorysynchronizer.core.cleaner;

import com.dirsynch.directorysynchronizer.di.annotation.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DirCleanerImpl implements DirCleaner {

    @Override
    public void clean(String dirPath) {
        try {
            Path file = Paths.get(dirPath);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
