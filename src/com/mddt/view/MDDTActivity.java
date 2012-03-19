package com.mddt.view;

import java.util.HashMap;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mddt.R;
import com.mddt.controller.DataUploadManager;
import com.mddt.model.Machine;
import com.mddt.model.MachineDBHelper;
/**
 * @TODO monitor the times users click things, put it into the log
 * @author Hareesh
 *
 */
public class MDDTActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button uploadButton = (Button) findViewById(R.id.uploadButton);
		Button viewInventoryButton = (Button) findViewById(R.id.viewInventoryButton);
		Button pastEntriesButton = (Button) findViewById(R.id.pastEntriesButton);
		Button testButton = (Button) findViewById(R.id.button1);
		
		OnClickListener uploadListener = new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(),
						UploadInterfaceActivity.class);

				Toast theToast = Toast.makeText(getBaseContext(), "Cameerraaa",
						Toast.LENGTH_LONG);
				theToast.show();
				v.getContext().startActivity(i);
			}
		};

		OnClickListener inventoryListener = new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(),
						InventoryViewActivity.class);

				Toast theToast = Toast.makeText(getBaseContext(), "Inventorieeeee",
						Toast.LENGTH_LONG);
				theToast.show();
				v.getContext().startActivity(i);
			}
		};

		OnClickListener pastEntriesListener = new OnClickListener() {
			public void onClick(View v) {
				TextView text = ((TextView) findViewById(R.id.textView1));
				text.setText("pastEntries");
			}
		};
		
		OnClickListener testListener = new OnClickListener() {
			public void onClick(View v) {
				test();
				Toast theToast = Toast.makeText(getBaseContext(), "testing 123",
						Toast.LENGTH_LONG);
				theToast.show();
			}
		};

		uploadButton.setOnClickListener(uploadListener);
		viewInventoryButton.setOnClickListener(inventoryListener);
		pastEntriesButton.setOnClickListener(pastEntriesListener);
		testButton.setOnClickListener(testListener);
	}
	
	public void test(){
		DataUploadManager d = new DataUploadManager(this);
		HashMap<String, String> props = new HashMap<String, String>();
		props.put(MachineDBHelper.C_ID, "2");
		props.put("created_at", "3192012");
		props.put("device_name", "helloworld");
		props.put("make", "duke");
		props.put("model", "2012");
		d.storeMachineLocally(new Machine("test", props));
		//System.out.println("hello");
	}
}

