package com.mddt.controller;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.mddt.model.Machine;
import com.mddt.model.MachineDBHelper;

public class DataUploadManager {

	MachineDBHelper mdbHelp;
	SQLiteDatabase db;

	public DataUploadManager(Context context) {
		mdbHelp = new MachineDBHelper(context);
	}

	public void uploadMachine(Machine m, String uri) {

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(uri);
			httppost.setEntity(m.makeJSON());
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			Log.d("HTTP", "HTTP: OK");
		} catch (Exception e) {
			Log.e("HTTP", "Error in http connection " + e.toString());
		}

	}

	/**
	 * Known issues: duplicate key errors when you run the same code twice with
	 * the same id
	 * 
	 * @return
	 */
	public void storeMachineLocally(Machine m) {
		db = mdbHelp.getWritableDatabase();

		ContentValues values = m.formatDBValues();

		try {
			db.insertOrThrow(MachineDBHelper.TABLE, null, values);
		} catch (SQLException e) {
			// db.delete(MachineDBHelper.TABLE, "_id = " + values.get("_id"),
			// null);
			// db.insertOrThrow(MachineDBHelper.TABLE, null, values);
			Log.d("db", "replacing id");
		}
		db.close();

	}

	public Machine getMachine(String columnName, String id) {
		db = mdbHelp.getReadableDatabase();
		String[] where = { id };
		Cursor query = db.query(mdbHelp.TABLE, null, columnName + " = ?",
				where, null, null, null);
		query.moveToFirst();

		Machine m = new Machine(query);
		db.close();

		return m;
	}

	public void populateLocalDB(int numOfMachines) {
		for (int i = 0; i < numOfMachines; i++)
			storeMachineLocally(new Machine(Machine.RANDOM));
	}

	public int fetchMachines(URI uri) {
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(uri);

			Log.d("HTTP", "HTTP: OK");
		} catch (Exception e) {
			Log.e("HTTP", "Error in http connection " + e.toString());
		}
		return 0;
	}
}
