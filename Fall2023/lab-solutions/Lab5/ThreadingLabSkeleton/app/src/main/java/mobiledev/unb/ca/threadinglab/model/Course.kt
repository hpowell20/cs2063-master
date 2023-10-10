package mobiledev.unb.ca.threadinglab.model

/**
 * This makes use of the data class pattern
 * NOTES:
 *  id and name are private (set only); they are retrieved as a combination result
 *  description is public (get and set)
 */
//data class Course(private val id: String?,
//                  private val name: String?,
//                  val description: String? = null) {
//    // Only need to include getter for the course title
//    val title: String
//        get() = "$id: $name"
//}

class Course private constructor(
    private val id: String?,
    private val name: String?,
    val description: String?,
) {
    // Only need to include getters
    val title: String
        get() = "$id: $name"

    data class Builder(
        var id: String? = null,
        var name: String? = null,
        var description: String? = null,
    ) {

        fun id(id: String) = apply { this.id = id }
        fun name(name: String) = apply { this.name = name }
        fun description(description: String) = apply { this.description = description }

        fun build() = Course(id, name, description)
    }
}