package com.sdsb.sbtalk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateRoomActivity extends AppCompatActivity {

    private String userUID;

    private DatabaseReference databaseReference;
    private List<User> userList;

    @BindView(R.id.recyclerView_user)
    RecyclerView recyclerViewUser;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        ButterKnife.bind(this);

        userList = new ArrayList<>();
        adapter = new UserAdapter();
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUser.setAdapter(adapter);

        userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("userList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                if(!user.getUid().equals(userUID)) {
                    userList.add(user);
                    adapter.notifyItemInserted(userList.size());
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

    private void onClickItem(User user) {

        String roomUID = String.valueOf(System.currentTimeMillis());

        databaseReference.child("roomList").child(userUID).push().setValue(new Room(roomUID));
        databaseReference.child("roomList").child(user.getUid()).push().setValue(new Room(roomUID));
        finish();
    }

    @OnClick(R.id.textView_confirm) void onClickConfirm() {

    }

    public class UserAdapter extends RecyclerView.Adapter<ItemHolder> {

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemHolder(getLayoutInflater().inflate(R.layout.item_user, parent, false));
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            final User user = userList.get(position);

            holder.textViewName.setText(user.getName());
            holder.textViewEmail.setText(user.getEmail());

            holder.itemUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItem(user);
                }
            });
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_user)
        LinearLayout itemUser;
        @BindView(R.id.textView_name)
        TextView textViewName;
        @BindView(R.id.textView_email)
        TextView textViewEmail;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
