package zeus0789.unicorn.filepicker.models;

/**
 * Created by Abhishek Tiwari on 07-01-2021.
 */
public class Config {

    /* Model to save configurations for the file picker */

    private boolean selectMultiple;
    private String rootDir;
    private boolean showHidden;

    public boolean isShowHidden() {
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
}
