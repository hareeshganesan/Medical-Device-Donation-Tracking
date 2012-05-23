package com.mddt.controller;

import com.mddt.model.Machine;

public interface RemoteUploadManager {
	public void uploadMachine(Machine m, String uri);

	public void storeMachineLocally(Machine m);
}
