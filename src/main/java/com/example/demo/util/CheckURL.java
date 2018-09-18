  
package com.example.demo.util;
import java.net.*;
import java.io.*;


public class CheckURL {
	 /**
     * 访问支付宝url地址，获取支付结果
     * @param url
     * @return 
     */
  public static String check(String urlvalue ) {
	  String inputLine="";
	  URL url=null;
	  HttpURLConnection urlConnection  =null;
	  BufferedReader in=null;
	  try{
		  url = new URL(urlvalue);
		  urlConnection  = (HttpURLConnection)url.openConnection();
		  in  = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		  inputLine = in.readLine().toString();
	  }catch(Exception e){
		  e.printStackTrace();
	  }finally {
		  if(null!=in) {
			  try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		  }
	  }
	  return inputLine;
  }
}