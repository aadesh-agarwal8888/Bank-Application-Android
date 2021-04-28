package com.example.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bankapplication.ui.User;

public class HomeScreenActivity extends AppCompatActivity {

    Button btnMakeTransaction;

    TextView txtName, txtBalance;

    String username;

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        btnMakeTransaction = (Button)findViewById(R.id.btnMakeTransaction);
        txtName = (TextView)findViewById(R.id.txtName);
        txtBalance = (TextView)findViewById(R.id.txtBalance);

        db = new DBHandler(this);
        User user = db.getUser(username);

        txtBalance.setText("Balance: " + user.getBalance());
        txtName.setText("Customer Name: " + user.getName());
    }

    public void onMakeTransaction(View view) {
        Intent intentTransactionActivity = new Intent(HomeScreenActivity.this, MakeTransactionActivity.class);
        intentTransactionActivity.putExtra("username", username);
        finish();
        startActivity(intentTransactionActivity);
    }
}
