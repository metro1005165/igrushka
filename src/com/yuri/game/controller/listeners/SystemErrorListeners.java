package com.yuri.game.controller.listeners;

import com.yuri.game.utils.ListenersAdapter;

public class SystemErrorListeners extends ListenersAdapter<SystemErrorListener>
		implements SystemErrorListener {

	private final Function<SystemErrorListener> onSocketTerminated = new Function<SystemErrorListener>() {

		@Override
		public void call(SystemErrorListener listener) {
			listener.onSocketTerminated();
		}
	};

	private final Function<SystemErrorListener> onWrongRequestStructure = new Function<SystemErrorListener>() {

		@Override
		public void call(SystemErrorListener listener) {
			listener.onWrongRequestStructure();
		}
	};

	private final Function<SystemErrorListener> onWrongCommandOrder = new Function<SystemErrorListener>() {

		@Override
		public void call(SystemErrorListener listener) {
			listener.onWrongCommandOrder();
		}
	};

	private final Function<SystemErrorListener> onMissingParameters = new Function<SystemErrorListener>() {

		@Override
		public void call(SystemErrorListener listener) {
			listener.onMissingParameters();
		}
	};

	private final Function<SystemErrorListener> onParameterNotFound = new Function<SystemErrorListener>() {

		@Override
		public void call(SystemErrorListener listener) {
			listener.onParameterNotFound();
		}
	};

	@Override
	public void onSocketTerminated() {
		callAllListeners(this.onSocketTerminated);
	}

	@Override
	public void onWrongRequestStructure() {
		callAllListeners(this.onWrongRequestStructure);
	}

	@Override
	public void onWrongCommandOrder() {
		callAllListeners(this.onWrongCommandOrder);
	}

	@Override
	public void onMissingParameters() {
		callAllListeners(this.onMissingParameters);
	}

	@Override
	public void onParameterNotFound() {
		callAllListeners(this.onParameterNotFound);
	}
}
