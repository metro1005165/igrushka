package com.yuri.game.model.listeners;

import com.yuri.game.model.actor.Actor;

public interface ActorStatsListener {
	void onPlayerStatsChanged();
	void onActorStatsRequested(Actor actor);
	void onCharNotFound(String message);
}
