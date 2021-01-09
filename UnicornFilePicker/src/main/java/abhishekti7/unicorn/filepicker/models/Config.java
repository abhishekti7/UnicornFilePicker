package abhishekti7.unicorn.filepicker.models;

import java.util.ArrayList;

/**
 * Created by Abhishek Tiwari on 07-01-2021.
 */
public final class Config {

    /* Model to save configurations for the file picker */

    private boolean selectMultiple;
    private String rootDir;
    private boolean showHidden;
    private ArrayList<String> extensionFilters;

    public static Config getInstance(){
        return InstanceHolder.INSTANCE;
    }

    public static Config getCleanInstance(){
        Config config = getInstance();
        config.reset();
        return config;
    }


    private Config() {
    }

    private void reset(){
        selectMultiple = false;
        rootDir = null;
        showHidden = false;
        extensionFilters = null;
    }

    public ArrayList<String> getExtensionFilters() {
        return extensionFilters;
    }

    public void setExtensionFilters(ArrayList<String> extensionFilters) {
        this.extensionFilters = extensionFilters;
    }

    public boolean showHidden() {
        return showHidden;
    }

    public void setShowHidden(boolean showHidden) {
        this.showHidden = showHidden;
    }

    public boolean isSelectMultiple() {
        return selectMultiple;
    }

    public void setSelectMultiple(boolean selectMultiple) {
        this.selectMultiple = selectMultiple;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }


    private static final class InstanceHolder{
        private static final Config INSTANCE = new Config();
    }
}
