package android.example.com.gymapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeAcitivty extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RequestQueue mQueue;
    private String USER_ID ;
    private String USER_NAME ;
    private static final String TAG = "MainActivity";
    private TextView Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_acitivty);


//        COde for receiving sessions from back end
        final Intent intent = getIntent();
        USER_ID = intent.getStringExtra("USER_ID");
        USER_NAME =intent.getStringExtra("USER_NAME");
//        mtextViewResult = findViewById(R.id.text_view_result);
//        Username = findViewById(R.id.UserName);
//        Username.setText(USER_NAME);

        Log.d(TAG, "onCreate: USER ID "+USER_ID);
        mQueue = Volley.newRequestQueue(this);

//        End of code for receiving data from backend

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.showgyms:
                        item.setChecked(true);
                        Log.d(TAG, "onNavigationItemSelected: Show gyms started");
                        Intent newintent = new Intent(HomeAcitivty.this,MainActivity.class);
                        startActivity(newintent);
                        Log.d(TAG, "onNavigationItemSelected: New activity started");
                        displayMessage("Retrieving all gyms in our network");
                        Log.d(TAG, "onNavigationItemSelected: " );
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.sessions:
                        item.setChecked(true);
                        Intent intent1= new Intent(HomeAcitivty.this, SessionActivity.class);
                        intent1.putExtra("USER_ID",USER_ID);
                        startActivity(intent1);
                        displayMessage("All past workout sessions");
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.startsession:
                        item.setChecked(true);
                        displayMessage("Record your session");
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.findgym:
                        item.setChecked(true);
                        displayMessage("Map Not ready");
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.profile:
                        item.setChecked(true);
                        displayMessage("Not implemented");
                        drawerLayout.closeDrawers();
                        return true;
                    case R.id.logout:
                        item.setChecked(true);
                        displayMessage("Not implemented");
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });

    }

    private void displayMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return  super.onOptionsItemSelected(item);
    }

//    private void jsonParse(){
//        String url = "http://b62d1a04.ngrok.io/api/showsession/"+USER_ID;
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, "onResponse: Listener started");
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("data");
//                            for (int i = 0 ; i < jsonArray.length() ; i++){
//                                JSONObject data = jsonArray.getJSONObject(i);
//
//                                Log.d(TAG, "onResponse: Listener still working");
//
//                                String exerciseType = data.getString("exerciseType");
//                                int Sets =data.getInt("Sets");
//                                String gymName = data.getString("gymName");
//                                String InstructorName = data.getString("InstructorName");
//
//                                Log.d(TAG, "onResponse: Progress "+exerciseType+", "+Sets+", "+gymName );
//
////                                mtextViewResult.append((exerciseType+","+String.valueOf(Sets) +","+gymName +","+InstructorName));
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), "An error has occured!!", Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "onErrorResponse: Error occured -->>" +error.getMessage());
//                error.printStackTrace();
//            }
//        }
//        );
//
//        request.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        mQueue.add(request);
//        Log.d(TAG, "jsonParse: All data should be shown");
//    }

}
