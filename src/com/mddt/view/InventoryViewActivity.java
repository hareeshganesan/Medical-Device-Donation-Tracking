package com.mddt.view;

import com.mddt.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class InventoryViewActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inventoryview);

		ListView l = (ListView) findViewById(R.id.listView1);

	}
}
