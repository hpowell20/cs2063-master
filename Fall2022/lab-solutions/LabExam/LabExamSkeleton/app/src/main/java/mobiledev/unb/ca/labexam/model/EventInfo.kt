package mobiledev.unb.ca.labexam.model

class EventInfo private constructor(builder: Builder) {
    val year: String
    val number: String
    val hostNation: String
    val dates: String
    val wikipediaLink: String

    // Only need to include getters
    val title: String
        get() = "$number - $hostNation"

    class Builder(
        val year: String,
        val number: String,
        val hostNation: String,
        val dates: String,
        val wikipediaLink: String
    ) {
        fun build(): EventInfo {
            return EventInfo(this)
        }
    }

    init {
        year = builder.year
        number = builder.number
        hostNation = builder.hostNation
        dates = builder.dates
        wikipediaLink = builder.wikipediaLink
    }
}
