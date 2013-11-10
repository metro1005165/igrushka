package com.yuri.game.controller.listeners;

public interface LocationListener {
	
	void onWrongLocationCommand();

	void onLocationPlayersListReceived();

	void onNewPlayerAddedToLocation();

	void onPlayerRemovedFromLocation();
	
}
