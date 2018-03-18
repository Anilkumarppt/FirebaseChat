package cs656.com.firebasemessengerapp.model;

import java.util.HashMap;
import java.util.List;

public class User {

    private String name;
    private String email;
    private String mobile;
    private String profilePicLocation;
    private boolean isAddFriend;

    public User(){

    }

    public User(String name, String email,String  mobile){
        this.name = name;
        this.email = email;
        this.mobile=mobile;
    }

    public User(String name, String encodedEmail) {
        this.name=name;
        this.email=email;
    }

    public String getMobile() {
        return mobile;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePicLocation() {
        return profilePicLocation;
    }

    public boolean isAddFriend() {
        return isAddFriend;
    }

    public void setAddFriend(boolean addFriend) {
        isAddFriend = addFriend;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile=" + mobile +
                ", profilePicLocation='" + profilePicLocation + '\'' +
                ", isAddFriend=" + isAddFriend +
                '}';
    }
}
