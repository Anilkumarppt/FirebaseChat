package cs656.com.firebasemessengerapp.ui;

import android.app.Application;

import cs656.com.firebasemessengerapp.model.MutualFriends;

/**
 * Created by swati on 3/18/2018.
 */

public class Singltonclass extends Application {
    MutualFriends mutualFriends=new MutualFriends();

}
