package com.example.owner.project_final;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.owner.project_final.adapter.RoomAdapter;
import com.example.owner.project_final.firebase.FirebaseApi;
import com.example.owner.project_final.firebase.PublicVariable;
import com.example.owner.project_final.location.LocationProvider;
import com.example.owner.project_final.model.RoomWrite;
import com.example.owner.project_final.volley.VolleyResult;
import com.example.owner.project_final.volley.VolleyService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomActivity extends AppCompatActivity {
    //[오투잡] 2019.04.13 서버로 올린 데이터 게시판 목록으로 불러들이는 작업 완료

    Intent intent;

    // For Activity finish -------------------------------------------------------------------------
    public static Activity roomActivity;


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

    // For ListView --------------------------------------------------------------------------------
    static ArrayList<String> items;
    static ArrayAdapter adapter;
    static ListView listView;
    static int count, checked;
    // ---------------------------------------------------------------------------------------------


    //[오투잡] mRecyclerView;
    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    private RoomAdapter mRoomAdapter;

    private void onLoad() {
        //[오투잡] Firebase 서버에서 게시판 목록 데이터 요청
        final ArrayList<String> UserKeyList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query req = reference.child(PublicVariable.FIREBASE_CHILD_ROOMS);


        req.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserKeyList.clear();
                    Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                    while ((iterator.hasNext())) {
                        final String key = iterator.next().getKey();
                        UserKeyList.add(key);
                    }

                    for (String userKey : UserKeyList) {
                        DatabaseReference qurey = FirebaseDatabase.getInstance().getReference().child(PublicVariable.FIREBASE_CHILD_ROOMS).child(userKey);
                        qurey.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                                        RoomWrite item = child.getValue(RoomWrite.class);
                                        mRoomAdapter.insertData(item);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ButterKnife.bind(this);

        // For Activity finish ---------------------------------------------------------------------
        roomActivity = RoomActivity.this;
        // -----------------------------------------------------------------------------------------

        // For Toolbar -----------------------------------------------------------------------------
        toolBar = (Toolbar) findViewById(R.id.roomToolbar);
        setSupportActionBar(toolBar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("단기방대여 게시판");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_black_24dp);

        setSupportActionBar(toolBar);
        // -----------------------------------------------------------------------------------------

        // For Navigation Drawer -------------------------------------------------------------------
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_room);
        navigationView = (NavigationView) findViewById(R.id.room_navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                int id = item.getItemId();

                switch (id) {
                    case R.id.navi_tab1:    //오늘 하루
                        Toast.makeText(RoomActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass(RoomActivity.this, Tab1Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab2:    //위치 서비스
                        Toast.makeText(RoomActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass(RoomActivity.this, Tab2Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3:    //게시판
                        Toast.makeText(RoomActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass(RoomActivity.this, Tab3Activity.class);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_1:    //공동구매 게시판
                        Toast.makeText(RoomActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass(RoomActivity.this, PurchaseActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_2:    //단기방대여 게시판
                        Toast.makeText(RoomActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass(RoomActivity.this, RoomActivity.class);
                        startActivity(intent);
                        break;
/*
                    case R.id.navi_tab3_3:    //음식주문 게시판
                        Toast.makeText(RoomActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( RoomActivity.this, Tab3Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_4:    //취미여가 게시판
                        Toast.makeText(RoomActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( RoomActivity.this, Tab3Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3_5:    //자유게시판
                        Toast.makeText(RoomActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( RoomActivity.this, Tab3Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab4:    //음성변조
                        Toast.makeText(RoomActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        break;
                    case R.id.navi_tab5:    //공지사항
                        Toast.makeText(RoomActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;
                    case R.id.navi_tab6:    //마이페이지
                        Toast.makeText(RoomActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;
*/
                }

                return true;
            }
        });
        // -----------------------------------------------------------------------------------------

        // For purchaseBackBtn clicked -------------------------------------------------------------
        Button roomBackBtn = (Button) findViewById(R.id.roomBackBtn);

        roomBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent().setClass(RoomActivity.this, Tab3Activity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        // -----------------------------------------------------------------------------------------

        // For ListView ----------------------------------------------------------------------------
        // 빈 데이터 리스트 생성.
        items = new ArrayList<String>();
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items);

        // listview 생성 및 adapter 지정.
        listView = (ListView) findViewById(R.id.roomListView);    //해당 리스트뷰 이름
        listView.setAdapter(adapter);

        count = adapter.getCount();
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

        mRoomAdapter = new RoomAdapter(getApplicationContext()) {
            @Override
            public void selectItem(RoomWrite item) {
                L.e(":::::Click item : " + item.toString());
                Intent intent = new Intent(RoomActivity.this, RoomDetailActivity.class);
                intent.putExtra("RoomWrite", item);
                startActivity(intent);
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(mRoomAdapter);
        onLoad();
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

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_room);

        // For ListView ----------------------------------------------------------------------------
        //count = adapter.getCount();
        //checked = listView.getCheckedItemPosition();
        //------------------------------------------------------------------------------------------

        switch (item.getItemId()) {
            case R.id.action_mainpage:
                Toast.makeText(getApplicationContext(), "메인화면 이동 버튼 클릭됨", Toast.LENGTH_LONG).show();
                intent = new Intent().setClass(RoomActivity.this, MainActivity.class);
                startActivity(intent);
                roomActivity.finish();
                overridePendingTransition(0, 0);
                return true;

            case R.id.action_refreshing:
                Toast.makeText(getApplicationContext(), "새로고침 버튼 클릭됨", Toast.LENGTH_LONG).show();
                intent = new Intent(RoomActivity.this, RoomActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                return true;

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.action_logout:
                Toast.makeText(getApplicationContext(), "로그아웃 버튼 클릭됨", Toast.LENGTH_LONG).show();
                intent = new Intent().setClass(RoomActivity.this, LoginActivity.class);
                startActivity(intent);
                roomActivity.finish();
                overridePendingTransition(0, 0);
                return true;

            case R.id.action_write:
                //[오투잡] 2019.04.12 글쓰기 버튼 클릭시 수정
                Toast.makeText(getApplicationContext(), "글쓰기 버튼 클릭됨", Toast.LENGTH_LONG).show();

                if (!gpsEnabled()) {
                    int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
                    int height = WindowManager.LayoutParams.WRAP_CONTENT;
                    new AlertDialog.Builder(RoomActivity.this, R.style.Theme_AppCompat_Light_Dialog)
                            .setMessage("계속하려면 Google 위치 서비스를 \n사용하는 기기 위치 기능을 사용 설정하세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), PublicVariable.GPS_REQUEST_CODE);
                                }
                            }).setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {

                        }
                    }).show().getWindow().setLayout(width, height);
                } else {
                    Dexter.withActivity(RoomActivity.this).withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                intent = new Intent().setClass(RoomActivity.this, RoomWriteActivity.class);
                                startActivity(intent);
                                roomActivity.finish();
                                overridePendingTransition(0, 0);
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();
                }
                return true;

            case R.id.action_modify:
                Toast.makeText(getApplicationContext(), "글수정 버튼 클릭됨", Toast.LENGTH_LONG).show();
                if (count > 0) {
                    if (checked > -1 && checked < count) {
                        // 아이템 수정
                        items.set(checked, Integer.toString(checked + 1) + "번 아이템 수정");

                        // listview 갱신
                        adapter.notifyDataSetChanged();
                    }
                }
                return true;

            case R.id.action_erase:
                Toast.makeText(getApplicationContext(), "글삭제 버튼 클릭됨", Toast.LENGTH_LONG).show();
                if (count > 0) {
                    if (checked > -1 && checked < count) {
                        // 아이템 삭제
                        items.remove(checked);

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

    public boolean gpsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    //----------------------------------------------------------------------------------------------
}
