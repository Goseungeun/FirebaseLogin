package com.example.loginpractice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText tx_email, tx_pw, tx_name, tx_checkpw;
    Button btn_sign;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        tx_email = findViewById(R.id.email);
        tx_pw = findViewById(R.id.pw);
        tx_name = findViewById(R.id.Name);
        tx_checkpw = findViewById(R.id.check_pw);
        btn_sign = findViewById(R.id.btn_register);

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = tx_email.getText().toString().trim();
                String password = tx_pw.getText().toString().trim();
                String pwcheck = tx_checkpw.getText().toString().trim();
                String name = tx_name.getText().toString().trim();

                if(password.equals(pwcheck)){
                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        //DB??? ??????
                                        final String uid = task.getResult().getUser().getUid();
                                        User user = new User();
                                        user.userName = name;
                                        user.userEmail = email;
                                        mDatabase.child("users").child(uid).setValue(user);
                                        firebaseAuth.signOut();
                                        //????????? ???????????? ??????
                                        Intent intent = new Intent(Signup.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        //???????????? ????????? ????????? ????????? ?????????
                                        Toast.makeText(Signup.this, " ?????? ????????? ?????????????????????.",Toast.LENGTH_SHORT).show();
                                        firebaseAuth.signOut();
                                    }
                                    else {
                                        Toast.makeText(Signup.this,"??????????????? ?????????????????????.",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
                }
                else {
                    //??????????????? ???????????? ????????? ???????????? ?????? ???
                    Toast.makeText(Signup.this, "??????????????? ???????????? ????????????. ?????? ????????? ?????????",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }
}
