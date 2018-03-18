package cs656.com.firebasemessengerapp.model;

import java.util.List;

/**
 * Created by swati on 3/16/2018.
 */

public class ContactList {
   private String name;
    private Long mobile,userid;
    private List<String> userContactlist;

    public ContactList(List<String> userContactlist) {
        this.userContactlist = userContactlist;
    }

    public List<String> getUserContactlist() {
        return userContactlist;
    }

    public void setUserContactlist(List<String> userContactlist) {
        this.userContactlist = userContactlist;
    }

    public ContactList(String name, Long mobile, Long userid) {
        this.name = name;
        this.mobile = mobile;
        this.userid = userid;
    }
    public ContactList(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "ContactList{" +
                "name='" + name + '\'' +
                ", mobile=" + mobile +
                ", userid=" + userid +
                '}';
    }
}
