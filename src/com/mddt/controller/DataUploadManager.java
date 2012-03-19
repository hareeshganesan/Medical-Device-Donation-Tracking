package com.mddt.controller;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mddt.model.Machine;
import com.mddt.model.MachineDBHelper;

public class DataUploadManager {


	MachineDBHelper mdbHelp;
	SQLiteDatabase db;
	
	public DataUploadManager(Context context){
		mdbHelp = new MachineDBHelper(context);
	}
	public void uploadMachine(Machine m, String uri) {

		InputStream is = null;
		
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(uri);
			httppost.setEntity(m.makeJSON());
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Log.d("HTTP", "HTTP: OK");
		} catch (Exception e) {
			Log.e("HTTP", "Error in http connection " + e.toString());
		}

	}

	public void storeMachineLocally(Machine m){
		Log.d("before_store", "hello");
		db = mdbHelp.getWritableDatabase();
		Log.d("after_store", "hello again");
		
		ContentValues values = new ContentValues();
		values.clear();
		
		values.put(MachineDBHelper.C_ID, m.lookupKey(MachineDBHelper.C_ID));
		values.put("created_at", m.lookupKey("created_at"));
		values.put("device_name", m.lookupKey("device_name"));
		values.put("make", m.lookupKey("make"));
		values.put("model", m.lookupKey("model"));
		
		db.insertOrThrow(MachineDBHelper.TABLE, null, values);
		db.close();
	}
	
}
