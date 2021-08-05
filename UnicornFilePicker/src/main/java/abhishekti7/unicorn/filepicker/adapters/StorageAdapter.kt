package abhishekti7.unicorn.filepicker.adapters

import abhishekti7.unicorn.filepicker.R
import abhishekti7.unicorn.filepicker.storage.StorageDirectoryParcelable
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView

import android.widget.TextView
import java.lang.IllegalArgumentException


class StorageAdapter(context: Context,private val items : List<StorageDirectoryParcelable>) : ArrayAdapter<StorageDirectoryParcelable>(context, R.layout.unicorn_storage_picker_item, items ) {
    private var inflater : LayoutInflater? = null


    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
        return rowView(convertView, position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return rowView(convertView, position) ?: throw IllegalArgumentException("Row view is null")
    }

    private fun rowView(convertView: View?, position: Int): View? {
        val rowItem: StorageDirectoryParcelable? = getItem(position)
        val holder: ViewHolder
        var rowView : View? = convertView
        if (rowView == null) {
            holder = ViewHolder()
            inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            rowView = inflater?.inflate(R.layout.unicorn_storage_picker_item, null)
            rowView?.let{
                holder.txtTitle = rowView.findViewById<View>(R.id.title) as TextView
                holder.icon = rowView.findViewById<View>(R.id.icon) as ImageView
                rowView.tag = holder
            }
        } else {
            holder = rowView.tag as ViewHolder
        }
        holder.icon?.setImageResource(rowItem?.iconRes ?: -1)
        holder.txtTitle?.text = rowItem?.name ?: ""
        return rowView
    }

    private class ViewHolder {
        var txtTitle: TextView? = null
        var icon: ImageView? = null
    }
}