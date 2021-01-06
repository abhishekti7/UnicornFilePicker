package zeus0789.unicorn.filepicker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import zeus0789.unicorn.filepicker.R;
import zeus0789.unicorn.filepicker.models.DirectoryModel;
import zeus0789.unicorn.filepicker.utils.Utils;


/**
 * Created by Abhishek Tiwari on 05-01-2021.
 */
public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<DirectoryModel> filesList;
    private ArrayList<DirectoryModel> filesListFiltered;
    private onFilesClickListener onFilesClickListener;
    private List<Integer> selected;
    private boolean canSelectMultiple;

    @Override
    public Filter getFilter() {
        return tempFilter;
    }

    private Filter tempFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<DirectoryModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList = filesList;
            } else {
                String filteredPattern = constraint.toString().toLowerCase().trim();
                for (DirectoryModel model : filesList) {
                    if (model.getName().toLowerCase().contains(filteredPattern)) {
                        filteredList.add(model);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filesListFiltered = (ArrayList<DirectoryModel>) results.values;
            notifyDataSetChanged();
        }
    };


    public interface onFilesClickListener {
        void onClicked(DirectoryModel model);

        void onFileSelected(DirectoryModel fileModel);
    }

    public DirectoryAdapter(Context context, ArrayList<DirectoryModel> list, boolean selectMultiple, onFilesClickListener onFilesClickListener) {
        this.context = context;
        this.filesList = list;
        this.filesListFiltered = list;
        this.onFilesClickListener = onFilesClickListener;
        this.selected = new ArrayList<>();
        this.canSelectMultiple = selectMultiple;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.item_layout_directory, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_layout_files, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (filesListFiltered.get(position).isDirectory()) {
            holder.tv_folder_name.setText(filesListFiltered.get(position).getName());
            holder.tv_num_files.setText(filesListFiltered.get(position).getNum_files() + " files");
        } else {
            holder.tv_file_name.setText(filesListFiltered.get(position).getName());
        }

        if (!filesListFiltered.get(position).isDirectory()) {
            if (selected.contains(position)) {
                holder.rl_file_root.setBackgroundColor(context.getResources().getColor(R.color.white));
                holder.rg_selected.setVisibility(View.VISIBLE);
            } else {
                holder.rl_file_root.setBackgroundColor(context.getResources().getColor(R.color.myColorPrimary));
                holder.rg_selected.setVisibility(View.GONE);
            }
        }

        holder.tv_date.setText(Utils.longToReadableDate(filesListFiltered.get(position).getLast_modif_time()));
        holder.itemView.setOnClickListener((v) -> {
            if (filesListFiltered.get(position).isDirectory()) {
                onFilesClickListener.onClicked(filesListFiltered.get(position));
            } else {
                if (canSelectMultiple) {
                    if (selected.contains(position)) {
                        selected.remove((Integer) position);
                        notifyItemChanged(position);
                        holder.rg_selected.setVisibility(View.GONE);
                        holder.rl_file_root.setBackgroundColor(context.getResources().getColor(R.color.myColorPrimary));
                    } else {
                        selected.add(position);
                        holder.rg_selected.setVisibility(View.VISIBLE);
                        holder.rl_file_root.setBackgroundColor(context.getResources().getColor(R.color.white));
                        notifyItemChanged(position);
                    }
                } else {
                    /* if selection is empty, add the current item */
                    if (selected.size()==0) {
                        selected.add(0, position);
                        notifyItemChanged(position);
                    }
                    /* if item already selected then remove it */
                    else if (selected.get(0) == position) {
                        selected.remove(0);
                        notifyItemChanged(position);
                    }
                    /* if another item selected, then remove and then add current item */
                    else{
                        int temp = selected.remove(0);
                        selected.add(0, position);
                        notifyItemChanged(temp);
                        notifyItemChanged(position);
                    }
                }
                onFilesClickListener.onFileSelected(filesListFiltered.get(position));
            }
        });
    }

    /**
     * resets the value of selected so that UI gets updated
     */
    public void resetSelection() {
        this.selected = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (filesListFiltered.get(position).isDirectory()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public int getItemCount() {
        return filesListFiltered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_folder_name;
        private TextView tv_file_name;
        private TextView tv_date;
        private TextView tv_num_files;
        private RadioButton rg_selected;
        private RelativeLayout rl_file_root;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_file_name = itemView.findViewById(R.id.tv_file_name);
            tv_folder_name = itemView.findViewById(R.id.tv_folder_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_num_files = itemView.findViewById(R.id.tv_num_files);
            rg_selected = itemView.findViewById(R.id.rg_selected);
            rl_file_root = itemView.findViewById(R.id.rl_file_root);
        }
    }
}
