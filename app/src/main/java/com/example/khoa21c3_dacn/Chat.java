package com.example.khoa21c3_dacn;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {
    String reciverimg, reciverUid, reciverName, SenderUID;
    CircleImageView profile;
    TextView reciverNName;
    CardView sendbtn;
    EditText textmsg;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static String senderImg;
    public static String reciverIImg;
    String senderRoom, reciverRoom;
    RecyclerView mmessagesAdapter;
    ArrayList<Kieumau> messagesArrayList;
    messageAdpter messageAdpter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        findViewById(R.id.back).setOnClickListener(
                view -> {
                    finish();
                }
        );
        mmessagesAdapter = findViewById(R.id.msgadpter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mmessagesAdapter.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setStackFromEnd(true);
        messageAdpter = new messageAdpter(Chat.this, messagesArrayList);
        mmessagesAdapter.setAdapter(messageAdpter);


        messagesArrayList = new ArrayList<>();


        reciverName = getIntent().getStringExtra("nameeee");
        reciverimg = getIntent().getStringExtra("reciverImg");
        reciverUid = getIntent().getStringExtra("uid");

        profile = findViewById(R.id.profileimgg);
        reciverNName = findViewById(R.id.recivername);
        Picasso.get().load(reciverimg).into(profile);
        reciverNName.setText(""+reciverName);
        SenderUID =  firebaseAuth.getUid();
        senderRoom = SenderUID+reciverUid;
        reciverRoom = reciverUid+SenderUID;

        sendbtn = findViewById(R.id.sendbtnn);
        textmsg = findViewById(R.id.textmsg);

        DatabaseReference  reference = database.getReference().child("user").child(firebaseAuth.getUid());
        DatabaseReference chatreference = database.getReference().child("user").child(senderRoom).child("messages");

        chatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Kieumau messages = dataSnapshot.getValue(Kieumau.class);
                    messagesArrayList.add(messages);
                }
                messageAdpter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                senderImg= snapshot.child("profilepic").getValue().toString();
                reciverIImg=reciverimg;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = textmsg.getText().toString();
                if (message.isEmpty()) {
                    Toast.makeText(Chat.this, "Nhập tin nhắn..", Toast.LENGTH_SHORT).show();
                    return;
                }
                textmsg.setText("");
                Date date = new Date();
                Kieumau messagess = new Kieumau(message,SenderUID,date.getTime());
                database=FirebaseDatabase.getInstance();
                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                database.getReference().child("chats")
                                        .child(reciverRoom)
                                        .child("messages")
                                        .push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });
            }
        });

    }
}
