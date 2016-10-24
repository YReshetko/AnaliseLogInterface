package com.my.home.ejb;

import com.my.home.BaseTestClass;
import com.my.home.directory.beans.FilesToProcess;
import com.my.home.utils.JsonUtils;
import junit.framework.TestCase;

/**
 * ProcessFiles tests
 */
public class ProcessFilesTest extends BaseTestClass
{
    private ProcessFiles classToTest;

    public void setUp() throws Exception
    {
        super.setUp();
        classToTest = new ProcessFiles();
    }

    public void testProcess() throws InterruptedException
    {
       // String toProcess = "{\"basePath\":\"/out/dir1/dir1_1\",\"selectedFiles\":[\"matrixtdp_02.log\",\"matrixtdp_04.log\",\"matrixtdp_01.log\",\"matrixtdp_06.log\",\"matrixtdp_03.log\",\"matrixtdp_05.log\"]}";
        String toProcess = "{\"basePath\":\"/out/dir1/dir1_1\",\"selectedFiles\":[\"matrixtdp_06.log\",\"matrixtdp_05.log\"]}";
        String result = classToTest.process(toProcess);
        assertNotNull(result);
        System.out.println(result);
        if (result != null && result.contains("START_PROCESS"))
        {
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean isComplete = false;
                    while (!isComplete){
                        System.out.println("Progress " + classToTest.getProgress() + "%");
                        isComplete = classToTest.isComplete();
                    }
                }
            });
            th.start();
            th.join();
        }
        else
        {
            fail();
        }

    }

    public void tearDown() throws Exception {
        super.tearDown();
        classToTest = null;
    }
}
