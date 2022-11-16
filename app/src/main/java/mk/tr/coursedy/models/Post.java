package mk.tr.coursedy.models;

public class Post {

    private String postId;
    private String postType; //assignments or post
    private String postText;
    private String userId;
    private String postUserName;
    private String dateTime;

    public Post() {
    }

    public Post(String postId, String postType, String postText, String userId, String postUserName, String dateTime) {
        this.postId = postId;
        this.postType = postType;
        this.postText = postText;
        this.userId = userId;
        this.postUserName = postUserName;
        this.dateTime = dateTime;
    }

    public String getPostId() {
        return postId;
    }

    public String getPostType() {
        return postType;
    }

    public String getPostText() {
        return postText;
    }

    public String getUserId() {
        return userId;
    }

    public String getPostUserName() {
        return postUserName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
