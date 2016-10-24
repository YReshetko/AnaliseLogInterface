package com.my.home.data.access.impl.directory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 12.10.16
 * Time: 21:50
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseProcessor implements Runnable
{
    protected AtomicLong currentIndex;
    protected boolean isComplete;
    protected abstract long getTotalSize();
    public abstract String getResult();
    public String next()
    {
        return null;
    }
    public String getProgress()
    {
        // TODO make it easier
        long totalSize = getTotalSize();
        String out = null;
        if (totalSize > 0){
            Double result = new Double(0);
            long currentSize = this.currentIndex.get();
            result = new Double(currentSize);
            result = result/totalSize;
            result *= 10000;
            int intRes = (int) Math.ceil(result);
            result = new Double(intRes);
            result = result/100;
            out = String.valueOf(result);
        }
        return out;
    }
    public boolean isComplete()
    {
        return isComplete;
    }

}
