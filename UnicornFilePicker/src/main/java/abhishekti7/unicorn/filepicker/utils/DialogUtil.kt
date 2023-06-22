package abhishekti7.unicorn.filepicker.utils

import android.app.Activity
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogUtil {
    fun showBasicDialog(
        activity: Activity,
        @StringRes title: Int,
        @StringRes message: Int,
        @StringRes postiveText: Int,
        @StringRes negativeText: Int,
        onPositiveAction : () ->Unit
    ): MaterialAlertDialogBuilder {
        val dialogBuilder: MaterialAlertDialogBuilder = MaterialAlertDialogBuilder(activity)
            .setTitle(activity.resources.getString(title))
            .setMessage(activity.resources.getString(message))
            .setPositiveButton(activity.resources.getString(postiveText)
            ) { dialog, p1 ->
                onPositiveAction()
                dialog.dismiss()
            }
            .setNegativeButton(activity.resources.getString(negativeText)){
                    dialog, p1 ->
                dialog.dismiss()
            }
        return dialogBuilder
    }
}