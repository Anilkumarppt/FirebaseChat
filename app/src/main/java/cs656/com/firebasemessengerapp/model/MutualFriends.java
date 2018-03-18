package cs656.com.firebasemessengerapp.model;

import android.app.Application;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by swati on 3/17/2018.
 */

public class MutualFriends {
    public String mobile;
    public String name;
    List<String> friendslist;

    public MutualFriends(List<String> friendslist) {
        this.friendslist = friendslist;
    }

    public List<String> getFriendslist() {
        return friendslist;
    }

    public void setFriendslist(List<String> friendslist) {
        this.friendslist = friendslist;
    }

    public MutualFriends(){}
    public MutualFriends(String mobile, String name) {
        this.mobile = mobile;
        this.name = name;
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

    @Override
    public String toString() {
        return "MutualFriends{" +
                "mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

   }
