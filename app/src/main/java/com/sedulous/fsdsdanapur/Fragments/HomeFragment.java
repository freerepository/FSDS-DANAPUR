package com.sedulous.fsdsdanapur.Fragments;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.sedulous.fsdsdanapur.Model.Get_Coach_Model;
import com.sedulous.fsdsdanapur.O;
import com.sedulous.fsdsdanapur.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public String get_coach_url = "http://fsds.projectrailway.in/api/get_train_coaches";
    RecyclerView recyclerView;
    SwipeRefreshLayout srl;
    private TextView notfoundText;

    private ImageView search_button;
    HomeCoachAdapter adapter;
    private ArrayList<Get_Coach_Model.CoachData> allTrainNumbers;
    private EditText etSearchTrain;
    private boolean checkEditextText = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        notfoundText = view.findViewById(R.id.notfoundText);
        recyclerView = view.findViewById(R.id.recyclerview);
        etSearchTrain = view.findViewById(R.id.et_search);
        search_button = view.findViewById(R.id.search_button);
        srl = view.findViewById(R.id.srl);
        allTrainNumbers = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new HomeCoachAdapter(allTrainNumbers);
        recyclerView.setAdapter(adapter);


        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etSearchTrain.getText().toString().isEmpty()){ //Not empty hai
                    if (!checkEditextText){ //true
                        String trainNumber = etSearchTrain.getText().toString().trim();
                        fetchTrainNumbersFromAPI(trainNumber);
                        search_button.setImageResource(R.drawable.cross);
                        O.closeKeyboard(requireActivity(), view);
                        checkEditextText = true;
                    }else{
                        checkEditextText = false;
                        etSearchTrain.setText("");
                        search_button.setImageResource(R.drawable.ic_baseline_search_24);
                    }
                }else{
                    Toast.makeText(requireContext(), "Please enter train number", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        search_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (etSearchTrain.getText().toString().isEmpty()){
//                    Toast.makeText(requireContext(), "Please enter train number ", Toast.LENGTH_SHORT).show();
//                }else{
//                    fetchTrainNumbersFromAPI(etSearchTrain.getText().toString().trim());
//                }
//            }
//        });

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (etSearchTrain.getText().toString().isEmpty()) {
                    Toast.makeText(requireContext(), "Please enter train number", Toast.LENGTH_SHORT).show();
                    srl.setRefreshing(false);
                } else {
                    fetchTrainNumbersFromAPI(etSearchTrain.getText().toString().trim());
                    search_button.setImageResource(R.drawable.cross);
                    srl.setRefreshing(false);
                }
            }
        });
        return view;
    }

    private void fetchTrainNumbersFromAPI(String value) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("train_number", value.trim());
            srl.setRefreshing(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String requestBody = jsonObject.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, get_coach_url,
                response -> {
                    Log.d("BedrollStocking", "Response: " + response); // Log the response
                    try {
                        Get_Coach_Model get_coach_model = new Gson().fromJson(response, Get_Coach_Model.class);

                        // Update data list and notify adapter
                        allTrainNumbers.clear();
                        allTrainNumbers.addAll(get_coach_model.coachDataList);

                        // Initialize adapter if not already initialized
                        if (adapter == null) {
                            adapter = new HomeCoachAdapter(allTrainNumbers);
                            recyclerView.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }

//                        // Check and update visibility based on data availability
                        checkEmptyData();
                    } catch (Exception e) {
                        Log.e("BedrollStocking", "Parsing error: " + e.getMessage(), e);
                    } finally {
                        srl.setRefreshing(false); // Stop refreshing animation
                    }
                },
                error -> {
                    Log.e("BedrollStocking", "Error Response: " + error.getMessage(), error);
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
                    Log.e("BedrollStocking", "Encoding error: " + uee.getMessage(), uee);
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

    public class HomeCoachAdapter extends RecyclerView.Adapter<HomeCoachAdapter.MyHomeCoachViewHolder> {

        private ArrayList<Get_Coach_Model.CoachData> arrayList;

        public HomeCoachAdapter(ArrayList<Get_Coach_Model.CoachData> arrayList) {
            this.arrayList = arrayList;
        }


        @NonNull
        @Override
        public MyHomeCoachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_search_item, parent, false);
            return new MyHomeCoachViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHomeCoachViewHolder holder, int position) {
            Get_Coach_Model.CoachData data = arrayList.get(position);
            holder.tvCoachNumber.setText(data.coachNumber);

//            if (position % 2 == 0) {
//                holder.switchButton.setImageResource(R.drawable.switch_off);
//            } else {
//                holder.switchButton.setImageResource(R.drawable.switch_on);
//            }

            if (data.switchStatus=="1"){
                holder.switchButton.setImageResource(R.drawable.switch_on);
            }else{
                holder.switchButton.setImageResource(R.drawable.switch_off);
            }

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class MyHomeCoachViewHolder extends RecyclerView.ViewHolder {
            TextView tvCoachNumber;
            @SuppressLint("UseSwitchCompatOrMaterialCode")
            ImageView switchButton;

            public MyHomeCoachViewHolder(@NonNull View itemView) {
                super(itemView);
                tvCoachNumber = itemView.findViewById(R.id.coach_number);
                switchButton = itemView.findViewById(R.id.switchBtn);
            }
        }
    }
}
