package dev.abhishekti7.filepickerexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import abhishekti7.unicorn.filepicker.UnicornFilePicker;
import abhishekti7.unicorn.filepicker.storage.StorageUtils;
import abhishekti7.unicorn.filepicker.utils.Constants;
import abhishekti7.unicorn.filepicker.utils.PermissionHelper;
import dev.abhishekti7.filepickerexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ActivityMainBinding mainBinding;
    private final int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",
    };
    public static final String SD_CARD = "sdCard";
    public static final String EXTERNAL_SD_CARD = "externalSdCard";
    private static final String ENV_SECONDARY_STORAGE = "SECONDARY_STORAGE";
    public static Map<String, File> getAllStorageLocations() {
        Map<String, File> storageLocations = new HashMap(10);
        File sdCard = Environment.getExternalStorageDirectory();
        storageLocations.put(SD_CARD, sdCard);
        final String rawSecondaryStorage = System.getenv(ENV_SECONDARY_STORAGE);
        if (!TextUtils.isEmpty(rawSecondaryStorage)) {
            String[] externalCards = rawSecondaryStorage.split(":");
            for (int i = 0; i < externalCards.length; i++) {
                String path = externalCards[i];
                storageLocations.put(EXTERNAL_SD_CARD + String.format(i == 0 ? "" : "_%d", i), new File(path));
            }
        }
        return storageLocations;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);


        PermissionHelper.INSTANCE.requestAllFilesAccess(this);
        if (allPermissionsGranted()) {
            Toast.makeText(MainActivity.this, "Permissions granted by the user.", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }


        Log.e(TAG, "GetAllLocations " + StorageUtils.INSTANCE.getStorageDirectories(this));
        File[] f = ContextCompat.getExternalFilesDirs(getApplicationContext(),null);
        for (int i=0;i< f.length;i++)
        {
            String path = f[i].getParent().replace("/Android/data/","").replace(getPackageName(),"");
            Log.d("DIRS",path); //sdcard and internal and usb
        }



        mainBinding.btnFilesDark.setOnClickListener((v)->{
            UnicornFilePicker.from(MainActivity.this)
                    .addConfigBuilder()
                    .selectMultipleFiles(true)
                    .showOnlyDirectory(true)
                    .setRootDirectory(Environment.getExternalStorageDirectory().getAbsolutePath())
                    .showHiddenFiles(false)
                    .setFilters(new String[]{"pdf", "png", "jpg", "jpeg"})
                    .addItemDivider(true)
                    .theme(R.style.UnicornFilePicker_Default_AppTheme)
                    .build()
                    .forResult(Constants.REQ_UNICORN_FILE);
        });

        mainBinding.btnFilesLight.setOnClickListener((v)->{
            UnicornFilePicker.from(MainActivity.this)
                    .addConfigBuilder()
                    .selectMultipleFiles(true)
                    .setRootDirectory(Environment.getExternalStorageDirectory().getAbsolutePath())
                    .showHiddenFiles(false)
                    .setFilters(new String[]{"pdf", "png", "jpg", "jpeg"})
                    .addItemDivider(true)
                    .theme(R.style.UnicornFilePicker_Default_AppTheme)
                    .build()
                    .forResult(Constants.REQ_UNICORN_FILE);
        });

        mainBinding.btnFilesCustom.setOnClickListener((v)->{
            UnicornFilePicker.from(MainActivity.this)
                    .addConfigBuilder()
                    .selectMultipleFiles(true)
                    .setRootDirectory(Environment.getExternalStorageDirectory().getAbsolutePath())
                    .showHiddenFiles(false)
                    .setFilters(new String[]{"pdf", "png", "jpg", "jpeg"})
                    .addItemDivider(true)
                    .theme(R.style.UnicornFilePicker_Default_AppTheme)
                    .build()
                    .forResult(Constants.REQ_UNICORN_FILE);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQ_UNICORN_FILE && resultCode == RESULT_OK){
            if(data!=null){
                ArrayList<String> files = data.getStringArrayListExtra("filePaths");
                for(String file : files){
                    Log.e(TAG, file);
                }
            }
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                Toast.makeText(MainActivity.this, "Permissions granted by the user.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}