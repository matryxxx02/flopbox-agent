package FileWatcher;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FileWatcher {
    public final static long INTERVAL = 1000;

    public static void main(String[] args) throws Exception {
        String directoryToWatch = "public";

        FileAlterationObserver observer = new FileAlterationObserver(directoryToWatch);
        FileAlterationMonitor monitor = new FileAlterationMonitor(INTERVAL);
        FileAlterationListener listener = new CustomFileAlterationListener();
        observer.addListener(listener);
        monitor.addObserver(observer);
        monitor.start();
        System.out.println("Starting watching files...");
        while (true) ;
    }
}
