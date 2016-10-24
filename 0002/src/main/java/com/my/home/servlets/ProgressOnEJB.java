package com.my.home.servlets;

import com.my.home.execution.IExecutionBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 *
 */
public class ProgressOnEJB extends AbstractServlet
{
    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response)
    {
        PrintWriter writer = null;
        EventSender sender = null;
        Map<String, IExecutionBean> map = null;
        try {
            //content type must be set to text/event-stream
            response.setContentType("text/event-stream");
            //encoding must be set to UTF-8
            response.setCharacterEncoding("UTF-8");

            writer = response.getWriter();
            sender = new EventSender(writer);
            HttpSession session = request.getSession();

            Object obj = session.getAttribute("PROCESS_EXECUTIONS");
            if (obj != null && obj instanceof Map)
            {
                map = (Map) obj;
                boolean isSetLabel = false;
                boolean isComplete = false;
                while (!isComplete)
                {
                    isComplete = true;
                    for (Map.Entry<String, IExecutionBean> entry : map.entrySet())
                    {
                        if(!isSetLabel)
                        {
                            sender.send("SET_LABEL_PROGRESS", entry.getKey());
                            isSetLabel = true;
                        }
                        sender.send("CURRENT_PROGRESS", entry.getValue().getProgress());
                        isComplete &= entry.getValue().isComplete();
                    }
                    Thread.sleep(100);
                }
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {

            e.printStackTrace();
        }
        finally
        {
            try {
                if(map != null && map.entrySet().size() > 0)
                {
                    for (Map.Entry<String, IExecutionBean> entry : map.entrySet())
                    {
                        sender.send(entry.getKey(), entry.getValue().getResult());
                    }
                    map.clear();
                }

                Thread.sleep(300);
                sender.send("COMPLETE_ALL_PROCESSES", "COMPLETE_ALL");
                writer.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

    }

    private class EventSender
    {
        private static final String EVENT_TEMPLATE = "event:%s\n";
        private static final String DATA_TEMPLATE = "data:%s\n\n";
        private PrintWriter writer;
        public EventSender(PrintWriter writer)
        {
            this.writer = writer;
        }
        private void send(String event, String data)
        {
            writer.write(String.format(EVENT_TEMPLATE, event));
            writer.write(String.format(DATA_TEMPLATE, data));
            writer.flush();
        }
    }
}
