package com.example.contactrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewEvents{
    ArrayList<Contact> contacts=new ArrayList<>();
    ContactRVAdapter contactRVAdapter;
    RecyclerView recyclerView;
    Context context=this;
    MainActivity mainActivity=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContacts();

        recyclerView=findViewById(R.id.recyclerView);
        contactRVAdapter=new ContactRVAdapter(this,contacts,this);
        recyclerView.setAdapter(contactRVAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setQueryHint("search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.equals("")){
                    contacts.clear();
                    ContactDB db = new getDB().getDB_instance(getApplicationContext());
                    ContactDAO contactDAO = db.contactDAO();
                    contacts.addAll(contactDAO.getAllByName(newText));
                    contactRVAdapter=new ContactRVAdapter(context,contacts,mainActivity);
                    recyclerView.setAdapter(contactRVAdapter);

                }else{
                    contacts.clear();
                    ContactDB db = new getDB().getDB_instance(getApplicationContext());
                    ContactDAO contactDAO = db.contactDAO();
                    contacts.addAll(contactDAO.getAll());
                    contactRVAdapter=new ContactRVAdapter(context,contacts,mainActivity);
                    recyclerView.setAdapter(contactRVAdapter);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void refrech(View view){
        contacts.clear();
        ContactDB db = new getDB().getDB_instance(getApplicationContext());
        ContactDAO contactDAO = db.contactDAO();
        contacts.addAll(contactDAO.getAll());
        contactRVAdapter=new ContactRVAdapter(context,contacts,mainActivity);
        recyclerView.setAdapter(contactRVAdapter);
    }

    public void setContacts(){

        /*Contact p1=new Contact(1,"ali","054545454");
        Contact p2=new Contact(2,"ahmed","052525255");
        Contact p3=new Contact(3,"yassin","0541414141");
        Contact p4=new Contact(4,"james","0534343434");*/
        ContactDB db = new getDB().getDB_instance(getApplicationContext());
        ContactDAO contactDAO = db.contactDAO();
        //contactDAO.insertAll(p1,p2,p3,p4);
        contacts.clear();
        contacts.addAll(contactDAO.getAll());
    }

    public void addContact(View view){
        Intent intent=new Intent(this,AddContactActivity.class);
        startActivity(intent);

    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(this,EditContactActivity.class);
        // creating a bundle object
        Bundle bundle = new Bundle();

        // storing the value in the bundle
        bundle.putInt("id",contacts.get(position).getId());
        bundle.putString("name",contacts.get(position).getName());
        bundle.putString("phone",contacts.get(position).getPhone());

        // passing the bundle into the intent
        intent.putExtras(bundle);
        // starting the intent
        startActivity(intent);

    }

    @Override
    public void onDelete(int position) {
        ContactDB db = new getDB().getDB_instance(getApplicationContext());
        ContactDAO contactDAO = db.contactDAO();
        contactDAO.delete(contacts.get(position));
        contacts.clear();
        contacts.addAll(contactDAO.getAll());
        contactRVAdapter=new ContactRVAdapter(context,contacts,mainActivity);
        recyclerView.setAdapter(contactRVAdapter);
    }
}