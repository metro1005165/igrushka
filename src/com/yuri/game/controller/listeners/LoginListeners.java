package com.yuri.game.controller.listeners;

import com.yuri.game.utils.ListenersAdapter;

public class LoginListeners extends
		ListenersAdapter<LoginListener> implements LoginListener {

	private final Function<LoginListener> onLoginSuccess = new Function<LoginListener>() {

		@Override
		public void call(LoginListener listener) {
			listener.onLoginSuccess();
		}
	};
	
	private final Function<LoginListener> onLoginFail = new Function<LoginListener>() {

		@Override
		public void call(LoginListener listener) {
			listener.onLoginFail();
		}
	};

	@Override
	public void onLoginSuccess() {
		callAllListeners(this.onLoginSuccess);	
	}

	@Override
	public void onLoginFail() {
		callAllListeners(this.onLoginFail);		
	}
}
