package agent_flopbox.Services;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Allows to manage actions between local files and remite files
 */
public class Controllers {

    private String urlApi;
    private String serverName;

    public Controllers(String urlApi, String serverName){
        this.urlApi = urlApi;
        this.serverName = serverName;
    }

    /**
     * Allows to save the File form the remote server
     * @param path File's path
     * @return void
     */
    public void saveFile(String path) throws IOException {
        FileUtils.copyURLToFile(new URL(urlApi + serverName+"/" + path), new File("./"+serverName+path));
    }

    public void uploadFile2(File file) throws IOException {
        String url = urlApi + serverName+"/" + FilenameUtils.separatorsToUnix(file.getPath());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost uploadFile = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        // This attaches the file to the POST:
        builder.addBinaryBody(
                "file",
                new FileInputStream(file),
                ContentType.APPLICATION_OCTET_STREAM,
                file.getName()
        );

        HttpEntity multipart = builder.build();
        uploadFile.setEntity(multipart);
        CloseableHttpResponse response = httpClient.execute(uploadFile);
    }

    /**
     * Allows to upload local file to remote server
     * @throws IOException
     */
    public void uploadFile(File file) throws  IOException{

        URLConnection urlconnection = null;
        try {
            URL url = new URL(urlApi + serverName + "/" + FilenameUtils.separatorsToUnix(file.getPath()));
            System.out.println(urlApi + serverName + "/" + FilenameUtils.separatorsToUnix(file.getPath()));

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
     * @throws IOException
     */
    public void uploadFileBeforeDeleted(File file) throws  IOException{

        URLConnection urlconnection = null;
        try {
            URL url = new URL(urlApi+serverName+"/deleted/"+FilenameUtils.separatorsToUnix(file.getPath()));
            System.out.println(serverName+"/deleted/"+FilenameUtils.separatorsToUnix(file.getPath()));
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
     * @throws IOException
     */
    public void deleteFile(File file) throws IOException {
        uploadFileBeforeDeleted(file);

        URLConnection urlconnection = null;
        try {
            URL url = new URL(urlApi+serverName+"/"+FilenameUtils.separatorsToUnix(file.getPath()));
            System.out.println(urlApi+serverName+"/"+FilenameUtils.separatorsToUnix(file.getPath()));
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
