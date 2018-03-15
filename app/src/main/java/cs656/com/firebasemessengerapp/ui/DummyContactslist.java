package cs656.com.firebasemessengerapp.ui;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cs656.com.firebasemessengerapp.model.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

import cs656.com.firebasemessengerapp.R;
import cs656.com.firebasemessengerapp.model.User;
import cs656.com.firebasemessengerapp.utils.Constants;

import static cs656.com.firebasemessengerapp.ui.ContactsList.RequestPermissionCode;

/**
 * Created by swati on 3/14/2018.
 */

public class DummyContactslist extends AppCompatActivity {
    FirebaseDatabase mUserdataabase;
    DatabaseReference mUserRef;
    private Cursor cursor;
    private ArrayList<User> contactsList;
    private ArrayList<String> testList;
    private SimpleCursorAdapter arrayAdapter;
    String name;
    String mobile;
    private ArrayList<String> contactnamelist;
    private ArrayList<String> fbMobilelist,mobilelist;
    private ArrayList<String > comparelist;
    private boolean result=false;
    TextView mobiletext,nametext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list);
        contactsList=new ArrayList<User>();
        testList=new ArrayList<String >();
        fbMobilelist=new ArrayList<String>();
        mUserdataabase = FirebaseDatabase.getInstance();
        mUserRef = mUserdataabase.getReference().child("users");
        mobilelist=new ArrayList<String >();
        comparelist=new ArrayList<String>();
        contactnamelist=new ArrayList<String >();
        enablePermission();
        mobiletext= (TextView) findViewById(R.id.textmobile);
        nametext= (TextView) findViewById(R.id.textname);
         getAllContacts();

    }


    @Override
    protected void onStart() {
        super.onStart();
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot db : dataSnapshot.getChildren()) {
                    User user = db.getValue(User.class);
                    contactsList.add(user);
                    String name=user.getName();
                    String mobile=String.valueOf(user.getMobile());
                    String email=user.getEmail();
                    System.out.println("Name "+name);
                    addToList(name,mobile,email);
                    comparedata();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void addToList(String name, String mobile, String email) {
        testList.add(name);
        testList.add(mobile);
        testList.add(email);
        System.out.println(testList.toString());
        System.out.print("Size  "+testList.size());
    }
    private void getAllContacts() {
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                System.out.println(name);
            mobile = (cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

            contactnamelist.add(name);
            mobilelist.add(mobile);

        }
    }

    private void comparedata() {
        for (User userinfo : contactsList) {
            System.out.println("Mobile in contactlist" + userinfo.getMobile());
            String result = String.valueOf(userinfo.getMobile());
            String userName=userinfo.getName();
            String userEmail=userinfo.getEmail();
            fbMobilelist.add("Mobile Numberss" + result);
            fbMobilelist.add("Username "+userName);
            fbMobilelist.add("Email"+userEmail);

        }
        System.out.println("fbData"+fbMobilelist.toString());
        for(int i=0;i<mobilelist.size();i++){

            for(int j=0;j<fbMobilelist.size();j++){
                result=  PhoneNumberUtils.compare(this,fbMobilelist.get(j),mobilelist.get(i));
                if(result==true){
                    comparelist.add(fbMobilelist.get(j));

                }


            }
        }

        LinkedHashSet<String > linkedHashSet=new LinkedHashSet<String >();
        linkedHashSet.addAll(comparelist);
        comparelist.clear();
        comparelist.addAll(linkedHashSet);
        Collections.sort(comparelist);
        System.out.println(comparelist.toString());
       }


    private void enablePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                DummyContactslist.this,
                android.Manifest.permission.READ_CONTACTS))
        {

            Toast.makeText(DummyContactslist.this,"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(DummyContactslist.this,new String[]{
                    android.Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }

    }
    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(DummyContactslist.this,"Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(DummyContactslist.this,"Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

}