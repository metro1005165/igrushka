package com.yuri.game.controller;

import com.yuri.game.context.WorldContext;
import com.yuri.game.controller.listeners.DuelRequestListeners;

public class DuelController {

	public final DuelRequestListeners duelRequestListeners = new DuelRequestListeners();

	public DuelController(WorldContext world) {
	}

	public void onDuelRequestListReceived() {
		duelRequestListeners.onDuelRequestListReceived();
	}

	public void onDuelRequestPublished() {
		duelRequestListeners.onDuelRequestPublished();
		
		// If player posted duel request he cant post new
		// Maybe he cant join or accept others etc.
		// Check all posible situations...
		
		// Update ListView

	}

	public void onDuelRequestRemoved() {
		duelRequestListeners.onDuelRequestRemoved();
		// Update ListView
		
		// Remove restrictions

	}

	public void onDuelIsFull() {

	}

	public void onNewPlayerAddedToDuel(String owner, String challenger) {
		duelRequestListeners.onNewPlayerAddedToDuel(owner, challenger);
	}

	public void onDuelToStartNotFound() {

	}

	public void onDuelStartFail() {

	}

	public void onDuelToRemoveNotFound() {

	}

	public void onDuelToAddNewPlayerNotFound() {

	}

	public void onDuelToRemovePlayerFromNotFound() {

	}

	public void onPlayerRemovedFromDuel() {

	}

}
