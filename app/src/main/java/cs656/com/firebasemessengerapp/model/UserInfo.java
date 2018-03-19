package cs656.com.firebasemessengerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by swati on 3/14/2018.
 */

public class UserInfo implements Parcelable {
    private String email,mobile,name;

    @Override
    public String toString() {
        return "UserInfo{" +
                "email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.email);
        dest.writeString(this.mobile);
        dest.writeString(this.name);
    }

    protected UserInfo(Parcel in) {
        this.email = in.readString();
        this.mobile = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
