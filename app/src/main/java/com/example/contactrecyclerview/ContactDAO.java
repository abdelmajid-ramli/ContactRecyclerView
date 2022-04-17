package com.example.contactrecyclerview;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ContactDAO {
    @Query("SELECT * FROM contact")
    List<Contact> getAll();

    @Query("SELECT * FROM contact ORDER by  id desc limit 1 ")
    Contact getLastContact();

    @Query("SELECT * FROM contact WHERE id IN (:contactIds)")
    List<Contact> loadAllByIds(int[] contactIds);

    @Query("SELECT * FROM contact WHERE name LIKE :name||'%'")
    List<Contact> getAllByName(String name);

    @Insert
    void insertAll(Contact... persons);

    @Update
    void update(Contact... contacts);

    @Delete
    void delete(Contact user);
}
