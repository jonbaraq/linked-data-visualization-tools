/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upm.fi.dia.oeg.map4rdf.server.servlet;

import es.upm.fi.dia.oeg.map4rdf.share.RDFDisplayer;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 * @author jonathangsc
 */
public class FileUploadServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "/tmp/uploads/";
    private static final String DEFAULT_TEMP_DIR = ".";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Process only multipart requests
        if (ServletFileUpload.isMultipartContent(req)) {
            File tempDir = getTempDir();
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            // Create a factory for disk-based file items
            FileItemFactory factory = new DiskFileItemFactory();

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            // Parse the request
            try {
                List<FileItem> items = upload.parseRequest(req);
                for (FileItem fileItem : items) {
                    // Process only file upload
                    if (fileItem.isFormField()) {
                        continue;
                    }

                    String fileName = fileItem.getName();
                    // Get only the file name not whole path
                    if (fileName != null) {
                        fileName = FilenameUtils.getName(fileName);
                    }

                    File uploadedFile = new File(UPLOAD_DIRECTORY, fileName);
                    if (uploadedFile.createNewFile()) {
                        fileItem.write(uploadedFile);
                        resp.setStatus(HttpServletResponse.SC_CREATED);
                        resp.getWriter().print(
                                "The file was created successfully: " + uploadedFile.getAbsolutePath());
                        resp.flushBuffer();
                    } else {
                        throw new IOException(
                                "The file already exists in repository.");
                    }
                }
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "An error occurred while creating the file : "
                        + e.getMessage());
            }

        } else {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Request contents type is not supported by the servlet.");
        }
    }

    private File getTempDir() {
        return new File(DEFAULT_TEMP_DIR);
    }
    
}