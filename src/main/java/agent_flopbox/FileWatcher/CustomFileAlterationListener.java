package agent_flopbox.FileWatcher;

import agent_flopbox.Services.Controllers;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CustomFileAlterationListener implements FileAlterationListener {

    private String serverName;
    private String urlApi;
    private Controllers controller;

    public CustomFileAlterationListener(String urlApi, String alias) {
        this.urlApi = urlApi;
        this.serverName = alias;
    }

    /**
     *
     * @param fileAlterationObserver
     */
    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {
        this.controller = new Controllers(urlApi, serverName);
    }

    /**
     *
     * @param file
     */
    @Override
    public void onDirectoryCreate(File file) {
        System.out.println("Directory created : " + file.getAbsolutePath());
        //TODO : /post server
    }

    /**
     *
     * @param file
     */
    @Override
    public void onDirectoryChange(File file) {
        System.out.println("Directory changed : " + file.getAbsolutePath());
    }

    /**
     *
     * @param file
     */
    @Override
    public void onDirectoryDelete(File file) {
        System.out.println("Directory deleted : " + file.getAbsolutePath());
        //TODO : delete /path
    }

    /**
     *
     * @param file
     */
    @Override
    public void onFileCreate(File file) {
        System.out.println("File created : " + file.getAbsolutePath())  ;
        try {
            this.controller.uploadFile2(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param file
     */
    @Override
    public void onFileChange(File file) {
        System.out.println("File changed : " + file.getAbsolutePath());
        try {
            this.controller.uploadFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param file
     */
    @Override
    public void onFileDelete(File file) {
        System.out.println(file.getName());
        try {
            FileInputStream fi = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("File deleted : " + file.getAbsolutePath());
        try {
            this.controller.deleteFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param fileAlterationObserver
     */
    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {
    }
}