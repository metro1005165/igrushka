package com.yuri.game.controller.listeners;

public interface RegistrationListener {
	
	void onSuccessfulRegistration();

	void onUsernameExists();

	void onEmailExists();
	
}
