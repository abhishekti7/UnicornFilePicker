package abhishekti7.unicorn.filepicker.filesystem

import android.content.Context
import android.hardware.usb.UsbConstants
import android.hardware.usb.UsbManager
import android.os.Build
import android.provider.DocumentsContract
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.documentfile.provider.DocumentFile

object OTGUtil {

    private val TAG = OTGUtil::class.java.simpleName
    const val PREFIX_OTG = "otg:/"
    const val PREFIX_MEDIA_REMOVABLE = "/mnt/media_rw"


}
