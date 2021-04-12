package FileWatcher;

public class FileWatcherAdapter implements FileListener {

    @Override
    public void onCreated(FileEvent event) {
        // TODO : send to API the created file or dir
        System.out.println("file.created "+ event.getFile().getName());
    }

    @Override
    public void onModified(FileEvent event) {
        // TODO : send to API the new version of modified file
        System.out.println("file.modified "+ event.getFile().getName());
    }

    @Override
    public void onDeleted(FileEvent event) {
        // TODO : remove file from the API
        System.out.println("file.deleted "+ event.getFile().getName());
    }
}
