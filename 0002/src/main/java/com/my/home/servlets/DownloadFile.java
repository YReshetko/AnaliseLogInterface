package com.my.home.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Yurchik
 * Date: 15.10.16
 * Time: 11:07
 * To change this template use File | Settings | File Templates.
 */
public class DownloadFile extends AbstractServlet {

    @Override
    protected void execute(HttpServletRequest request, HttpServletResponse response)
    {
        OutputStream outStream = null;
        FileInputStream inStream = null;
        try {
            String fileName = request.getParameter("data");
            // reads input file from an absolute path
            String filePath = "D:/temp/" + fileName;
            File downloadFile = new File(filePath);
            inStream = new FileInputStream(downloadFile);

            // if you want to use a relative path to context root:
            /*String relativePath = getServletContext().getRealPath("");
            System.out.println("relativePath = " + relativePath);
*/
            // obtains ServletContext
            ServletContext context = getServletContext();

            // gets MIME type of the file
           /* String mimeType = context.getMimeType(filePath);
            if (mimeType == null) {*/
                // set to binary type if MIME mapping not found
                String mimeType = "application/octet-stream";
           // }
            System.out.println("MIME type: " + mimeType);

            // modifies response
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            // forces download
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            // obtains response's output stream
            outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
                outStream.flush();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(outStream != null)
            {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inStream != null)
                try
                {
                    inStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
        }

    }
}
