package mk.tr.coursedy.models;

public class StudentListOfTheCourses
{
    private String studentId,studentEmail,studentName,studentPicture,courseId;

    public StudentListOfTheCourses() {
    }

    public StudentListOfTheCourses(String studentId, String studentEmail, String studentName,
                                   String studentPicture,String courseId) {
        this.studentId = studentId;
        this.studentEmail = studentEmail;
        this.studentName = studentName;
        this.studentPicture = studentPicture;
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentPicture() {
        return studentPicture;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentPicture(String studentPicture) {
        this.studentPicture = studentPicture;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
