package agent_flopbox.Services;


import okhttp3.ResponseBody;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class FunctionsApi {

    public FunctionsApi(){}
    /**
     * Allows to save the File form the remote server
     * @param alias Server's alias
     * @param path File's path
     * @return void
     */
    public void saveFile(String alias, String path) throws IOException {
        FileUtils.copyURLToFile(new URL("http://localhost:8080/servers/" + alias+"/" + path), new File("./"+alias+path));
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
            URL url = new URL("http://localhost:8080/servers/"+alias+path);
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
            URL url = new URL("http://localhost:8080/servers/"+alias+"/deleted/"+file.getName());
            System.out.println(alias+"/deleted/"+file.getName());
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
     * Allows to delete file and move the file in right directory in remote server
     * @param alias Server's alias
     * @param path File's path
     * @throws IOException
     */
    public void deleteFile(String alias, String path) throws IOException {
        uploadFileBeforeDeleted(alias, path);

        URLConnection urlconnection = null;
        try {
            URL url = new URL("http://localhost:8080/servers/"+alias+path);
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


}
