package com.mddt.model;

import com.mddt.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class MachineDBHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "machines.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "test";
	public static final String C_ID = BaseColumns._ID;
	Context context;

	public MachineDBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.context = context;
		Log.d("DbHelper", "instantiated");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "create table "
				+ TABLE
				+ " ("
				+ C_ID
				+ " text primary key, created_at text, device_name text, make text, model text)";
		db.execSQL(sql);
		Log.d("DbHelper", "onCreated sql: " + sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists " + TABLE);
		Log.d("DbHelper", "onUpdated");
		onCreate(db);
	}

}
