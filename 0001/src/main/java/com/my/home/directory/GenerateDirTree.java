package com.my.home.directory;

import com.my.home.directory.beans.Directory;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Class for creation dir tree using recursion
 */
public class GenerateDirTree
{
    private File file;
    private boolean wasUsed;
    public GenerateDirTree(File file)
    {
        this.file = file;
        this.wasUsed = false;
    }

    public Directory getDirTree()
    {
        if (wasUsed)
        {
            throw new RuntimeException("This generator can't be used twice");
        }
        wasUsed = true;
        generateTree(file);
        return generateTree(file);
    }

    private Directory generateTree(File file)
    {
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory() && pathname.getName().equals(".ali"))
                {
                    return false;
                }
                return true;
            }
        };
        Iterator<File> iterator = Arrays.asList(file.listFiles(fileFilter)).iterator();
        File currentFile;
        Directory out = getDir(file);
        while (iterator.hasNext())
        {
            currentFile = iterator.next();
            if (currentFile.isFile())
            {
                 out.getFiles().add(getFile(currentFile));
            }
            if(currentFile.isDirectory())
            {
                 out.getDirectories().add(generateTree(currentFile));
            }
        }
        return out;
    }

    private com.my.home.directory.beans.File getFile(File file)
    {
        com.my.home.directory.beans.File out = new com.my.home.directory.beans.File();
        out.setFileName(file.getName());
        out.setIsSelect(false);
        return out;
    }

    private Directory getDir(File file)
    {
        Directory out = new Directory();
        out.setName(file.getName());
        out.setIsOpen(false);
        return out;
    }


}
