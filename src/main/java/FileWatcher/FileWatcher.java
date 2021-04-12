package FileWatcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileWatcher implements Runnable {
    protected final File folder;
    protected List<FileListener> listeners = new ArrayList<>();
    protected static final List<WatchService> watchServices = new ArrayList<>();


    public FileWatcher(File file) {
        this.folder = file;
    }

    public List<FileListener> getListeners() {
        return this.listeners;
    }

    public FileWatcher addListener(FileListener l) {
        listeners.add(l);
        return this;
    }

    public FileWatcher removeListener(FileListener l) {
        listeners.remove(l);
        return this;
    }

    public FileWatcher setListeners(List<FileListener> listeners) {
        this.listeners = listeners;
        return this;
    }

    public static List<WatchService> getWatchServices() {
        return Collections.unmodifiableList(watchServices);
    }

    public void watch() {
        if (folder.exists()) {
            Thread thread = new Thread(this);
            //thread.setDaemon(true);
            thread.start();
        }
    }

    @Override
    public void run() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            Path path = Paths.get(folder.getAbsolutePath());
            path.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
            watchServices.add(watchService);
            boolean poll = true;
            while (poll) {
                poll = pollEvents(watchService);
            }
        } catch (IOException | InterruptedException | ClosedWatchServiceException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected boolean pollEvents(WatchService watchService) throws InterruptedException {
        WatchKey key = watchService.take();
        Path path = (Path) key.watchable();
        for(WatchEvent<?> event : key.pollEvents()) {
            notifyListeners(event.kind(), path.resolve((Path) event.context()).toFile());
        }
        return key.reset();
    }

    protected void notifyListeners(WatchEvent.Kind<?> kind, File file) {
        FileEvent event = new FileEvent(file);
        switch (kind.name()) {
            case("ENTRY_CREATE"):
                for (FileListener l : listeners) {
                    l.onCreated(event);
                }
                if(file.isDirectory()) {
                    System.out.println("direcotry");
                    new FileWatcher(file).setListeners(listeners).watch();
                }
                break;
            case("ENTRY_MODIFY"):
                for (FileListener l: listeners) {
                    l.onModified(event);
                }
                break;
            case("ENTRY_DELETE"):
                for (FileListener l : listeners) {
                    l.onDeleted(event);
                }
                break;
        }

    }
}
