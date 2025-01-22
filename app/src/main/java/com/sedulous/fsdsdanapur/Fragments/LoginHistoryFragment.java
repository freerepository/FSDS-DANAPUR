package com.sedulous.fsdsdanapur.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sedulous.fsdsdanapur.Model.Get_Coach_Model;
import com.sedulous.fsdsdanapur.Model.LogInHistoryModel;
import com.sedulous.fsdsdanapur.Model.LoginModel;
import com.sedulous.fsdsdanapur.O;
import com.sedulous.fsdsdanapur.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class LoginHistoryFragment extends Fragment {

    private final String loginHistoryUrl = "http://fsds.projectrailway.in/api/get_loginHistory";
    RecyclerView recyclerView;
    SwipeRefreshLayout srl;
    private TextView notfoundText;
    private ArrayList<LogInHistoryModel.UserLoginData> allTrainNumbers;
    private LoginAdapter adapter;

    private LoginModel loginModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_history, container, false);


        try {
                loginModel = new Gson().fromJson(O.getPreference(requireContext(), O.USER_DATA), LoginModel.class);
        }catch (Exception e){
            Log.d("errors", e.getMessage());
        }

        notfoundText = view.findViewById(R.id.notfoundText);
        recyclerView = view.findViewById(R.id.recyclerview);
        srl = view.findViewById(R.id.srl);
        allTrainNumbers = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new LoginAdapter(allTrainNumbers);
        recyclerView.setAdapter(adapter);

        fetchLoginHistory(loginModel.adminData.get(0).id);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchLoginHistory(loginModel.adminData.get(0).id);
            }
        });

        return view;
    }
    private void fetchLoginHistory(String id){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", id);
            srl.setRefreshing(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String requestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginHistoryUrl,
                response -> {
                    Log.d("loginerror_response", "Response: " + response); // Log the response
                    try {
                        LogInHistoryModel get_coach_model = new Gson().fromJson(response, LogInHistoryModel.class);
                        allTrainNumbers.clear();
                        srl.setRefreshing(false);
                        allTrainNumbers.addAll(get_coach_model.loginList);

                        if (adapter == null) {
                            adapter = new LoginAdapter(allTrainNumbers);
                            recyclerView.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }

                        checkEmptyData();
                    } catch (Exception e) {
                        srl.setRefreshing(false);
                        Log.e("loginError", "Parsing error: " + e.getMessage(), e);
                    } finally {
                        srl.setRefreshing(false); // Stop refreshing animation
                    }
                },
                error -> {
                    Log.e("loginerror", "Error Response: " + error.getMessage(), error);
                    srl.setRefreshing(false); // Stop refreshing animation
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    Log.e("loginerror", "Encoding error: " + uee.getMessage(), uee);
                    return null;
                }
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(stringRequest);

    }

    private void checkEmptyData() {
        if (allTrainNumbers.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            notfoundText.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            notfoundText.setVisibility(View.GONE);
        }
    }
    private class LoginAdapter extends RecyclerView.Adapter<LoginAdapter.LoginViewHolder> {

        private final ArrayList<LogInHistoryModel.UserLoginData> arrayList;

        public LoginAdapter(ArrayList<LogInHistoryModel.UserLoginData> arrayList) {
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public LoginViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new LoginViewHolder(LayoutInflater.from(requireContext()).inflate(R.layout.login_layout_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull LoginViewHolder holder, int position) {
            LogInHistoryModel.UserLoginData data = arrayList.get(position);
            holder.indexTextview.setText(String.valueOf(position+1));
            holder.username.setText(data.name);
            holder.loginTime.setText(data.last_login);
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class LoginViewHolder extends RecyclerView.ViewHolder {
            private final TextView indexTextview, username, loginTime;

            public LoginViewHolder(@NonNull View itemView) {
                super(itemView);
                indexTextview = itemView.findViewById(R.id.indexnumberTextview);
                username = itemView.findViewById(R.id.nameTextview);
                loginTime = itemView.findViewById(R.id.last_loginTextview);
            }
        }
    }
}
