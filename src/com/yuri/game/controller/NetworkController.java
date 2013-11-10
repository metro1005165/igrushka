package com.yuri.game.controller;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yuri.game.GameApplication;
import com.yuri.game.network.RequestThread;
import com.yuri.game.network.RespondThread;
import com.yuri.game.network.ServerMessageHandler;
import com.yuri.game.network.ServerRequestFormer;
import com.yuri.game.network.SocketThread;

public class NetworkController {
	
	private GameApplication app;
	public RespondThread listeningThread;
	
	private String command;
	private String code;

	public NetworkController(GameApplication app) {
		this.app = app;
	}

	public void connectToServer() {
		SocketThread socketThread = new SocketThread(app);
		Thread thread = new Thread(socketThread, "socketThread");
		thread.start();
	}
	
	public void startPendingRequest(String request) {
		RequestThread requestThread = new RequestThread(app, request);
		Thread thread = new Thread(requestThread, "requestThread");
		thread.start();
	}
	
	public void startListeningToServer() {
		listeningThread = new RespondThread(app, handler);
		Thread thread = new Thread(listeningThread, "respondThread");
		thread.start();
	}
	
	public void closeConnection() {
		if (listeningThread != null) {
			listeningThread.stopThread();
			sendRequest(new ServerRequestFormer().shutdown());
		}	
	}
	
	public void sendRequest(String request) {
		if (app.clientSocket.connected) {
			app.clientSocket.outStream.println(request);
			app.clientSocket.outStream.flush();	
			Log.e("sendRequest", "Sent Request: " + request);
		}
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			command = msg.getData().getString("COMMAND");
			code = msg.getData().getString("CODE");
			Log.e("HANDLER", "RECEIVED: CODE: " + code + " COMMAND: " + command);
			
			if (!command.isEmpty() && !code.isEmpty()) {
				new ServerMessageHandler(app, code, command)
						.processServerMessage();
			}
		}	
	};
}
