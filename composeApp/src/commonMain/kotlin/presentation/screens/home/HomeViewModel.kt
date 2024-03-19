package presentation.screens.home

import data.BeltLevel
import data.VimeoRepository
import utils.CommonPlatform
import utils.Platform

class HomeViewModel (val platform: Platform = CommonPlatform(), curriculumMaker: CurriculumMaker = CurriculumMaker()) {
    val isLocalDataEnabled: Boolean = true // todo - use a feature flag
    val vimeoService = if (isLocalDataEnabled) platform.localAppDataSource else VimeoRepository()
    val learningCurriculum: Map<BeltLevel, List<CurriculumModel>> = curriculumMaker.makeCurriculum()


}