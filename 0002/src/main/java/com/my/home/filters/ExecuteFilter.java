package com.my.home.filters;

import com.my.home.factory.ExecuteBeanFactoryImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 04.09.16
 * Time: 7:38
 * To change this template use File | Settings | File Templates.
 */
public class ExecuteFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String command = req.getParameter("command");
        boolean valid = "POST".equals(((HttpServletRequest) req).getMethod());
        valid = valid && validateCommand(command);
        if (valid)
        {
            chain.doFilter(req, resp);
        }
        else
        {
            redirectToStartPage((HttpServletResponse) resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    private boolean validateCommand(String command)
    {
         if (command != null && !"".equals(command) && ExecuteBeanFactoryImpl.getInstance().isCommandValid(command)){
             return true;
         }
            else
         {
             return false;
         }
    }

    private void redirectToStartPage(HttpServletResponse response)
    {
        try {
            response.sendRedirect("index.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
