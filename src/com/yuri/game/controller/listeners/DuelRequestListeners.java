package com.yuri.game.controller.listeners;

import com.yuri.game.utils.ListenersAdapter;

public class DuelRequestListeners extends ListenersAdapter<DuelRequestListener>
		implements DuelRequestListener {

	private final Function<DuelRequestListener> onDuelRequestListReceived = new Function<DuelRequestListener>() {

		@Override
		public void call(DuelRequestListener listener) {
			listener.onDuelRequestListReceived();
		}
	};

	private final Function<DuelRequestListener> onDuelRequestPublished = new Function<DuelRequestListener>() {

		@Override
		public void call(DuelRequestListener listener) {
			listener.onDuelRequestPublished();
		}
	};

	private final Function<DuelRequestListener> onDuelRequestRemoved = new Function<DuelRequestListener>() {

		@Override
		public void call(DuelRequestListener listener) {
			listener.onDuelRequestRemoved();
		}
	};

	private final Function<DuelRequestListener> onDuelIsFull = new Function<DuelRequestListener>() {
		@Override
		public void call(DuelRequestListener listener) {
			listener.onDuelStartFail();
		}
	};
	
	private final Function2<DuelRequestListener, String, String> onNewPlayerAddedToDuel = new Function2<DuelRequestListener, String, String>() {

		@Override
		public void call(DuelRequestListener listener, String owner,
				String challenger) {
			listener.onNewPlayerAddedToDuel(owner, challenger);
		}
	};

	private final Function<DuelRequestListener> onDuelToStartNotFound = new Function<DuelRequestListener>() {

		@Override
		public void call(DuelRequestListener listener) {
			listener.onDuelToStartNotFound();
		}
	};
	
	private final Function<DuelRequestListener> onDuelStartFail = new Function<DuelRequestListener>() {

		@Override
		public void call(DuelRequestListener listener) {
			listener.onDuelStartFail();
		}
	};
	
	private final Function<DuelRequestListener> onDuelToRemoveNotFound = new Function<DuelRequestListener>() {

		@Override
		public void call(DuelRequestListener listener) {
			listener.onDuelToRemoveNotFound();
		}
	};
	
	private final Function<DuelRequestListener> onDuelToAddNewPlayerNotFound = new Function<DuelRequestListener>() {

		@Override
		public void call(DuelRequestListener listener) {
			listener.onDuelToAddNewPlayerNotFound();
		}
	};
	
	private final Function<DuelRequestListener> onDuelToRemovePlayerFromNotFound = new Function<DuelRequestListener>() {

		@Override
		public void call(DuelRequestListener listener) {
			listener.onDuelToRemovePlayerFromNotFound();
		}
	};
	
	private final Function<DuelRequestListener> onPlayerRemovedFromDuel = new Function<DuelRequestListener>() {

		@Override
		public void call(DuelRequestListener listener) {
			listener.onPlayerRemovedFromDuel();
		}
	};

	@Override
	public void onDuelRequestListReceived() {
		callAllListeners(this.onDuelRequestListReceived);
	}

	@Override
	public void onDuelRequestPublished() {
		callAllListeners(this.onDuelRequestPublished);
		
	}

	@Override
	public void onDuelRequestRemoved() {
		callAllListeners(this.onDuelRequestRemoved);
		
	}

	@Override
	public void onDuelIsFull() {
		callAllListeners(this.onDuelIsFull);
		
	}

	@Override
	public void onNewPlayerAddedToDuel(String owner, String challenger) {
		callAllListeners(this.onNewPlayerAddedToDuel, owner, challenger);

	}

	@Override
	public void onDuelToStartNotFound() {
		callAllListeners(this.onDuelToStartNotFound);
		
	}

	@Override
	public void onDuelStartFail() {
		callAllListeners(this.onDuelStartFail);
		
	}

	@Override
	public void onDuelToRemoveNotFound() {
		callAllListeners(this.onDuelToRemoveNotFound);
		
	}

	@Override
	public void onDuelToAddNewPlayerNotFound() {
		callAllListeners(this.onDuelToAddNewPlayerNotFound);
		
	}

	@Override
	public void onDuelToRemovePlayerFromNotFound() {
		callAllListeners(this.onDuelToRemovePlayerFromNotFound);
		
	}

	@Override
	public void onPlayerRemovedFromDuel() {
		callAllListeners(this.onPlayerRemovedFromDuel);
		
	}
}
