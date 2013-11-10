package com.yuri.game.controller.listeners;

import com.yuri.game.utils.ListenersAdapter;

public class LocationListeners extends ListenersAdapter<LocationListener>
		implements LocationListener {
	
	private final Function<LocationListener> onWrongLocationCommand = new Function<LocationListener>() {
		@Override public void call(LocationListener listener) { listener.onWrongLocationCommand(); }
	};
	
	private final Function<LocationListener> onLocationPlayersListReceived = new Function<LocationListener>() {
		@Override public void call(LocationListener listener) { listener.onLocationPlayersListReceived(); }
	};
	
	private final Function<LocationListener> onNewPlayerAddedToLocation = new Function<LocationListener>() {
		@Override public void call(LocationListener listener) { listener.onNewPlayerAddedToLocation(); }
	};
	
	private final Function<LocationListener> onPlayerRemovedFromLocation = new Function<LocationListener>() {
		@Override public void call(LocationListener listener) { listener.onPlayerRemovedFromLocation(); }
	};

	@Override
	public void onWrongLocationCommand() {
		callAllListeners(this.onWrongLocationCommand);
	}

	@Override
	public void onLocationPlayersListReceived() {
		callAllListeners(this.onLocationPlayersListReceived);
	}

	@Override
	public void onNewPlayerAddedToLocation() {
		callAllListeners(this.onNewPlayerAddedToLocation);
	}

	@Override
	public void onPlayerRemovedFromLocation() {
		callAllListeners(this.onPlayerRemovedFromLocation);
	}
}
