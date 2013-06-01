/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gwt.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.*;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

;

/**
 *
 * @author Айрат
 */
public class FileUploadServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileUploadException {

        if (ServletFileUpload.isMultipartContent(request)) {
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            ServletFileUpload uploadItems = new ServletFileUpload(fileItemFactory);
//            ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
            List<FileItem> items = uploadItems.parseRequest(request);
            Iterator i = items.iterator();
            String targetPath = ((FileItem) i.next()).getString("UTF-8");

            if (targetPath == null) {
                targetPath = getServletContext().getInitParameter("base.dir");
            }
            //FileItemIterator iter = upload.getItemIterator(request);

            while (i.hasNext()) {
                FileItem item = (FileItem) i.next();
                if (!item.isFormField()) {
                    String filePath = item.getName();
                    InputStream inp = item.getInputStream();
                    String fName = filePath.substring(filePath.lastIndexOf("\\") + 1);
                    File uploadfile = new File(targetPath, fName);
                    FileOutputStream out = new FileOutputStream(uploadfile);
                    int len;
                    byte[] buffer = new byte[8192];
                    while ((len = inp.read(buffer, 0, buffer.length)) != -1) {
                        out.write(buffer, 0, len);
                    }

                    //Streams.copy(inp, new FileOutputStream(uploadfile), true);
                    out.flush();
                    out.close();
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (FileUploadException ex) {
            Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
