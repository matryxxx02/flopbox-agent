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
     *
     * @param
     * @param body
     * @return
     */
    public void saveFile(ResponseBody body,String alias, String path) throws IOException {
        FileUtils.copyURLToFile(new URL("http://localhost:8080/servers/" + alias+"/" + path), new File("./"+alias+path));
    }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            InputStream inputStream;
            int responseCode = ((HttpURLConnection) urlconnection).getResponseCode();
            if ((responseCode >= 200) && (responseCode <= 202)) {
                inputStream = ((HttpURLConnection) urlconnection).getInputStream();
                int j;
                while ((j = inputStream.read()) > 0) {
                    System.out.println(j);
                }

            } else {
                inputStream = ((HttpURLConnection) urlconnection).getErrorStream();
            }
            ((HttpURLConnection) urlconnection).disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            InputStream inputStream;
            int responseCode = ((HttpURLConnection) urlconnection).getResponseCode();
            if ((responseCode >= 200) && (responseCode <= 202)) {
                inputStream = ((HttpURLConnection) urlconnection).getInputStream();
                int j;
                while ((j = inputStream.read()) > 0) {
                    System.out.println(j);
                }

            } else {
                inputStream = ((HttpURLConnection) urlconnection).getErrorStream();
            }
            ((HttpURLConnection) urlconnection).disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            InputStream inputStream;
            int responseCode = ((HttpURLConnection) urlconnection).getResponseCode();
            if ((responseCode >= 200) && (responseCode <= 202)) {
                inputStream = ((HttpURLConnection) urlconnection).getInputStream();
                int j;
                while ((j = inputStream.read()) > 0) {
                    System.out.println(j);
                }

            } else {
                inputStream = ((HttpURLConnection) urlconnection).getErrorStream();
            }
            ((HttpURLConnection) urlconnection).disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
