//package com.sedulous.fsdsdanapur.Model;
//
//
//import com.google.gson.annotations.SerializedName;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//
//public class LogInHistoryModel implements Serializable {
//    @SerializedName("message")
//    private String message;
//
//    @SerializedName("Getlogindata")
//    public ArrayList<UserLoginData> loginList;
//
//    public class UserLoginData implements Serializable{
//
//        @SerializedName("id")
//        public String id;
//
//        @SerializedName("user_id")
//        public String user_id;
//
//        @SerializedName("name")
//        public String name;
//
//        @SerializedName("phone_no")
//        public String phone_no;
//
//        @SerializedName("last_login")
//        public String last_login;
//
//        @SerializedName("act_status")
//        public String act_status;
//
//        @SerializedName("del_status")
//        public String del_status;
//
//    }
//}


package com.sedulous.fsdsdanapur.Model;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

public class LogInHistoryModel implements Serializable {

    // Message field for status or description
    @SerializedName("message")
    private String message;

    // List of user login data
    @SerializedName("Getlogindata")
    public ArrayList<UserLoginData> loginList;

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Setter for message
    public void setMessage(String message) {
        this.message = message;
    }

    // UserLoginData inner class to represent each login record
    public static class UserLoginData implements Serializable {

        @SerializedName("id")
        public String id;

        @SerializedName("user_id")
        public String user_id;

        @SerializedName("name")
        public String name;

        @SerializedName("phone_no")
        public String phone_no;

        @SerializedName("last_login")
        public String last_login;

        @SerializedName("act_status")
        public String act_status;

        @SerializedName("del_status")
        public String del_status;

        // Getters and setters for each field
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return user_id;
        }

        public void setUserId(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNo() {
            return phone_no;
        }

        public void setPhoneNo(String phone_no) {
            this.phone_no = phone_no;
        }

        public String getLastLogin() {
            return last_login;
        }

        public void setLastLogin(String last_login) {
            this.last_login = last_login;
        }

        public String getActStatus() {
            return act_status;
        }

        public void setActStatus(String act_status) {
            this.act_status = act_status;
        }

        public String getDelStatus() {
            return del_status;
        }

        public void setDelStatus(String del_status) {
            this.del_status = del_status;
        }
    }
}
