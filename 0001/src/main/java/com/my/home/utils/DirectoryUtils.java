package com.my.home.utils;

import com.my.home.directory.RepositoryConfig;
import com.my.home.directory.GenerateDirTree;
import com.my.home.directory.beans.Directory;
import com.my.home.exceptions.AnyException;
import com.my.home.main.beans.BaseBean;

import java.io.*;

/**
 *
 */
public class DirectoryUtils extends BaseBean
{
    private static final String SEPARATOR = "/";
    private RepositoryConfig builder;
    public DirectoryUtils(RepositoryConfig builder)
    {
        this.builder = builder;
    }
    /**
     * Create absolute path to folder with log using subRepositoryId
     * @param repositoryId - repositoryId
     * @param subRepositoryId - subRepositoryId
     * @return - absolute path
     */
    public String getBasePath(final String repositoryId, final String subRepositoryId)
    {
        if(this.builder != null)
        {
            String basePath = subRepositoryId;
            if(basePath.indexOf(SEPARATOR) == 0){
                basePath = basePath.substring(1);
                if(basePath.indexOf(SEPARATOR)!=-1)
                {
                    basePath = basePath.substring(basePath.indexOf(SEPARATOR));
                }
            }
            String path = builder.getPath() + basePath;
            return path;
        }
        else
        {
            return subRepositoryId;
        }
    }

    /**
     * Create repository structure (Recursion)
     * @param path - path to the folder which we need to get structure
     * @return - Directory object
     */
    public Directory getDirStructure(String path)
    {
        java.io.File file = new java.io.File(path);
        Directory out = null;
        if(file.exists() && file.isDirectory())
        {
            GenerateDirTree generator = new GenerateDirTree(file);
            out = generator.getDirTree();
        }
        return out;
    }

    /**
     *  Create directory
     * @param path - path to directory
     * @return - true if directory was created
     */
    public boolean createDir(String path)
    {
        File file = new File(path);
        return file.mkdir();
    }


    public String readFile(String fileName, String path) throws AnyException
    {
        String fullPath = path + SEPARATOR + fileName;
        File file = new File(fullPath);
        if(file.exists() && file.isFile())
        {
            return readFile(file);
        }
        else
        {
            throw new AnyException(new RuntimeException(), "File " + fullPath + " doesn't exist");
        }
    }
    /**
     * Read file as string
     * @param file - file which should be read
     * @return - file as text
     * @throws IOException - File exception
     */
    public String readFile(File file) throws AnyException
    {
        BufferedReader in = null;
        try
        {
            String s;
            StringBuilder out = new StringBuilder();
            in = new BufferedReader(new FileReader(file));
            while ((s = in.readLine()) != null)
            {
                out.append(s);
                out.append("\n");
            }
            return out.toString();
        }
        catch (IOException e)
        {
            throw new AnyException(e, " Can't read file " + file.getAbsolutePath());
        }
        finally
        {
            try
            {
                in.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean saveFile(String fileName, String path, String text) throws AnyException
    {
        String fullPath = path + SEPARATOR + fileName;
        return saveFile(fullPath, text);
    }

    /**
     * Save file by path with text
     * @param path - path
     * @param text - text
     * @return - true if file was saved
     * @throws AnyException - some exception
     */
    public boolean saveFile(String path, String text) throws AnyException
    {
        File file = new File(path);
        return saveFile(file, text);

    }

    /**
     * Save file object
     * @param file - file
     * @param text - text
     * @return - true if file was saved
     * @throws AnyException - some exception
     */
    public boolean saveFile(File file, String text) throws AnyException
    {
        PrintWriter out = null;
        try {
            if (!file.exists())
            {
                file.createNewFile();
                out = new PrintWriter(file);
                out.print(text);
                return true;
            }
            else
            {
                throw new AnyException(new RuntimeException(), "File " + file.getAbsoluteFile() + " already exists");
            }
        }
        catch (IOException e)
        {
            throw new AnyException(e, "Can't save file");
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean saveBinaryFile(String fileName, String path,  byte[] content) throws AnyException
    {
        String fullPath = path + SEPARATOR + fileName;
        return saveBinaryFile(fullPath, content);
    }

    /**
     *
     */
    public boolean saveBinaryFile(String path,  byte[] content) throws AnyException
    {
        File file = new File(path);
        return saveBinaryFile(file, content);

    }
    public boolean saveBinaryFile(File file, byte[] content) throws AnyException
    {
        OutputStream out = null;
        try {
            if (!file.exists())
            {
                out = new FileOutputStream(file);
                out.write(content);
                return true;
            }
            else
            {
                throw new AnyException(new RuntimeException(), "Plugin file " + file.getAbsoluteFile() + " already exists");
            }
        }
        catch (IOException e)
        {
            throw new AnyException(e, "Can't save plugin");
        }
        finally
        {
            try
            {
                assert out != null;
                out.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean removeFile(String fileName, String path)
    {
        String fullPath = path + SEPARATOR + fileName;
        return removeFile(fullPath);
    }

    /**
     *
     */
    public boolean removeFile(String path)
    {
        File file = new File(path);
        return removeFile(file);
    }
    public boolean removeFile(File file)
    {
        return file.delete();
    }
}
