package com.example.owner.project_final;

/**
 * 2019.03.15 04:37
 * 게시판 글 목록 보여주기, 수정, 삭제 기능
 * 게시판 글 목록 보여줄 때, 제목, 작성자, 가격, 주소, 게시기간 보여주기
 * 게시판 별 글쓰기 상세 변화주기
 * 게시글 입력 시 주소는 다음 주소 API 이용하여 정확한 주소로 입력받기
 * 게시판 글 내용 상세 보기 화면 구성
 */

import android.app.Activity;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.ButterKnife;

public class Tab3Activity extends MainActivity {
    // For Activity finish -------------------------------------------------------------------------

    public static Activity tab3Activity;


    MainActivity mainact = (MainActivity) MainActivity.mainActivity;
    Tab1Activity tab1act = (Tab1Activity) Tab1Activity.tab1Activity;
    Tab2Activity tab2act = (Tab2Activity) Tab2Activity.tab2Activity;
    Tab3Activity tab3act = (Tab3Activity) Tab3Activity.tab3Activity;
    PurchaseActivity purchaseact = (PurchaseActivity) PurchaseActivity.purchaseActivity;
    RoomActivity roomact = (RoomActivity) RoomActivity.roomActivity;
    //----------------------------------------------------------------------------------------------

    // For Toolbar ---------------------------------------------------------------------------------
    Toolbar toolBar;
    //----------------------------------------------------------------------------------------------

    // For Navigation Drawer -----------------------------------------------------------------------
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3);


        // For Activity finish ---------------------------------------------------------------------
        tab3Activity = Tab3Activity.this;


//        tab1act.finish();
//        tab2act.finish();
//        tab3act.finish();
        // -----------------------------------------------------------------------------------------

        // For Toolbar -----------------------------------------------------------------------------
        toolBar = (Toolbar) findViewById(R.id.tab3Toolbar);
        setSupportActionBar(toolBar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("게시판");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_black_24dp);
        // -----------------------------------------------------------------------------------------

        // For Navigation Drawer -------------------------------------------------------------------
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_tab3);  //각 레이아웃의 가장 큰 DrawerLayout 이름
        navigationView = (NavigationView) findViewById(R.id.navigationView_tab3);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                int id = item.getItemId();

                switch (id) {
                    case R.id.navi_tab1:    //오늘 하루
                        Toast.makeText(Tab3Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass(Tab3Activity.this, Tab1Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab2:    //위치 서비스
                        Toast.makeText(Tab3Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass(Tab3Activity.this, Tab2Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3:    //게시판
                        Toast.makeText(Tab3Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass(Tab3Activity.this, Tab3Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_1:    //공동구매 게시판
                        Toast.makeText(Tab3Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass(Tab3Activity.this, PurchaseActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_2:    //단기방대여 게시판
                        Toast.makeText(Tab3Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass(Tab3Activity.this, RoomActivity.class);
                        startActivity(intent);
                        break;
/*
                    case R.id.navi_tab3_3:    //음식주문 게시판
                        Toast.makeText(Tab3Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( Tab3Activity.this, Tab3Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_4:    //취미여가 게시판
                        Toast.makeText(Tab3Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( Tab3Activity.this, Tab3Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_5:    //자유게시판
                        Toast.makeText(Tab3Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( Tab3Activity.this, Tab3Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab4:    //음성변조
                        Toast.makeText(Tab3Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        break;
                    case R.id.navi_tab5:    //공지사항
                        Toast.makeText(Tab3Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;
                    case R.id.navi_tab6:    //마이페이지
                        Toast.makeText(Tab3Activity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;
*/
                }

                return true;
            }
        });
        // -----------------------------------------------------------------------------------------

        final Button purchaseButton = (Button) findViewById(R.id.purchaseButton); // 공동구매
        final Button foodButton = (Button) findViewById(R.id.foodButton); // 음식배달
        final Button hobbyButton = (Button) findViewById(R.id.hobbyButton); // 취미공유
        final Button roomButton = (Button) findViewById(R.id.roomButton); // 단기방대여

        Button noticeButton = (Button) findViewById(R.id.noticeButton);   // 공지버튼
        final Button freeButton = (Button) findViewById(R.id.freeButton);    // 자유게시판 버튼

        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //다음페이지로 화면을 전환
                //화면 전환시 사용하는 클래스
                Intent intent1 = new Intent(Tab3Activity.this, PurchaseActivity.class);
                //화면전환하기
                startActivity(intent1);
            }

        });

        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Tab3Activity.this, FoodActivity.class);
                startActivity(intent2);
            }
        });

        roomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Tab3Activity.this, RoomActivity.class);
                startActivity(intent3);
            }
        });

        hobbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Tab3Activity.this, HobbyActivity.class);
                startActivity(intent4);
            }
        });

        noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent7 = new Intent(Tab3Activity.this, NoticeActivity.class);
                startActivity(intent7);
            }
        });

        freeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent8 = new Intent(Tab3Activity.this, FreeActivity.class);
                startActivity(intent8);
            }
        });

        /*------------------------------------------------------------------------------------------
        Button btn3_first=(Button)findViewById(R.id.btn3_first);
        Button btn3_second=(Button)findViewById(R.id.btn3_second);
        Button btn3_third=(Button)findViewById(R.id.btn3_third);


        btn3_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent().setClass( Tab3Activity.this,Tab1Activity.class );
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        btn3_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent().setClass( Tab3Activity.this,Tab2Activity.class );
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        });
        btn3_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent().setClass( Tab3Activity.this,Tab3Activity.class );
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        ------------------------------------------------------------------------------------------*/

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

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_tab3);  //각자에 맞는 레이아웃의 가장 겉 DrawerLayout 이용할 것

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
                intent = new Intent().setClass(Tab3Activity.this, LoginActivity.class);
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
