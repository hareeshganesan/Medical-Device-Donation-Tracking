package com.mddt.tests;

import java.util.HashMap;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.mddt.R;
import com.mddt.controller.MyLocation;
import com.mddt.controller.MyLocation.LocationResult;

public class DatabaseTestActivity extends Activity {

	String location = "nothing";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		LocationResult locationResult = new LocationResult(){
		    @Override
		    public void gotLocation(Location location){
		        //Got the location!
		    	Toast theToast = Toast.makeText(getBaseContext(), ""+location.getLatitude()+" "+location.getLongitude(),
						Toast.LENGTH_LONG);
				theToast.show();
		    }
		};
		MyLocation myLocation = new MyLocation();
		String location = "unavailable";
		myLocation.getLocation(this, locationResult);
		
	}
}
