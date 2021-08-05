package abhishekti7.unicorn.filepicker.utils

import android.annotation.TargetApi
import android.os.Build
import android.os.storage.StorageVolume
import java.io.File
import java.lang.Exception
import java.lang.RuntimeException
import java.util.*

/**
 * Created by Abhishek Tiwari on 07-01-2021.
 */
object Utils {
    var mapOfMonths: HashMap<Int, String> = object : HashMap<Int, String>() {
        init {
            put(1, "Jan")
            put(2, "Feb")
            put(3, "Mar")
            put(4, "Apr")
            put(5, "May")
            put(6, "Jun")
            put(7, "Jul")
            put(8, "Aug")
            put(9, "Sep")
            put(10, "Oct")
            put(11, "Nov")
            put(12, "Dec")
        }
    }

    @JvmStatic
    fun longToReadableDate(time: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return mapOfMonths[calendar[Calendar.MONTH] + 1].toString() + " " +
                calendar[Calendar.DAY_OF_MONTH] + ", " + calendar[Calendar.YEAR]
    }

    @JvmStatic
    @TargetApi(Build.VERSION_CODES.N)
    fun getVolumeDirectory(volume: StorageVolume?): File? {
        return try {
            val f = StorageVolume::class.java.getDeclaredField("mPath")
            f.isAccessible = true
            f[volume] as File
        } catch (e: Exception) {
            // This shouldn't fail, as mPath has been there in every version
            throw RuntimeException(e)
        }
    }
}