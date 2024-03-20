package utils

import data.LocalDataStore
import data.VimeoService
import presentation.screens.SearchViewModel
import presentation.views.Destination
import utils.navigation.NavigationStack

interface Platform {
    val localAppDataSource: VimeoService
    val searchViewModel: SearchViewModel
    val navigationStack: NavigationStack<Destination>
}

class CommonPlatform(override val navigationStack: NavigationStack<Destination>) : Platform {
    override val localAppDataSource: VimeoService
        get() = LocalDataStore(resourceReader = ResourceReader())
    override val searchViewModel: SearchViewModel
        get() = SearchViewModel(this)
}