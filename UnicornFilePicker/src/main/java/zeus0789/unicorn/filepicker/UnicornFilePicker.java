package zeus0789.unicorn.filepicker;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

import zeus0789.unicorn.filepicker.ui.FilePickerActivity;

/**
 * Created by Abhishek Tiwari on 07-01-2021.
 */

/* This is the configuration class for File Picker */
public class UnicornFilePicker {
    
    private final WeakReference<Activity> mActivity;
    private final WeakReference<Fragment> mContext;

    private UnicornFilePicker(Activity activity){
        this(activity, null);
    }

    private UnicornFilePicker(Fragment fragment){
        this(fragment.getActivity(), fragment);
    }

    public UnicornFilePicker(Activity activity, Fragment fragment) {
        this.mActivity = new WeakReference<>(activity);
        this.mContext = new WeakReference<>(fragment);
    }

    /**
     * Start UnicornFilePicker from an activity
     *
     * @param activity Activity instance
     * @return UnicornFilePicker instance
     */
    public static UnicornFilePicker from(Activity activity){
        return new UnicornFilePicker(activity);
    }

    /**
     * Start UnicornFilePicker from a fragment
     *
     * @param fragment Fragment instance
     * @return UnicornFilePicker instance
     */
    public static UnicornFilePicker from(Fragment fragment){
        return new UnicornFilePicker(fragment);
    }

    /**
     * Start FilePicker activity and wait for result
     * @param requestCode Integer identity for Activity or Fragment request
     */
    public void forResult(int requestCode){
        Activity activity = getActivity();
        if(activity==null){
            return;
        }

        Intent intent = new Intent(activity, FilePickerActivity.class);

        Fragment fragment = getFragment();
        if(fragment==null){
            activity.startActivity(intent);
        }else{
            fragment.startActivity(intent);
        }
    }


    @Nullable
    Activity getActivity(){
        return mActivity.get();
    }

    @Nullable
    Fragment getFragment(){
        return mContext.get();
    }


    public static class Builder{

        private String rootDir;
        private boolean showHidden;
        private boolean selectMultiple;

        public Builder showHidden(boolean showHiddenFiles){
            this.showHidden = showHiddenFiles;
            return this;
        }

        public Builder selectMultiple(boolean selectMultipleFiles){
            this.selectMultiple = selectMultipleFiles;
            return this;
        }
    }
}
