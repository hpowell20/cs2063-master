package mobiledev.unb.ca.composelistlab.models

/**
 * This makes use of the data class pattern
 * NOTES:
 *  id and name are private (set only); they are retrieved as a combination result
 *  description is public (get and set)
 */
data class Course(private val id: String?,
                  private val name: String?,
                  val description: String? = null) {
    // Only need to include getter for the course title
    val title: String
        get() = "$id: $name"
}

fun dummyData(): List<Course> {
    val course = Course(
        id = "CS2063",
        name = "Introduction to Mobile Application Development 4ch",
        description = "Introduces students to the development of application software for mobile computing platforms.  Characteristics of mobile computing platforms versus non-mobile platforms. Mobile application design principles, including design of effective user interaction and factors that affect application performance. Programming common mobile application functionality such as location, orientation, and motion awareness, as well as touch, gesture, and camera input. Interacting with remote APIs (e.g. Google Maps). Students will gain experience creating and testing applications for a selected currently prominent mobile platform.Prerequisite(s): CS 2043 or CS 2253 ."
    )

    return listOf(course)
}
