package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bankapplication.ui.User;

public class MainActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    EditText txtUsername, txtPassword;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnRegister);

        txtUsername = (EditText)findViewById(R.id.txtUsername);
        txtPassword = (EditText)findViewById(R.id.txtPassword);

        db = new DBHandler(this);
    }

    public void onLogin(View view) {

        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        User user = db.getUser(username);
        if(user == null) {
            Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT);
        }
        else if(password.equals(user.getPassword())) {
            Intent intentHomeScreenActivity = new Intent(MainActivity.this, HomeScreenActivity.class);
            intentHomeScreenActivity.putExtra("username", username);
            finish();
            startActivity(intentHomeScreenActivity);
        } else {
            Toast.makeText(this, "Wrong Username/password entered", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRegister(View view) {
        Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

}
