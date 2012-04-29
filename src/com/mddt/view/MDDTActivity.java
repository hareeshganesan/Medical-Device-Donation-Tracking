package com.mddt.view;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.mddt.R;
import com.mddt.controller.DataUploadManager;
import com.mddt.controller.MyLocation;
import com.mddt.controller.MyLocation.LocationResult;
import com.mddt.model.Machine;
import com.mddt.model.MachineDBHelper;
import com.mddt.tests.DatabaseTestActivity;

/**
 * @TODO monitor the times users click things, put it into the log
 * @author Hareesh
 * 
 */
public class MDDTActivity extends Activity {
	/** Called when the activity is first created. */
	
	
	public static String myLocation = "UNAVAILABLE";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button uploadButton = (Button) findViewById(R.id.uploadButton);
		Button viewInventoryButton = (Button) findViewById(R.id.viewInventoryButton);
		Button pastEntriesButton = (Button) findViewById(R.id.pastEntriesButton);
		Button testButton = (Button) findViewById(R.id.button1);
		Button sendEmailButton = (Button) findViewById(R.id.sendEmailButton);
		OnClickListener uploadListener = new OnClickListener() {
			public void onClick(View v) {
				startActivity(v, UploadInterfaceActivity.class,
						"moved to upload");
			}
		};

		OnClickListener inventoryListener = new OnClickListener() {
			public void onClick(View v) {
				startActivity(v, InventoryViewActivity.class, "inventory view");
			}
		};

		OnClickListener sendRequestListener = new OnClickListener() {
			public void onClick(View v) {
				startActivity(v, SendEmailActivity.class, "Begin Email Request");
			}
		};

		OnClickListener pastEntriesListener = new OnClickListener() {
			public void onClick(View v) {
				Toast theToast = Toast.makeText(getBaseContext(),
						"this feature is not yet implemented",
						Toast.LENGTH_LONG);
				theToast.show();
			}
		};

		OnClickListener testListener = new OnClickListener() {
			public void onClick(View v) {
				startActivity(v, DatabaseTestActivity.class, "database test");
			}
		};

		LocationResult locationResult = new LocationResult(){
		    @Override
		    public void gotLocation(Location location){
		        //Got the location!
		    	MDDTActivity.myLocation = location.getLatitude()+", "+location.getLongitude();
		    }
		};
		
		MyLocation myLocation = new MyLocation();
		myLocation.getLocation(this, locationResult);
		
		uploadButton.setOnClickListener(uploadListener);
		viewInventoryButton.setOnClickListener(inventoryListener);
		pastEntriesButton.setOnClickListener(pastEntriesListener);
		sendEmailButton.setOnClickListener(sendRequestListener);
		testButton.setOnClickListener(testListener);
	}

	private void startActivity(View v, Class activity, String toast) {
		Intent i = new Intent(v.getContext(), activity);

		if (toast != null) {
			Toast theToast = Toast.makeText(getBaseContext(), toast,
					Toast.LENGTH_LONG);
			theToast.show();
		}
		v.getContext().startActivity(i);
	}
	

}
