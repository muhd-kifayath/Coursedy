package mk.tr.coursedy.models;

public class UploadAssignment {
    private String courseId, assignmentId, assignmentInfoText,uploadAssignment,studentId;

    public UploadAssignment(String courseId, String assignmentId, String assignmentInfoText, String uploadAssignment, String studentId) {
        this.courseId = courseId;
        this.assignmentId = assignmentId;
        this.assignmentInfoText = assignmentInfoText;
        this.uploadAssignment = uploadAssignment;
        this.studentId = studentId;
    }

    public UploadAssignment() {
    }

    public String getCourseId() {
        return courseId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public String getAssignmentInfoText() {
        return assignmentInfoText;
    }

    public String getUploadAssignment() {
        return uploadAssignment;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public void setAssignmentInfoText(String assignmentInfoText) {
        this.assignmentInfoText = assignmentInfoText;
    }

    public void setUploadAssignment(String uploadAssignment) {
        this.uploadAssignment = uploadAssignment;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
