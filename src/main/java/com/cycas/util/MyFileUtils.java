package com.cycas.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class MyFileUtils {
    /**
     * 创建临时文件
     * @param inputStream
     * @param name
     * @param ext
     * @param tmpDirFile
     * @return
     * @throws IOException
     */
    public static File createTmpFile(InputStream inputStream, String name, String ext, File tmpDirFile) throws IOException {
        File resultFile = File.createTempFile(name, '.' + ext, tmpDirFile);
        resultFile.deleteOnExit();
        org.apache.commons.io.FileUtils.copyInputStreamToFile(inputStream, resultFile);
        return resultFile;
    }

    /**
     * 创建临时文件
     * @param inputStream
     * @param name
     * @param ext
     * @return
     * @throws IOException
     */
    public static File createTmpFile(InputStream inputStream, String name, String ext) throws IOException {
        return createTmpFile(inputStream, name, ext, Files.createTempDirectory("temp").toFile());
    }
}