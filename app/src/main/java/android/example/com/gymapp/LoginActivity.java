package android.example.com.gymapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "Loginactivity";
    private SharedPreferenceConfig preferenceConfig;
    private EditText email, password;
    private Button btn_login;
    private TextView link_register;
    private ProgressBar loading;
//    private  static String URL_LOGIN = "http://b62d1a04.ngrok.io/api/login";
    private  static String URL_LOGIN = "http://gymappapi.000webhostapp.com/api/login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        loading = findViewById(R.id.login_loading);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        btn_login = findViewById(R.id.btn_login);
        link_register = findViewById(R.id.link_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();

                String mPass = password.getText().toString().trim();


                if (!mEmail.isEmpty() || !mPass.isEmpty()) {

                    Login(mEmail,mPass);


                } else {
                    email.setError("Kindly insert an email address");
                    password.setError("Please insert your password");
                }
            }
        });

        link_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
    private void Login(final String email, final String password){
        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            String name = jsonObject.getString("name");
                            String id = jsonObject.getString("user_id");
//                            String user = String.valueOf(id);
                            String email = jsonObject.getString("email");

                            if (success.equals("1")){
                                    Toast.makeText(LoginActivity.this,"Welcome "+name+ " Your email "+email,Toast.LENGTH_SHORT).show();
                                    loading.setVisibility(View.GONE);
                                    Intent intent = new Intent(LoginActivity.this, HomeAcitivty.class);
                                    intent.putExtra("USER_ID",id);
                                Log.d(TAG, "onResponse: USER ID is "+id);
                                    intent.putExtra("USER_NAME", name);
                                    startActivity(intent);
                                    finish();
                            }
                            else if (success.equals("0")){
                                    Toast.makeText(LoginActivity.this,"There is something wrong",Toast.LENGTH_SHORT).show();
                                    loading.setVisibility(View.GONE);
                                    btn_login.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse: Something wrong happened" +e.toString());
                            Toast.makeText(LoginActivity.this, "Something happened", Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onResponse: Something wrong happened" +error.toString());
                loading.setVisibility(View.GONE);
                btn_login.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, "Something happened", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);


    }
}
