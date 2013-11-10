package com.yuri.game.controller.listeners;

public interface DuelRequestListener {

	void onDuelRequestListReceived();

	void onDuelRequestPublished();

	void onDuelRequestRemoved();

	void onDuelIsFull();

	void onNewPlayerAddedToDuel(String owner, String challenger);

	void onDuelToStartNotFound();

	void onDuelStartFail();

	void onDuelToRemoveNotFound();

	void onDuelToAddNewPlayerNotFound();

	void onDuelToRemovePlayerFromNotFound();

	void onPlayerRemovedFromDuel();

}
