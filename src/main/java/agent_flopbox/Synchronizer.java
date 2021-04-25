package agent_flopbox;

import agent_flopbox.Models.FileChecksum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Synchronizer {
    private static String sha256(String filepath) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // file hashing with DigestInputStream
        try (DigestInputStream dis = new DigestInputStream(new FileInputStream(filepath), md)) {
            while (dis.read() != -1) ; //empty loop to clear the data
            md = dis.getMessageDigest();
        }

        // bytes to hex
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        List<FileChecksum> checksums = new ArrayList<>();
        checksums.add(new FileChecksum("public/Trame.csv","bbda60b6b6102cfd6941007d118e873d8c4739c88f26ff05268402551e66e1be"));
        File mainDir = new File("public");

        for(File f: mainDir.listFiles()){
            if(f.isFile()){
                System.out.println(f.getPath());
                FileChecksum checksum = checksums.stream()
                        .filter(fileChecksum -> f.getPath().equals(fileChecksum.getPath()))
                        .findAny()
                        .orElse(null);
                if(checksum == null){
                    //TODO delete the file
                } else if (checksum.getHash() != sha256(f.getPath())){
                    //TODO download the file
                }
            } else if (f.isDirectory()) {

            }
        }
    }
}
