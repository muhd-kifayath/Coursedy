package mk.tr.coursedy.models;

public class Comment {

    private String commentText, userId;

    public Comment(String commentText, String userId) {
        this.commentText = commentText;
        this.userId = userId;
    }

    public Comment() {
    }

    public String getCommentText() {
        return commentText;
    }

    public String getUserId() {
        return userId;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
