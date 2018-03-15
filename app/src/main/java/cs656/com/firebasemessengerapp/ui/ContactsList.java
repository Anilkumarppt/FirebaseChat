package cs656.com.firebasemessengerapp.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cs656.com.firebasemessengerapp.R;
import cs656.com.firebasemessengerapp.model.Friend;
import cs656.com.firebasemessengerapp.model.User;
import cs656.com.firebasemessengerapp.utils.Constants;
import cs656.com.firebasemessengerapp.utils.EmailEncoding;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by swati on 3/13/2018.
 */

public class ContactsList extends AppCompatActivity {
    ListView listView;
    ArrayList<String> StoreContacts;
    ArrayAdapter<String> arrayAdapter ;
    Cursor cursor ;
    FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCurrentUsersFriends;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference databaseReference;
    String name, phonenumber ;
    public  static final int RequestPermissionCode  = 1 ;
    private String mCurrentUserEmail;
    private DatabaseReference mUserDatabaseReference;
    private int array;
    private FirebaseListAdapter mFriendListAdapter;
    private DatabaseReference mFriendsLocationDatabaseReference;
    private String email;
    private ValueEventListener mValueEventListener;
    private ArrayList<User> usersarry;
    private FirebaseListAdapter mFirebaseListAdapter;
    private  ArrayList<String> nameList ;
    private ArrayList<String> phoneNoList;
    private  DatabaseReference mUserDatabaseRef;
    private DatabaseReference mCurrentUserDatabaseReference;
    HashMap<String,String> contactList = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list);
        listView= (ListView) findViewById(R.id.list_contacts);
        //usersarry=new ArrayList<User>();
        StoreContacts=new ArrayList<String>();
        nameList= new ArrayList<String>();
        phoneNoList=new ArrayList<String>();
        initializeScreen();
        enablePermission();
       getContactlist();
      /* arrayAdapter= new ArrayAdapter<>(getApplicationContext(),R.layout.contents_contcts,R.id.contct_name,StoreContacts);
        listView.setAdapter(arrayAdapter);
      */
      //showContactList();
        datafromFirebase();

   }

   private void datafromFirebase(){


      }
    /*private void showUserList(){
        mFirebaseListAdapter= new FirebaseListAdapter<User>(this, User.class,R.layout.contacts_list, mUserDatabaseReference) {
            @Override
            protected void populateView(final View v, User model, int position) {
                if(model.getEmail()!=null) {
                    email = model.getEmail();
                }


                final DatabaseReference friendRef =
                        mFirebaseDatabase.getReference(Constants.USERS_LOCATION
                                + "/" + mCurrentUserEmail + "/" + EmailEncoding.commaEncodePeriod(email));
                friendRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot user: dataSnapshot.getChildren()){
                            Map value=(Map) dataSnapshot.getValue(User.class);
                            if(value!=null){
                                Iterator myIterator=value.keySet().iterator();
                                while(myIterator.hasNext()){
                                    String name= (String) myIterator.next();
                                    if(name!=null&&StoreContacts.contains(name)){
                                        Map userData= (Map) value.get(name);
                                        Toast.makeText(mActivity, userData.toString(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };
        //arrayAdapter= new ArrayAdapter<>(this,R.layout.contents_contcts,R.id.contct_name,StoreContacts);
    //    listView.setAdapter(mFirebaseListAdapter);
    }*/
     private void initializeScreen() {
         System.out.println("going into FB DB");
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        /* mCurrentUserEmail = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail().toString());
         System.out.println("Current Email"+"  "+mCurrentUserEmail);
        */
         mCurrentUserEmail = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail().toString());

         System.out.println(mUserDatabaseRef);

        // mUserDatabaseReference = mFirebaseDatabase.getReference();
         //https://fir-chat-master-494c9.firebaseio.com/users/anil@gmail,com/Mobile
     }

    private void getContactlist() {
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                System.out.println(name);
            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

           contactList.put(name ,phonenumber);
        }

        cursor.close();

    }
    private void showContactList(){
//        System.out.println("database"+mUserDatabaseRef.toString());
        FirebaseUser user=mFirebaseAuth.getCurrentUser();
        DatabaseReference mAllusersdata;
        mAllusersdata=FirebaseDatabase.getInstance().getReference().child(Constants.USERS_LOCATION).child(mCurrentUserEmail).child("name");

       /* mUserDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() { //all users profile data

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String myName= dataSnapshot.getChildren().toString();
              System.out.println("key"+"   "+dataSnapshot.getChildren());
                System.out.println(myName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       */
       ValueEventListener  positionListner=new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               User user=dataSnapshot.getValue(User.class);
               System.out.println(user.getMobile());
              // System.out.println(user.getUsername());
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       };
      /* mValueEventListener = mUserDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user == null){
                    finish();
                    return;
                }
                else{
                    System.out.println(user.getUsername());
                }
//                mFriendListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

*/
      mAllusersdata.addValueEventListener(positionListner);
    }

    private void enablePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                ContactsList.this,
                Manifest.permission.READ_CONTACTS))
        {

            Toast.makeText(ContactsList.this,"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(ContactsList.this,new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }
    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(ContactsList.this,"Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(ContactsList.this,"Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

}
