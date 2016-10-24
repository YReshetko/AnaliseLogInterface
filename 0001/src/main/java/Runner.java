import com.my.home.ejb.DirTreeControllerRemote;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Created by Yury_Rashetska on 9/26/2016.
 */
public class Runner
{
    public static void main(String[] args) throws NamingException
    {
        Properties props = new Properties();

        props.setProperty("java.naming.factory.initial",
                "com.sun.enterprise.naming.SerialInitContextFactory");

        props.setProperty("java.naming.factory.url.pkgs",
                "com.sun.enterprise.naming");
        props.setProperty("java.naming.factory.state",
                "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        // optional.  Defaults to localhost.  Only needed if web server is running
        // on a different host than the appserver
        props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");

        // optional.  Defaults to 3700.  Only needed if target orb port is not 3700.
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

        InitialContext ctx = new InitialContext(props);

        // Looks up the EJB with JNDI
        Object obj = ctx.lookup("java:global/LogAnaliseService-1.0/DirTreeControllerRemote!com.my.home.ejb.DirTreeControllerRemote");

        DirTreeControllerRemote bookEJB = (DirTreeControllerRemote)obj;
        String tree = bookEJB.getDirTree();
        System.out.println(tree);
    }
}
