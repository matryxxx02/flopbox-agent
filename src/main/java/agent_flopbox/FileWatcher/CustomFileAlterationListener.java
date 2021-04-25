package agent_flopbox.FileWatcher;

import agent_flopbox.Services.Controllers;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.io.IOException;

public class CustomFileAlterationListener implements FileAlterationListener {

    private String serverName;
    private Controllers controller;

    public CustomFileAlterationListener(String alias) {
        this.serverName = alias;
    }

    /**
     *
     * @param fileAlterationObserver
     */
    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {
        this.controller = new Controllers();
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
        System.out.println("File created : " + file.getPath())  ;
        try {
            this.controller.uploadFile(this.serverName,file.getPath());
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
            this.controller.uploadFile(this.serverName,file.getPath());
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
        System.out.println("File deleted : " + file.getAbsolutePath());
        try {
            this.controller.deleteFile(this.serverName,file.getPath());
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