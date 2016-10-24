package com.my.home.ejb;

/**
 *
 */
public interface GetThreadsRemote
{
    String getThreads(String value);
    String downloadThread(String value);
    String removeThreads(String value);
    String getProgress();
    boolean isComplete();
    String getResult();
    String next();
}
