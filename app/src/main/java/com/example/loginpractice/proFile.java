package com.example.loginpractice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class proFile extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mdatabase;

    private TextView tv_userName;
    private Button btn_logOut;
    private Button btn_modify;
    private Button btn_delete;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        tv_userName = (TextView) findViewById(R.id.tv_username);
        btn_logOut = (Button) findViewById(R.id.btn_logout);
        btn_delete = (Button) findViewById(R.id.btn_deleteuser);
        btn_modify = (Button) findViewById(R.id.btn_modify);

        firebaseAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        final String uid = user.getUid();
        DatabaseReference userName = mdatabase.child("users").child(uid).child("userName");
        DatabaseReference now_user = mdatabase.child("users").child(uid);

        userName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                tv_userName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그아웃 버튼 눌렀을 때
                firebaseAuth.signOut();
                Toast.makeText(proFile.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(proFile.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원 정보 수정 버튼 눌렀을 때
                final EditText new_name = new EditText(proFile.this);
                AlertDialog.Builder modify_dia = new AlertDialog.Builder(proFile.this);
                modify_dia.setTitle("이름 수정");
                modify_dia.setView(new_name);
                modify_dia.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String n_name = new_name.getText().toString();
                        Map<String ,Object> updateMap = new HashMap<>();
                        updateMap.put("userName",n_name);
                        //DB 변경
                        now_user.updateChildren(updateMap);
                    }
                });
                modify_dia.show();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                now_user.removeValue();
                firebaseAuth.signOut();
                user.delete().addOnCompleteListener(proFile.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull  Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(proFile.this,"회원탈퇴되었습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Intent intent = new Intent(proFile.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
