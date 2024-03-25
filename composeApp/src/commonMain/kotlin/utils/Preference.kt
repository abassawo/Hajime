package utils

expect fun UserSettings.putInt(key: String, value: Int)

expect fun UserSettings.getInt(key: String, default: Int): Int

expect fun UserSettings.getLong(key: String, default: Long): Long

expect fun UserSettings.putString(key: String, value: String)

expect fun UserSettings.getString(key: String) : String?

expect fun UserSettings.putBool(key: String, value: Boolean)

expect fun UserSettings.getBool(key: String, default: Boolean): Boolean