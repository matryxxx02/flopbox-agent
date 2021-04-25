package agent_flopbox.Models;

public class Checksum {

    private String filepath;
    private String file;

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFilepath() {
        return filepath;
    }

    public String getHash() {
        return file;
    }
}
