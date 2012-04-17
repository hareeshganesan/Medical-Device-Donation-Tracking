package com.mddt.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class Machine {

	HashMap<String, String> myProperties;
	String myId;
	static public final int RANDOM = 1;

	public Machine(int specialCase) {
		switch (specialCase) {
		case RANDOM:
			myId = UUID.randomUUID().toString().substring(0,5);
			myProperties = new HashMap<String, String>();
			myProperties.put("info",
					UUID.randomUUID().toString().substring(0, 5));
			break;
		}

	}

	public Machine(String id) {
		myProperties = new HashMap<String, String>();
		myId = id;
	}

	public Machine(String id, HashMap<String, String> properties) {
		myProperties = properties;
		myId = id;
	}

	public Machine(HashMap<String, String> properties) {
		myProperties = properties;
		myId = UUID.randomUUID().toString();
	}

	public Machine(Cursor query) {
		myId = query.getString(0);
		myProperties = new HashMap<String, String>();
		myProperties.put("info", query.getString(1));
	}

	public ArrayList<String> getKeys() {
		ArrayList<String> keys = new ArrayList<String>();
		myProperties.keySet().addAll(keys);
		Log.d("machine", Integer.toString(myProperties.size()));
		return keys;
	}

	public String lookupKey(String key) {
		return myProperties.get(key);
	}

	public UrlEncodedFormEntity makeJSON() {
		StringBuilder builder = new StringBuilder();
		for (String key : myProperties.keySet())
			builder.append(key + " : " + myProperties.get(key) + ";;");

		ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("tag", myId));
		nameValuePairs.add(new BasicNameValuePair("value", builder.toString()));
		try {
			return new UrlEncodedFormEntity(nameValuePairs);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ContentValues formatDBValues() {
		ContentValues values = new ContentValues();
		values.clear();

		values.put(MachineDBHelper.C_ID, myId);
		values.put("info", toString());
		return values;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (String key : myProperties.keySet())
			builder.append(key + " : " + myProperties.get(key) + ";;");
		return builder.toString();

	}

}
