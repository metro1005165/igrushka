package com.yuri.game.model.location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

import com.yuri.game.model.actor.LocationType;
import com.yuri.game.model.duel.DuelRequest;

public class Location {
	
	private LocationType type;
	private List<LocationPlayer> players = new ArrayList<LocationPlayer>();
	private List<DuelRequest> duelRequests = new ArrayList<DuelRequest>();
	
	public Location() {}

	public LocationType getType() {
		return type;
	}

	public void setType(LocationType type) {
		this.type = type;
	}

	public List<LocationPlayer> getPlayers() {
		return players;
	}

	public void addPlayers(List<LocationPlayer> players) {
		this.players = players;
	}
	
	public void addNewPlayer(LocationPlayer player) {
		Log.e("addNewPlayer", "Adding: " + player.toString());

		boolean hasPlayerName = false;
		for (LocationPlayer p : players) {
			if (p.getName().equals(player.getName())) {
				hasPlayerName = true;
			}
		}
		
		if (!hasPlayerName) {
			players.add(player);
			Log.e("addNewPlayer", "Added!");
		}
	}
	
	public void removePlayer(LocationPlayer player) {
		Log.e("removePlayer", "Removing: " + player.toString());
		Iterator<LocationPlayer> it = players.iterator();
		while (it.hasNext()) {
			LocationPlayer p = it.next();
			if (p.getName().equals(player.getName())) {
				it.remove();
				Log.e("removePlayer", "Removed");
			}
		}
	}
	
	public void clearList() {
		players.clear();
	}

	public List<DuelRequest> getDuelRequestsList() {
		return duelRequests;
	}

	public void setDuelRequestsList(List<DuelRequest> duelList) {
		this.duelRequests = duelList;
	}

	
	public void addDuelRequest(DuelRequest duelRequest) {
		Log.e("addDuelRequest", "Adding: " + duelRequest.toString());

		boolean hasPlayerName = false;
		for (DuelRequest d : duelRequests) {
			if (d.getOwner().equals(duelRequest.getOwner())) {
				hasPlayerName = true;
				Log.e("addDuelRequest", "Owner exists");
			}
		}
		
		if (!hasPlayerName) {
			duelRequests.add(duelRequest);
			Log.e("addDuelRequest", "Added!");
		}
	}

	public void removeDuelRequest(DuelRequest duelRequest) {
		Log.e("removeDuelRequest", "Removing: " + duelRequest.toString());
		Iterator<DuelRequest> it = duelRequests.iterator();
		while (it.hasNext()) {
			DuelRequest d = it.next();
			if (d.getOwner().equals(duelRequest.getOwner())) {
				it.remove();
				Log.e("removeDuelRequest", "Removed");
			}
		}
	}
	
	public void showPlayersInLocation() {
		
		Log.e("showPlayersInLocation()", "");
		for (LocationPlayer p : players) {
		    String name = p.getName();
		    int level = p.getLevel();
		    Log.e("Player: " + name + ", ", "Level: " + level + ".");
		}
	}
}
