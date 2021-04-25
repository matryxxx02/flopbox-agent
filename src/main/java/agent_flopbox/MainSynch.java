package agent_flopbox;

import agent_flopbox.Models.Checksum;
import org.apache.hc.core5.http.ParseException;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MainSynch {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ParseException {
        //List<Checksum> checksums = new ArrayList<>();
        //checksums.add(new FileChecksum("public/Trame.csv","bbda60b6b6102cfd6941007d118e873d8c4739c88f26ff05268402551e66e1be"));

        Synchronizer sync = new Synchronizer("http://localhost:8080/servers/", "local");
        sync.sync();


    }
}
