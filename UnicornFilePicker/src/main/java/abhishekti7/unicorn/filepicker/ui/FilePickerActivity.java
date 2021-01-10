package abhishekti7.unicorn.filepicker.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import abhishekti7.unicorn.filepicker.R;
import abhishekti7.unicorn.filepicker.adapters.DirectoryAdapter;
import abhishekti7.unicorn.filepicker.adapters.DirectoryStackAdapter;
import abhishekti7.unicorn.filepicker.databinding.UnicornActivityFilePickerBinding;
import abhishekti7.unicorn.filepicker.models.Config;
import abhishekti7.unicorn.filepicker.models.DirectoryModel;
import abhishekti7.unicorn.filepicker.utils.UnicornSimpleItemDecoration;

/**
 * Created by Abhishek Tiwari on 06-01-2021.
 */

public class FilePickerActivity extends AppCompatActivity {

    private static final String TAG = "FilePickerActivity";
    private UnicornActivityFilePickerBinding filePickerBinding;

    private File root_dir;
    private ArrayList<String> selected_files;
    private ArrayList<DirectoryModel> arr_dir_stack;
    private ArrayList<DirectoryModel> arr_files;

    private DirectoryStackAdapter stackAdapter;
    private DirectoryAdapter directoryAdapter;

    private final String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE",
    };

    private Config config;
    private ArrayList<String> filters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        config = Config.getInstance();
        setTheme(config.getThemeId());
        filePickerBinding = UnicornActivityFilePickerBinding.inflate(getLayoutInflater());
        View view = filePickerBinding.getRoot();
        setContentView(view);

        initConfig();
    }

    private void initConfig() {
        filters = config.getExtensionFilters();


        setSupportActionBar(filePickerBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (config.getRootDir() != null) {
            root_dir = new File(config.getRootDir());
        } else {
            root_dir = Environment.getExternalStorageDirectory();
        }
        selected_files = new ArrayList<>();
        arr_dir_stack = new ArrayList<>();
        arr_files = new ArrayList<>();

        setUpDirectoryStackView();
        setUpFilesView();

        if (allPermissionsGranted()) {
            fetchDirectory(new DirectoryModel(
                    true,
                    root_dir.getAbsolutePath(),
                    root_dir.getName(),
                    root_dir.lastModified(),
                    root_dir.listFiles() == null ? 0 : root_dir.listFiles().length
            ));
        } else {
            Log.e(TAG, "Storage permissions not granted. You have to implement it before starting the file picker");
            finish();
        }

        filePickerBinding.fabSelect.setOnClickListener((v)->{
            Intent intent = new Intent();
            if(config.showOnlyDirectory()){
                selected_files.clear();
                selected_files.add(arr_dir_stack.get(arr_dir_stack.size()-1).getPath());
            }
            intent.putStringArrayListExtra("filePaths", selected_files);
            setResult(config.getReqCode(), intent);
            setResult(RESULT_OK, intent);
            finish();
        });

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.unicorn_fabColor, typedValue, true);
        if(typedValue.data!=0){
            filePickerBinding.fabSelect.setBackgroundTintList(ColorStateList.valueOf(typedValue.data));
        }else{
            filePickerBinding.fabSelect.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.unicorn_colorAccent)));
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
                if(config.isSelectMultiple()){
                    if(selected_files.contains(fileModel.getPath())){
                        selected_files.remove(fileModel.getPath());
                    }else{
                        selected_files.add(fileModel.getPath());
                    }
                }else{
                    selected_files.clear();
                    selected_files.add(fileModel.getPath());
                }
            }
        });
        filePickerBinding.rvFiles.setAdapter(directoryAdapter);
        directoryAdapter.notifyDataSetChanged();
        if(config.addItemDivider()){
            filePickerBinding.rvFiles.addItemDecoration(new UnicornSimpleItemDecoration(FilePickerActivity.this));
        }
    }

    private void setUpDirectoryStackView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(FilePickerActivity.this, RecyclerView.HORIZONTAL, false);
        filePickerBinding.rvDirPath.setLayoutManager(layoutManager);
        stackAdapter = new DirectoryStackAdapter(FilePickerActivity.this, arr_dir_stack, model -> {
            Log.e(TAG, model.toString());
            arr_dir_stack = new ArrayList<>(arr_dir_stack.subList(0, arr_dir_stack.indexOf(model) + 1));
            setUpDirectoryStackView();
            fetchDirectory(arr_dir_stack.remove(arr_dir_stack.size() - 1));
        });

        filePickerBinding.rvDirPath.setAdapter(stackAdapter);
        stackAdapter.notifyDataSetChanged();
    }

    /**
     * Fetches list of files in a folder and filters files if filter present
     */
    private void fetchDirectory(DirectoryModel model) {
        filePickerBinding.rlProgress.setVisibility(View.VISIBLE);
        selected_files.clear();

        arr_files.clear();
        File dir = new File(model.getPath());
        File[] files_list = dir.listFiles();
        if (files_list != null) {
            for (File file : files_list) {
                DirectoryModel directoryModel = new DirectoryModel();
                directoryModel.setDirectory(file.isDirectory());
                directoryModel.setName(file.getName());
                directoryModel.setPath(file.getAbsolutePath());
                directoryModel.setLast_modif_time(file.lastModified());

                if (config.showHidden() || (!config.showHidden() && !file.isHidden())) {
                    if (file.isDirectory()) {
                        if (file.listFiles() != null)
                            directoryModel.setNum_files(file.listFiles().length);
                        arr_files.add(directoryModel);
                    } else {
                        if(!config.showOnlyDirectory()){
                            // Filter out files if filters specified
                            if(filters!=null){
                                try {
                                    // Extract the file extension
                                    String fileName = file.getName();
                                    String extension = fileName.substring(fileName.lastIndexOf("."));
                                    for (String filter : filters) {
                                        if (extension.toLowerCase().contains(filter)) {
                                            arr_files.add(directoryModel);
                                        }
                                    }
                                } catch (Exception e) {
//                                Log.e(TAG, "Encountered a file without an extension: ", e);
                                }
                            }else{
                                arr_files.add(directoryModel);
                            }
                        }
                    }
                }

            }
            Collections.sort(arr_files, new CustomFileComparator());

            arr_dir_stack.add(model);
            filePickerBinding.rvDirPath.scrollToPosition(arr_dir_stack.size() - 1);
            filePickerBinding.toolbar.setTitle(model.getName());
        }
        if (arr_files.size() == 0) {
            filePickerBinding.rlNoFiles.setVisibility(View.VISIBLE);
        } else {
            filePickerBinding.rlNoFiles.setVisibility(View.GONE);
        }
        filePickerBinding.rlProgress.setVisibility(View.GONE);
        stackAdapter.notifyDataSetChanged();
        directoryAdapter.notifyDataSetChanged();
    }

    // Custom Comparator to sort the list of files in lexicographical order
    public static class CustomFileComparator implements Comparator<DirectoryModel> {
        @Override
        public int compare(DirectoryModel o1, DirectoryModel o2) {
            if (o1.isDirectory() && o2.isDirectory()) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            } else if (o1.isDirectory() && !o2.isDirectory()) {
                return -1;
            } else if (!o1.isDirectory() && o2.isDirectory()) {
                return 1;
            } else {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.unicorn_menu_file_picker, menu);

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
                directoryAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    /**
     * This method checks whether STORAGE permissions are granted or not
     */
    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(FilePickerActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (arr_dir_stack.size() > 1) {
            // pop off top value and display
            arr_dir_stack.remove(arr_dir_stack.size() - 1);
            DirectoryModel model = arr_dir_stack.remove(arr_dir_stack.size() - 1);
            fetchDirectory(model);
        } else {
            // Nothing left in stack so exit
            Intent intent = new Intent();
            setResult(config.getReqCode(), intent);
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}