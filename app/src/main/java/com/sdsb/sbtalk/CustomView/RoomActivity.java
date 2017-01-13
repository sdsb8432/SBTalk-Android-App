package com.sdsb.sbtalk.CustomView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsb.sbtalk.Chat;
import com.sdsb.sbtalk.R;
import com.sdsb.sbtalk.Room;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomActivity extends AppCompatActivity {

    private Room room;
    private String name;
    private String userUID;
    List<Chat> chatList;

    private DatabaseReference databaseReference;

    @BindView(R.id.recyclerView_chat)
    RecyclerView recyclerViewChat;
    private ChatAdapter adapter;
    private LinearLayoutManager manager;

    @BindView(R.id.editText_message)
    EditText editTextMessage;

    private boolean itemStatus = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);

        room = (Room) getIntent().getSerializableExtra("room");
        name = getIntent().getStringExtra("name");
        userUID = getIntent().getStringExtra("userUID");
        chatList = new ArrayList<>();

        adapter = new ChatAdapter();
        manager = new LinearLayoutManager(this);
        recyclerViewChat.setLayoutManager(manager);
        recyclerViewChat.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("room").child(room.getUniqueID()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat chat = dataSnapshot.getValue(Chat.class);
                chatList.add(chat);
                adapter.notifyItemInserted(chatList.size());
                manager.scrollToPosition(chatList.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.textView_send) void onClickSend() {
        databaseReference.child("room").child(room.getUniqueID()).push().setValue(new Chat(userUID, editTextMessage.getText().toString(), name));
        editTextMessage.setText("");
    }

    public class ChatAdapter extends RecyclerView.Adapter<ItemHolder> {

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemHolder(getLayoutInflater().inflate(R.layout.item_chat, parent, false));
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Chat chat = chatList.get(position);

            holder.textViewName.setText(chat.getName());
            holder.textViewMessage.setText(chat.getMessage());

            if(userUID.equals(chat.getUserUID()))
                holder.itemChat.setGravity(Gravity.RIGHT);
            else
                holder.itemChat.setGravity(Gravity.LEFT);
        }

        @Override
        public int getItemCount() {
            return chatList.size();
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_chat)
        LinearLayout itemChat;
        @BindView(R.id.textView_name)
        TextView textViewName;
        @BindView(R.id.textVIew_message)
        TextView textViewMessage;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
