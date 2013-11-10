package com.yuri.game.network;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yuri.game.GameApplication;

public class RespondThread implements Runnable {

	private final String TAG = "RespondThread";
	private final int TIMEOUT = 70000;

	private ClientSocket clientSocket;
	private Handler handler;
	private boolean isRunning = false;
	private long lastReadTime = 0;

	public RespondThread(GameApplication app, Handler handler) {
		clientSocket = app.clientSocket;
		this.handler = handler;
	}

	@Override
	public void run() {

		String command = "";
		String serverMessage = null;
		String code = null;

		synchronized (clientSocket) {
			try {
				clientSocket.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (!clientSocket.connected) {
			return;
		}

		Log.e("RespondThread", "Starting listening -> Connected = "
				+ clientSocket.connected);
		isRunning = true;

		while (isRunning) {
			serverMessage = null;

			try {
				serverMessage = clientSocket.inStream.readLine();
			} catch (IOException e) {
				Log.e(TAG, e.toString());
			}

			if (serverMessage != null) {
				lastReadTime = System.currentTimeMillis();
				Log.e("LISTENING LOOP", "RECEIVED HEARTBEAT!");
				synchronized (this) {
					clientSocket.outStream.println("HEARTBEAT");
					clientSocket.outStream.flush();
					Log.e("LISTENING LOOP", "SENT HEARTBEAT!");
				}

				if (serverMessage.contains("</code>")) {
					Pattern tagPattern = Pattern
							.compile("<code>\\s*(.+?)\\s*</code>");
					Matcher contentMatcher = tagPattern.matcher(serverMessage);

					if (contentMatcher.find()) {
						code = contentMatcher.group(1);
						Log.e(TAG, "code = " + code + "");
						if (Integer.parseInt(code) == 0) {
							Message message = new Message();
							Bundle bundle = new Bundle();
							bundle.putString("COMMAND", command);
							bundle.putString("CODE", code);
							message.setData(bundle);
							handler.sendMessage(message);
							clientSocket.closeClientSocket();
							break;
						}
					}
				}

				if (!serverMessage.contains("HEARTBEAT")) {
					command += serverMessage.trim();
				}

				if (serverMessage.contains("</systemMessage>")
						|| serverMessage.contains("</gameMessage>")) {
					Message message = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("COMMAND", command);
					bundle.putString("CODE", code);
					message.setData(bundle);
					handler.sendMessage(message);
					command = "";
				}
			}

			if (lastReadTime != 0) {
				if (!isConnectionAlive()) {
					break;
				}
			}
		}
		
		clientSocket.closeClientSocket();
		stopThread();	
	}

	public void stopThread() {
		isRunning = false;
	}

	private boolean isConnectionAlive() {
		return System.currentTimeMillis() - lastReadTime < TIMEOUT;
	}
}
