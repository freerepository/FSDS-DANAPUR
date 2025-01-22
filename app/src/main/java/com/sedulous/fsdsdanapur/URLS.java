package com.sedulous.fsdsdanapur;

public class URLS {
    public  static String baseUrl = "http://fsds.projectrailway.in/";
    public static String adminLogin = "http://fsds.projectrailway.in/api/adminlogin";
//    Method : Post
//    {
//        "phoneNumber":"1234567890",
//            "password":"1234"
//    }


    public static String adminRegistration = "http://fsds.projectrailway.in/api/adminregister";
//    Method : Post
//    {
//        "name":"Admin",
//            "phoneNumber":"1234567890",
//            "password":"1234",
//            "role":"admin"
//    }

//    Add Train and Coach
//--------------------
    public static String addTrain_and_coach = "http://fsds.projectrailway.in/api/add_train_data";
//    method : post
//    {
//        "train_number":"123456",
//            "coach_number":"1234568"
//    }

//    Get Train and Coach
    public static String get_train_and_coach = "http://fsds.projectrailway.in/api/get_train_coaches";
//    method : post
//    {
//        "train_number":"123456"
//    }


}
