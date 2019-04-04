package com.example.owner.project_final;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PurchaseActivity extends AppCompatActivity {


    Intent intent;

    // For Activity finish -------------------------------------------------------------------------
    public static Activity purchaseActivity;


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

    // For ListView --------------------------------------------------------------------------------
    static ArrayList<String> items;
    static ArrayAdapter adapter;
    static ListView listView;
    static int count, checked;
    // ---------------------------------------------------------------------------------------------

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference purRef = db.collection("purchase");


    String title, contents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        // For Activity finish ---------------------------------------------------------------------
        purchaseActivity = PurchaseActivity.this;
        // -----------------------------------------------------------------------------------------

        // For Toolbar -----------------------------------------------------------------------------
        toolBar = (Toolbar)findViewById(R.id.purchaseToolbar);
        setSupportActionBar(toolBar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("공동구매 게시판");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_black_24dp);

        setSupportActionBar(toolBar);
        // -----------------------------------------------------------------------------------------

        // For Navigation Drawer -------------------------------------------------------------------
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_purchase);
        navigationView = (NavigationView)findViewById(R.id.purchase_navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                int id = item. getItemId();

                switch (id) {
                    case R.id.navi_tab1:    //오늘 하루
                        Toast.makeText(PurchaseActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( PurchaseActivity.this,Tab1Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab2:    //위치 서비스
                        Toast.makeText(PurchaseActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( PurchaseActivity.this,Tab2Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3:    //게시판
                        Toast.makeText(PurchaseActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( PurchaseActivity.this,Tab3Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_1:    //공동구매 게시판
                        Toast.makeText(PurchaseActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( PurchaseActivity.this, PurchaseActivity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_2:    //단기방대여 게시판
                        Toast.makeText(PurchaseActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( PurchaseActivity.this, RoomActivity.class );
                        startActivity(intent);
                        break;
/*
                    case R.id.navi_tab3_3:    //음식주문 게시판
                        Toast.makeText(PurchaseActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( PurchaseActivity.this, Tab3Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_4:    //취미여가 게시판
                        Toast.makeText(PurchaseActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( PurchaseActivity.this, Tab3Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_5:    //자유게시판
                        Toast.makeText(PurchaseActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( PurchaseActivity.this, Tab3Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab4:    //음성변조
                        Toast.makeText(PurchaseActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        break;
                    case R.id.navi_tab5:    //공지사항
                        Toast.makeText(PurchaseActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;
                    case R.id.navi_tab6:    //마이페이지
                        Toast.makeText(PurchaseActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;
*/
                }

                return true;
            }
        });
        // -----------------------------------------------------------------------------------------

        // For purchaseBackBtn clicked -------------------------------------------------------------
        Button purchaseBackBtn = (Button)findViewById(R.id.purchaseBackBtn);

        purchaseBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent().setClass( PurchaseActivity.this,Tab3Activity.class );
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        // -----------------------------------------------------------------------------------------

        // For ListView ----------------------------------------------------------------------------
        // 빈 데이터 리스트 생성.
        items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.


        // listview 생성 및 adapter 지정.
        listView = (ListView)findViewById(R.id.purchaseListView);    //해당 리스트뷰 이름
        listView.setAdapter(adapter);

        purRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        System.out.println("성공" + document.getData());


                        title = document.getString("title");
                        items.add(title);

                        

                    }

                }else{
                    System.out.println("구매게시판불러오기실패");

                }

                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_single_choice, items) ;
                count = adapter.getCount();

            }


        });




        checked = listView.getCheckedItemPosition();
/*
        // add button에 대한 이벤트 처리.
        Button addButton = (Button)findViewById(R.id.action_purchase_write);
        addButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count;
                count = adapter.getCount();

                // 아이템 추가.
                items.add("LIST" + Integer.toString(count + 1));

                // listview 갱신
                adapter.notifyDataSetChanged();
            }
        });

        // modify button에 대한 이벤트 처리.
        Button modifyButton = (Button)findViewById(R.id.action_purchase_modify) ;
        modifyButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count, checked ;
                count = adapter.getCount() ;

                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    checked = listView.getCheckedItemPosition();
                    if (checked > -1 && checked < count) {
                        // 아이템 수정
                        items.set(checked, Integer.toString(checked+1) + "번 아이템 수정") ;

                        // listview 갱신
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }) ;

        // delete button에 대한 이벤트 처리.
        Button deleteButton = (Button)findViewById(R.id.action_purchase_erase) ;
        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                int count, checked ;
                count = adapter.getCount() ;

                if (count > 0) {
                    // 현재 선택된 아이템의 position 획득.
                    checked = listView.getCheckedItemPosition();

                    if (checked > -1 && checked < count) {
                        // 아이템 삭제
                        items.remove(checked) ;

                        // listview 선택 초기화.
                        listView.clearChoices();

                        // listview 갱신.
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }) ;
        //------------------------------------------------------------------------------------------*/
    }

    //* For Toolbar ---------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

        drawerLayout = (DrawerLayout)findViewById(R.id.activity_purchase);


        // For ListView ----------------------------------------------------------------------------
        //count = adapter.getCount();
        //checked = listView.getCheckedItemPosition();
        //------------------------------------------------------------------------------------------

        switch (item.getItemId()) {
            case R.id.action_mainpage:
                Toast.makeText(getApplicationContext(), "공동구매 메인화면 이동 버튼 클릭됨", Toast.LENGTH_LONG).show();
                intent = new Intent().setClass( PurchaseActivity.this, MainActivity.class );
                startActivity(intent);
                purchaseActivity.finish();
                overridePendingTransition(0, 0);
                return true;

            case R.id.action_refreshing:
                Toast.makeText(getApplicationContext(), "새로고침 버튼 클릭됨", Toast.LENGTH_LONG).show();
                intent = new Intent(PurchaseActivity.this, PurchaseActivity.class);
                //ntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                adapter.notifyDataSetChanged(); // 화면전환,...
                startActivity(intent);
                return true;

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_logout:
                Toast.makeText(getApplicationContext(), "공동구매 로그아웃 버튼 클릭됨", Toast.LENGTH_LONG).show();
                intent = new Intent().setClass( PurchaseActivity.this, LoginActivity.class );
                startActivity(intent);
                purchaseActivity.finish();
                overridePendingTransition(0, 0);
                return true;

            case R.id.action_write:
                Toast.makeText(getApplicationContext(), "공동구매 글쓰기 버튼 클릭됨", Toast.LENGTH_LONG).show();
                intent = new Intent().setClass( PurchaseActivity.this, PurchaseWriteActivity.class );
                startActivity(intent);
                purchaseActivity.finish();
                overridePendingTransition(0, 0);

                // 아이템 추가.
             //  items.add("LIST" + Integer.toString(count + 1));

                // listview 갱신
                adapter.notifyDataSetChanged();
                return true;

            case R.id.action_modify:
                Toast.makeText(getApplicationContext(), "공동구매 글수정 버튼 클릭됨", Toast.LENGTH_LONG).show();
                if (count > 0) {
                    if (checked > -1 && checked < count) {
                        // 아이템 수정
                        items.set(checked, Integer.toString(checked+1) + "번 아이템 수정") ;

                        // listview 갱신
                        adapter.notifyDataSetChanged();
                    }
                }
                return true;

            case R.id.action_erase:
                Toast.makeText(getApplicationContext(), "공동구매 글삭제 버튼 클릭됨", Toast.LENGTH_LONG).show();
                if (count > 0) {
                    if (checked > -1 && checked < count) {
                        // 아이템 삭제
                        items.remove(checked) ;

                        // listview 선택 초기화.
                        listView.clearChoices();

                        // listview 갱신.
                        adapter.notifyDataSetChanged();
                    }
                }
                return true;

            default:
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }
    //----------------------------------------------------------------------------------------------
}