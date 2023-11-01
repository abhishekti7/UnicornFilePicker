package abhishekti7.unicorn.filepicker.filesystem

import android.net.Uri

class SingletonUsbOtg private constructor() {
    private var connectedDevice: UsbOtgRepresentation? = null
    private var usbOtgRoot: Uri? = null
    fun setConnectedDevice(connectedDevice: UsbOtgRepresentation?) {
        this.connectedDevice = connectedDevice
    }

    val isDeviceConnected: Boolean
        get() = connectedDevice != null

    fun setUsbOtgRoot(root: Uri?) {
        checkNotNull(connectedDevice) { "No device connected!" }
        usbOtgRoot = root
    }

    fun resetUsbOtgRoot() {
        connectedDevice = null
        usbOtgRoot = null
    }

    fun getUsbOtgRoot(): Uri? {
        return usbOtgRoot
    }

    fun checkIfRootIsFromDevice(device: UsbOtgRepresentation): Boolean {
        return usbOtgRoot != null && connectedDevice.hashCode() === device.hashCode()
    }

    companion object {
        var instance: SingletonUsbOtg? = null
            get() {
                if (field == null) field = SingletonUsbOtg()
                return field
            }
            private set
    }
}
