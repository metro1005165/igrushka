package com.yuri.game.controller;

import android.util.Log;

import com.yuri.game.controller.listeners.RegistrationListeners;

public class RegistrationController {

	public final RegistrationListeners registrationListeners = 
			new RegistrationListeners();
	
	public void onSuccessfulRegistration() {
		Log.e("RegistrationController", "Next:registrationListeners.onSuccessfulRegistration()");
		registrationListeners.onSuccessfulRegistration();
	}

	public void onUsernameExists() {
		Log.e("RegistrationController", "Next registrationListeners.onUsernameExists();");
		registrationListeners.onUsernameExists();
	}

	public void onEmailExists() {
		registrationListeners.onEmailExists();
	}
}
