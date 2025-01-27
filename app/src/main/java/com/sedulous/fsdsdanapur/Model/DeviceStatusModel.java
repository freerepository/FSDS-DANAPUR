package com.sedulous.fsdsdanapur.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DeviceStatusModel implements Serializable {

    @SerializedName("GetDevicedata")
    public ArrayList<DeviceStatus> arrayList;

    public static class DeviceStatus  implements Serializable{

        @SerializedName("id")
        public String id;

        @SerializedName("decive_id")
        public String decive_id;

        @SerializedName("device_status")
        public String device_status;

        @SerializedName("last_active")
        public String last_active;

        @SerializedName("act_status")
        public String act_status;

        @SerializedName("del_status")
        public String del_status;

    }
}
