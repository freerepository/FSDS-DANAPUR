package com.sedulous.fsdsdanapur.Model;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

public class LoginModel implements Serializable {

    @SerializedName("success")
    public int success;

    @SerializedName("message")
    public String message;

    @SerializedName("admin_data")
    public List<AdminData> adminData;

    public static class AdminData implements Serializable {

        @SerializedName("id")
        public String id;

        @SerializedName("name")
        public String name;

        @SerializedName("role")
        public String role;

        @SerializedName("phone_no")
        public String phoneNo;

        @SerializedName("password")
        public String password;

        @SerializedName("act_status")
        public String actStatus;

        @SerializedName("del_status")
        public String delStatus;

        @SerializedName("created_by")
        public String createdBy;

        @SerializedName("created_date")
        public String createdDate;

        @SerializedName("updated_by")
        public String updatedBy;

        @SerializedName("updated_date")
        public String updatedDate;
    }
}

