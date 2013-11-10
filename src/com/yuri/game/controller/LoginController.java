package com.yuri.game.controller;

import com.yuri.game.controller.listeners.LoginListeners;

public class LoginController {

	public final LoginListeners loginListeners = new LoginListeners();
	
	
	public void onLoginSuccess() {
		loginListeners.onLoginSuccess();
	}
	
	public void onLoginFail() {
		loginListeners.onLoginFail();
	}
}
