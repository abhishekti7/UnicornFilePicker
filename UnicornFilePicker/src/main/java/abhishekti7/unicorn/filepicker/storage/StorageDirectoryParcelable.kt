package abhishekti7.unicorn.filepicker.storage

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes

/** Identifies a mounted volume  */
class StorageDirectoryParcelable : Parcelable {
    val path: String
    val name: String

    @DrawableRes
    val iconRes: Int

    constructor(path: String, name: String, iconRes: Int) {
        this.path = path
        this.name = name
        this.iconRes = iconRes
    }

    constructor(im: Parcel) {
        path = im.readString()!!
        name = im.readString()!!
        iconRes = im.readInt()
    }

    override fun toString(): String {
        return "StorageDirectory(path=$path, name=$name, icon=$iconRes)"
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(path)
        parcel.writeString(name)
        parcel.writeInt(iconRes)
    }


    companion object CREATOR : Parcelable.Creator<StorageDirectoryParcelable> {
        override fun createFromParcel(parcel: Parcel): StorageDirectoryParcelable {
            return StorageDirectoryParcelable(parcel)
        }

        override fun newArray(size: Int): Array<StorageDirectoryParcelable?> {
            return arrayOfNulls(size)
        }
    }
}
