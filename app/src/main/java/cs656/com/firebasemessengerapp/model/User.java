package cs656.com.firebasemessengerapp.model;

import java.util.HashMap;
import java.util.List;

public class User {

    private String name;
    private String email;
    private Long mobile;
    private String profilePicLocation;

    public User(){

    }

    public User(String name, String email,Long mobile){
        this.name = name;
        this.email = email;
        this.mobile=mobile;
    }

    public User(String name, String encodedEmail) {
        this.name=name;
        this.email=email;
    }

    public Long getMobile() {
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

}
