package com.sedulous.fsdsdanapur.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sedulous.fsdsdanapur.Model.Get_Coach_Model;
import com.sedulous.fsdsdanapur.R;
import java.util.ArrayList;

public class TrainNumberAdapter extends RecyclerView.Adapter<TrainNumberAdapter.ViewHolder> {

    public static ArrayList<Get_Coach_Model.CoachData> coachDataList;
    private Context context;

    public TrainNumberAdapter(ArrayList<Get_Coach_Model.CoachData> coachDataList, Context context) {
        this.coachDataList = coachDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.train_search_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Get_Coach_Model.CoachData coachData = coachDataList.get(position);

        // Display multiple coach numbers if available
        ArrayList<String> coachNumbers = coachData.getCoachNumbers();
        StringBuilder coachNumberText = new StringBuilder();
        for (String coachNumber : coachNumbers) {
            coachNumberText.append(coachNumber).append(", ");
        }

        // Remove last comma and space
        if (coachNumberText.length() > 0) {
            coachNumberText.setLength(coachNumberText.length() - 2);
        }

        holder.tvCoachNumber.setText(coachNumberText.toString());
        holder.switchButton.setEnabled(false);

        // You can also handle the switch status if needed
        // if (coachData.switchStatus.equals("1")) {
        //     holder.switchButton.setImageResource(R.drawable.switch_on);
        // } else {
        //     holder.switchButton.setImageResource(R.drawable.switch_off);
        // }
    }

    @Override
    public int getItemCount() {
        return coachDataList != null ? coachDataList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCoachNumber;
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        ImageView switchButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCoachNumber = itemView.findViewById(R.id.coach_number);
            switchButton = itemView.findViewById(R.id.switchBtn);
        }
    }
}
