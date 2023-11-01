package abhishekti7.unicorn.filepicker.storage

import abhishekti7.unicorn.filepicker.R
import abhishekti7.unicorn.filepicker.filesystem.ExternalSdCardOperation
import abhishekti7.unicorn.filepicker.filesystem.FileUtils
import abhishekti7.unicorn.filepicker.filesystem.OTGUtil
import abhishekti7.unicorn.filepicker.filesystem.SingletonUsbOtg
import abhishekti7.unicorn.filepicker.utils.Utils
import android.Manifest
import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Environment
import android.os.storage.StorageManager
import android.os.storage.StorageVolume
import android.text.TextUtils
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import java.io.*
import java.lang.Exception
import java.lang.NumberFormatException
import java.util.*
import java.util.regex.Pattern

object StorageUtils {
    private const val TAG = "StorageUtils"//flags//mount point
    private const val INTERNAL_SHARED_STORAGE = "Internal shared storage"
    private const val DEFAULT_FALLBACK_STORAGE_PATH = "/storage/sdcard0"
    val DIR_SEPARATOR = Pattern.compile("/")
    /** @return paths to all available volumes in the system (include emulated)
     */
    @JvmStatic
    @Synchronized
    fun getStorageDirectories(context : Context): ArrayList<StorageDirectoryParcelable>? {
        val volumes: ArrayList<StorageDirectoryParcelable>
        volumes = if (VERSION.SDK_INT >= VERSION_CODES.N) {
            getStorageDirectoriesNew(context)
        } else {
            getStorageDirectoriesLegacy(context)
        }
        if (isRootExplorer()) {
            volumes.add(
                StorageDirectoryParcelable(
                    "/",
                    context.getResources().getString(R.string.unicorn_root_directory),
                   -1
                )
            )
        }
        return volumes
    }

    private fun isRootExplorer(): Boolean {
        return false
    }

    /**
     * @return All available storage volumes (including internal storage, SD-Cards and USB devices)
     */
    @TargetApi(VERSION_CODES.N)
    @Synchronized
    fun getStorageDirectoriesNew(context : Context): ArrayList<StorageDirectoryParcelable> {
        // Final set of paths
        val volumes: ArrayList<StorageDirectoryParcelable> = ArrayList<StorageDirectoryParcelable>()
        val sm: StorageManager = context.getSystemService(StorageManager::class.java)
        for (volume in sm.storageVolumes) {
            if (!volume.state.equals(Environment.MEDIA_MOUNTED, ignoreCase = true)
                && !volume.state.equals(Environment.MEDIA_MOUNTED_READ_ONLY, ignoreCase = true)
            ) {
                continue
            }
            val path: File? = Utils.getVolumeDirectory(volume)
            var name = volume.getDescription(context)
            if (INTERNAL_SHARED_STORAGE.equals(name, ignoreCase = true)) {
                name = context.getString(R.string.unicorn_storage_internal)
            }
            var icon: Int = -1
            if (!volume.isRemovable) {
                icon = R.drawable.ic_baseline_smartphone_24
            } else {
                // HACK: There is no reliable way to distinguish USB and SD external storage
                // However it is often enough to check for "USB" String
                if (name.uppercase().contains("USB") || path?.path?.uppercase()?.contains("USB") == true) {
                    icon = R.drawable.ic_baseline_usb_24
                } else {
                    icon = R.drawable.ic_baseline_sd_storage_24
                }
            }
            volumes.add(StorageDirectoryParcelable(path?.path ?: "", name, icon))
        }
        return volumes
    }

