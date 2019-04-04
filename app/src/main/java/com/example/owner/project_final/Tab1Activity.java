package com.example.owner.project_final;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;

import static android.icu.util.Calendar.YEAR;

public class Tab1Activity extends MainActivity {
    // 달력받아와서
    // 체크박스 등록 -> 알림창으로 나타내기

    //------------------------------------------------------------------------------------------
    //Dialog dia;
    //------------------------------------------------------------------------------------------

    // For Activity finish -------------------------------------------------------------------------
    public static Activity tab1Activity;


    MainActivity mainact = (MainActivity)MainActivity.mainActivity;
    Tab1Activity tab1act = (Tab1Activity)Tab1Activity.tab1Activity;
    Tab2Activity tab2act = (Tab2Activity)Tab2Activity.tab2Activity;
    Tab3Activity tab3act = (Tab3Activity)Tab3Activity.tab3Activity;
    PurchaseActivity purchaseact = (PurchaseActivity)PurchaseActivity.purchaseActivity;
    RoomActivity roomact = (RoomActivity)RoomActivity.roomActivity;
    //----------------------------------------------------------------------------------------------

    // For Toolbar ---------------------------------------------------------------------------------
    Toolbar toolBar;
    //----------------------------------------------------------------------------------------------

    // For Navigation Drawer -----------------------------------------------------------------------
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    // ---------------------------------------------------------------------------------------------

    int year, month, date ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1);

       final CalendarView cal1 = (CalendarView)findViewById(R.id.cal1);

        // For Activity finish ---------------------------------------------------------------------
        tab1Activity = Tab1Activity.this;


//        tab1act.finish();
//        tab2act.finish();
//        tab3act.finish();
        // -----------------------------------------------------------------------------------------

        // For Toolbar -----------------------------------------------------------------------------
        toolBar = (Toolbar)findViewById(R.id.tab1Toolbar);
        setSupportActionBar(toolBar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("오늘 하루");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_black_24dp);
        // -----------------------------------------------------------------------------------------

        // For Navigation Drawer -------------------------------------------------------------------
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_tab1);  //각 레이아웃의 가장 큰 DrawerLayout 이름
        navigationView = (NavigationView)findViewById(R.id.navigationView_tab1);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                int id = item. getItemId();

                switch (id) {
                    case R.id.navi_tab1:    //오늘 하루
                        Toast.makeText(Tab1Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( Tab1Activity.this,Tab1Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab2:    //위치 서비스
                        Toast.makeText(Tab1Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( Tab1Activity.this,Tab2Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3:    //게시판
                        Toast.makeText(Tab1Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( Tab1Activity.this,Tab3Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_1:    //공동구매 게시판
                        Toast.makeText(Tab1Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( Tab1Activity.this, PurchaseActivity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_2:    //단기방대여 게시판
                        Toast.makeText(Tab1Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( Tab1Activity.this, RoomActivity.class );
                        startActivity(intent);
                        break;
/*
                    case R.id.navi_tab3_3:    //음식주문 게시판
                        Toast.makeText(Tab1Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( Tab1Activity.this, Tab3Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_4:    //취미여가 게시판
                        Toast.makeText(Tab1Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( Tab1Activity.this, Tab3Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_5:    //자유게시판
                        Toast.makeText(Tab1Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( Tab1Activity.this, Tab3Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab4:    //음성변조
                        Toast.makeText(Tab1Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        break;
                    case R.id.navi_tab5:    //공지사항
                        Toast.makeText(Tab1Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;
                    case R.id.navi_tab6:    //마이페이지
                        Toast.makeText(Tab1Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;
*/
                }

                return true;
            }
        });
        // -----------------------------------------------------------------------------------------

       /* cal1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

            }
        });*/

        /*------------------------------------------------------------------------------------------

        Button btn1_first=(Button)findViewById(R.id.btn1_first);
        Button btn1_second=(Button)findViewById(R.id.btn1_second);
        Button btn1_third=(Button)findViewById(R.id.btn1_third);   // 삭제될 예정


        btn1_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent().setClass( Tab1Activity.this,Tab1Activity.class );
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        btn1_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent().setClass( Tab1Activity.this,Tab2Activity.class );
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
        btn1_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent().setClass( Tab1Activity.this,Tab3Activity.class );
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });


        ------------------------------------------------------------------------------------------*/




        cal1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year1, int month1, int dayOfMonth1) {
                year = year1;
                month = month1 + 1;
                date = dayOfMonth1;

                String data = String.format("%d-%d-%d", year, month, date);



                    Intent intent = new Intent(getApplicationContext(), checkbox_tab1.class);
                    intent.putExtra("today", data);
                    startActivity(intent);





            }
        });
    }

    // For Toolbar ---------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);    //각자에 맞는 R.menu. 파일 작성할 것
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

        drawerLayout = (DrawerLayout)findViewById(R.id.activity_tab1);  //각자에 맞는 레이아웃의 가장 겉 DrawerLayout 이용할 것

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "환경설정 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_mypage:
                Toast.makeText(getApplicationContext(), "마이페이지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return true;

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_logout:
                Toast.makeText(getApplicationContext(), "공동구매 로그아웃 버튼 클릭됨", Toast.LENGTH_LONG).show();
                intent = new Intent().setClass( Tab1Activity.this, LoginActivity.class );
                startActivity(intent);
                overridePendingTransition(0, 0);
                return true;

            default:
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }
    //----------------------------------------------------------------------------------------------
}


        // 1. 날짜가 입력된날짜로만 뜸 선택된날짜로 출력안됨 - 완료!!!
        // 2. 데이터베이스에 저장한 값들을 날짜에 맞게 불러올수있어야함 - 완료!!
        // 3. 선택된 날짜를 통해 알고리즘? 작성하여 메세지를 제공할수있어야함
        // 4. 캘린더의 대화상자가 두번이상 나오지않게 설정 - 완료!!


