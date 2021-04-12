package FileWatcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class mainTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        FileWatcherAdapter adapter = new FileWatcherAdapter();
        File folder = new File("public");
        System.out.println(folder);
        FileWatcher watcher = new FileWatcher(folder);
        watcher.addListener(adapter).watch();

//        WatchService watchService = FileSystems.getDefault().newWatchService();
//        Path path = Paths.get("public");
//        path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
//
//        boolean poll = true;
//        while (poll) {
//
//            WatchKey key = watchService.take();
//
//            for (WatchEvent<?> event : key.pollEvents()) {
//                System.out.println("Event kind : " + event.kind() + " - File : " + event.context());
//                System.out.println(event.toString());
//            }
//            poll = key.reset();
//        }
    }
}
