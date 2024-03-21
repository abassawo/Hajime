package utils

import data.LocalDataStore
import data.VimeoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import presentation.screens.SearchViewModel
import presentation.screens.home.HomeViewModel
import presentation.views.Destination
import utils.navigation.NavigationStack

interface Platform {

    val coroutineScope: CoroutineScope
    val localAppDataSource: VimeoService
    val searchViewModel: SearchViewModel
    val homeViewModel: HomeViewModel
    val navigationStack: NavigationStack<Destination>
}

class CommonPlatform(override val navigationStack: NavigationStack<Destination>) : Platform {
    override val coroutineScope: CoroutineScope
        get()  = CoroutineScope(
    Dispatchers.IO + SupervisorJob()
        )
    override val localAppDataSource: VimeoService
        get() = LocalDataStore(resourceReader = ResourceReader())
    override val searchViewModel: SearchViewModel
        get() = SearchViewModel(this)
    override val homeViewModel: HomeViewModel
        get() = HomeViewModel(this)
}