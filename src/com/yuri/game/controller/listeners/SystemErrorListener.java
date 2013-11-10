package com.yuri.game.controller.listeners;

public interface SystemErrorListener {
	
	void onSocketTerminated();

	void onWrongRequestStructure();

	void onWrongCommandOrder();

	void onMissingParameters();

	void onParameterNotFound();
	
}
