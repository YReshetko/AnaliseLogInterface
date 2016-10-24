package com.my.home.log;

import com.my.home.BaseTestClass;
import com.my.home.log.beans.LogNode;

import java.text.ParseException;

/**
 * LogNodeProcessor tests
 */
public class LogNodeProcessorTest extends BaseTestClass
{
    private static final String LOG_STRING = "2016-08-26 14:08:19,866 [DEBUG] [http-0.0.0.0-8080-5] [com.handlers.UnitHandler] UnitRetrieveHandler";
    private static final String EXPECTED_TIME = "14:08:19";
    private static final String EXPECTED_MILLIS = "866";
    private static final String EXPECTED_DATE = "2016-08-26";
    private static final String EXPECTED_LOG_LEVEL = "DEBUG";
    private static final String EXPECTED_THREAD = "http-0.0.0.0-8080-5";
    private static final String EXPECTED_CLASS = "com.handlers.UnitHandler";
    private static final String EXPECTED_MESSAGE = "UnitRetrieveHandler";

    private LogNodeProcessor classToTest;
    public void setUp() throws Exception
    {
       super.setUp();
        classToTest = super.getBean("LogNodeProcessor", LogNodeProcessor.class);
    }

    public void testProcess() throws ParseException
    {
        LogNode logNode = classToTest.process(LOG_STRING);
        assertNotNull(logNode);
        assertEquals(logNode.getDate(), EXPECTED_DATE);
        assertEquals(logNode.getTime(), EXPECTED_TIME);
        assertEquals(logNode.getMillisecond(), EXPECTED_MILLIS);
        assertEquals(logNode.getLogLevel(), EXPECTED_LOG_LEVEL);
        assertEquals(logNode.getThread(), EXPECTED_THREAD);
        assertEquals(logNode.getClassPackage(), EXPECTED_CLASS);
        assertEquals(logNode.getMessage(), EXPECTED_MESSAGE);

        String back = classToTest.process(logNode);
        assertEquals(LOG_STRING, back);
    }
}
