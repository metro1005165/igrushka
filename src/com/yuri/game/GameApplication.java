package com.yuri.game;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.yuri.game.context.ControllerContext;
import com.yuri.game.context.WorldContext;
import com.yuri.game.network.ClientSocket;

public class GameApplication extends Application {

	public final ClientSocket clientSocket = new ClientSocket();
	public final WorldContext world = new WorldContext();
	public final ControllerContext controllers = new ControllerContext(this, world);

	public static GameApplication getApplicationFromActivity(Activity activity) {
		return ((GameApplication) activity.getApplication());
	}

	public static GameApplication getApplicationFromActivityContext(
			Context context) {
		return getApplicationFromActivity(getActivityFromActivityContext(context));
	}

	private static Activity getActivityFromActivityContext(Context context) {
		return (Activity) context;
	}
}
