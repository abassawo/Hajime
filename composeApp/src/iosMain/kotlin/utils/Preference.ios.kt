package utils

actual fun UserSettings.putInt(key: String, value: Int) {
}

actual fun UserSettings.getInt(key: String, default: Int): Int {
    return 0
}



actual fun UserSettings.getLong(key: String, default: Long): Long {
    return 0L
}

actual fun UserSettings.putString(key: String, value: String) {

}

actual fun UserSettings.getString(key: String) : String? {
    return null
}

actual fun UserSettings.putBool(key: String, value: Boolean) {

}

actual fun UserSettings.getBool(key: String, default: Boolean): Boolean {
    return false
}