package com.yuri.game.context;

import com.yuri.game.controller.ActorStatsController;
import com.yuri.game.controller.DuelController;
import com.yuri.game.controller.LocationController;
import com.yuri.game.controller.LoginController;
import com.yuri.game.controller.RegistrationController;
import com.yuri.game.controller.NetworkController;
import com.yuri.game.controller.SystemErrorController;
import com.yuri.game.GameApplication;

import android.content.res.Resources;

public final class ControllerContext {

	public final ActorStatsController actorStatsController;
	public final DuelController duelController;
	public final LocationController locationController;
	public final LoginController loginController;
	public final SystemErrorController systemErrorController;
	public final NetworkController networkController;
	public final RegistrationController registrationController;
	private final GameApplication app;
	
	public ControllerContext(GameApplication app, WorldContext world) {
		this.app = app;
		
		this.actorStatsController = new ActorStatsController(world);
		this.registrationController = new RegistrationController();
		this.duelController = new DuelController(world);
		this.locationController = new LocationController(world);
		this.loginController = new LoginController();
		this.systemErrorController = new SystemErrorController();
		this.networkController = new NetworkController(app);
	}
	
	public Resources getResources() {
		return app.getResources();
	}
}
