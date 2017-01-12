package com.sdsb.sbtalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsb.sbtalk.CustomView.RoomActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private String userUID;

    @BindView(R.id.textView_user_name)
    TextView textViewUserName;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;

    private List<User> userList;
    private List<Room> roomList;

    @BindView(R.id.recyclerView_room)
    RecyclerView recyclerViewRoom;
    private RoomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        userList = new ArrayList<>();
        roomList = new ArrayList<>();

        adapter = new RoomAdapter();
        recyclerViewRoom.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRoom.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        userUID = firebaseAuth.getCurrentUser().getUid();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                if(firebaseUser != null) {
                    databaseReference.child("userList").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            User user = dataSnapshot.getValue(User.class);
                            userList.add(user);

                            Log.e("name", user.getUid());

                            if(firebaseUser.getUid().equals(user.getUid())) {
                                textViewUserName.setText(user.getName());
                            }
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
                else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        };

        databaseReference.child("roomList").child(userUID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Room room = dataSnapshot.getValue(Room.class);
                roomList.add(room);
                adapter.notifyItemInserted(roomList.size());
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

    private void onClickRoom(Room room) {
        startActivity(new Intent(this, RoomActivity.class).putExtra("room", room).putExtra("name", textViewUserName.getText().toString()));
    }

    @OnClick(R.id.textView_logout) void onClickLogout() {
        firebaseAuth.signOut();
    }

    @OnClick(R.id.floatingActionButton) void onClickAdd() {
        startActivity(new Intent(this, CreateRoomActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    public class RoomAdapter extends RecyclerView.Adapter<ItemHolder> {

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemHolder(getLayoutInflater().inflate(R.layout.item_room, parent, false));
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            final Room room = roomList.get(position);

            holder.textViewRoomUID.setText(room.getUniqueID());
            holder.itemRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickRoom(room);
                }
            });
        }

        @Override
        public int getItemCount() {
            return roomList.size();
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_room)
        LinearLayout itemRoom;
        @BindView(R.id.textView_room_uid)
        TextView textViewRoomUID;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
