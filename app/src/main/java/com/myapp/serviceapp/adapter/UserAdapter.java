package com.myapp.serviceapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.serviceapp.R;
import com.myapp.serviceapp.activities.admin_panel.CnicActivity;
import com.myapp.serviceapp.helper.Toasty;
import com.myapp.serviceapp.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private Context context;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserName;
        TextView txtEmail;
        TextView txtAddress;
        TextView txtPhone;
        TextView txtCnic;
        CardView userCard;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.tv_name);
            txtEmail = itemView.findViewById(R.id.tv_email);
            txtAddress = itemView.findViewById(R.id.tv_address);
            txtPhone = itemView.findViewById(R.id.tv_phone);
            txtCnic = itemView.findViewById(R.id.tv_cnic);
            userCard = itemView.findViewById(R.id.user_card);
        }

        public void bind(User user) {
            String userName = "Name : " + user.getName();
            String userEmail = "Email : " + user.getEmail();
            String userAddress = "Address : " + user.getAddress();
            String userPhone = "Phone : " + user.getPhone();
            String userCnic = "Cnic : " + user.getCnic();
            txtUserName.setText(userName);
            txtEmail.setText(userEmail);
            txtAddress.setText(userAddress);
            txtPhone.setText(userPhone);
            txtCnic.setText(userCnic);

            userCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user.getCnicUrl() != null) {
                        Intent intent = new Intent(context, CnicActivity.class);
                        intent.putExtra("cnic", user.getCnicUrl());
                        context.startActivity(intent);
                    } else {
                        Toasty.show(context, "No Cnic");
                    }
                }

            });

        }
    }
}
