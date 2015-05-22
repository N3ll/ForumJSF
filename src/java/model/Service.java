package model;

import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named("service")
@ApplicationScoped
public class Service implements Serializable {

    private ArrayList<Category> categories = new ArrayList<Category>();

    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Topic> topics = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();
    private static final String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    private User tarzan;
    private User ross;
    private User neli;

    //only Ross is admin
    public Service() {
        users.add(new User("Ross", "11111", true));
        users.add(new User("Neli", "11111", false));
        users.add(new User("Tarzan", "11111", false));
        users.add(new User("Jane", "22222", false));
        users.add(new User("Sue", "33333", false));
        ross = users.get(0);
        neli = users.get(1);

        categories.add(new Category("C# Programming", ross));
        Topic first = new Topic("Question about the generals", lorem, ross);
        topics.add(first);
        categories.get(0).addTopic(first);
        Comment firstComment = new Comment("My first comment", ross);
        first.addComment(firstComment);
        comments.add(firstComment);
        Comment secondComment = new Comment("You are wrong", neli);
        first.addComment(secondComment);
        comments.add(secondComment);
        Comment thirdComment = new Comment("No, I just know this", ross);
        first.addComment(thirdComment);
        comments.add(thirdComment);
        Topic second = new Topic("Question about the code", "MESSAGE INFO", ross);
        categories.get(0).addTopic(second);
        topics.add(second);
        Topic third = new Topic("Exercise questions", "MESSAGE INFO", ross);
        categories.get(0).addTopic(third);
        topics.add(third);
        categories.add(new Category("Front-End Development", ross));
        categories.add(new Category("Web Development", ross));
        categories.add(new Category("Mobile Development", ross));
        categories.add(new Category("Desktop Development", ross));
        categories.add(new Category("QA", ross));
        categories.add(new Category("New technologies", ross));
        categories.add(new Category("Oculus experience", ross));
        categories.add(new Category("Apple jobs", ross));
        categories.add(new Category("Jobs Abroad", ross));
        categories.add(new Category("FAQ", ross));
        categories.add(new Category("Entertainment for Programmers", ross));
        categories.add(new Category("IT in General", ross));

    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void addUser(User person) {
        users.add(person);
    }

    public User getValidUser(User user) {
        for (User u : users) {
            if (u.getUserName().equals(user.getUserName())
                    && u.getPassword().equals(user.getPassword())) {
                return u;
            }
        }
        return null;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<Topic> topics) {
        this.topics = topics;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void deleteComments(Topic topic) {
        ArrayList<Comment> commentsTodelete = topic.getComments();
        for (int i = 0; i < commentsTodelete.size(); i++) {
            deleteComment(commentsTodelete.get(i));
        }
        comments.removeAll(topic.getComments());
    }

    //added remove from topics
    public void deleteComment(Comment comment) {

        System.out.println("in delete comment");
        for (Topic topic : topics) {
            topic.getComments().remove(comment);
            //remove the comments inside the category topic
            for (Category category : categories) {
                for (int i = 0; i < category.getMessages().size(); i++) {
                    for (int j = 0; j < category.getMessages().get(i).getComments().size(); j++) {
                        if (category.getMessages().get(i).getComments().get(j).getContent().equals(comment.getContent())) {
                            category.getMessages().get(i).getComments().remove(j);
                        }
                    }
                }
            }

        }
        comments.remove(comment);
    }

    public void deleteTopics(Category category) {
        ArrayList<Topic> topicsToDelete = category.getMessages();

        for (int i = 0; i < topicsToDelete.size(); i++) {
            deleteComments(topicsToDelete.get(i));
            deleteTopic(topicsToDelete.get(i));

        }

        //remove from arraylList in service class
        topics.removeAll(topicsToDelete);

    }

    //added remove from the categories
    public void deleteTopic(Topic topic) {
        System.out.println("in delete topic");
        for (Category category : categories) {
            for (Topic t : category.getMessages()) {
                System.out.println(t.getTitle());
            }
            System.out.println(category.getMessages().remove(topic));
        }
        deleteComments(topic);
        topics.remove(topic);
    }

    public void deleteCategories(User user) {
        ArrayList<Category> tempCategories = new ArrayList<>();

        for (Category category : categories) {
            if (category.getCreator().getUserName().equals(user.getUserName())) {
                tempCategories.add(category);
            }
        }

        System.out.println("in delete categories");
        if (!tempCategories.isEmpty()) {
            for (int i = tempCategories.size() - 1; i < 0; i--) {
                deleteCategory(tempCategories.get(i));
            }
        }
        categories.removeAll(tempCategories);
    }

    public void deleteCategory(Category category) {

        ArrayList<Topic> topicsToDeletes = category.getMessages();
        System.out.println("in delete categoriy");

        for (int i = 0; i > topicsToDeletes.size(); i++) {
            deleteTopic(topicsToDeletes.get(i));
        }

        deleteTopics(category);
        categories.remove(category);
    }

    //user deletes his profile by himself
    public void deleteUser(User userToDelete) {
        System.out.println(userToDelete.getUserName());
        for (User user : users) {
            if (user.getUserName().equals(userToDelete.getUserName())
                    && user.getPassword().equals(userToDelete.getPassword())) {
                System.out.println("in delete user");
                deleteCategories(user);

            }
        }
        System.out.println("before deleting the topics");
        System.out.println(topics.size());
        ArrayList<Topic> temp = new ArrayList<>();

        for (int i = 0; i < topics.size(); i++) {
            System.out.println("inside deleting the topics");
            System.out.println(userToDelete.getUserName());
            if (topics.get(i).getCreator().getUserName().equals(userToDelete.getUserName())) {

                temp.add(topics.get(i));
            }
        }
        for (Topic temp1 : temp) {
            deleteTopic(temp1);
        }

        System.out.println("before deleting the comments");
        System.out.println(comments.size());

        ArrayList<Comment> tempC = new ArrayList<>();
        for (int i = 0; i < comments.size(); i++) {
            System.out.println("inside deleting the comments");
            if (comments.get(i).getCreator().getUserName().equals(userToDelete.getUserName())) {
                tempC.add(comments.get(i));
            }
        }
        for (Comment tempC1 : tempC) {
            deleteComment(tempC1);
        }

        User tempUser = new User();
        for (User user : users) {
            if (user.getUserName().equalsIgnoreCase(userToDelete.getUserName())
                    && user.getPassword().equalsIgnoreCase(userToDelete.getPassword())) {
                tempUser = user;
            }
        }
        System.out.println(users.remove(tempUser));
    }
}
