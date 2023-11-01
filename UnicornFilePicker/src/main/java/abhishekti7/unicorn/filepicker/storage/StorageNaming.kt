package abhishekti7.unicorn.filepicker.storage

import androidx.annotation.IntDef
import java.io.File

object StorageNaming {
    const val STORAGE_INTERNAL = 0
    const val STORAGE_SD_CARD = 1
    const val ROOT = 2
    const val NOT_KNOWN = 3

    /** Retrofit of [android.os.storage.StorageVolume.getDescription] to older apis  */
    @DeviceDescription
    fun getDeviceDescriptionLegacy(file: File): Int {
        val path = file.path
        return when (path) {
            "/storage/emulated/legacy", "/storage/emulated/0", "/mnt/sdcard" -> STORAGE_INTERNAL
            "/storage/sdcard", "/storage/sdcard1" -> STORAGE_SD_CARD
            "/" -> ROOT
            else -> NOT_KNOWN
        }
    }

    @IntDef(STORAGE_INTERNAL, STORAGE_SD_CARD, ROOT, NOT_KNOWN)
    annotation class DeviceDescription
}
