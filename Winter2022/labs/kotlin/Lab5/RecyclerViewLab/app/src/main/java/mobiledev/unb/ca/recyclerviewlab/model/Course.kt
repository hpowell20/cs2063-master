package mobiledev.unb.ca.recyclerviewlab.model

class Course private constructor() {

    class Builder(
        private var id: String,
        private var name: String,
        private var description: String? = null
    ) {

        // Only need to include getters
        fun getTitle(): String {
            return "$id: $name"
        }

        fun getDescription(): String? {
            return description
        }

        fun build(): Course {
            return Course()
        }
    }
}
