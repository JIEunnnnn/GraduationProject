package com.example.owner.project_final;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

public class FreeActivity extends AppCompatActivity {
    // 이부분이 그 사용자들이 게시글을 올리면 그에 대한 정보들을 여기에 출력해야지!

    /* For Toolbar ---------------------------------------------------------------------------------
    Toolbar toolBar;
    ----------------------------------------------------------------------------------------------*/

    /* For Navigation Drawer -----------------------------------------------------------------------
    Intent intent;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ----------------------------------------------------------------------------------------------*/




    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // freeWrite부분에서 받은 데이터들을 documnetSnspshot으로 출력하기!
    CollectionReference colRef = db.collection("freeWrite");

    ArrayList<String> dataArr = new ArrayList<String>(); // 데이터부분
    ListView listView; // 데이터를 저장하는 뷰


    Button preButton, writeButton ;


    String title, username, cont;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);

        preButton = (Button)findViewById(R.id.fButton);

        writeButton = (Button) findViewById(R.id.writeButton);

        listView = (ListView) findViewById(R.id.ListView); // 뷰영역


        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){


                    for(QueryDocumentSnapshot document : task.getResult()){
                        System.out.println("성공"+document.getData());

                      //  title = document.getString("title");
                        username = document.getString("user");
                        cont =   document.getString("contents");

                        dataArr.add(cont);

                    }

                }else{
                    System.out.println("콜렉션불러오기실패2");
                }



                ArrayAdapter<String> adapter
                        = new ArrayAdapter<String>(
                        getApplicationContext(), // 현재 화면의 제어권자
                        android.R.layout.simple_list_item_1,  //한행마다 보여줄 레이아웃을 지정
                        dataArr); // 다량의 데이터


                System.out.println("성공"+adapter);
                listView.setAdapter(adapter);


            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent3 = new Intent( FreeActivity.this , freeDetailActivity.class);

                intent3.putExtra("contents", cont);
                startActivity(intent3);
            }
        });





        preButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //이전 페이지로 화면전환
                Intent intent = new Intent (FreeActivity.this, Tab3Activity.class);

                startActivity(intent);
            }

        });

        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent (FreeActivity.this , FreeWriteActivity.class);
                startActivity(intent2);
            }
        });

        /* For Toolbar -----------------------------------------------------------------------------
        toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("우리동네 자취생");

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_menu_black_24dp);
        ------------------------------------------------------------------------------------------*/

        /* For Navigation Drawer -------------------------------------------------------------------
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main);
        navigationView = (NavigationView)findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                int id = item. getItemId();

                switch (id) {
                    case R.id.navi_tab1:
                        Toast.makeText(FreeActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( FreeActivity.this,Tab1Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab2:
                        Toast.makeText(FreeActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( FreeActivity.this,Tab2Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab3:
                        Toast.makeText(FreeActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        intent = new Intent().setClass( FreeActivity.this,Tab3Activity.class );
                        startActivity(intent);
                        break;
                    case R.id.navi_tab4:
                        Toast.makeText(FreeActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;
                    case R.id.navi_tab5:
                        Toast.makeText(FreeActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;
                    case R.id.navi_tab6:
                        Toast.makeText(FreeActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                        break;

                }

                return true;
            }
        });
        ------------------------------------------------------------------------------------------*/

    }

    /* For Toolbar ---------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);

//        drawerLayout = (DrawerLayout)findViewById(R.id.activity_main);

        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "환경설정 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return true;

            case R.id.action_mypage:
                Toast.makeText(getApplicationContext(), "마이페이지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return true;

            case android.R.id.home:
//                drawerLayout.openDrawer(GravityCompat.START);
                return true;

            default:
                Toast.makeText(getApplicationContext(), "나머지 버튼 클릭됨", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }
    ----------------------------------------------------------------------------------------------*/
}
