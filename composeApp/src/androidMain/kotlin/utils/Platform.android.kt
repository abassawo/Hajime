package utils

import android.content.Context
import data.LocalDataStore
import data.VimeoService

class AndroidPlatform(private val context: Context) : Platform {
    override val localAppDataSource: VimeoService
        get() = LocalDataStore(resourceReader = ResourceReader(context))
}