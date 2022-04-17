package com.example.contactrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;

public class AddContactActivity extends AppCompatActivity {
    TextView txtName;
    TextView txtPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        txtName=findViewById(R.id.txtName);
        txtPhone=findViewById(R.id.txtPhone);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        MenuItem menuItem=menu.findItem(R.id.add);
        ActionMenuView action=(ActionMenuView)menuItem.getActionView();

        return super.onCreateOptionsMenu(menu);
    }

    public void addContact(View view){
        ContactDB db = new getDB().getDB_instance(getApplicationContext());
        ContactDAO contactDAO = db.contactDAO();
        Contact lastContact=contactDAO.getLastContact();
        String name=txtName.getText().toString();
        String phone=txtPhone.getText().toString();
        contactDAO.insertAll(new Contact(lastContact.getId()+1,name,phone));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
