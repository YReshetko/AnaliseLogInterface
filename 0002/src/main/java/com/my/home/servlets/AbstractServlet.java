package com.my.home.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 */
public abstract class AbstractServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        execute(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //sendMessage(response, "Service is using only POST requests");
        execute(request, response);
    }
    protected void sendMessage(HttpServletResponse response, String message)
    {
        try {
            OutputStream os = response.getOutputStream();
            String s = message;
            os.write(s.getBytes());

            os.flush();
            os.close();
        }
        catch (IOException e)
        {
            try {
                response.sendError(404, "Can't execute");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    protected abstract void execute(HttpServletRequest request, HttpServletResponse response);
}
