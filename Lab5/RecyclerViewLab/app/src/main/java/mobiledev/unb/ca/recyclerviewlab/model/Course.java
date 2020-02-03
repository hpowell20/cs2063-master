package mobiledev.unb.ca.recyclerviewlab.model;

public class Course {

    private final String id;
    private final String name;
    private final String description;

    private Course (CourseBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
    }

    /*public Course(String id, String name, String desc) {
        mId = id;
        mName = name;
        mDesc = desc;
    }*/

    public String getTitle() {
        return id + ": " + name;
    }

    public String getDescription() {
        return description;
    }

    public static class CourseBuilder {
        private String id;
        private String name;
        private String description;

        public CourseBuilder() {

        }

        public CourseBuilder id(String id) {
            this.id = id;
            return this;
        }

        public CourseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CourseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Course build() {
            return new Course(this);
        }
    }
}
