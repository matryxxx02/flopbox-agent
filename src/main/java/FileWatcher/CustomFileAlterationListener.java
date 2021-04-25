package FileWatcher;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

public class CustomFileAlterationListener implements FileAlterationListener {

    /**
     *
     * @param fileAlterationObserver
     */
    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {
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
    }

    /**
     *
     * @param file
     */
    @Override
    public void onFileChange(File file) {
        System.out.println("File changed : " + file.getAbsolutePath());
        //TODO :
    }

    /**
     *
     * @param file
     */
    @Override
    public void onFileDelete(File file) {
        System.out.println("File deleted : " + file.getAbsolutePath());
    }

    /**
     *
     * @param fileAlterationObserver
     */
    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {
    }
}