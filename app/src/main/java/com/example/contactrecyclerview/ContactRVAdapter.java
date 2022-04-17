package com.example.contactrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ContactRVAdapter extends RecyclerView.Adapter<ContactRVAdapter.ContactViewHolder> {
    Context context;
    ArrayList<Contact> contacts;
    RecyclerViewEvents recyclerViewEvents;
    public ContactRVAdapter(Context context, ArrayList<Contact> contacts,RecyclerViewEvents recyclerViewEvents){
        this.context=context;
        this.contacts=contacts;
        this.recyclerViewEvents=recyclerViewEvents;

    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{
        TextView txtView;
        ImageView imgView;
        ImageView imgDelete;
        public ContactViewHolder(@NonNull View itemView,RecyclerViewEvents recyclerViewEvents) {
            super(itemView);
            this.txtView=itemView.findViewById(R.id.txtView);
            this.imgView=itemView.findViewById(R.id.imgView);
            this.imgDelete=itemView.findViewById(R.id.imgDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewEvents!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            recyclerViewEvents.onItemClick(position);
                        }
                    }
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewEvents!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            recyclerViewEvents.onDelete(position);
                        }
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public ContactRVAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.recycler_item,parent,false);
        return new ContactRVAdapter.ContactViewHolder(view,recyclerViewEvents);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactRVAdapter.ContactViewHolder holder, int position) {
        holder.txtView.setText(contacts.get(position).getName());
        holder.imgView.setImageResource(R.drawable.avatar);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
