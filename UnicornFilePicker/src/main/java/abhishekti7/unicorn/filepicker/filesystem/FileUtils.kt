package abhishekti7.unicorn.filepicker.filesystem

import java.io.File
/** Functions that deal with files  */
object FileUtils {

    fun canListFiles(f: File): Boolean {
        return f.canRead() && f.isDirectory
    }


}