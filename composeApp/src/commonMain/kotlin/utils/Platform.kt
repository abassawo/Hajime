package utils

import data.LocalDataStore
import data.VimeoService
import presentation.screens.SearchViewModel

interface Platform {
    val localAppDataSource: VimeoService
    val searchViewModel: SearchViewModel
}

class CommonPlatform : Platform {
    override val localAppDataSource: VimeoService
        get() = LocalDataStore(resourceReader = ResourceReader())
    override val searchViewModel: SearchViewModel
        get() = SearchViewModel()
}