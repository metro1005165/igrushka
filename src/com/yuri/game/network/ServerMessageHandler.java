package com.yuri.game.network;

import com.yuri.game.GameApplication;
import com.yuri.game.context.ControllerContext;
import com.yuri.game.resource.parsers.ActorStatsParser;
import com.yuri.game.resource.parsers.DuelRequestParser;
import com.yuri.game.resource.parsers.LocationPlayersParser;
import com.yuri.game.resource.parsers.PlayerStatsParser;

public class ServerMessageHandler {

	private final ControllerContext controllers;
	private final GameApplication app;
	private int code;
	private String message;

	public ServerMessageHandler(GameApplication app, String code, String message) {
		this.controllers = app.controllers;
		this.app = app;
		this.code = Integer.parseInt(code);
		this.message = message;
	}

	public void processServerMessage() {
		resolveCode();
	}

	public void resolveCode() {

		switch (code) {

		// System error block starts here
		case ServerRespondCodes.SOCKET_TERMINATED:
			controllers.systemErrorController.onSocketTerminated();	
			break;
		case ServerRespondCodes.WRONG_MESSAGE_STRUCTURE:
			controllers.systemErrorController.onWrongRequestStructure();
			break;
		case ServerRespondCodes.WRONG_COMMAND_ORDER:
			controllers.systemErrorController.onWrongCommandOrder();
			break;
		case ServerRespondCodes.MISSING_PARAMETERS:
			controllers.systemErrorController.onMissingParameters();
			break;
		case ServerRespondCodes.PARAMETER_NOT_FOUND:
			controllers.systemErrorController.onParameterNotFound();
			break;
		// System error block ends here

			
		// Registration block starts here
		case ServerRespondCodes.SUCCESSFUL_REGISTRATION:
			controllers.registrationController.onSuccessfulRegistration();
			break;
		case ServerRespondCodes.USERNAME_EXISTS:
			controllers.registrationController.onUsernameExists();
			break;
		case ServerRespondCodes.EMAIL_EXISTS:
			controllers.registrationController.onEmailExists();
			break;
		// Registration block ends here

			
		// Login block starts here
		case ServerRespondCodes.LOGIN_SUCCESSFUL:
			controllers.loginController.onLoginSuccess();
			break;
		case ServerRespondCodes.LOGIN_FAILED:
			controllers.loginController.onLoginFail();
			break;
		// Login block ends here

			
		// Character block starts here
		case ServerRespondCodes.PLAYER_STATS:
			PlayerStatsParser.startParsing(app, message);
			break;
		case ServerRespondCodes.ACTOR_STATS:
			ActorStatsParser.startParsing(app, message);
			break;
		case ServerRespondCodes.CHARACTER_NOT_FOUND:
			break;
		// Character block ends here

			
		// Location block starts here
		case ServerRespondCodes.WRONG_LOCATION_COMMAND:
			controllers.locationController.onWrongLocationCommand();
			break;
		case ServerRespondCodes.PLAYERS_LIST_IN_LOCATION:
		case ServerRespondCodes.NEW_PLAYER_IN_LOCATION:
		case ServerRespondCodes.PLAYER_GONE_IN_LOCATION:
			LocationPlayersParser.startParsing(app, message);
			break;
		// Location block ends here

			
		// Duel block starts here
		case ServerRespondCodes.DUEL_REQUESTS_LIST:
		case ServerRespondCodes.NEW_DUEL_REQUEST_ADDED:
		case ServerRespondCodes.DUEL_REQUEST_REMOVED:
			DuelRequestParser.startParsing(app, message);
			break;
		case ServerRespondCodes.DUEL_APPLICATION_FULL:
			// Cope here
			break;
		case ServerRespondCodes.PLAYER_ACCEPTED_DUEL_REQUEST:
			DuelRequestParser.startParsing(app, message);
			break;
		case ServerRespondCodes.PLAYER_REJECTED_DUEL_REQUEST:
			// To do this phase
			break;
		case ServerRespondCodes.DUEL_TO_START_NOT_FOUND:
			// To do this phase
			break;
		case ServerRespondCodes.DUEL_START_FAIL_SOMEONE_LEFT:
			// Cope here
			break;
		case ServerRespondCodes.DUEL_TO_REMOVE_NOT_FOUND:
			// bla bla
			break;
		case ServerRespondCodes.DUEL_TO_ADD_NEW_PLAYER_NOT_FOUND:
			// bla bla
			break;
		case ServerRespondCodes.DUEL_TO_REMOVE_PLAYER_FROM_NOT_FOUND:
			// bla bla
			break;
		// Duel block ends here

			
		// Fight block starts here
		case ServerRespondCodes.FIGHT_STARTED:
			// Cope here
			break;
		case ServerRespondCodes.FIGHT_ENDED:
			// Cope here
			break;
		case ServerRespondCodes.FIGHT_PROGRESS_MESSAGE:
			// Cope here
			break;
		// Fight block ends here

		}
	}
}
