package com.yuri.game.controller.listeners;

import com.yuri.game.utils.ListenersAdapter;

public final class RegistrationListeners extends
		ListenersAdapter<RegistrationListener> implements RegistrationListener {

	private final Function<RegistrationListener> onSuccessfulRegistration = new Function<RegistrationListener>() {

		@Override
		public void call(RegistrationListener listener) {
			listener.onSuccessfulRegistration();
		}
	};

	private final Function<RegistrationListener> onUsernameExists = new Function<RegistrationListener>() {

		@Override
		public void call(RegistrationListener listener) {
			listener.onUsernameExists();
		}
	};

	private final Function<RegistrationListener> onEmailExists = new Function<RegistrationListener>() {

		@Override
		public void call(RegistrationListener listener) {
			listener.onEmailExists();
		}
	};

	@Override
	public void onSuccessfulRegistration() {
		callAllListeners(this.onSuccessfulRegistration);
	}

	@Override
	public void onUsernameExists() {
		callAllListeners(this.onUsernameExists);
	}

	@Override
	public void onEmailExists() {
		callAllListeners(this.onEmailExists);
	}
}
