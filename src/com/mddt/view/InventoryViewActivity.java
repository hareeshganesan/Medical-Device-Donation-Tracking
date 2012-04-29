package com.mddt.view;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.mddt.R;
import com.mddt.controller.DataUploadManager;
import com.mddt.model.MachineDBHelper;

public class InventoryViewActivity extends Activity {
	
	MachineDBHelper mdbHelper;
	SQLiteDatabase db;
	Cursor cursor;
	ListView list;
	SimpleCursorAdapter adapter;
	static final String[] FROM = {
		MachineDBHelper.C_ID, "info"
	};
	static final int[] TO = {
		R.id.machine_id,R.id.info
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inventoryview);
		
		list = (ListView) findViewById(R.id.listView1);
		
		mdbHelper = new MachineDBHelper(this);
		db = mdbHelper.getReadableDatabase();
	}
	
	public void onDestroy(){
		super.onDestroy();
		
		db.close();
	}
	
	protected void onResume(){
		super.onResume();
		
		cursor = db.query(mdbHelper.TABLE, null, null, null, null, null, null);
		startManagingCursor(cursor);
		
		adapter = new SimpleCursorAdapter(this, R.layout.row, cursor, FROM, TO);
		list.setAdapter(adapter);
	}
}
