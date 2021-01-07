package zeus0789.unicorn.filepicker.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import zeus0789.unicorn.filepicker.R;
import zeus0789.unicorn.filepicker.adapters.DirectoryAdapter;
import zeus0789.unicorn.filepicker.adapters.DirectoryStackAdapter;
import zeus0789.unicorn.filepicker.databinding.ActivityFilePickerBinding;
import zeus0789.unicorn.filepicker.models.DirectoryModel;
import zeus0789.unicorn.filepicker.utils.Constants;

/**
 * Created by Abhishek Tiwari on 06-01-2021.
 */

public class FilePickerActivity extends AppCompatActivity {

    private static final String TAG = "FilePickerActivity";
    private ActivityFilePickerBinding filePickerBinding;

    private final int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",
    };
    private File root_dir;
    private ArrayList<String> selected_files;
    private ArrayList<DirectoryModel> arr_dir_stack;
    private ArrayList<DirectoryModel> arr_files;

    private DirectoryStackAdapter stackAdapter;
    private DirectoryAdapter directoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.UnicornFilePicker_Default);
        filePickerBinding = ActivityFilePickerBinding.inflate(getLayoutInflater());
        View view = filePickerBinding.getRoot();
        setContentView(view);

        initConfig();
    }

    private void initConfig() {
        setSupportActionBar(filePickerBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        root_dir = Environment.getExternalStorageDirectory();
        selected_files = new ArrayList<>();
        arr_dir_stack = new ArrayList<>();
        arr_files = new ArrayList<>();

        setUpDirectoryStackView();
        setUpFilesView();

        if (allPermissionsGranted()) {
            fetchDirectory(new DirectoryModel(
                    true,
                    root_dir.getAbsolutePath(),
                    "Phone Storage",
                    root_dir.lastModified(),
                    root_dir.listFiles()==null ? 0 : root_dir.listFiles().length
            ));
        } else {
            ActivityCompat.requestPermissions(FilePickerActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

    }

    private void setUpFilesView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(FilePickerActivity.this);
        filePickerBinding.rvFiles.setLayoutManager(layoutManager);
        directoryAdapter = new DirectoryAdapter(FilePickerActivity.this, arr_files, false, new DirectoryAdapter.onFilesClickListener() {
            @Override
            public void onClicked(DirectoryModel model) {
                fetchDirectory(model);
            }

            @Override
            public void onFileSelected(DirectoryModel fileModel) {

            }
        });
        filePickerBinding.rvFiles.setAdapter(directoryAdapter);
        directoryAdapter.notifyDataSetChanged();
    }

    private void setUpDirectoryStackView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(FilePickerActivity.this, RecyclerView.HORIZONTAL, false);
        filePickerBinding.rvDirPath.setLayoutManager(layoutManager);
        stackAdapter = new DirectoryStackAdapter(FilePickerActivity.this, arr_dir_stack, model -> {
            Log.e(TAG, model.toString());
            arr_dir_stack = new ArrayList<>(arr_dir_stack.subList(0, arr_dir_stack.indexOf(model)+1));
            setUpDirectoryStackView();
            fetchDirectory(arr_dir_stack.remove(arr_dir_stack.size()-1));
        });

        filePickerBinding.rvDirPath.setAdapter(stackAdapter);
        stackAdapter.notifyDataSetChanged();
    }

    private void fetchDirectory(DirectoryModel model) {
        filePickerBinding.rlProgress.setVisibility(View.VISIBLE);


        arr_files.clear();
        File dir = new File(model.getPath());
        File[] files_list = dir.listFiles();
        for(File file : files_list){
            if(!file.isHidden()){
                DirectoryModel directoryModel = new DirectoryModel();
                directoryModel.setDirectory(file.isDirectory());
                directoryModel.setName(file.getName());
                directoryModel.setPath(file.getAbsolutePath());
                directoryModel.setLast_modif_time(file.lastModified());
                if(file.isDirectory()){
                    directoryModel.setNum_files(file.listFiles().length);
                }
                arr_files.add(directoryModel);
            }
        }
        Collections.sort(arr_files, new CustomFileComparator());
        directoryAdapter.notifyDataSetChanged();
        filePickerBinding.rlProgress.setVisibility(View.GONE);

        arr_dir_stack.add(model);
        stackAdapter.notifyDataSetChanged();
        filePickerBinding.rvDirPath.scrollToPosition(arr_dir_stack.size()-1);
        filePickerBinding.toolbar.setTitle(model.getName());
    }

    public static class CustomFileComparator implements Comparator<DirectoryModel> {
        @Override
        public int compare(DirectoryModel o1, DirectoryModel o2) {
            if(o1.isDirectory() && o2.isDirectory()){
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }else if(o1.isDirectory() && !o2.isDirectory()){
                return -1;
            }else if(!o1.isDirectory() && o2.isDirectory()){
                return 1;
            }else{
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_file_picker, menu);

        MenuItem item_search = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) item_search.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //TODO: send query to adapter
                return false;
            }
        });
        return true;
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(FilePickerActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                fetchDirectory(new DirectoryModel(
                        true,
                        root_dir.getAbsolutePath(),
                        "Phone Storage",
                        root_dir.lastModified(),
                        root_dir.listFiles()==null ? 0 : root_dir.listFiles().length
                ));
            } else {
                Toast.makeText(FilePickerActivity.this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(arr_dir_stack.size()>1){
            arr_dir_stack.remove(arr_dir_stack.size() - 1);
            DirectoryModel model = arr_dir_stack.remove(arr_dir_stack.size()-1);
            fetchDirectory(model);
        }else{
            finish();
//            Intent intent = new Intent();
//            setResult(Constants.REQ_PICK_FILE, intent);
//            setResult(RESULT_CANCELED, intent);
//            finish();
//            this.overridePendingTransition(R.anim.no_anim, R.anim.anim_bottom_down);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return true;
    }
}