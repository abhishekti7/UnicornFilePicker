package abhishekti7.unicorn.filepicker.models;

import androidx.annotation.IdRes;
import androidx.annotation.StyleRes;

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
    private int reqCode;
    private boolean addItemDivider;
    private boolean showOnlyDirectory;

    @StyleRes
    private int themeId;

    @IdRes
    private int ic_folder;

    @IdRes
    private int ic_arrow;

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
        addItemDivider = false;
    }

    public ArrayList<String> getExtensionFilters() {
        return extensionFilters;
    }

    public void setExtensionFilters(ArrayList<String> extensionFilters) {
        this.extensionFilters = extensionFilters;
    }

    public boolean showOnlyDirectory() {
        return showOnlyDirectory;
    }

    public void setShowOnlyDirectory(boolean showOnlyDirectory) {
        this.showOnlyDirectory = showOnlyDirectory;
    }

    public boolean addItemDivider() {
        return addItemDivider;
    }

    public void setAddItemDivider(boolean addItemDivider) {
        this.addItemDivider = addItemDivider;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(@StyleRes int themeId) {
        this.themeId = themeId;
    }

    public int getReqCode() {
        return reqCode;
    }

    public void setReqCode(int reqCode) {
        this.reqCode = reqCode;
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

    public void setFolderIcon(int folderRes){
        this.ic_folder = folderRes;
    }

    public int getFolderIcon(){
        return this.ic_folder;
    }


    public void setArrowIcon(int arrowRes){
        this.ic_arrow = arrowRes;
    }

    public int getArrowIcon(){
        return this.ic_arrow;
    }

    private static final class InstanceHolder{
        private static final Config INSTANCE = new Config();
    }
}
