/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.upm.fi.dia.oeg.map4rdf.server.servlet;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
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
        // Process only multipart requests
        if (ServletFileUpload.isMultipartContent(req)) {
            // Create a factory for disk-based file items
            FileItemFactory factory = new DiskFileItemFactory();

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = new ArrayList<FileItem>();
            try {
                items = upload.parseRequest(req);
            } catch (FileUploadException ex) {
                Logger.getLogger(FileUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (!req.getParameter("urlShapeFile").isEmpty()) {
                processUrl(req.getParameter("urlShapeFile"), resp);
            } else {
                processFileUpload(items, resp);
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
        
        File directory = new File(uploadDirectory + "/" + getDirectoryName(
                filesToDownloadMap));
        if (directory == null) {
             throw new IOException("The files cannot be downloaded."
                     + " Files on the repository don't have the right naming.");
        }

        directory.mkdirs();
        
        for (String key : filesToDownloadMap.keySet()) {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
                    directory.getAbsolutePath() + "/" + filesToDownloadMap.get(key))));
            if (filesToDownloadMap.get(key).equals(SHAPE_FILE_CONFIGURATION_FILE)) {
                bw = new BufferedWriter(new FileWriter(new File(
                    uploadDirectory + "/" + filesToDownloadMap.get(key))));
            }
            
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

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().print("The files were created successfully: "
                + directory.getAbsolutePath()
                + "/" + SHAPE_FILE_CONFIGURATION_FILE);
        resp.flushBuffer();
    }
    
    private void processFileUpload(List<FileItem> items, HttpServletResponse resp)
            throws ServletException, IOException {   
        // Parse the request.
        try {  
            System.out.println("Antes del for size del list: " + items.size());
            System.out.println("ParameterNames");
            ///for(Enumeration e = req.getParameterNames(); e.hasMoreElements(); ){
            //    System.out.println(e.nextElement());
            //}
            for (FileItem fileItem : items) {
                System.out.println("dentro del for");
                // Process only file upload
                if (fileItem.isFormField()) {
                    continue;
                }

                String fileName = fileItem.getName();
                System.out.println("Filename: " + fileName);
                // Get only the file name not whole path
                if (fileName != null) {
                    fileName = FilenameUtils.getName(fileName);
                }

                File uploadedFile = new File(createDirectory(), fileName);
                if (uploadedFile.createNewFile()) {
                    System.out.println("Escribir fichero .zip");
                    fileItem.write(uploadedFile);
                    System.out.println("Fichero escrito");
                    String configurationFile =
                            unzipFile(uploadedFile.getAbsolutePath());
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().print(
                            "The files were created successfully: "
                            + configurationFile);
                    resp.flushBuffer();
                } else {
                    throw new IOException(
                            "The file already exists in repository.");
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while creating the file : "
                    + e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "An error occurred while creating the file : "
                    + e.getMessage());
        }
   
    }
    
    private String unzipFile(String filePath) {
        Enumeration entries;
        ZipFile zipFile;

        try {
            zipFile = new ZipFile(filePath);
            entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.isDirectory()) {
                    // Assume directories are stored parents first then children.
                    System.err.println("Extracting directory: " + entry.getName());
                    // This is not robust, just for demonstration purposes.
                    (new File(entry.getName())).mkdir();
                    continue;
                }
                System.err.println("Extracting file: " + entry.getName());
                copyInputStream(zipFile.getInputStream(entry),
                        new BufferedOutputStream(new FileOutputStream(entry.getName())));
            }
            zipFile.close();
        } catch (IOException ioe) {
            System.err.println("Unhandled exception:");
            ioe.printStackTrace();
        }
        
        return "";
    }
    
    public void copyInputStream(InputStream in, OutputStream out)
            throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
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
                // Return the name without the extension of the file.
                return map.get(key).substring(0, map.get(key).length() - 4);
            }
        }
        return null;
    }
    
}