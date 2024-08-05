package com.dirsynch.directorysynchronizer.core.archiver;

import java.io.File;
import java.io.IOException;

public interface Zipper {
    void zip(File inputDir, File outputDir, String pass) throws IOException;
}
