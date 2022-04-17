package com.example.contactrecyclerview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class EditContactActivity extends AppCompatActivity {
    private int REQUEST_CALL;
    TextView txtName;
    TextView txtPhone;
    Contact contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);

        txtName=findViewById(R.id.txtNameEdit);
        txtPhone=findViewById(R.id.txtPhoneEdit);

        // getting the bundle back from the android
        Bundle bundle = getIntent().getExtras();
        // getting the string back
        int id = bundle.getInt("id", 0);
        String name = bundle.getString("name", "empty");
        String phone = bundle.getString("phone", "empty");
        contact=new Contact(id,name,phone);

        txtName.setText(name);
        txtPhone.setText(phone);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        MenuItem menuItem=menu.findItem(R.id.add);
        ActionMenuView action=(ActionMenuView)menuItem.getActionView();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        return super.onCreateOptionsMenu(menu);
    }

    public void EditContact(View view){
        ContactDB db = new getDB().getDB_instance(getApplicationContext());
        ContactDAO contactDAO = db.contactDAO();
        Contact lastContact=contactDAO.getLastContact();
        contact.setName(txtName.getText().toString());
        contact.setPhone(txtPhone.getText().toString());
        contactDAO.update(contact);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void call(View view){
        phoneCall(contact);
    }

    private void phoneCall(Contact contact) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE,contact.getPhone()}, REQUEST_CALL);
        } else {
            String phoneNumber = "tel:" + contact.getPhone();
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber)));
        }
    }
}
