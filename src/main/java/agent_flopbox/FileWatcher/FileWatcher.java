package agent_flopbox.FileWatcher;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileWatcher {
    public final static long INTERVAL = 1000;

    public static void main(String[] args) throws Exception {
        String directoryToWatch = "public/blabla";
        String urlApi = "http://localhost:8080/servers/";
        String serverName = "local";

        FileAlterationObserver observer = new FileAlterationObserver(directoryToWatch);
        FileAlterationMonitor monitor = new FileAlterationMonitor(INTERVAL);
        FileAlterationListener listener = new CustomFileAlterationListener(urlApi, serverName);
        observer.addListener(listener);
        monitor.addObserver(observer);
        monitor.start();

        System.out.println("Starting watching files...");

        while (true) ;
    }
}
