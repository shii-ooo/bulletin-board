package practice_webapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    
    int postNumber;
    String userName;
    String threadTitle;
    int threadId;
    Date date;
    String post;
    String img;
    
    public Post(int postNumber, String userName, String threadTitle, int threadId, Date date, String post, String img) {
        this.postNumber = postNumber;
        this.userName = userName;
        this.threadTitle = threadTitle;
        this.threadId = threadId;
        this.date = date;
        this.post = post;
        this.img = img;
    }
    
    public Post(String userName, String threadTitle, int threadId, Date date, String post, String img) {
        this(0, userName, threadTitle, threadId, date, post, img);
    }

    public Post(int postNumber, String userName, String threadTitle, int threadId, Date date, String post) {
        this(0, userName, threadTitle, threadId, date, post, null);
    }
    
    public Post(String userName, String threadTitle, int threadId, Date date, String post) {
        this(0, userName, threadTitle, threadId, date, post);
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getThreadTitle() {
        return threadTitle;
    }

    public void setThreadTitle(String threadTitle) {
        this.threadTitle = threadTitle;
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
    
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    

}