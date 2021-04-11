package agent_flopbox;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileWatcher {

    public static void main(String[] args) throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get("public");
        path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);

        boolean poll = true;
        while (poll) {

            WatchKey key = watchService.take();

            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("Event kind : " + event.kind() + " - File : " + event.context());
            }
            poll = key.reset();
        }
    }
}
