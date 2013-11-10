package com.yuri.game.controller;

import android.util.Log;

import com.yuri.game.controller.listeners.SystemErrorListeners;

public class SystemErrorController {
	
	public final SystemErrorListeners systemErrorListeners = new SystemErrorListeners();
	
	public void onSocketTerminated() {
		Log.e("SystemErrorController", "before calling systemErrorListeners.onSocketTerminated()");
		systemErrorListeners.onSocketTerminated();
	}

	public void onWrongRequestStructure() {
		systemErrorListeners.onWrongRequestStructure();
	}

	public void onWrongCommandOrder() {
		systemErrorListeners.onWrongCommandOrder();
	}

	public void onMissingParameters() {
		systemErrorListeners.onMissingParameters();
	}

	public void onParameterNotFound() {
		systemErrorListeners.onParameterNotFound();
	}
}
