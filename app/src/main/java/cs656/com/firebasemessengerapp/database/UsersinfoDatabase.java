package cs656.com.firebasemessengerapp.database;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cs656.com.firebasemessengerapp.model.User;
import cs656.com.firebasemessengerapp.utils.Constants;
import cs656.com.firebasemessengerapp.utils.EmailEncoding;

/**
 * Created by swati on 3/17/2018.
 */

public class UsersinfoDatabase extends AppCompatActivity{
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mUserdatabaseref;
    FirebaseAuth mAuth;
    String email;
    public String name,mobile;
    DatabaseHelper dbhelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbhelper=new DatabaseHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public UsersinfoDatabase(String email, String name, String mobile) {
        this.email = email;
        this.name = name;
        this.mobile = mobile;
    }


}
