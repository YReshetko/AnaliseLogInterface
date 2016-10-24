package com.my.home.ejb;

import javax.ejb.Remote;

/**
 *
 */
@Remote
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
