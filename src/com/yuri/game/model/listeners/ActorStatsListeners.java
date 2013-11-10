package com.yuri.game.model.listeners;

import com.yuri.game.model.actor.Actor;
import com.yuri.game.utils.ListenersAdapter;

public final class ActorStatsListeners extends ListenersAdapter<ActorStatsListener> implements ActorStatsListener {

	private final Function<ActorStatsListener> onPlayerStatsChanged = new Function<ActorStatsListener>() {
		@Override public void call(ActorStatsListener listener) { listener.onPlayerStatsChanged(); }
	};
	
	private final Function1<ActorStatsListener, Actor> onActorStatsRequested = new Function1<ActorStatsListener, Actor>() {
		@Override public void call(ActorStatsListener listener, Actor actor) { listener.onActorStatsRequested(actor);}
	};
	
	private final Function1<ActorStatsListener, String> onCharNotFound = new Function1<ActorStatsListener, String>() {
		@Override public void call(ActorStatsListener listener, String message) { listener.onCharNotFound(message); }
	};

	@Override
	public void onPlayerStatsChanged() {
		callAllListeners(this.onPlayerStatsChanged);
	}

	@Override
	public void onActorStatsRequested(Actor actor) {
		callAllListeners(this.onActorStatsRequested, actor);
	}

	@Override
	public void onCharNotFound(String message) {
		callAllListeners(this.onCharNotFound, message);
	}
}
