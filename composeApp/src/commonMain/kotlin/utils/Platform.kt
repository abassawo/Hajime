package utils

import data.LocalDataStore
import data.VimeoRepository
import data.VimeoService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import presentation.screens.ProfileViewModel
import presentation.screens.SearchViewModel
import presentation.screens.home.HomeViewModel
import presentation.screens.onboarding.OnboardingViewModel
import presentation.screens.saved_videos.FavoritesViewModel


interface Platform {

    var isMockDataEnabled: Boolean

    val coroutineScope: CoroutineScope
    val appDataSource: VimeoService

    val onboardingViewModel: OnboardingViewModel
    val searchViewModel: SearchViewModel
    val homeViewModel: HomeViewModel
    val profileViewModel: ProfileViewModel
    val favoriteViewModel: FavoritesViewModel
    val sessionManager: SessionManager
}

class CommonPlatform(
    override val sessionManager: SessionManager = SessionManager(UserSettings()),
    override val coroutineScope: CoroutineScope = CoroutineScope(
        Dispatchers.IO + SupervisorJob()
    ),
    override var isMockDataEnabled: Boolean = false,
) : Platform {

    private val localAppDataSource = LocalDataStore(resourceReader = ResourceReader())

    override val appDataSource: VimeoService
        get() = if (isMockDataEnabled) localAppDataSource else VimeoRepository()
    override val onboardingViewModel: OnboardingViewModel
        get() = OnboardingViewModel()

    override val searchViewModel: SearchViewModel
        get() = SearchViewModel(this)
    override val homeViewModel: HomeViewModel
        get() = HomeViewModel(this)
    override val profileViewModel: ProfileViewModel
        get() = ProfileViewModel(this)
    override val favoriteViewModel: FavoritesViewModel
        get() = FavoritesViewModel(this)
}