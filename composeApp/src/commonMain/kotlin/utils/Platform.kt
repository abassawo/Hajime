package utils

import data.VimeoService

interface Platform {
    val localAppDataSource: VimeoService
}
