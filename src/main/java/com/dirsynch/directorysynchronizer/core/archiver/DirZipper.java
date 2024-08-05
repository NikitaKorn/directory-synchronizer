//package com.dirsynch.directorysynchronizer.core.archiver;
//
//import net.lingala.zip4j.ZipFile;
//import net.lingala.zip4j.model.ZipParameters;
//import net.lingala.zip4j.model.enums.CompressionLevel;
//import net.lingala.zip4j.model.enums.EncryptionMethod;
//
//import java.io.File;
//import java.io.IOException;
//
//public class DirZipper {
//
//    synchronized public void zip(File inputDir, File outputDir, String pass) throws IOException {
//        try (ZipFile zipFile = new ZipFile(outputDir, pass.toCharArray())) {
//            zipFile.addFolder(inputDir, getConfiguredZipParameters());
//        }
//    }
//
//    private ZipParameters getConfiguredZipParameters() {
//        ZipParameters zipParameters = new ZipParameters();
//        zipParameters.setCompressionLevel(CompressionLevel.HIGHER);
//        zipParameters.setEncryptFiles(true);
//        zipParameters.setEncryptionMethod(EncryptionMethod.AES);
//        return zipParameters;
//    }
//}
