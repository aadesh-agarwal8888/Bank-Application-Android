package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bankapplication.ui.User;

public class RegisterActivity extends AppCompatActivity {

    Button btnOpenAccount;
    EditText txtName, txtAddress, txtContact, txtUsername, txtPassword, txtConfirmPassword;

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnOpenAccount = (Button)findViewById(R.id.btnOpenAccount);

        txtName = (EditText)findViewById(R.id.txtName);
        txtAddress = (EditText)findViewById(R.id.txtAddress);
        txtContact = (EditText)findViewById(R.id.txtContact);
        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText)findViewById(R.id.txtConfirmPassword);


        db = new DBHandler(this);

    }

    public void onOpenAccount(View view) {
        String name = txtName.getText().toString();
        String contact = txtContact.getText().toString();
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPassword = txtConfirmPassword.getText().toString();

        if(password.equals(confirmPassword)) {
            User newUser = new User(name, contact, username, password, 0);
            db.addUser(newUser);
            Intent intentLoginActivity = new Intent(RegisterActivity.this, MainActivity.class);
            finish();
            startActivity(intentLoginActivity);
        } else {
            Toast.makeText(RegisterActivity.this, "Password doesnt match", Toast.LENGTH_LONG).show();
        }
    }
}
