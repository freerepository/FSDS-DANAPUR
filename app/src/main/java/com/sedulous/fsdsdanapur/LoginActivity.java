package com.sedulous.fsdsdanapur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sedulous.fsdsdanapur.Model.LoginModel;
import com.sedulous.fsdsdanapur.Utils.O;
import com.sedulous.fsdsdanapur.Utils.URLS;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {
    private AppCompatButton login;
    private EditText email, password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.bt_submit);
        email = findViewById(R.id.et_uid);
        password = findViewById(R.id.et_password);
        progressBar = findViewById(R.id.progressBar);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty() && password.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Both Fields are empty", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
                } else if(email.getText().toString().length() != 10){
                    Toast.makeText(LoginActivity.this, "Phone number is wrong", Toast.LENGTH_SHORT).show();
                }else{
                    if (O.checkNetwork(getApplicationContext())) {
                        login(email.getText().toString(), password.getText().toString());
                    } else {
                        Toast.makeText(LoginActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void login(String email, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phoneNumber", email);
            jsonObject.put("password", password);
        } catch (Exception e) {
            Log.d("ErrorWhileLogin", "JSON Error: " + e.getMessage());
        }

//        O.show_aleart_dialog(LoginActivity.this,  "Login Account");
        O.show_aleart_dialog(this, R.layout.progress_alert_dialog, "Login Account");

        final String requestBody = jsonObject.toString();
        Log.d("body", requestBody);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.adminLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("status", response.toString());
                        try {
                            LoginModel loginModel = new Gson().fromJson(response, LoginModel.class);
                            if (loginModel != null) {
                                O.savePreference(LoginActivity.this, O.USER_DATA, response);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                O.hide_aleart_dialog();
                                finish();
                            } else {
                                O.hide_aleart_dialog();
                                Toast.makeText(LoginActivity.this, "Invalid Number or Password", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            O.hide_aleart_dialog();
                            Log.d("responseError", "Parsing Error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            Log.d("ErrorWhileLogin22", "Error Code: " + error.networkResponse.statusCode);
                            try {
                                String errorMessage = new String(error.networkResponse.data, "UTF-8");
                                JSONObject jsonObject = new JSONObject(errorMessage);
                                String message = jsonObject.getString("message");
                                O.hide_aleart_dialog();
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                            } catch (UnsupportedEncodingException e) {
                                O.hide_aleart_dialog();

                                Log.d("ErrorWhileLogin24", "Encoding Error: " + e.getMessage());
                                Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                Log.d("err", "Encoding Error: " + e.getMessage());
                                O.hide_aleart_dialog();

                                e.printStackTrace();
                            }
                        } else {
                            O.hide_aleart_dialog();
                            Log.d("ErrorWhileLogin25", "Unknown Error: " + error.getMessage());
                        }
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws com.android.volley.AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    O.hide_aleart_dialog();

                    Log.d("ErrorWhileLogin26", "Body Encoding Error: " + uee.getMessage());
                    return null;
                }
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}