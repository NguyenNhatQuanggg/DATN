package com.project2.banhangmypham.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project2.banhangmypham.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {
    private static ChatManager _instance ;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
    private IEventMessage callback ;
    private String userId= "";
    public static ChatManager getInstance() {
        if (_instance == null) {
            _instance = new ChatManager();
        }
        return _instance;
    }
    public void setEventListener(IEventMessage iEventMessage){
        callback = iEventMessage;
    }
    public void setUserId(String data){
        userId = data;
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists() && snapshot.hasChildren()){
                List<Message> dataMessageList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Message message = dataSnapshot.getValue(Message.class);
                    dataMessageList.add(message);
                }
                callback.onReceived(dataMessageList);
            }else {
                callback.onError(new Throwable("No exist"));
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            callback.onError(new Throwable(error.getMessage()));
        }
    };
    public void registerMessage(){
        databaseReference.child(userId).addValueEventListener(valueEventListener);
    }

    public void unRegisterMessage(){
        databaseReference.child(userId).removeEventListener(valueEventListener);
    }
}
