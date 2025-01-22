package com.sedulous.fsdsdanapur.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Get_Coach_Model implements Serializable {

    @SerializedName("message")
    public String message;

    @SerializedName("Getdata")
    public ArrayList<CoachData> coachDataList;

    public static class CoachData implements Serializable {

        @SerializedName("id")
        public String id;

        @SerializedName("train_number")
        public String trainNumber;

        @SerializedName("coach_number")
        public String coachNumber;

        @SerializedName("blynk_auth_token")
        public String blynkAuthToken;

        @SerializedName("pin")
        public String pin;

        @SerializedName("switch_status")
        public String switchStatus;

        // You can add a method to get multiple coach numbers as a list if needed
        public ArrayList<String> getCoachNumbers() {
            ArrayList<String> coachNumbers = new ArrayList<>();
            if (coachNumber != null && coachNumber.contains(",")) {
                String[] splitCoachNumbers = coachNumber.split(",");
                for (String coach : splitCoachNumbers) {
                    coachNumbers.add(coach);
                }
            } else {
                coachNumbers.add(coachNumber);
            }
            return coachNumbers;
        }
    }
}
