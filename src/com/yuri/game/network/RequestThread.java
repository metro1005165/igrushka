package com.yuri.game.network;

import android.util.Log;

import com.yuri.game.GameApplication;

public class RequestThread implements Runnable {

	private ClientSocket clientSocket;
	private String request;
	
	public RequestThread(GameApplication app, String request) {
		clientSocket = app.clientSocket;
		this.request = request;
	}
	
	@Override
	public void run() {
		synchronized (clientSocket) {
			try {
				clientSocket.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if (clientSocket.connected) {
			clientSocket.outStream.println(request);
			clientSocket.outStream.flush();
			Log.e("RequestThread", "Sent request: " + request);
		}	
	}
}
