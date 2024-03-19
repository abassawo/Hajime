package presentation.screens.home

import data.BeltLevel

typealias CurriculumModel = String

class CurriculumMaker {
    val noBeltCurriculum: List<CurriculumModel> =
        listOf("Jiujitsu History")

    val whiteBeltCurriculum: List<CurriculumModel> =
        listOf("Hip escape, Rolamentos", "Americana", "Jiujitsu Roll", "Classical standup", "Triangle")

    val blueBeltCurriculum: List<CurriculumModel> =
        listOf("Side control", "Jiujitsu sweeps", "Knee on Belly", "Passing the guard", "Omoplata")

    val purpleBeltCurriculum: List<CurriculumModel> =
        listOf("O soto gari", "O uchi gari", "Ko uchi gari", "De ashi barai", "Tai otoshi")

    val brownBeltCurriculum: List<CurriculumModel> =
        listOf("Seoi nage", "Tomoe nage", "Bahiana", "Kata Guruma", "Uchi mata")

    val blackBeltCurriculum: List<CurriculumModel> =
        listOf("Berimbolo, Rolamentos", "Jiujitsu Roll", "Classical standup")


    fun makeCurriculum(): Map<BeltLevel, List<CurriculumModel>> {
        return buildMap {
            BeltLevel.entries.forEach { belt ->
                when (belt) {
                    BeltLevel.White -> whiteBeltCurriculum
                    BeltLevel.Blue -> blueBeltCurriculum
                    BeltLevel.Purple -> purpleBeltCurriculum
                    BeltLevel.Brown -> brownBeltCurriculum
                    BeltLevel.Black -> blackBeltCurriculum
                    BeltLevel.None -> noBeltCurriculum
                }
            }
        }
    }
}