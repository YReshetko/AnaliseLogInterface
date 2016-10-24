package com.my.home.factory;

import com.my.home.exceptions.AnyException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 04.09.16
 * Time: 8:00
 * To change this template use File | Settings | File Templates.
 */
public class RemoteBeanFactory implements IRemoteBeanFactory
{
    private String contextFactoryKey;
    private String contextFactoryValue;

    private String packageKey;
    private String packageValue;

    private String jndiFactoryKey;
    private String jndiFactoryValue;

    private String ejbHostKey;
    private String ejbHostValue;

    private String ejbPortKey;
    private String ejbPortValue;


    private InitialContext ejbContext;
    public RemoteBeanFactory()
    {
    }
    public void init()
    {
        if (ejbContext == null)
        {
            try{
                /*
                Properties props = new Properties();
                props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
                props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
                props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
                // optional.  Defaults to localhost.  Only needed if web server is running
                // on a different host than the appserver
                props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
                // optional.  Defaults to 3700.  Only needed if target orb port is not 3700.
                props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
                 */
                Properties props = new Properties();
                props.setProperty(contextFactoryKey, contextFactoryValue);
                props.setProperty(packageKey, packageValue);
                props.setProperty(jndiFactoryKey, jndiFactoryValue);
                props.setProperty(ejbHostKey, ejbHostValue);
                props.setProperty(ejbPortKey, ejbPortValue);
                ejbContext = new InitialContext(props);
            }
            catch (NamingException e)
            {
                throw new AnyException(e);
            }
        }
    }
    @Override
    public Object getBean(String jar, String beanName, String beanPackage) {

        Object obj = null;
        try {
            BeanAttributes attributes = new BeanAttributes(jar, beanPackage, beanName);
            obj = ejbContext.lookup(attributes.toString());
        }
        catch (NamingException e)
        {
            throw new AnyException(e);
        }
        return obj;
    }

    public String getContextFactoryKey() {
        return contextFactoryKey;
    }

    public void setContextFactoryKey(String contextFactoryKey) {
        this.contextFactoryKey = contextFactoryKey;
    }

    public String getContextFactoryValue() {
        return contextFactoryValue;
    }

    public void setContextFactoryValue(String contextFactoryValue) {
        this.contextFactoryValue = contextFactoryValue;
    }

    public String getPackageKey() {
        return packageKey;
    }

    public void setPackageKey(String packageKey) {
        this.packageKey = packageKey;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getJndiFactoryKey() {
        return jndiFactoryKey;
    }

    public void setJndiFactoryKey(String jndiFactoryKey) {
        this.jndiFactoryKey = jndiFactoryKey;
    }

    public String getJndiFactoryValue() {
        return jndiFactoryValue;
    }

    public void setJndiFactoryValue(String jndiFactoryValue) {
        this.jndiFactoryValue = jndiFactoryValue;
    }

    public String getEjbHostKey() {
        return ejbHostKey;
    }

    public void setEjbHostKey(String ejbHostKey) {
        this.ejbHostKey = ejbHostKey;
    }

    public String getEjbHostValue() {
        return ejbHostValue;
    }

    public void setEjbHostValue(String ejbHostValue) {
        this.ejbHostValue = ejbHostValue;
    }

    public String getEjbPortKey() {
        return ejbPortKey;
    }

    public void setEjbPortKey(String ejbPortKey) {
        this.ejbPortKey = ejbPortKey;
    }

    public String getEjbPortValue() {
        return ejbPortValue;
    }

    public void setEjbPortValue(String ejbPortValue) {
        this.ejbPortValue = ejbPortValue;
    }

    public static class BeanAttributes
    {
        //"java:global/LogAnaliseService-1.0/DirTreeControllerRemote!com.my.home.ejb.DirTreeControllerRemote"
        private static final String EGB_LOOKUP_TEMPLATE = "java:global/%1$s/%2$s!%3$s.%2$s";
        private String jar;
        private String pkg;
        private String name;

        public BeanAttributes(String jar, String pkg, String name) {
            this.jar = jar;
            this.pkg = pkg;
            this.name = name;
        }

        @Override
        public String toString() {
            return String.format(EGB_LOOKUP_TEMPLATE, jar, name, pkg);
        }
    }
}
