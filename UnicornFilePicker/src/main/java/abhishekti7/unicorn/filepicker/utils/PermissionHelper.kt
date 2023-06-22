package abhishekti7.unicorn.filepicker.utils

import abhishekti7.unicorn.filepicker.R
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
object PermissionHelper {
    private val TAG = PermissionHelper::class.java.simpleName
    /**
     * Request all files access on android 11+
     *
     * @param onPermissionGranted permission granted callback
     */
     fun requestAllFilesAccess(activity: Activity) {
        activity?.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
                val materialDialog: MaterialAlertDialogBuilder = DialogUtil.showBasicDialog(
                    activity = activity,
                    title = R.string.unicorn_grant_permission,
                    message = R.string.unicorn_grant_all_files_permission,
                    postiveText = R.string.unicorn_grant,
                    negativeText = R.string.unicorn_cancel,
                ){
                    try {
                        val intent =
                            Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                                .setData(Uri.parse("package:" + activity.packageName))
                        activity.startActivity(intent)
                    } catch (e: java.lang.Exception) {
                        Log.e(
                            TAG,
                            "Failed to initial activity to grant all files access",
                            e
                        )
                        Toast.makeText(activity, activity.resources.getText(
                            R.string.unicorn_error_permission_not_granted), Toast.LENGTH_LONG).show()
                    }
                }
                materialDialog.setCancelable(false)
                materialDialog.show()
            }
        }
    }

}