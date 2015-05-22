package model;

public class Comment {

    private String content;
    private User creator;
    //variable for the label in the profile
    private String label;
    //variable to be used when updating the comment
    private boolean selected;
    
    //getter and setter for the label variable
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label =label;
    }
            
    //getter and setter for the selected variable
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    

    public Comment() {
        content = "";
        creator = new User();
    }

    public Comment(String content, User creator) {
        this.content = content;
        this.creator = creator;
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

}