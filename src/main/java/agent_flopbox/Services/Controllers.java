package agent_flopbox.Services;

import agent_flopbox.Models.Server;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Allows to manage actions between local files and remite files
 */
public class Controllers {

    public Controllers(){}
    /**
     * Allows to save the File form the remote server
     * @param alias Server's alias
     * @param path File's path
     * @return void
     */
    public void saveFile(String alias, String path) throws IOException {
        FileUtils.copyURLToFile(new URL("http://localhost:8080/file/" + alias+"/" + path), new File("./"+alias+path));
    }

    /**
     * Allows to upload local file to remote server
     * @param alias Server's alias
     * @param path File's path
     * @throws IOException
     */
    public void uploadFile(String alias, String path) throws  IOException{

        URLConnection urlconnection = null;
        try {
            File file = FileUtils.getFile(alias, path);
            URL url = new URL("http://localhost:8080/file/"+alias+path);
            System.out.println(alias+path);
            urlconnection = url.openConnection();
            urlconnection.setDoOutput(true);
            urlconnection.setDoInput(true);

            if (urlconnection instanceof HttpURLConnection) {
                ((HttpURLConnection) urlconnection).setRequestMethod("POST");
                ((HttpURLConnection) urlconnection).setRequestProperty("Content-type", "Multipart/form-data");
                ((HttpURLConnection) urlconnection).connect();
            }

            BufferedOutputStream bos = new BufferedOutputStream(urlconnection.getOutputStream());
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            int i;
            // read byte by byte until end of stream
            while ((i = bis.read()) > 0) {
                bos.write(i);
            }
            bis.close();
            bos.close();
            System.out.println(((HttpURLConnection) urlconnection).getResponseMessage());
            ((HttpURLConnection) urlconnection).disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Allows to move the deleted file to the right directory
     * @param alias Server's alias
     * @param path File's path in local
     * @throws IOException
     */
    public void uploadFileBeforeDeleted(String alias, String path) throws  IOException{

        URLConnection urlconnection = null;
        try {
            File file = FileUtils.getFile(alias, path);
            URL url = new URL("http://localhost:8080/file/"+alias+"/deleted/"+file.getName());
            System.out.println(alias+"/deleted/"+file.getName());
            urlconnection = url.openConnection();
            urlconnection.setDoOutput(true);
            urlconnection.setDoInput(true);

            if (urlconnection instanceof HttpURLConnection) {
                ((HttpURLConnection) urlconnection).setRequestMethod("POST");
                ((HttpURLConnection) urlconnection).setRequestProperty("Content-type", "Multipart/form-data");
                ((HttpURLConnection) urlconnection).connect();
            }



            //BufferedOutputStream bos = new BufferedOutputStream(urlconnection.getOutputStream());
            //BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            //int i;
            // read byte by byte until end of stream
            //while ((i = bis.read()) > 0) {
            //    bos.write(i);
            //}
            //bis.close();
            //bos.close();
            System.out.println(((HttpURLConnection) urlconnection).getResponseMessage());
            ((HttpURLConnection) urlconnection).disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Allows to delete file and move the file in right directory in remote server
     * @param alias Server's alias
     * @param path File's path
     * @throws IOException
     */
    public void deleteFile(String alias, String path) throws IOException {
        uploadFileBeforeDeleted(alias, path);

        URLConnection urlconnection = null;
        try {
            URL url = new URL("http://localhost:8080/file/"+alias+path);
            System.out.println(alias+path);
            urlconnection = url.openConnection();
            urlconnection.setDoOutput(true);
            urlconnection.setDoInput(true);

            if (urlconnection instanceof HttpURLConnection) {
                ((HttpURLConnection) urlconnection).setRequestMethod("DELETE");
                ((HttpURLConnection) urlconnection).connect();
            }

            System.out.println(((HttpURLConnection) urlconnection).getResponseMessage());
            ((HttpURLConnection) urlconnection).disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Server> getServers(String url, int timeout) {
        HttpURLConnection urlconnection = null;
        try {
            URL u = new URL(url);
            urlconnection = (HttpURLConnection) u.openConnection();
            urlconnection.setRequestMethod("GET");
            urlconnection.setRequestProperty("Content-length", "0");
            urlconnection.setUseCaches(false);
            urlconnection.setAllowUserInteraction(false);
            urlconnection.setConnectTimeout(timeout);
            urlconnection.setReadTimeout(timeout);
            urlconnection.connect();
            int status = urlconnection.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    Type listType = new TypeToken<List<Server>>() {}.getType();
                    final ObjectMapper objectMapper = new ObjectMapper();
                    Server[] langs = objectMapper.readValue(sb.toString(), Server[].class);
                    return new ArrayList(Arrays.asList(langs));
            }

        } catch (MalformedURLException ex) {
           System.out.println("err"+getClass().getName());
        } catch (IOException ex) {
            System.out.println("err"+getClass().getName());
        } finally {
            if (urlconnection != null) {
                try {
                    urlconnection.disconnect();
                } catch (Exception ex) {
                    System.out.println("err"+getClass().getName());
                }
            }
        }
        return null;
    }


}
