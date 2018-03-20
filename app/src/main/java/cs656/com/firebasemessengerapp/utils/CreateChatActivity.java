package cs656.com.firebasemessengerapp.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import cs656.com.firebasemessengerapp.model.Chat;
import cs656.com.firebasemessengerapp.model.Friend;
import cs656.com.firebasemessengerapp.model.Message;
import cs656.com.firebasemessengerapp.model.User;
import cs656.com.firebasemessengerapp.model.UserInfo;
import cs656.com.firebasemessengerapp.ui.ChatMessagesActivity;

/**
 * Created by swati on 3/20/2018.
 */


public class CreateChatActivity extends AppCompatActivity {
    private static final String TAG = "Chat Tag";
    private Chat mChat;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mChatDatabaseRef;
    private DatabaseReference mCurrentUserDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mFriendsLocationDatabaseReference;
    private DatabaseReference mFriendDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        UserInfo userInfo = intent.getExtras().getParcelable("info");
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mChatDatabaseRef=mFirebaseDatabase.getReference();
        mChat = new Chat("","");
        mCurrentUserDatabaseReference = mFirebaseDatabase.getReference().child(Constants.USERS_LOCATION
                + "/" + EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail()));
        mFriendsLocationDatabaseReference = mFirebaseDatabase.getReference().child(Constants.FRIENDS_LOCATION
                + "/" + EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail()));
        createChat(userInfo);
    }

    private void createChat(UserInfo userInfo) {
        final DatabaseReference chatRef = mFirebaseDatabase.getReference(Constants.CHAT_LOCATION);
        final DatabaseReference messageRef = mFirebaseDatabase.getReference(Constants.MESSAGE_LOCATION);
        final DatabaseReference pushRef = chatRef.push();

        final String pushKey = pushRef.getKey();
        Log.e(TAG, "Push key is: " + pushKey);

        mChat.setUid(pushKey);
        mChat.setChatName(userInfo.getName().toString());

        //Create HashMap for Pushing Conv
        HashMap<String, Object> chatItemMap = new HashMap<String, Object>();
        HashMap<String,Object> chatObj = (HashMap<String, Object>) new ObjectMapper()
                .convertValue(mChat, Map.class);
        chatItemMap.put("/" + pushKey, chatObj);
        chatRef.updateChildren(chatItemMap);

        //Create corresponding message location for this chat
        String initialMessage = userInfo.getName()+"Say Hello";
      UserInfo user=new UserInfo();
      //User user=new User();
        System.out.println("Username in chat activity"+user.getName());
        Message initialMessages =
                new Message("System", initialMessage,user.getName(),"");
        final DatabaseReference initMsgRef =
                mFirebaseDatabase.getReference(Constants.MESSAGE_LOCATION + "/" + pushKey);
        final DatabaseReference msgPush = initMsgRef.push();
        final String msgPushKey = msgPush.getKey();
        initMsgRef.child(msgPushKey).setValue(initialMessages);

        //Must add chat reference under every user object. Chat/User/Chats[chat1, chat2 ..]
        //Add to current users chat object
        //TODO: OPTIMIZATION!! decide how we will solve data replication issue, we could just send chat id
        // but this would require more complex queries on other pages
        chatItemMap = new HashMap<String, Object>();
        chatItemMap.put("/chats/" + pushKey, chatObj); //repushes chat obj -- Not space efficient
        mCurrentUserDatabaseReference.updateChildren(chatItemMap); //Adds Chatkey to users chats

        //Push chat to all friends
        for(Friend f: mChat.getFriends()){
            mFriendDatabaseReference = mFirebaseDatabase.getReference().child(Constants.USERS_LOCATION
                    + "/" + EmailEncoding.commaEncodePeriod(f.getEmail()));
            chatItemMap = new HashMap<String, Object>();
            chatItemMap.put("/chats/" + pushKey, chatObj);
            mFriendDatabaseReference.updateChildren(chatItemMap);
            mFriendDatabaseReference = null;
        }

        Intent intent = new Intent(this, ChatMessagesActivity.class);
        String messageKey = pushKey;
        intent.putExtra(Constants.MESSAGE_ID, messageKey);
        intent.putExtra(Constants.CHAT_NAME, mChat.getChatName());
        startActivity(intent);


    }
}
