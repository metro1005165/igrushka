package com.yuri.game.model;

import com.yuri.game.model.actor.Player;
import com.yuri.game.model.location.Location;

public final class ModelContainer {

	public Player player;	
	public Location location;
	
	public ModelContainer() {
		player = new Player();
		location = new Location();
	}
}
