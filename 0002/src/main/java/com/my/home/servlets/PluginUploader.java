package com.my.home.servlets;

import com.my.home.ejb.UploadPluginRemote;
import com.my.home.factory.RemoteBeanFactory;
import com.my.home.factory.SpringFactory;
import com.sun.org.apache.xerces.internal.impl.dv.util.ByteListImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 20.09.16
 * Time: 21:05
 * To change this template use File | Settings | File Templates.
 */
public class PluginUploader extends AbstractServlet {
    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            Collection<Part> parts = request.getParts();
            Map<String, List<Byte>> out = new HashMap<>();
            for (Part part : parts)
            {
                //String path = "D:/out/" + part.getSubmittedFileName();
                //FileOutputStream outputStream = new FileOutputStream(path);
                List<Byte> bytes = retrieveBytes(part.getInputStream());
                out.put(part.getName(), bytes);

            }
            SpringFactory springFactory = SpringFactory.getInstance();
            RemoteBeanFactory remoteBeanFactory = (RemoteBeanFactory) springFactory.getBean("RemoteBeanFactory");
            UploadPluginRemote upload = (UploadPluginRemote)
                    remoteBeanFactory.getBean("LogAnaliseService-1.0", "UploadPluginRemote", "com.my.home.ejb");
            upload.upload(out);
            sendMessage(response, "Plugin uploaded correctly!");
        }
        catch (Exception e)
        {
            sendMessage(response, "Uploading error\n" + e.getMessage());
        }
    }

    private List<Byte> retrieveBytes(InputStream is) throws IOException
    {
        int bufferSize = 128;
        List<Byte> out = new ArrayList<>();
        int read = 0;
        byte[] bytes = new byte[bufferSize];
        while ( (read = is.read(bytes)) != -1)
        {
            if(read < bufferSize)
            {
                bytes = Arrays.copyOfRange(bytes, 0, read);
            }
            out.addAll(new ByteListImpl(bytes));

        }
        is.close();
        return out;
    }
}
