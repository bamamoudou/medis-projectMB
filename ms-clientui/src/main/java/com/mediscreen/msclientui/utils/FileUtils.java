package com.mediscreen.msclientui.utils;

import org.apache.commons.lang.StringUtils;

import java.io.File;

/**
 * @author MorganCpn
 */
public class FileUtils {

    public FileUtils() {
    }

    /**
     * Check is file exist
     * @param relativePath
     * @return
     */
    public static String getFileExtention(String relativePath, String fileType){
        String extention = null;
        if (!StringUtils.isBlank(relativePath)) {
            if (fileType.equals("img")) {
                if (new File(relativePath + ".png").exists()) extention = ".png";
                if (new File(relativePath + ".jpg").exists()) extention = ".jpg";
            } else if (new File(relativePath).exists()) {
                extention = "unknown";
            }
        }
        return extention;
    }
}