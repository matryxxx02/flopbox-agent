package agent_flopbox.Services;

import agent_flopbox.Models.Checksum;
import agent_flopbox.Models.Server;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    protected int TIMEOUT = 10000;

    public Controllers(){}
    /**
     * Allows to save the File form the remote server
     * @param path File's path
     * @return void
     */
    public void saveFile(String path) throws IOException {
        String unixPath = FilenameUtils.separatorsToUnix(path);
        System.out.println(urlApi + serverName+"/" + unixPath);
        FileUtils.copyURLToFile(new URL(urlApi + serverName+"/" + unixPath), new File("./"+serverName+"/"+unixPath));
    }

    /**
     *
     * @param file
     * @throws IOException
     */
    public void uploadFile(File file) throws IOException {
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
     * Allows to delete file and move the file in right directory in remote server
     * @throws IOException
     */
    public void deleteFile(File file) throws IOException {

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

    /**
     * Allows to Flopbox's servers
     * @return List of servers
     */
    public List<Server> getServers() {
        HttpURLConnection urlconnection = null;
        try {
            URL u = new URL(urlApi);
            urlconnection = (HttpURLConnection) u.openConnection();
            urlconnection.setRequestMethod("GET");
            urlconnection.setRequestProperty("Content-length", "0");
            urlconnection.setUseCaches(false);
            urlconnection.setAllowUserInteraction(false);
            urlconnection.setConnectTimeout(TIMEOUT);
            urlconnection.setReadTimeout(TIMEOUT);
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
                    final ObjectMapper objectMapper = new ObjectMapper();
                    Server[] servers = objectMapper.readValue(sb.toString(), Server[].class);
                    return new ArrayList(Arrays.asList(servers));
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

    public List<Checksum> getChecksum2(String path) throws IOException, ParseException {

        String url = urlApi + serverName+"/" + FilenameUtils.separatorsToUnix(path);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet connection = new HttpGet(url);
        HttpResponse response = httpClient.execute(connection);
        String checksums= EntityUtils.toString(((CloseableHttpResponse) response).getEntity(), "UTF-8");
        final ObjectMapper objectMapper = new ObjectMapper();
        Checksum[] checksumList = objectMapper.readValue(checksums, Checksum[].class);
        return Arrays.asList(checksumList);

    }

    /**
     * Allows to get hash / checksum from directory
     * @param path : Directory's path
     * @return
     */
    public List<Checksum> getChecksum(String path) {
        HttpURLConnection urlconnection = null;
        try {
            URL u = new URL(urlApi+serverName+"/checksum/public");
            System.out.println(urlApi+serverName+"/checksum/public");
            urlconnection = (HttpURLConnection) u.openConnection();
            urlconnection.setRequestMethod("GET");
            urlconnection.setUseCaches(false);
            urlconnection.setAllowUserInteraction(false);
            urlconnection.setConnectTimeout(TIMEOUT);
            urlconnection.setReadTimeout(TIMEOUT);
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
                    final ObjectMapper objectMapper = new ObjectMapper();
                    Checksum[] checksums = objectMapper.readValue(sb.toString(), Checksum[].class);
                    urlconnection.disconnect();
                    return new ArrayList(Arrays.asList(checksums));
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
