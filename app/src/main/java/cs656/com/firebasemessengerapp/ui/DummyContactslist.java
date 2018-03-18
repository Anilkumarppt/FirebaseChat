package cs656.com.firebasemessengerapp.ui;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;

import cs656.com.firebasemessengerapp.R;
import cs656.com.firebasemessengerapp.database.DatabaseHelper;
import cs656.com.firebasemessengerapp.model.*;
import cs656.com.firebasemessengerapp.utils.Constants;
import cs656.com.firebasemessengerapp.utils.EmailEncoding;

import static cs656.com.firebasemessengerapp.database.Note.getDefaults;
import static cs656.com.firebasemessengerapp.database.Note.setDefaults;

/**
 * Created by swati on 3/14/2018.
 */

public class DummyContactslist extends AppCompatActivity {
    FirebaseDatabase mUserdataabase;
    DatabaseReference mUserRef;
    private Cursor cursor;
    public  static final int RequestPermissionCode  = 1 ;
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
    ListView lv;
    private ArrayAdapter listAdapter;
    private DatabaseReference mUserContactsRef;
    private String mCurrentuser;
    private FirebaseAuth mFirebaseAuth;
    DatabaseHelper dbHelper;
    private ArrayList<MutualFriends> mutualfriend;
    private ArrayList<ContactList> contactModelList;
    private FirebaseDatabase mFirebaseDatabase;
    private MyApplication myApplication;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list);



        lv= (ListView) findViewById(R.id.list_contacts);
        contactsList=new ArrayList<User>();
        comparelist=new ArrayList<String>();
        mutualfriend=new ArrayList<MutualFriends>();
        listAdapter = new ArrayAdapter<>(this, R.layout.friend_item,R.id.messageTextView,comparelist);
        lv.setAdapter(listAdapter);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mCurrentuser=mFirebaseAuth.getCurrentUser().getEmail();
        System.out.println("CurrentUsername"+mCurrentuser);
        testList=new ArrayList<String >();
        fbMobilelist=new ArrayList<String>();
        mUserdataabase = FirebaseDatabase.getInstance();
        mUserRef = mUserdataabase.getReference().child("users");
        contactModelList=new ArrayList<ContactList>();
        mobilelist=new ArrayList<String >();
        contactnamelist=new ArrayList<String >();
        myApplication =(MyApplication)getApplicationContext();
        System.out.println(comparelist.toString());
        //arrayAdapter.notifyDataSetChanged();
        //System.out.println("listadapter"+listAdapter.getItem(1).toString());
        mobiletext= (TextView) findViewById(R.id.textmobile);
        nametext= (TextView) findViewById(R.id.textname);
  //      new MyContacts().execute();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listAdapter.notifyDataSetChanged();
            }
        });
      //  listAdapter.notifyDataSet
          //getAllContacts();
        //addFriendTodatabase();
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
                    String name = user.getName();
                    String mobile = String.valueOf(user.getMobile());
                    String email = user.getEmail();
                    System.out.println("Name " + name);
                    //addToList(name, mobile, email);
                    comparedata();
                    //addFriendTodatabase();

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
        boolean status=false;
        for (User userinfo : contactsList) {
            System.out.println("Mobile in contactlist" + userinfo.getMobile());
            String result = String.valueOf(userinfo.getMobile());
            String userName=userinfo.getName();
            String userEmail=userinfo.getEmail();
            fbMobilelist.add(result);
            fbMobilelist.add(userName);
            fbMobilelist.add(userEmail);
        }
        MutualFriends mutualFriends=new MutualFriends();
       // System.out.println("fbData"+fbMobilelist.toString());
        for(int i=0;i<mobilelist.size();i++){
            for(int j=0;j<fbMobilelist.size();j++){
                String mobileContact = mobilelist.get(i).replace(" ", "").replace("+91","");
                String firebaseContact = fbMobilelist.get(j);//replace(" ", "").replace("+91","").replace("-","").replace("(","").replace(")","");
                result=  PhoneNumberUtils.compare(this, firebaseContact, mobileContact);
                if(result==true){
                    comparelist.add(mobileContact+ " "+contactnamelist.get(i));
                    mutualFriends.setName(contactnamelist.get(i));
                    mutualFriends.setMobile(mobileContact);
                    mutualFriends.setFriendslist(comparelist);
                    System.out.println("mutual"+mutualFriends.getFriendslist());
                    setDefaults("mobile",mobileContact,getApplicationContext());
                    setDefaults("name",contactnamelist.get(i),getApplicationContext());
                    String mobile=mobileContact;
                    String name=contactnamelist.get(i);
                   // status= dbHelper.insertUserdata(name,mobile,"dummy");
                    //names.put(mobileContact,contactnamelist.get(i));

                }
            }
        }
        //System.out.println("Maps Data"+names.toString());
        LinkedHashSet<String > linkedHashSet=new LinkedHashSet<String >();
        linkedHashSet.addAll(comparelist);
        comparelist.clear();
        comparelist.addAll(linkedHashSet);
        Collections.sort(comparelist);
            String valuesin=getDefaults("mobile",getApplicationContext());
            System.out.println("values in shared"+valuesin);
        System.out.println("Data in compare list"+comparelist);
        listAdapter.notifyDataSetChanged();
        myApplication.setFriendslist(comparelist);
        // addFriendTodatabase();

       }
        private void addFriendTodatabase(){
            mFirebaseDatabase=FirebaseDatabase.getInstance();
            DatabaseReference friendsRef = mFirebaseDatabase.getReference(Constants.FRIENDS_LOCATION
                    + "/" + EmailEncoding.commaEncodePeriod(mCurrentuser));
            MutualFriends mutualFriends=new MutualFriends(comparelist);

            //Add myApplication to current users myApplication list
            friendsRef.child(EmailEncoding.commaEncodePeriod(mCurrentuser)).setValue(mutualFriends);
            //Add myApplication to current users myApplication list
            System.out.println("Test the data in firendtodatabase"+mutualFriends.getName());
        }
        public ArrayList<String> mutualcontacts(){
                   return comparelist;
        }


}