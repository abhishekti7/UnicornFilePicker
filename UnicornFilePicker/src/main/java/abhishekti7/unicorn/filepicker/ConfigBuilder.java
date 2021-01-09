package abhishekti7.unicorn.filepicker;

import java.util.ArrayList;

import abhishekti7.unicorn.filepicker.models.Config;

/**
 * Created by Abhishek Tiwari on 09-01-2021.
 */
public final class ConfigBuilder {
    private String rootDir;
    private boolean showHidden;
    private boolean selectMultiple;
    private final UnicornFilePicker unicornFilePicker;
    private ArrayList<String> extensionFilters;
    private Config config;

    public ConfigBuilder(UnicornFilePicker unicornFilePicker) {
        this.unicornFilePicker = unicornFilePicker;
        this.config = Config.getCleanInstance();
    }

    public ConfigBuilder setRootDirectory(String dirPath){
        this.rootDir = dirPath;
        return this;
    }

    public ConfigBuilder showHiddenFiles(boolean value){
        this.showHidden = value;
        return this;
    }

    public ConfigBuilder selectMultipleFiles(boolean value){
        this.selectMultiple = value;
        return this;
    }

    public ConfigBuilder setFilters(ArrayList<String> filters){
        this.extensionFilters = filters;
        return this;
    }

    public UnicornFilePicker build(){
        config.setRootDir(this.rootDir);
        config.setSelectMultiple(this.selectMultiple);
        config.setShowHidden(this.showHidden);
        config.setExtensionFilters(this.extensionFilters);

        return unicornFilePicker;
    }

}
