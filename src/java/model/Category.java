package model;

import java.util.ArrayList;

public class Category {

    private String name;
    private User creator;
    ArrayList<Topic> messages;
    
    
    //NEW ------------------------------------START
    
    public void addTopic(Topic topic){
        messages.add(topic);
    }
    public String toString(){
        return name;
    }
    
    //NEW ------------------------------------END
    

    public Category() {
        name = "";
        creator = new User();
        messages = new ArrayList<Topic>();
    }

    public Category(String name, User creator, ArrayList<Topic> messages) {
        this.name = name;
        this.creator = creator;
        this.messages = messages;
    }
    
    //added constructor to add categories
    public Category(String name, User creator) {
        this.name = name;
        this.creator = creator;
        messages = new ArrayList<Topic>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public ArrayList<Topic> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Topic> messages) {
        this.messages = messages;
    }

}