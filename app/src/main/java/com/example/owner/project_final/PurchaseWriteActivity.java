package com.example.owner.project_final;

import android.app.Activity;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PurchaseWriteActivity extends AppCompatActivity {

    Intent intent;

    SeekBar seekBar;
    TextView outcome; //  period_count_purchase
    Button save, cancle;
    EditText purchase_title, purchase_User, purchase_Pass, purchase_Contents,  purchase_tradelocation ;

    public int number=1;

    public static Activity purchaseWriteActivity;
    PurchaseWriteActivity purchaseWriteact = (PurchaseWriteActivity) PurchaseWriteActivity.purchaseWriteActivity;

   // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //String email = user.getEmail(); // 접근자

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference tab1Ref = db.collection("purchase");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_write);

        purchaseWriteActivity = PurchaseWriteActivity.this;

        seekBar = (SeekBar)findViewById(R.id.period_purchase);
        outcome = (TextView)findViewById(R.id.period_count_purchase);
        save = (Button)findViewById(R.id.saveButton_purchase);
        cancle = (Button)findViewById(R.id.cancelButton_purchase);

        purchase_title = (EditText)findViewById(R.id.purchase_title);
//        purchase_User = (EditText)findViewById(R.id.purchase_User);
//        purchase_Pass = (EditText)findViewById(R.id.purchase_Pass);
        purchase_tradelocation = (EditText) findViewById(R.id.purchase_tradelocation);
        purchase_Contents = (EditText) findViewById(R.id.purchase_Contents);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                number = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                number = seekBar.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                number = seekBar.getProgress();
                update();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String title = purchase_title.getText().toString();
                String user = purchase_User.getText().toString();
                String pass = purchase_Pass.getText().toString();
                String tradelocation = purchase_tradelocation.getText().toString();
                String contents = purchase_Contents.getText().toString();


                Map<String, Object> purchase = new HashMap<>();
                purchase.put("title", title);
                purchase.put("user", user);
                purchase.put("pass",pass);
                purchase.put("location",tradelocation);
                purchase.put("contents", contents);
                purchase.put("day", number);




                tab1Ref.document(title).set(purchase)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                System.out.println("구매게시판성공");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("구매게시판실패");
                            }
                        });



                intent = new Intent().setClass( PurchaseWriteActivity.this, PurchaseActivity.class );
                startActivity(intent);
                purchaseWriteActivity.finish();
                overridePendingTransition(0, 0);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent().setClass( PurchaseWriteActivity.this, PurchaseActivity.class );
                startActivity(intent);
                purchaseWriteActivity.finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    public void update() {
        outcome.setText(new StringBuilder().append(number));
    }

}