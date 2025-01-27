package com.sedulous.fsdsdanapur.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sedulous.fsdsdanapur.LoginActivity;
import com.sedulous.fsdsdanapur.Model.LoginModel;
import com.sedulous.fsdsdanapur.Utils.O;
import com.sedulous.fsdsdanapur.R;
import com.sedulous.fsdsdanapur.Utils.URLS;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ChangePasswordFragment extends Fragment {
    private LoginModel loginModel;
    private EditText changePassword, confirmPassword;
    private AppCompatButton change_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        try {
            loginModel = new Gson().fromJson(O.getPreference(requireContext(), O.USER_DATA), LoginModel.class);
        } catch (Exception e) {
            Log.d("fetchError", e.getMessage());
        }

        changePassword = view.findViewById(R.id.et_change_password);
        confirmPassword = view.findViewById(R.id.et_change_password_confirm);
        change_button = view.findViewById(R.id.bt_button);
        change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (changePassword.getText().toString().isEmpty() && confirmPassword.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(), "Both field are empty", Toast.LENGTH_SHORT).show();
                } else if (changePassword.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(), "First Field is empty", Toast.LENGTH_SHORT).show();
                } else if (confirmPassword.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(), "Second Field is empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (!changePassword.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {
                        Toast.makeText(requireContext(), "Password is not same", Toast.LENGTH_SHORT).show();
                    } else {
                        updatePassword(changePassword.getText().toString().trim(), confirmPassword.getText().toString(), loginModel.adminData.get(0).id);
                    }
                }
            }
        });


        return view;
    }

    private void updatePassword(String password, String confirmpassword, String id) {


        JSONObject jsonObject = new JSONObject();
        try {
            if (id.isEmpty() && id == null) {
                id = loginModel.adminData.get(0).id;
                jsonObject.put("user_id", id);
            } else {
                jsonObject.put("user_id", id);
            }
            jsonObject.put("new_password", password);
            jsonObject.put("confirm_password", confirmpassword);
        } catch (Exception e) {
            Log.d("ErrorWhilelogin", e.getMessage());
        }

        String reqbody = jsonObject.toString();
        Log.d("body", reqbody);
        O.show_aleart_dialog(requireActivity(), R.layout.progress_alert_dialog, "Wait a second ...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.changePassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("UpdatePassword", "Response: " + response);
                    JSONObject jsonResponse = new JSONObject(response);
                    if (jsonResponse.has("success")) {
                        int successValue = jsonResponse.getInt("success"); // Parse as integer
                        if (successValue == 1) {
                            startActivity(new Intent(requireActivity(), LoginActivity.class));
                            requireActivity().finishAffinity();
                            Toast.makeText(requireContext(), "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            String message = jsonResponse.has("message") ? jsonResponse.getString("message") : "Unknown error occurred";
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "Unexpected response format", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d("ErrorWhileParsing", e.getMessage());
                    Toast.makeText(requireContext(), "Error while processing response", Toast.LENGTH_SHORT).show();
                } finally {
                    O.hide_aleart_dialog(); // Ensure the dialog is hidden
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.getMessage());
                O.hide_aleart_dialog();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws com.android.volley.AuthFailureError {
                try {
                    return reqbody == null ? null : reqbody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    O.hide_aleart_dialog();

                    Log.d("ErrorWhileLogin26", "Body Encoding Error: " + uee.getMessage());
                    return null;
                }
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);

    }

}