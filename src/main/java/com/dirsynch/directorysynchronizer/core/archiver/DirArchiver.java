package com.dirsynch.directorysynchronizer.core.archiver;

import com.dirsynch.directorysynchronizer.di.annotation.Component;

import java.io.*;
import java.net.URI;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class DirArchiver implements Zipper {

    @Override
    public void zip(File inputDir, File outputDir, String pass) {
        URI base = inputDir.toURI();
        Deque<File> queue = new LinkedList<>();
        queue.push(inputDir);

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(outputDir))) {
            while (!queue.isEmpty()) {
                inputDir = queue.pop();

                for (File kid : Objects.requireNonNull(inputDir.listFiles())) {
                    String name = base.relativize(kid.toURI()).getPath();
                    if (kid.isDirectory()) {
                        queue.push(kid);
                        name = name.endsWith("/") ? name : name + "/";
                        zipOutputStream.putNextEntry(new ZipEntry(name));
                    } else {
                        zipOutputStream.putNextEntry(new ZipEntry(name));
                        copy(kid, zipOutputStream);
                        zipOutputStream.closeEntry();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[4096];
        while (true) {
            int readCount = in.read(buffer);
            if (readCount < 0) {
                break;
            }
            out.write(buffer, 0, readCount);
        }
    }

    private void copy(File file, OutputStream out) throws IOException {
        try (InputStream in = new FileInputStream(file)) {
            copy(in, out);
        }
    }
}
