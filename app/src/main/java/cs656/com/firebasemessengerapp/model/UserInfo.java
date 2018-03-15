package cs656.com.firebasemessengerapp.model;

/**
 * Created by swati on 3/14/2018.
 */

public class UserInfo {
    private String email,mobile,name;

    public UserInfo(){

    }
    public UserInfo(String email, String mobile, String name) {
        this.email = email;
        this.mobile = mobile;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
