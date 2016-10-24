package com.my.home.execution;

import com.my.home.factory.IRemoteBeanFactory;
import com.my.home.factory.ISpringFactory;
import com.my.home.factory.SpringFactory;

/**
 *
 */
public abstract class AbstractExecutionBean<V> implements IExecutionBean
{
    private String jarName;
    private String packageName;
    private String beanName;
    private V bean;

    protected V getBean()
    {
        if (bean == null)
        {
            ISpringFactory springFactory = SpringFactory.getInstance();
            IRemoteBeanFactory ejbFactory = (IRemoteBeanFactory) springFactory.getBean("RemoteBeanFactory");
            Object obj = ejbFactory.getBean(getJarName(), getBeanName(), getPackageName());
            if (obj != null)
            {
                V bean = (V) obj;
                return bean;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return bean;
        }
    }
    @Override
    public boolean isComplete()
    {
        return true;
    }
    @Override
    public String getProgress()
    {
        return null;
    }
    @Override
    public String getResult()
    {
        return "";
    }

    public String getJarName() {
        return jarName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setJarName(String jarName) {
        this.jarName = jarName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
