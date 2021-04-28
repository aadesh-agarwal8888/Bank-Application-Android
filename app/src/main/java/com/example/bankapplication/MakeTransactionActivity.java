package com.example.bankapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MakeTransactionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button btnInitiateTransaction;
    TextView txtBalanceUpdate;
    Spinner spinnerTransactionType;
    EditText txtAmount;
    String username;

    String transactionTypes[] = {"Deposit", "Withdraw"};

    int transactionTypeSelected = 0;

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_transaction);

        btnInitiateTransaction = (Button)findViewById(R.id.btnInitiateTransaction);
        txtBalanceUpdate = (TextView) findViewById(R.id.txtBalanceUpdate);
        spinnerTransactionType = (Spinner) findViewById(R.id.spinnerTransactionType);
        txtAmount = (EditText) findViewById(R.id.txtAmount);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        spinnerTransactionType.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,transactionTypes);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTransactionType.setAdapter(aa);

        db = new DBHandler(this);
    }

    public void onInitiateTransaction(View view) {
        int amount = Integer.parseInt(txtAmount.getText().toString());
        boolean status;
        if(transactionTypeSelected == 0) {
            status = db.addBalance(username, amount);
        } else {
            status = db.deductBalance(username, amount);
        }
        if(!status) {
            Toast.makeText(this, "Balance Unavailable", Toast.LENGTH_SHORT).show();
        } else {
            Intent intentHomeScreen = new Intent(this, HomeScreenActivity.class);
            intentHomeScreen.putExtra("username", username);
            finish();
            startActivity(intentHomeScreen);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id){
        transactionTypeSelected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {
        // Auto-generated method stub
    }
}
