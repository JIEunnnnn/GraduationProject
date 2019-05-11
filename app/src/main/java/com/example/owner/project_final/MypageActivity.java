package com.example.owner.project_final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MypageActivity extends AppCompatActivity {

    @BindView(R.id.username)
    TextView tvUser;

    MypageActivity mMypageActivity;
    Intent intent;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //private String id;
    String id;
    String writer;

    public MypageActivity() {
    }

    public MypageActivity(String id, String writer) {
        this.id = id;   //autokey
        this.writer = writer;   //editUser
    }

    public String getId() {
        return id;
    }
    public String getWriter() {
        return writer;
    }

    @Override
    public String toString() {
        return "MypageActivity{" +
                "writer='" + writer + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        ButterKnife.bind(this);

        Intent intent_detail = getIntent();
        Bundle bundle = new Bundle();
        if (intent_detail != null) {
            mMypageActivity = (MypageActivity) intent_detail.getSerializableExtra("MypageActivity");
            L.e("::::::시리얼 라이저블 : " + mMypageActivity.toString());

            id = mMypageActivity.getId();    //키 값 가져오기

            tvUser.setText(mMypageActivity.getWriter());


        }


    }
}


