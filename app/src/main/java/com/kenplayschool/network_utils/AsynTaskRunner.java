// Developed by Kranti Mohanty

package com.kenplayschool.network_utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class AsynTaskRunner extends AsyncTask<String,String,String> {

 private String resp="Response from AsyncTask";
 private static Context context;
 @Override
 protected String doInBackground(String... params) {
   int count = params.length;  
         if(count==2){
        	 
        	 String username=params[0].toString();
        	 String password=params[1].toString();
        	 
      // ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
     //  postParameters.add(new BasicNameValuePair("username",params[0]));
    //   postParameters.add(new BasicNameValuePair("password",params[1]));
       String response = null;
   try {
   //   response = SimpleHttpClient.executeHttpPost("https://192.168.1.3:8443/LoginServer/login.do", postParameters);
  //    String res = response.toString();
 //     resp = res.replaceAll("\\s+", "");
//      "http://192.168.1.10:8087/api/Kenlogin"
	     //String request = "http://192.168.1.10:9091/api/Login";
	   //http://192.168.1.10:8088/api/GetLogin
	   //Username: Kurshid & Password: 12345
//	     String request = "http://192.168.1.10:8088/api/Login";
	     String request = "http://babylonia.in/BabyLoniaWebApi/api/Login";
		 URL url = new URL(request);
		 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		 connection.setDoOutput(true);
		 connection.setDoInput(true);
		 connection.setInstanceFollowRedirects(false);
		 connection.setRequestMethod("POST");
		 connection.setRequestProperty("Content-Type","application/json");
	   
		 connection.setUseCaches (false);
		 DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
		 JSONObject jsonParam = new JSONObject();
	   
		 // jsonParam.put("Email", "gulu@gmail.com" or rahul@gmail.com);
		//  jsonParam.put("PasswordHash","Test@1234" or 12345);

		 jsonParam.put("UserName",username);
		 jsonParam.put("Password",password);
		 
//		 jsonParam.put("Loginmail",username);
//		 jsonParam.put("Password",password);

		 wr.writeBytes(jsonParam.toString());
		 wr.flush();
		 wr.close();
           
		 int  responseCode = connection.getResponseCode();
		  System.out.println("Result ........." + responseCode);
		 // resp=String.valueOf(responseCode);
		 
                 if(responseCode==200)
                 	 resp="Login successful";
                 else
       			  
           		  resp="Login unsuccessful";
		 
                 Log.v("POST Response Code :: ",resp);
                 
	   	   
   } catch (Exception e) {
    e.printStackTrace();
    resp = e.getMessage();
   }
         }else{
          resp="Invalid number of arguments-"+count;
         }
         return resp;
 }
 

}


 
 

 
 

		 
	