package com.dirsynch.directorysynchronizer.core;

public class GlobalProperties {
    private static String ARCHIVE_FILE_NAME = "archive";
    private static String ARCHIVE_EXTENSION = "zip";
    public static String DIR_PATH;
    public static String CHAT_ID;
    public static String PASSWORD;
    public static Long SYNCH_TIME;
    public static boolean IS_AUTO_SYNCH_ENABLED;

    public static String getConfigurationFileName(){
        String userDir = System.getProperty("user.dir");
        return String.format("%s/%s.json", userDir, "config");
    }

    public static String getArchiveFileName(){
        String userDir = System.getProperty("user.dir");
        return String.format("%s/%s.%s", userDir, ARCHIVE_FILE_NAME, ARCHIVE_EXTENSION);
    }
}
