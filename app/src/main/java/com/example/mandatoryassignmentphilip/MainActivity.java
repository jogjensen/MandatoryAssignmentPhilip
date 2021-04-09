package com.example.mandatoryassignmentphilip;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d("MyTag","Current user: " + currentUser);

    }


    public void login(View view) {
        EditText emailView = findViewById(R.id.mainEmailEditText);
        EditText passwordView = findViewById(R.id.mainPasswordEditText);
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        if("".equals(email)) {
            emailView.setError("No email");
            return;
        }
        if("".equals(password)){
            passwordView.setError("No password");
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("MyTag", "signInWithCustomToken:success");

                            Intent intent = new Intent(MainActivity.this, menu.class);
                            intent.putExtra("email", email);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("MyTag", "signInWithCustomToken:failure", task.getException());
                            Toast.makeText(getBaseContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                            TextView messageview = findViewById(R.id.mainMessageTextView);
                            messageview.setText("Sorry..." + task.getException().getMessage());
                        }
                    }
                });
    }

    public void register(View view){
        EditText emailView = findViewById(R.id.mainEmailEditText);
        EditText passwordView = findViewById(R.id.mainPasswordEditText);
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        if("".equals(email)) {
            emailView.setError("No email");
            return;
        }
        if("".equals(password)){
            passwordView.setError("No password");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("MyTag", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            TextView messageview = findViewById(R.id.mainMessageTextView);
                            messageview.setText("Welcome new user " + user.getEmail());
                        } else {
                            Log.w("MyTag", "createUseWithEmail:faulure", task.getException());

                            TextView messageview = findViewById(R.id.mainMessageTextView);
                            messageview.setText(task.getException().getMessage());
                        }
                    }

                });
    }



    public void logout(View view){
        mAuth.signOut();
        FirebaseUser user = mAuth.getCurrentUser();
        TextView messageView = findViewById(R.id.mainMessageTextView);
        messageView.setText("You are logged out, see you another time " + user.getEmail());
    };

}