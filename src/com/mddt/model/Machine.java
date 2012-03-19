package com.mddt.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class Machine {

	HashMap<String, String> myProperties;
	String myId;
	
	public Machine(String id){
		myProperties = new HashMap<String, String>();
		myId = id;
	}
	
	public Machine(String id, HashMap<String, String>properties){
		myProperties = properties;
		myId = id;
	}
	
	public ArrayList<String> getKeys(){
		ArrayList<String> keys = new ArrayList<String>();
		myProperties.keySet().addAll(keys);
		Log.d("machine", Integer.toString(myProperties.size()));
		return keys;
	}
	
	public String lookupKey(String key){
		return myProperties.get(key);
	}
	public UrlEncodedFormEntity makeJSON(){
		ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		for(String key : myProperties.keySet())
			nameValuePairs.add(new BasicNameValuePair(key, myProperties.get(key)));
		try {
			return new UrlEncodedFormEntity(nameValuePairs);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

