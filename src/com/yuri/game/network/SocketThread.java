package com.yuri.game.network;

import android.util.Log;

import com.yuri.game.GameApplication;

public class SocketThread implements Runnable {
	
	private ClientSocket clientSocket;
	
	public SocketThread(GameApplication app) {
		clientSocket = app.clientSocket;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized (clientSocket) {
			clientSocket.openClientSocket();
			clientSocket.notifyAll();
			Log.e("SocketThread", "Successfully connected to Server!Socket = " + clientSocket.socket);
		}
	}
}
