package com.example.projectcyber;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HelperAdapter extends RecyclerView.Adapter<HelperAdapter.ViewHolderClass> {

    Context context;
    List<Users> userDataList;

    public HelperAdapter(Context context, List<Users> userDataList) {
        this.userDataList = userDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull HelperAdapter.ViewHolderClass holder, int position) {


        Users usersData = userDataList.get(position);
        holder.name.setText(usersData.getName());
        holder.role.setText(usersData.getRole());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ItemRecycleActivity.class);
                intent.putExtra("name", usersData.getName());
                intent.putExtra("role", usersData.getRole());
                intent.putExtra("phone", usersData.getPhone());
                intent.putExtra("password", usersData.getPassword());
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return userDataList.size();
    }


    public class ViewHolderClass extends RecyclerView.ViewHolder  {
        TextView name, role;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            role = itemView.findViewById(R.id.item_role);
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
