package practice_webapp;

public class ThreadTitle {
    
    int threadId;
    String title;
    String categoryName;
    
    public ThreadTitle(int threadId, String title, String categoryName) {
        this.threadId = threadId;
        this.title = title;
        this.categoryName = categoryName;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    

}