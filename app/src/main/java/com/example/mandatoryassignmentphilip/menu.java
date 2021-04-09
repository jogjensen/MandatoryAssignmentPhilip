package com.example.mandatoryassignmentphilip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2);
        Intent intent = getIntent();
        String name = intent.getStringExtra("email");
        Log.d("MYTAG", "email " + name);

        TextView welcomeView = findViewById(R.id.welcomeTextView);
        welcomeView.setText("Welcome " + name);

    }

    public void allMessages(View view){
        Intent intent = new Intent(menu.this, AddTwitterMessageActivity.class);

        startActivity(intent);

    }
}