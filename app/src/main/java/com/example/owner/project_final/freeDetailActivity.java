package com.example.owner.project_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class freeDetailActivity extends AppCompatActivity {


    EditText  titles , users, contents ;
    Button cancel, revise, delete ;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference colrf = db.collection("freeWrite");

    Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freedetail);


        titles = (EditText)findViewById(R.id.freeTitle);
        users = (EditText) findViewById(R.id.freeUser);
        contents = (EditText) findViewById(R.id.freeContents);

        revise = (Button)findViewById(R.id.reviseButton);
        cancel = (Button)findViewById(R.id.cancelButton);
        delete = (Button) findViewById(R.id.deleteButton);

        intent = getIntent();

        final String cont = intent.getExtras().getString("contents"); // freeActivity에서 받아온 사용자정보


        final DocumentReference docref = colrf.document(cont);

        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    System.out.println("문서불러오기성공"+document.getData());

                    if(document.exists()){
                        String sttitle = document.getString("title");
                        String stuser = document.getString("user");
                        String stcontents = document.getString("contents");

                        titles.setText(sttitle);
                        users.setText(stuser);
                        contents.setText(stcontents);


                    }else{
                        System.out.println("문서불러오기실패");
                    }
                }else{
                    System.out.println("문서불러오기실패2222");

                }
            }
        });





        // 수정 삭제 뒤로가기 버튼


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // FreeredeActivity
                // String tag = "delete";
                //Intent,putExtra("delete", tag);

                // 비밀번호 입력하여 수정이나 삭제할수있게끔 하기!



            }
        });

        revise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // FreeredeActivity -> FreeDeleteActivity
                // String tag ="revise";
                //Intent,putExtra("revise", tag);

            }});
    }
}
