/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upm.fi.dia.oeg.map4rdf.server.servlet;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

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
    private static final String HREF_SYNTAX = "<a href=\"";
    private static final String SHAPE_FILE_CONFIGURATION_FILE =
            "shpoptions.properties";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        File tempDir = getTempDir();
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        // Process only multipart requests
        if (ServletFileUpload.isMultipartContent(req)) {
            if (req.getParameter("urlShapeFile") != null
                    && !req.getParameter("urlShapeFile").isEmpty()) {
                processUrl(req.getParameter("urlShapeFile"), resp);
            } else {
                processFileUpload(req, resp);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
                    "Request contents type is not supported by the servlet.");
        }
    }
    
    private void processUrl(String url, HttpServletResponse resp)
            throws ServletException, IOException {
        String uploadDirectory = createDirectory();
        
        Map<String, String> filesToDownloadMap = getFilesToDownload(url);
        
        File directory = new File(uploadDirectory + "/" + getDirectoryName(filesToDownloadMap));
        directory.mkdirs();
        
        for (String key : filesToDownloadMap.keySet()) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
                    directory.getAbsolutePath() + "/" + filesToDownloadMap.get(key))));
            
            // Download the file from the repository.
            String line;
            BufferedReader br = new BufferedReader(
                new InputStreamReader(new URL(key).openStream()));
        
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        }

    }
    
    private void processFileUpload(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {      
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

                File uploadedFile = new File(createDirectory(), fileName);
                if (uploadedFile.createNewFile()) {
                    fileItem.write(uploadedFile);
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().print(
                            "The file was created successfully: "
                            + uploadedFile.getAbsolutePath());
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
   
    }

    private File getTempDir() {
        return new File(DEFAULT_TEMP_DIR);
    }
    
    private String createDirectory() {
        File file = null;
        do {
            Random random = new Random();
            int nextRandom = random.nextInt();
            if (nextRandom < 0) { 
                nextRandom = nextRandom * -1;
            }
            file = new File(UPLOAD_DIRECTORY + "/" + nextRandom);
        } while (file.exists());
        file.mkdirs();
        
        return file.getAbsolutePath();
    }
    
    private Map<String, String> getFilesToDownload(String url)
            throws MalformedURLException, IOException {
        Map<String, String> filesToDownloadMap = new HashMap<String, String>();
            
        // Generate the index.html file that contains all the files to be
        // downloaded that form the shapefile model.
        String line;
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new URL(url).openStream()));
        
        while ((line = br.readLine()) != null) {
            if (line.startsWith(HREF_SYNTAX)) {
                String link = getLinkFromHref(url, line);
                String filename = getFilenameFromHref(line);
                // If the link ends with / we don't save the link as it will redirect
                // to the parent directory.
                if (link.endsWith("/")) {
                    continue;
                }
                filesToDownloadMap.put(link, filename);
            }
        }
        
        return filesToDownloadMap;       
    }
    
    private String getLinkFromHref(String url, String line) {
        String link = line.split("\"")[1];
        if (!link.startsWith("http://")) {
            link = url + link.substring(1).split("/")[link.substring(1).split("/").length - 1];
        }
        return link;
    }
    
    private String getFilenameFromHref(String line) {
        String filename = "";
        String[] fragments = line.split(">");
        for (String fragment : fragments) {
            
            if (!fragment.startsWith("<")) {
                filename = fragment.split("<")[0];
            }
        }
        return filename;
    }
    
    private String getDirectoryName(Map<String, String> map) {
        for (String key : map.keySet()) {
            if (!map.get(key).equals(SHAPE_FILE_CONFIGURATION_FILE)) {
                System.out.println("Directory Name: " + map.get(key));
                return map.get(key).split(".")[0];
            }
        }
        return null;
    }
    
}