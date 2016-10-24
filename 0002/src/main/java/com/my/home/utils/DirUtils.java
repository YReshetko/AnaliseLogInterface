package com.my.home.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 15.10.16
 * Time: 10:10
 * To change this template use File | Settings | File Templates.
 */
public class DirUtils
{
    private static final String SEPARATOR = "/";
    private String tempDir;


    public boolean isTempFileExist(String name)
    {
        File file = new File(tempDir);
        File[] files = file.listFiles();
        for(File currentFile : files)
        {
            if (currentFile.isFile() && currentFile.getName().equals(name))
            {
                return true;
            }
        }
        return false;
    }
    public boolean saveTempFile(String name, String text) throws IOException
    {
        Path path = Paths.get(pathToTempFile(name));
        OutputStream os = Files.newOutputStream(path);
        os.write(text.getBytes());
        os.flush();
        os.close();
        return true;
    }
    public String pathToTempFile(String fileName)
    {
       return pathToFile(tempDir, fileName);
    }
    private String pathToFile(String path, String fileName)
    {
        return path + SEPARATOR + fileName;

    }
    public String getTempDir() {
        return tempDir;
    }

    public void setTempDir(String tempDir) {
        this.tempDir = tempDir;
    }
}
