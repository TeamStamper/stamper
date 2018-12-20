package com.example.kyungyoungheo.stamper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    EditText IDEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        IDEditText = findViewById(R.id.IDEditText);
    }

    public void onLoginClick(View v){



        // If login success
        String idVal = IDEditText.getText().toString();
        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra("ID",idVal);
        startActivity(intent);
    }
}
