package com.project2.banhangmypham.admin.chat;

import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project2.banhangmypham.R;
import com.project2.banhangmypham.adapter.MessageAdapter;
import com.project2.banhangmypham.common_screen.LoginActivity;
import com.project2.banhangmypham.databinding.ActivityAdminChatBinding;
import com.project2.banhangmypham.firebase.ChatManager;
import com.project2.banhangmypham.firebase.IEventMessage;
import com.project2.banhangmypham.model.Message;
import com.project2.banhangmypham.model.User;

import java.util.List;

public class AdminChatActivity extends AppCompatActivity {

    ActivityAdminChatBinding binding ;
    MessageAdapter messageAdapter ;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Chats");
    User currentUser ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAdminChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        messageAdapter = new MessageAdapter();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                currentUser = bundle.getParcelable("loginState", User.class);
            }else {
                currentUser = bundle.getParcelable("loginState");
            }
        }
        assert currentUser != null;
        binding.nameTitle.setText(currentUser.getUsername());
        messageAdapter.setCurrentUserId(LoginActivity.userCurrent.get_id());
        ChatManager.getInstance().setUserId(currentUser.get_id());
        ChatManager.getInstance().setEventListener(new IEventMessage() {
            @Override
            public void onReceived(List<Message> data) {
                messageAdapter.submitList(data);
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
        ChatManager.getInstance().registerMessage();
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
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
            databaseReference.child(currentUser.get_id()).child(idMessage).setValue(message);
            binding.edtMessage.setText("");
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ChatManager.getInstance().registerMessage();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ChatManager.getInstance().unRegisterMessage();
    }
}