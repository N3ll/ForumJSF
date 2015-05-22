package model;

import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {
    
    @Inject
    private Service service;
    
    private User user = new User();
    private User validUser;
    //variable for selected user
    private User selectedUser = new User();
    
    private boolean showCategories;
    private Category selectedCategory;
    private Category editedCategry;
    
    private boolean showTopics;
    private Topic selectedTopic;
    private Topic editedTopic;
    
    private boolean showComments;
    private Comment selectedComment;
    private Comment editedComment;
    
    private Category currentCategory;
    private String newCategoryName;
    private Topic currentTopic;

    //3 variables to store the info when adding new topic
    private String addTopicCategory;
    private String addTopicSubject;
    private String addTopicMessage;

    //variable to keep the comment string
    private String newComment;

    //added constructor for UserBean
    public UserBean() {
        this.service = new Service();
        currentCategory = null;
        newCategoryName = "";
        currentTopic = null;
        newComment = "";
    }
    
    public Service getService() {
        return service;
    }
    
    public void setService(Service service) {
        this.service = service;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    //getter and setter for selectedUser variable
    public User getSelectedUser() {
        return selectedUser;
    }
    
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    //changed method
    public String login() {
        validUser = service.getValidUser(user);
        if (validUser != null) {
            user.update(validUser);
            selectedUser.update(validUser);
            showCategories = false;
            showComments = false;
            showTopics = false;
            validUser = null;
            return "category";
        } else {
            user = new User();
            return "error";
        }
    }

    //changed method
    public String createUser() {
        user.setIsAdmin(false);
        service.addUser(user);
        selectedUser = user;
        return "category";
    }

    //method fot taking the name fo the user and putting it in the button
    public String userProfile() {
        return user.getUserName() + " Profile";
    }

    //method for the admin to select a user
    public String selectUser(User user) {
        selectedUser = user;
        
        return "profile";
    }

    //check if the user and the selectedUser user are one the same, in order to see if the admin has clicked on one
    public boolean canEditUser() {
        return (!(user.getUserName().equals(selectedUser.getUserName())
                && user.getPassword().equals(selectedUser.getPassword()))) || (!user.isIsAdmin());
    }

    //return a String with who is the user to be edited
    public String userToEdit() {
        return selectedUser.getUserName() + " will be update.";
    }

    //method for deleting the user and all of his posessions 
    public String deleteUser() {
        if (currentCategory != null) {
            if (currentCategory.getCreator().getUserName().equals(selectedUser.getUserName())) {
                currentCategory = new Category();
            }
        }
        
        service.deleteUser(selectedUser);
        
        if (user.getUserName().equals(selectedUser.getUserName())
                && user.getPassword().equals(selectedUser.getPassword())) {
            selectedUser = new User();
            user = new User();
            return "index";
        }
        selectedUser = user;
        return "profile";
    }

    //method getUsers
    public ArrayList<User> users() {
        return service.getUsers();
    }

    //--------------Categories---------------------------------
    public boolean isShowCategories() {
        return showCategories;
    }
    
    public void setShowCategories(boolean showCategories) {
        this.showCategories = showCategories;
    }
    
    public String showCategories() {
        this.showCategories = !showCategories;
        this.showTopics = false;
        this.showComments = false;
        return "";
    }

//changed method
    public String numberOfCategories() {
        ArrayList<Category> tempCategories = service.getCategories();
        int numberOfCategories = 0;
        for (Category category : tempCategories) {
            if (category.getCreator().getUserName().equals(selectedUser.getUserName())) {
                numberOfCategories++;
            }
        }
        String result = numberOfCategories + " Categories";
        return result;
    }

    //changed method
    public ArrayList<Category> userCategories() {
        ArrayList<Category> tempCategories = service.getCategories();
        ArrayList<Category> userCategories = new ArrayList<>();
        for (Category category : tempCategories) {
            if (category.getCreator().getUserName().equals(selectedUser.getUserName())) {
                userCategories.add(category);
            }
        }
        
        return userCategories;
    }
    
    public Category getEditedCategry() {
        return editedCategry;
    }
    
    public void setEditedCategry(Category editedCategry) {
        this.editedCategry = editedCategry;
    }
    
    public Category getSelectedCategory() {
        return selectedCategory;
    }
    
    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
    
    public String selectCategory(Category category) {
        this.selectedCategory = category;
        this.editedCategry = category;
        return "editCategory";
    }
    
    public String updateCategory() {
        ArrayList<Category> categories = service.getCategories();
        
        for (Category category : categories) {
            if (category.getName().equals(selectedCategory.getName())) {
                category.setName(editedCategry.getName());
            }
        }
        return "profile";
    }

    //changed method
    public String deleteCategory() {
        if (currentCategory != null) {
            if (currentCategory.getName().equals(selectedCategory.getName())) {
                currentCategory = new Category();
            }
        }
        
        ArrayList<Category> categories = service.getCategories();
        Category temp = new Category();
        
        for (Category category : categories) {
            if (category.getName().equals(selectedCategory.getName())) {
                temp = category;
            }
        }
        
        service.deleteCategory(temp);
        return "profile";
    }

    //--------------Topics---------------------------------
    public boolean isShowTopics() {
        return showTopics;
    }
    
    public void setShowTopics(boolean showTopics) {
        this.showTopics = showTopics;
    }
    
    public String showTopics() {
        this.showTopics = !showTopics;
        this.showCategories = false;
        this.showComments = false;
        return "";
    }
    
    public Topic getSelectedTopic() {
        return selectedTopic;
    }
    
    public void setSelectedTopic(Topic selectedTopic) {
        this.selectedTopic = selectedTopic;
    }
    
    public Topic getEditedTopic() {
        return editedTopic;
    }
    
    public void setEditedTopic(Topic editedTopic) {
        this.editedTopic = editedTopic;
    }

    //changed method
    public String numberOfTopics() {
        ArrayList<Topic> tempTopics = service.getTopics();
        int numberOfTopics = 0;
        for (Topic topic : tempTopics) {
            if (topic.getCreator().getUserName().equals(selectedUser.getUserName())) {
                numberOfTopics++;
            }
        }
        String result = numberOfTopics + " Topics";
        return result;
    }

    //changed method
    public ArrayList<Topic> userTopics() {
        ArrayList<Topic> tempTopics = service.getTopics();
        ArrayList<Topic> userTopics = new ArrayList<>();
        for (Topic topic : tempTopics) {
            if (topic.getCreator().getUserName().equals(selectedUser.getUserName())) {
                userTopics.add(topic);
            }
        }
        
        return userTopics;
    }

    //method for showing topic with categories
    public ArrayList<Topic> userTopicsWithCategory() {
        ArrayList<Category> tempCategory = service.getCategories();
        ArrayList<Topic> userTopicsWithCategory = new ArrayList<>();
        String result = "";
        
        ArrayList<Topic> tempTopics = new ArrayList<>();
        
        for (Category category : tempCategory) {
            tempTopics = category.getMessages();
            for (Topic topic : tempTopics) {
                if (topic.getCreator().getUserName().equals(selectedUser.getUserName())) {
                    
                    result = "Category: " + category.getName() + " > " + topic.getTitle();
                    topic.setLabel(result);
                    userTopicsWithCategory.add(topic);
                }
            }
        }
        return userTopicsWithCategory;
    }

    //change to go to editTopic
    public String selectTopic(Topic topic) {
        this.selectedTopic = topic;
        this.editedTopic = topic;
        return "editTopic";
    }

    //changed method
    public String updateTopic() {
        ArrayList<Topic> topics = service.getTopics();
        
        for (Topic topic : topics) {
            if (topic.getTitle().equals(selectedTopic.getTitle())) {
                topic.setTitle(editedTopic.getTitle());
                topic.setContent(editedTopic.getContent());
            }
        }
        selectedCategory = new Category();
        editedCategry = new Category();
        return "profile";
    }

    //changed method
    public String deleteTopic() {
        ArrayList<Topic> topics = service.getTopics();
        Topic temp = new Topic();
        
        for (Topic topic : topics) {
            if (topic.getTitle().equals(selectedTopic.getTitle())) {
                temp = topic;
            }
        }
        
        if (currentTopic != null) {
            if (currentTopic.getTitle().equals(selectedTopic.getTitle())) {
                
                currentTopic = new Topic();
            }
        }
        
        if (currentCategory != null) {
            for (int i = 0; i > currentCategory.getMessages().size() - 1; i++) {
                if (currentCategory.getMessages().get(i).getTitle().equals(selectedTopic.getTitle())) {
                    currentCategory.getMessages().remove(i);
                }
            }
        }
        
        service.deleteTopic(temp);
        selectedCategory = new Category();
        editedCategry = new Category();
        return "profile";
    }

    //--------------Comments---------------------------------
    public boolean isShowComments() {
        return showComments;
    }
    
    public void setShowComments(boolean showComments) {
        this.showComments = showComments;
    }
    
    public String showComments() {
        this.showComments = !showComments;
        this.showCategories = false;
        this.showTopics = false;
        return "";
    }
    
    public Comment getSelectedComment() {
        return selectedComment;
    }
    
    public void setSelectedComment(Comment selectedComment) {
        this.selectedComment = selectedComment;
    }
    
    public Comment getEditedComment() {
        return editedComment;
    }
    
    public void setEditedComment(Comment editedComment) {
        this.editedComment = editedComment;
    }

    //changed method
    public String numberOfComments() {
        ArrayList<Comment> tempComments = service.getComments();
        int numberOfComments = 0;
        for (Comment comment : tempComments) {
            if (comment.getCreator().getUserName().equals(selectedUser.getUserName())) {
                numberOfComments++;
            }
        }
        String result = numberOfComments + " Comments";
        return result;
    }

    //changed method
    public ArrayList<Comment> userComments() {
        ArrayList<Comment> tempComments = service.getComments();
        ArrayList<Comment> userComments = new ArrayList<>();
        for (Comment comment : tempComments) {
            if (comment.getCreator().getUserName().equals(selectedUser.getUserName())) {
                userComments.add(comment);
            }
        }
        
        return userComments;
    }

    //method showing the comments to related topic connected with the category
    public ArrayList<Comment> userCommentsWithTopic() {
        ArrayList<Category> tempCategory = service.getCategories();
        ArrayList<Comment> userCommentsWithTopic = new ArrayList<>();
        String result = "";
        
        ArrayList<Topic> tempTopics = new ArrayList<>();
        ArrayList<Comment> tempComments = new ArrayList<>();
        
        for (Category category : tempCategory) {
            tempTopics = category.getMessages();
            for (Topic topic : tempTopics) {
                tempComments = topic.getComments();
                for (Comment tempComment : tempComments) {
                    if (tempComment.getCreator().getUserName().equals(selectedUser.getUserName())) {
                        String temp = String.format("%.30s...", tempComment.getContent());
                        result = "Category: " + category.getName() + " > " + "Topic: " + topic.getTitle();
                        tempComment.setLabel(result);
                        userCommentsWithTopic.add(tempComment);
                    }
                }
            }
        }
        return userCommentsWithTopic;
    }

    //change to go to editComment and setSelected
    public String selectComment(Comment comment) {
        comment.setSelected(true);
        this.selectedComment = comment;
        this.editedComment = comment;
        return "editComment";
    }

    //changed method
    public String updateComment() {
        ArrayList<Comment> comments = service.getComments();
        
        for (Comment comment : comments) {
            if (comment.isSelected()) {
                comment.setContent(editedComment.getContent());
                comment.setSelected(false);
            }
        }
        selectedComment = new Comment();
        editedComment = new Comment();
        return "profile";
    }

    //changed method
    public String deleteComment() {
        ArrayList<Comment> comments = service.getComments();
        Comment temp = new Comment();
        
        for (Comment comment : comments) {
            if (comment.getContent().equals(selectedComment.getContent())) {
                temp = comment;
            }
        }
        
        if (currentCategory != null) {
            for (int i = 0; i > currentCategory.getMessages().size() - 1; i++) {
                for (int j = currentCategory.getMessages().get(i).getComments().size() - 1; j < 0; j--) {
                    if (currentCategory.getMessages().get(i).getComments().get(i).getContent().equals(temp.getContent())) {
                        currentCategory.getMessages().get(i).getComments().remove(i);
                        currentTopic = currentCategory.getMessages().get(i);
                    }
                }
            }
            
        }
        
        service.deleteComment(temp);
        selectedComment = new Comment();
        editedComment = new Comment();
        return "profile";
    }

    //----------Ross------------------------
    public String getNewComment() {
        return newComment;
    }
    
    public void setNewComment(String newComment) {
        this.newComment = newComment;
    }
    
    public String getAddTopicCategory() {
        return addTopicCategory;
    }
    
    public void setAddTopicCategory(String addTopicCategory) {
        this.addTopicCategory = addTopicCategory;
    }
    
    public String getAddTopicSubject() {
        return addTopicSubject;
    }
    
    public void setAddTopicSubject(String addTopicSubject) {
        this.addTopicSubject = addTopicSubject;
    }
    
    public String getAddTopicMessage() {
        return addTopicMessage;
    }
    
    public void setAddTopicMessage(String addTopicMessage) {
        this.addTopicMessage = addTopicMessage;
    }
    
    public Topic getCurrentTopic() {
        return currentTopic;
    }
    
    public void setCurrentTopic(Topic currentTopic) {
        this.currentTopic = currentTopic;
    }
    
    public String getNewCategoryName() {
        return newCategoryName;
    }
    
    public void setNewCategoryName(String newCategory) {
        this.newCategoryName = newCategory;
    }
    
    public Category getCurrentCategory() {
        return currentCategory;
    }
    
    public void setCurrentCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    //to change the current category to the one that is selected
    public String changeCategory(String category) {
        
        for (int i = 0; i < service.getCategories().size(); i++) {
            if (category.equalsIgnoreCase(service.getCategories().get(i).getName())) {
                currentCategory = service.getCategories().get(i);
                return "category";
            }
            
        }
        return "category";
    }
    
    public String changeTopic(String topic) {
        
        for (int i = 0; i < currentCategory.messages.size(); i++) {
            if (topic.equalsIgnoreCase(currentCategory.getMessages().get(i).getTitle())) {
                currentTopic = currentCategory.getMessages().get(i);
                return "topic";
            }
        }
        return "topic";
    }
    
    public String addCategory() {
        
        for (int i = 0; i < service.getUsers().size(); i++) {
            if (user.getUserName().equalsIgnoreCase(service.getUsers().get(i).getUserName())) {
                Category newCategory = new Category(newCategoryName, service.getUsers().get(i));
                service.addCategory(newCategory);
                newCategoryName = "";
            }
        }
        return "category";
    }
    
    public String addTopic() {

        //1st,loop to find the user to which you will assign it
        for (int i = 0; i < service.getUsers().size(); i++) {
            if (user.getUserName().equalsIgnoreCase(service.getUsers().get(i).getUserName())) {
                //then loop to add it to the right category
                for (int j = 0; j < service.getCategories().size(); j++) {
                    //find the category in which to store the new topic
                    if (addTopicCategory.equalsIgnoreCase(service.getCategories().get(j).getName())) {
                        Topic topic = new Topic(addTopicSubject, addTopicMessage, service.getUsers().get(i));
                        service.getCategories().get(j).addTopic(topic);
                        service.getTopics().add(topic);
                        addTopicCategory = "";
                        addTopicMessage = "";
                        addTopicSubject = "";
                        
                        return "category";
                    }
                }
            }
            
        }
        
        return "category";
    }
    
    public String addComment(String newCommentEntry) {
        
        Comment comment = null;
        //find the right category
        for (int j = 0; j < service.getCategories().size(); j++) {
            if (currentCategory.getName().equalsIgnoreCase(service.getCategories().get(j).getName())) {
                //find the right topic
                for (int h = 0; h < service.getCategories().get(j).getMessages().size(); h++) {
                    if (currentTopic.getTitle().equalsIgnoreCase(service.getCategories().get(j).getMessages().get(h).getTitle())) {
                        //find the right user
                        for (int i = 0; i < service.getUsers().size(); i++) {
                            if (user.getUserName().equalsIgnoreCase(service.getUsers().get(i).getUserName())) {
                                comment = new Comment(newCommentEntry, service.getUsers().get(i));
                                service.getCategories().get(j).getMessages().get(h).addComment(comment);
                                newComment = "";
                                service.getComments().add(comment);
                                return "topic";
                                
                            }
                            
                        }
                    }
                }
            }
            
        }
        return "topic";
    }
    
}
