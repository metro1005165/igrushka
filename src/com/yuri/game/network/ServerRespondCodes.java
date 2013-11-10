package com.yuri.game.network;

public final class ServerRespondCodes {
	
	// System Error Message Codes 
	public final static int SOCKET_TERMINATED = 0;
	public final static int WRONG_MESSAGE_STRUCTURE = 1;
	public final static int WRONG_COMMAND_ORDER = 2;
	public final static int MISSING_PARAMETERS = 3;
	public final static int PARAMETER_NOT_FOUND = 4;
	
	// Registration Message Codes
	public final static int SUCCESSFUL_REGISTRATION = 10;
	public final static int USERNAME_EXISTS = 11;
	public final static int EMAIL_EXISTS = 12;
	
	// Login Message Codes
	public final static int LOGIN_SUCCESSFUL = 13;
	public final static int LOGIN_FAILED = 14;
	
	// Client Closes Socket
	public final static int SHUTDOWN = 19;
	
	// Actor and Player Message Codes
	public final static int PLAYER_STATS = 101;
	public final static int ACTOR_STATS = 102;
	public final static int CHARACTER_NOT_FOUND = 103;
	
	// Location Related Message Codes
	public final static int WRONG_LOCATION_COMMAND = 300;
	public final static int PLAYERS_LIST_IN_LOCATION = 301;
	public final static int NEW_PLAYER_IN_LOCATION = 302;
	public final static int PLAYER_GONE_IN_LOCATION = 303;
	
	// Duel Related Message Codes
	public final static int DUEL_REQUESTS_LIST = 401;
	public final static int NEW_DUEL_REQUEST_ADDED = 402;
	public final static int DUEL_REQUEST_REMOVED = 403;
	public final static int DUEL_APPLICATION_FULL = 404;
	public final static int PLAYER_ACCEPTED_DUEL_REQUEST = 405;
	public final static int PLAYER_REJECTED_DUEL_REQUEST = 406;
	public final static int DUEL_TO_START_NOT_FOUND = 407;
	public final static int DUEL_START_FAIL_SOMEONE_LEFT = 408;
	public final static int DUEL_TO_REMOVE_NOT_FOUND = 409;
	public final static int DUEL_TO_ADD_NEW_PLAYER_NOT_FOUND = 410;
	public final static int DUEL_TO_REMOVE_PLAYER_FROM_NOT_FOUND = 411;
	
	// Fight Codes
	public final static int FIGHT_STARTED = 1001;
	public final static int FIGHT_ENDED = 1002;
	public final static int FIGHT_PROGRESS_MESSAGE = 1003;
	
}
