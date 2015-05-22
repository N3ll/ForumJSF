package model;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

public class User {

    private String userName;
    private String password;
    private String confirmPassword;
    private boolean isAdmin;

    public User() {
        userName = "";
        password = "";
        isAdmin = false;

    }

    public User(String userName, String password, boolean isAdmin) {
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        System.out.println("Password 1 " + this.password);
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        System.out.println("Pass word 2" + this.password);
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    //changed method
    public void update(User user) {
        userName = user.userName;
        password = user.password;
        isAdmin = user.isAdmin;
    }

    public void validatePass(ComponentSystemEvent event) {

        UIComponent source = event.getComponent();
        UIInput passInput = (UIInput) source.findComponent("pass");
        UIInput confirmPassInput = (UIInput) source.findComponent("confirmPass");
        String pass = passInput.getLocalValue().toString();
        String confirmPass = confirmPassInput.getLocalValue().toString();

        if (pass.length() != 5) {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Incorrect length in password box",
                    "Password should be 5 symbols");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(source.getClientId(), message);
            context.renderResponse();
        }

        if (confirmPass.length() != 5) {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Incorrect length in retype box",
                    "Password should be 5 symbols");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(source.getClientId(), message);
            context.renderResponse();
        }

        if (!pass.equals(confirmPass)) {
            password = "";
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Password mismatch",
                    "Password mismatch");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(source.getClientId(), message);
            context.renderResponse();
        }

        confirmPassword = "";

    }

}
