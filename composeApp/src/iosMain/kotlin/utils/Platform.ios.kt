import data.LocalDataStore
import data.VimeoService
import utils.Platform
import utils.ResourceReader

class CommonPlatform : Platform {
    override val localAppDataSource: VimeoService
        get() = LocalDataStore(resourceReader = ResourceReader())
}