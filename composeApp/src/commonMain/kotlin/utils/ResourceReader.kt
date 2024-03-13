package utils

expect class ResourceReader {
    suspend fun loadJsonFile(): String?

}