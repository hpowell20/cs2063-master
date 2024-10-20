package mobiledev.unb.ca.labexam.model

/**
 * Data class used to hold Event information
 */
data class EventInfo(
    private val year: String,
    private val number: String,
    private val hostCity: String,
    private val dates: String,
    private val wikipediaLink: String
) {
    // Only need to include getter for the event title
    val eventTitle: String
        get() = "$number - $hostCity"
}
