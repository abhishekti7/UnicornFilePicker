package abhishekti7.unicorn.filepicker.storage

import abhishekti7.unicorn.filepicker.R
import android.content.Context
import java.io.File

object StorageNamingHelper {
    fun getNameForDeviceDescription(
        context: Context,
        file: File,
        @StorageNaming.DeviceDescription deviceDescription: Int
    ): String {
        return when (deviceDescription) {
            StorageNaming.STORAGE_INTERNAL -> context.getString(R.string.unicorn_storage_internal)
            StorageNaming.STORAGE_SD_CARD -> context.getString(R.string.unicorn_storage_sd_card)
            StorageNaming.ROOT -> context.getString(R.string.unicorn_root_directory)
            StorageNaming.NOT_KNOWN -> file.name
            else -> file.name
        }
    }
}
