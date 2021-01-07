package dev.zeus0789.filepickerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import zeus0789.unicorn.filepicker.UnicornFilePicker;
import zeus0789.unicorn.filepicker.ui.FilePickerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UnicornFilePicker.from(MainActivity.this)
                .forResult(0);
    }
}