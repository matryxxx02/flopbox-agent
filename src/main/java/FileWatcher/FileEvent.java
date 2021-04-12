package FileWatcher;

import java.io.File;
import java.util.EventObject;

public class FileEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param file the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public FileEvent(File file) {
        super(file);
    }

    public File getFile() {
        return (File) getSource();
    }


}
