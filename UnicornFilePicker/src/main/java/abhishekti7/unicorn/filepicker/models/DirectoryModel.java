package abhishekti7.unicorn.filepicker.models;

/**
 * Created by Abhishek Tiwari on 07-01-2021.
 */
public class DirectoryModel {

    private boolean isDirectory;
    private String path;
    private String name;
    private long last_modif_time;
    private int num_files;

    public DirectoryModel(){

    }

    public DirectoryModel(boolean isDirectory, String path, String name, long last_modif_time, int num_files) {
        this.isDirectory = isDirectory;
        this.path = path;
        this.name = name;
        this.last_modif_time = last_modif_time;
        this.num_files = num_files;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLast_modif_time() {
        return last_modif_time;
    }

    public void setLast_modif_time(long last_modif_time) {
        this.last_modif_time = last_modif_time;
    }

    public int getNum_files() {
        return num_files;
    }

    public void setNum_files(int num_files) {
        this.num_files = num_files;
    }


    @Override
    public String toString() {
        return "DirectoryModel{" +
                "isDirectory=" + isDirectory +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", last_modif_time=" + last_modif_time +
                ", num_files=" + num_files +
                '}';
    }

}
