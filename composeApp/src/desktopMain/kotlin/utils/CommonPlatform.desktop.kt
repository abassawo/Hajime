package utils

import data.LocalDataStore
import data.VimeoService
class CommonPlatform : Platform {
    override val localAppDataSource: VimeoService
        get() = LocalDataStore(resourceReader = ResourceReader())
}