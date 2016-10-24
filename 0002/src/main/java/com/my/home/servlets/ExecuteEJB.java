package com.my.home.servlets;
import com.my.home.ejb.DirTreeControllerRemote;
import com.my.home.execution.IExecutionBean;
import com.my.home.factory.*;
import com.my.home.messages.AliMessage;
import com.my.home.utils.JsonUtils;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 */
public class ExecuteEJB extends AbstractServlet {


    protected void execute(HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession();
        IExecuteBeanFactory factory = ExecuteBeanFactoryImpl.getInstance();
        String command = request.getParameter("command");
        IExecutionBean executionBean = factory.getExecution(request.getParameter("command"));
        String result = executionBean.execute(request.getParameter("data"));
        if (result.contains("START_PROCESS"))
        {
            Object obj = session.getAttribute("PROCESS_EXECUTIONS");
            Map<String, IExecutionBean> map = null;
            if (obj == null)
            {
                map = new HashMap<String, IExecutionBean>();
                session.setAttribute("PROCESS_EXECUTIONS", map);
            }
            else
            {
                map = (HashMap) obj;
            }
            map.put(command, executionBean);
            AliMessage message = new AliMessage();
            message.setCommand("CONNECT_TO_PROCESS");
            message.setType("INFO");
            message.setMessage("");
            JsonUtils.getJson(message);
            sendMessage(response, JsonUtils.getJson(message));
        }
        else
        {
            sendMessage(response, result);
        }
    }

}
