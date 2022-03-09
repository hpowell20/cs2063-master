package mobiledev.unb.ca.labexam.model

class GamesInfo private constructor(builder: Builder) {
    val year: String
    val number: String
    val hostCity: String
    val dates: String
    val wikipediaLink: String

    // Only need to include getters
    val title: String
        get() = "$number - $hostCity"

    class Builder(
        val year: String,
        val number: String,
        val hostCity: String,
        val dates: String,
        val wikipediaLink: String
    ) {
        fun build(): GamesInfo {
            return GamesInfo(this)
        }
    }

    init {
        year = builder.year
        number = builder.number
        hostCity = builder.hostCity
        dates = builder.dates
        wikipediaLink = builder.wikipediaLink
    }
}
