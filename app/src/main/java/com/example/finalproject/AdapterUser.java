package com.example.finalproject;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.ViewHolder>{
    private ArrayList<ModelUsers> modelUsersArrayList;
    private Context context;
    int lastPos = -1;
    private UserClickInterface userClickInterface;

    public AdapterUser(ArrayList<ModelUsers> modelUsersArrayList, Context context, UserClickInterface userClickInterface) {
        this.modelUsersArrayList = modelUsersArrayList;
        this.context = context;
        this.userClickInterface = userClickInterface;
    }

    @NonNull
    @Override
    public AdapterUser.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_users,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull AdapterUser.ViewHolder holder, int position) {
        ModelUsers modelUsers = modelUsersArrayList.get(position);
        holder.viewthname.setText(modelUsers.getUsersName());
        setAnimation(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userClickInterface.onUserClick(position);
            }
        });

    }
    private void setAnimation(View itemview, int position){
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        itemview.setAnimation(animation);
        lastPos  = position;

    }

    @Override
    public int getItemCount() {
        return modelUsersArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView viewthname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            viewthname = itemView.findViewById(R.id.viewthname);
        }
    }
    public interface UserClickInterface{
        void onUserClick(int position);
    }
}
