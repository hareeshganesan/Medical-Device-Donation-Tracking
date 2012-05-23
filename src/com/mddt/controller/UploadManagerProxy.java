package com.mddt.controller;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.mddt.model.Machine;

public class UploadManagerProxy implements RemoteUploadManager{

	static UploadManagerProxy u;
	
	Context myContext;
	ArrayList<Machine> machines;
	
	public UploadManagerProxy(Context c){
		myContext = c;
	}
	
	public void uploadMachine(Machine m, String uri) {
		final ConnectivityManager conMgr =  (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

		machines.add(m);
		
		if (activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED) {
			MachineDataManager manager = new MachineDataManager(myContext);
			for(Machine machine : machines)
				manager.uploadMachine(machine, uri);
		}
		
	}

	public static UploadManagerProxy getInstance(Context c){
		if(u==null)
			return u;
		else
			return new UploadManagerProxy(c);
	}

	public void storeMachineLocally(Machine m) {
		(new MachineDataManager(myContext)).storeMachineLocally(m);
		
	}
}
