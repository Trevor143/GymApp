package android.example.com.gymapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SessionActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SessionAdapter sessionAdapter;
    private ArrayList<SessionItem> sessionList;
    private RequestQueue requestQueue;
    private String USER_ID ;
    private static String TAG ="SessionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);


        recyclerView = findViewById(R.id.recycler_view_session);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sessionList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        USER_ID = intent.getStringExtra("USER_ID");
        Log.d(TAG, "onCreate: USER_Id"+USER_ID);
        parseSession();

    }

    private void parseSession() {


        Log.d(TAG, "parseSession: User Id is "+USER_ID);


//        String url ="http://b62d1a04.ngrok.io/api/showsession/"+USER_ID;
        String url ="http://gymappapi.000webhostapp.com/api/showsession/"+USER_ID;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i<jsonArray.length();i++){
                                JSONObject session = jsonArray.getJSONObject(i);

                                String exercise = session.getString("exerciseType");
                                int sets  = session.getInt("Sets");
                                String gymName = session.getString("gymName");
                                String instructorName = session.getString("InstructorName");

                                sessionList.add(new SessionItem(exercise,sets,gymName,instructorName));

                            }
                            sessionAdapter = new SessionAdapter(SessionActivity.this,sessionList);
                            recyclerView.setAdapter(sessionAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

requestQueue.add(request);
    }

}
