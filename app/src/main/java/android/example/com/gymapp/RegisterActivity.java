package android.example.com.gymapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {
    private static final String TAG = "REGISTER ACTIVITY";
    private EditText name, email, gender, password, c_password;
    private Button btn_register;
    private ProgressBar loading;
    private static String URL_REGISTER = "http://b62d1a04.ngrok.io/api/register";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loading = findViewById(R.id.loading);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        gender = findViewById(R.id.gender);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Register();

            }
        });
    }

    private void Register(){
        loading.setVisibility(View.VISIBLE);
        btn_register.setVisibility(View.GONE);

        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String gender = this.gender.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(getApplicationContext(),"Welcome "+name, Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onResponse: You are logged in");
                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else if (success.equals("0")) {
                                Toast.makeText(RegisterActivity.this, "email address already has account with us", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                Log.d(TAG, "onResponse: ");
                                btn_register.setVisibility(View.VISIBLE);
                            }
                            else
                                Toast.makeText(RegisterActivity.this, "Cant send data to server",Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_register.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            Log.d(TAG, "onResponse: Error occurred" +e.toString());
                            loading.setVisibility(View.GONE);
                            e.printStackTrace();
                            btn_register.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: Something happened" +error.toString());
                loading.setVisibility(View.GONE);
                error.printStackTrace();
                btn_register.setVisibility(View.VISIBLE);

            }
        }
        ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("email", email);
                params.put("gender" , gender);
                params.put("password" , password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }

}
