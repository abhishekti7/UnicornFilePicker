package dev.abhishekti7.filepickerexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import java.util.ArrayList;

import abhishekti7.unicorn.filepicker.UnicornFilePicker;
import abhishekti7.unicorn.filepicker.utils.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> filters = new ArrayList<>();
        filters.add("pdf");
        filters.add("png");
        filters.add("jpg");
        filters.add("jpeg");
        UnicornFilePicker.from(MainActivity.this)
                .addConfigBuilder()
                .selectMultipleFiles(false)
                .setRootDirectory(Environment.getExternalStorageDirectory() + "/Documents")
                .showHiddenFiles(false)
                .setFilters(filters)
                .build()
                .forResult(Constants.REQ_UNICORN_FILE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQ_UNICORN_FILE && resultCode==RESULT_OK){

        }
    }
}