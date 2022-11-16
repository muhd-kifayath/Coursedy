package mk.tr.coursedy.models;

public class Course {
    private String courseId, courseName, coursePeriod, courseCode, studentName,
            courseTeacherName, courseTeacherId, courseStudentsId ;

    public Course() {
    }

    public Course(String courseId, String courseName, String coursePeriod,
                  String courseCode, String studentName, String courseTeacherName, String courseTeacherId, String courseStudentsId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.coursePeriod = coursePeriod;
        this.courseCode = courseCode;
        this.studentName = studentName;
        this.courseTeacherName = courseTeacherName;
        this.courseTeacherId = courseTeacherId;
        this.courseStudentsId = courseStudentsId;
    }

    public String getCourseStudentsId() {
        return courseStudentsId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCoursePeriod() {
        return coursePeriod;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCourseTeacherName() {
        return courseTeacherName;
    }

    public String getCourseTeacherId() {
        return courseTeacherId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCoursePeriod(String coursePeriod) {
        this.coursePeriod = coursePeriod;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setCourseTeacherName(String courseTeacherName) {
        this.courseTeacherName = courseTeacherName;
    }

    public void setCourseTeacherId(String courseTeacherId) {
        this.courseTeacherId = courseTeacherId;
    }

    public void setCourseStudentsId(String courseStudentsId) {
        this.courseStudentsId = courseStudentsId;
    }
}
