package agent_flopbox;

import agent_flopbox.Models.Checksum;
import agent_flopbox.Services.Controllers;
import org.apache.commons.io.FilenameUtils;
import org.apache.hc.core5.http.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Synchronizer {

    private String urlApi = "http://localhost:8080/servers/" ;
    private String serverName = "local";
    private Controllers controller;

    public String getUrlApi() {
        return urlApi;
    }

    public String getServerName() {
        return serverName;
    }

    public Controllers getController() {
        return controller;
    }

    public Synchronizer(String urlApi, String serverName) {
        this.urlApi = urlApi;
        this.serverName = serverName;
        this.controller = new Controllers(urlApi, serverName);
    }

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

    public void updateAllFilesFromRemote(String path,List<Checksum> checksums) throws IOException, NoSuchAlgorithmException {
        File currentDir = new File(path);
        System.out.println(currentDir.listFiles().length);
        if(currentDir.listFiles().length > 0){
            for(File f: currentDir.listFiles()){
                System.out.println(f.getPath());
                if(f.isFile()){
                    System.out.println(f.getPath());
                    Checksum checksum = checksums.stream()
                            .filter(fileChecksum -> FilenameUtils.separatorsToUnix(f.getPath()).equals(fileChecksum.getFilepath()))
                            .findAny()
                            .orElse(null);
                    if(checksum == null){
                        f.delete();
                    } else if (checksum.getHash() != sha256(f.getPath())){
                        controller.saveFile(checksum.getFilepath());
                        checksums.remove(checksum);
                    }
                } else if (f.isDirectory()) {
                    updateAllFilesFromRemote(f.getPath(), checksums);
                }
            }
        }
        if(checksums.size()>0){
            for(Checksum remoteFile : checksums ) {
                System.out.println("pas en local: "+remoteFile.getFilepath());
                controller.saveFile(remoteFile.getFilepath());
            }
        }

    }

    public void sync () throws IOException, NoSuchAlgorithmException, ParseException {
        List<Checksum> checksums = controller.getChecksum("");
        checksums.forEach(x-> System.out.println(x.getHash()));
        this.updateAllFilesFromRemote("./local/public/", checksums);
    }

}
