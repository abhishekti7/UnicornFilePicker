package abhishekti7.unicorn.filepicker.filesystem

class UsbOtgRepresentation(val productID: Int, val vendorID: Int, val serialNumber: String?) {
    /**
     * This does not ensure a device is equal to another! This tests parameters to know to a certain
     * degree of certanty that a device is "similar enough" to another one to be the same one.
     */
    override fun equals(obj: Any?): Boolean {
        if (obj !is UsbOtgRepresentation) return false
        val other = obj
        return productID == other.productID && vendorID == other.vendorID && (serialNumber == null && other.serialNumber == null
                || serialNumber == other.serialNumber)
    }

    override fun hashCode(): Int {
        var result = productID
        result = 37 * result + vendorID
        result = 37 * result + (serialNumber?.hashCode() ?: 0)
        return result
    }
}
