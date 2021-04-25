package agent_flopbox.Models;

public class FileChecksum {

    String path;
    String hash;

    public FileChecksum(String path, String hash){
        this.path = path;
        this.hash = hash;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
