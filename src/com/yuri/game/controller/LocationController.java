package com.yuri.game.controller;

import com.yuri.game.context.WorldContext;
import com.yuri.game.controller.listeners.LocationListeners;

public class LocationController {

	public final LocationListeners locationListeners = new LocationListeners();
	//private final WorldContext world;

	public LocationController(WorldContext world) {
		//this.world = world;
	}

	public void onWrongLocationCommand() {
		locationListeners.onWrongLocationCommand();
	}

	public void locationPlayersListChanged() {
		locationListeners.onLocationPlayersListReceived();

	}

	public void onNewPlayerAddedToLocation() {
		locationListeners.onNewPlayerAddedToLocation();
	}

	public void onPlayerRemovedFromLocation() {
		locationListeners.onPlayerRemovedFromLocation();
	}
}
