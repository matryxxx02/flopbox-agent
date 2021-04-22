package FileWatcher;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

public class CustomFileAlterationListener implements FileAlterationListener {
    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {
    }

    @Override
    public void onDirectoryCreate(File file) {
        System.out.println("Directory created : " + file.getAbsolutePath());
        //TODO : /post server
    }

    @Override
    public void onDirectoryChange(File file) {
        System.out.println("Directory changed : " + file.getAbsolutePath());
    }

    @Override
    public void onDirectoryDelete(File file) {
        System.out.println("Directory deleted : " + file.getAbsolutePath());
        //TODO : delete /path
    }

    @Override
    public void onFileCreate(File file) {
        System.out.println("File created : " + file.getAbsolutePath())  ;
    }

    @Override
    public void onFileChange(File file) {
        System.out.println("File changed : " + file.getAbsolutePath());
        //TODO :
    }

    @Override
    public void onFileDelete(File file) {
        System.out.println("File deleted : " + file.getAbsolutePath());
    }

    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {
    }
}