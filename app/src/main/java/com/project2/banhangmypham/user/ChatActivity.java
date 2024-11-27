package com.project2.banhangmypham.user;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project2.banhangmypham.adapter.MessageAdapter;
import com.project2.banhangmypham.common_screen.LoginActivity;
import com.project2.banhangmypham.databinding.ActivityChatBinding;
import com.project2.banhangmypham.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    public static final String TAG = "ChatActivity";
    ActivityChatBinding binding ;
    List<com.project2.banhangmypham.model.Message> messageList = new ArrayList<>();
    MessageAdapter messageAdapter ;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Chats");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        messageAdapter = new MessageAdapter();
        messageAdapter.setCurrentUserId(LoginActivity.userCurrent.get_id());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseReference.child(LoginActivity.userCurrent.get_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.hasChildren()) {
                    Log.d(TAG, "onDataChange: ====> data" );
                    messageList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        com.project2.banhangmypham.model.Message message = dataSnapshot.getValue(Message.class);
                        Log.d(TAG, "onDataChange: ====> message = " + message );
                        messageList.add(message);
                    }
                    messageAdapter.submitList(messageList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.rcvChat.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.rcvChat.hasFixedSize();
        binding.rcvChat.setAdapter(messageAdapter);
        binding.ivBack.setOnClickListener(view -> {
            finish();
        });
        binding.btnSend.setOnClickListener(view ->{
            Message message = new Message();
            message.setIdSender(LoginActivity.userCurrent.get_id());
            message.setMessage(binding.edtMessage.getText().toString());
            message.setTimeSent(System.currentTimeMillis());
            String idMessage = databaseReference.push().getKey();
            message.setIdMessage(idMessage);
            databaseReference.child(LoginActivity.userCurrent.get_id()).child(idMessage).setValue(message);
            binding.edtMessage.setText("");
        });
    }
}