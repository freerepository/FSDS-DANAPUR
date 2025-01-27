package com.sedulous.fsdsdanapur.Fragments;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.sedulous.fsdsdanapur.Model.DeviceStatusModel;
import com.sedulous.fsdsdanapur.Model.LoginModel;
import com.sedulous.fsdsdanapur.Utils.O;
import com.sedulous.fsdsdanapur.R;
import com.sedulous.fsdsdanapur.Utils.URLS;

import java.util.ArrayList;

public class DeviceStatusFragment extends Fragment {
    RecyclerView recyclerView;
    SwipeRefreshLayout srl;
    private TextView notfoundText;
    private ArrayList<DeviceStatusModel.DeviceStatus> allTrainNumbers;
    private LoginAdapter adapter;
    LoginModel loginModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_status, container, false);
        try {
            loginModel = new Gson().fromJson(O.getPreference(requireContext(), O.USER_DATA), LoginModel.class);
        } catch (Exception e) {
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

        fetchDeviceStatus();
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchDeviceStatus();
            }
        });

        return view;
    }

    private void fetchDeviceStatus() {
        srl.setRefreshing(true);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.getdeviceHistory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    DeviceStatusModel get_coach_model = new Gson().fromJson(response, DeviceStatusModel.class);
                    allTrainNumbers.clear();
                    srl.setRefreshing(false);
                    allTrainNumbers.addAll(get_coach_model.arrayList);

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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                srl.setRefreshing(false);
                Log.d("Error ", error.getMessage());
            }
        });
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

        private final ArrayList<DeviceStatusModel.DeviceStatus> arrayList;

        public LoginAdapter(ArrayList<DeviceStatusModel.DeviceStatus> arrayList) {
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public LoginAdapter.LoginViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new LoginAdapter.LoginViewHolder(LayoutInflater.from(requireContext()).inflate(R.layout.device_status_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull LoginAdapter.LoginViewHolder holder, int position) {
            DeviceStatusModel.DeviceStatus data = arrayList.get(position);
            holder.indexTextview.setText(String.valueOf(position + 1));
            holder.username.setText(data.decive_id);
            if (data.device_status.equals("1")){
                holder.status.setText("ON");
            }else{
                holder.status.setText("OFF");
            }
            holder.loginTime.setText(data.last_active);
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class LoginViewHolder extends RecyclerView.ViewHolder {
            private final TextView indexTextview, username,status, loginTime;

            public LoginViewHolder(@NonNull View itemView) {
                super(itemView);
                indexTextview = itemView.findViewById(R.id.indexnumberTextview);
                username = itemView.findViewById(R.id.nameTextview);
                status = itemView.findViewById(R.id.last_loginTextview);
                loginTime = itemView.findViewById(R.id.last_loginTextview2);
            }
        }
    }
}