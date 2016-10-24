package com.my.home.data.access.impl.directory;

import com.my.home.BaseTestClass;
import com.my.home.exceptions.AnyException;
import com.my.home.log.LogNodeProcessor;
import com.my.home.log.beans.LogNode;
import com.my.home.utils.DirectoryUtils;

import java.util.Iterator;

/**
 *
 */
public class DAORepositoryDirTest extends BaseTestClass
{
    private DAORepositoryDir classToTest;
    public void setUp() throws Exception
    {
        super.setUp();
        classToTest = new DAORepositoryDir();
    }

    public void testLogNodeIterator() throws AnyException
    {
        Iterator<LogNode> iterator = classToTest.getLogNodeIterator("", "/out/dir1/dir1_1", "http-0.0.0.0-8080-4");
        LogNodeProcessor processor = getBean("LogNodeProcessor", LogNodeProcessor.class);
        StringBuilder buffer = new StringBuilder();
        while (iterator.hasNext())
        {
            buffer.append(processor.process(iterator.next()));
            buffer.append("\n");
        }
        DirectoryUtils utils = new DirectoryUtils(null);
        utils.saveFile("result.log", "d:/out", buffer.toString());
    }
}
