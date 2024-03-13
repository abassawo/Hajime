package utils

internal expect class ResourceReader() {
    fun loadJsonFile(fileName: String): String?
}