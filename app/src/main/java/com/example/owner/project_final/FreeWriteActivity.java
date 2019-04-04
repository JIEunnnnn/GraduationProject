package com.example.owner.project_final;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class FreeWriteActivity extends AppCompatActivity {


   //  String username = user.getDisplayName();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference freeRef = db.collection("freeWrite"); // 회원들의 전체정보 collection, 그 중 하나정보 document


   //   DocumentReference freeDoc = db.collection("freeWrite").document("");

    EditText freeTitle, freeUsers, freeContents, freePassword;
    Button SaveButton, CancelButoon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freewrite);

        freeTitle = (EditText)findViewById(R.id.freeTitle);
        freeUsers = (EditText) findViewById(R.id.freeUser);
        freeContents = (EditText) findViewById(R.id.freeContents);
        freePassword = (EditText)findViewById(R.id.freePass);

        SaveButton = (Button) findViewById(R.id.saveButton);
        CancelButoon = (Button)findViewById(R.id.cancelButton);


        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = freeTitle.getText().toString();
                String user = freeUsers.getText().toString();
                String contents = freeContents.getText().toString();
                String password = freePassword.getText().toString();


                Map<String, Object> freepostValues = new HashMap<>();
                freepostValues.put("title", title);
                freepostValues.put("user", user);
                freepostValues.put("contents", contents);
                freepostValues.put("password", password);

                freeRef.document(contents).set(freepostValues)

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("성공");
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("실패");
                    }
                });


                Toast.makeText(getApplicationContext(), "저장완료", Toast.LENGTH_SHORT).show();
                // 액티비티 종료코드 추가
                finish();
            }
        });



        CancelButoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });



        }}

