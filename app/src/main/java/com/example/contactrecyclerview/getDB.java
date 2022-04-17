package com.example.contactrecyclerview;

import android.content.Context;

import androidx.room.Room;

public class getDB {
    public ContactDB getDB_instance(Context context){
        ContactDB db = Room.databaseBuilder(context,
                ContactDB.class, "contact_db").allowMainThreadQueries().build();
        return db;
    }

}