    /**
     * Returns all available SD-Cards in the system (include emulated)
     *
     *
     * Warning: Hack! Based on Android source code of version 4.3 (API 18) Because there was no
     * standard way to get it before android N
     *
     * @return All available SD-Cards in the system (include emulated)
     */
    @Synchronized
    fun getStorageDirectoriesLegacy(context : Context): ArrayList<StorageDirectoryParcelable> {
        val rv: MutableList<String> = ArrayList()

        // Primary physical SD-CARD (not emulated)
        val rawExternalStorage = System.getenv("EXTERNAL_STORAGE")
        // All Secondary SD-CARDs (all exclude primary) separated by ":"
        val rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE")
        // Primary emulated SD-CARD
        val rawEmulatedStorageTarget = System.getenv("EMULATED_STORAGE_TARGET")
        if (TextUtils.isEmpty(rawEmulatedStorageTarget)) {
            // Device has physical external storage; use plain paths.
            if (TextUtils.isEmpty(rawExternalStorage)) {
                // EXTERNAL_STORAGE undefined; falling back to default.
                // Check for actual existence of the directory before adding to list
                if (File(DEFAULT_FALLBACK_STORAGE_PATH).exists()) {
                    rv.add(DEFAULT_FALLBACK_STORAGE_PATH)
                } else {
                    // We know nothing else, use Environment's fallback
                    rv.add(Environment.getExternalStorageDirectory().absolutePath)
                }
            } else {
                rv.add(rawExternalStorage)
            }
        } else {
            // Device has emulated storage; external storage paths should have
            // userId burned into them.
            val rawUserId: String
            if (VERSION.SDK_INT < VERSION_CODES.JELLY_BEAN_MR1) {
                rawUserId = ""
            } else {
                val path = Environment.getExternalStorageDirectory().absolutePath
                val folders: Array<String> = DIR_SEPARATOR.split(path)
                val lastFolder = folders[folders.size - 1]
                var isDigit = false
                try {
                    Integer.valueOf(lastFolder)
                    isDigit = true
                } catch (ignored: NumberFormatException) {
                }
                rawUserId = if (isDigit) lastFolder else ""
            }
            // /storage/emulated/0[1,2,...]
            if (TextUtils.isEmpty(rawUserId)) {
                rv.add(rawEmulatedStorageTarget)
            } else {
                rv.add(rawEmulatedStorageTarget + File.separator + rawUserId)
            }
        }
        // Add all secondary storages
        if (!TextUtils.isEmpty(rawSecondaryStoragesStr)) {
            // All Secondary SD-CARDs splited into array
            val rawSecondaryStorages =
                rawSecondaryStoragesStr.split(File.pathSeparator).toTypedArray()
            Collections.addAll(rv, *rawSecondaryStorages)
        }
        if (VERSION.SDK_INT >= VERSION_CODES.M && checkStoragePermission(context)) rv.clear()
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            val strings: Array<String> = ExternalSdCardOperation.getExtSdCardPathsForActivity(context)
            for (s in strings) {
                val f = File(s)
                if (!rv.contains(s) && FileUtils.canListFiles(f)) rv.add(s)
            }
        }
        val usb: File? = getUsbDrive()
        if (usb != null && !rv.contains(usb.path)) rv.add(usb.path)
        if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
            if (SingletonUsbOtg.instance?.isDeviceConnected == true) {
                rv.add(OTGUtil.PREFIX_OTG.toString() + "/")
            }
        }

        // Assign a label and icon to each directory
        val volumes: ArrayList<StorageDirectoryParcelable> = ArrayList<StorageDirectoryParcelable>()
        for (file in rv) {
            val f = File(file)
            @DrawableRes var icon: Int = -1
            if ("/storage/emulated/legacy" == file || "/storage/emulated/0" == file || "/mnt/sdcard" == file) {
                icon = R.drawable.ic_baseline_smartphone_24
            } else if ("/storage/sdcard1" == file) {
               icon = R.drawable.ic_baseline_sd_storage_24
            } else if ("/" == file) {
               // icon = R.drawable.ic_drawer_root_white
            } else {
                icon = R.drawable.ic_baseline_sd_storage_24
            }
            @StorageNaming.DeviceDescription val deviceDescription: Int =
                StorageNaming.getDeviceDescriptionLegacy(f)
            val name: String =
                StorageNamingHelper.getNameForDeviceDescription(context , f, deviceDescription)
            volumes.add(StorageDirectoryParcelable(file, name, icon))
        }
        return volumes
    }
    fun checkStoragePermission(context: Context): Boolean {
        // Verify that all required contact permissions have been granted.
        return (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
    }

    fun getUsbDrive(): File? {
        var parent = File("/storage")
        try {
            for (f in parent.listFiles()) if (f.exists() && f.name.toLowerCase()
                    .contains("usb") && f.canExecute()
            ) return f
        } catch (e: Exception) {
        }
        parent = File("/mnt/sdcard/usbStorage")
        if (parent.exists() && parent.canExecute()) return parent
        parent = File("/mnt/sdcard/usb_storage")
        return if (parent.exists() && parent.canExecute()) parent else null
    }

}