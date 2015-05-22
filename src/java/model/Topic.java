package model;

import java.util.ArrayList;

public class Topic {

    private String title;
    private String content;
    private User creator;
    private ArrayList<Comment> comments;
    //add variable for the label in the profile
    private String label;
    
    //Getter and setter for the label

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    

    public Topic() {
        title = "";
        content = "";
        creator = new User();
        comments = new ArrayList<>();
    }

    public Topic(String title, String content, User creator, ArrayList<Comment> comments) {
        this.title = title;
        this.content = content;
        this.creator = creator;
        this.comments = comments;
    }
    
     public Topic(String title, String content, User creator) {
        this.title = title;
        this.content = content;
        this.creator = creator;
        this.comments = new ArrayList<>();
    }
    
   public void addComment(Comment comment){
       comments.add(comment);
   }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

}
