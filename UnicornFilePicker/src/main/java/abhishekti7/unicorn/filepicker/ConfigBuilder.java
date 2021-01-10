package abhishekti7.unicorn.filepicker;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import abhishekti7.unicorn.filepicker.models.Config;
import abhishekti7.unicorn.filepicker.ui.FilePickerActivity;

/**
 * Created by Abhishek Tiwari on 09-01-2021.
 */
public final class ConfigBuilder {
    private String rootDir;
    private boolean showHidden = false;
    private boolean selectMultiple = false;
    private boolean addDivider = false;
    private boolean showOnlyDir = false;

    @StyleRes
    private int themeId = R.style.UnicornFilePicker_Default;

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

    public ConfigBuilder setFilters(String[] filters){
        this.extensionFilters = new ArrayList<>(Arrays.asList(filters));
        return this;
    }

    public ConfigBuilder addItemDivider(boolean value){
        this.addDivider = value;
        return this;
    }

    public ConfigBuilder theme(@StyleRes int theme){
        this.themeId = theme;
        return this;
    }

    public ConfigBuilder showOnlyDirectory(boolean value){
        this.showOnlyDir = value;
        return this;
    }

    public UnicornFilePicker build(){
        config.setRootDir(this.rootDir);
        config.setSelectMultiple(this.selectMultiple);
        config.setShowHidden(this.showHidden);
        config.setExtensionFilters(this.extensionFilters);
        config.setAddItemDivider(this.addDivider);
        config.setThemeId(this.themeId);
        config.setShowOnlyDirectory(this.showOnlyDir);
        return unicornFilePicker;
    }


}
