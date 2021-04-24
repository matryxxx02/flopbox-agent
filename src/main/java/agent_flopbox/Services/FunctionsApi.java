package agent_flopbox.Services;

import okhttp3.ResponseBody;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import static org.glassfish.jersey.client.ClientProperties.CONNECT_TIMEOUT;
import static org.glassfish.jersey.client.ClientProperties.READ_TIMEOUT;

public class FunctionsApi {

    public FunctionsApi(){}
    /**
     *
     * @param
     * @param body
     * @return
     */
    public void saveFile2(ResponseBody body) throws IOException {
        FileUtils.copyURLToFile(new URL("http://localhost:8080/file/home/Main.py"), new File("./public/new.txt"), 10, 1000);
    }

    public boolean saveFile(ResponseBody body) {
        System.out.println(">> DEBUG : SaveFile Function");
        try {
            File futureFile = new File("get.txt");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    System.out.println("file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();
                inputStream.close();
                outputStream.close();
                System.exit(0);
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}
