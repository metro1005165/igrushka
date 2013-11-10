package com.yuri.game.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;

public class ClientSocket {
	
	public static final String SERVERIP = "78.47.105.119";
	public static final int SERVERPORT = 2012;
	public static final String TAG = "ClientSocket";
	
	public PrintWriter outStream;
	public BufferedReader inStream;
	public Socket socket;
	public boolean connected = false;
	
	public synchronized void openClientSocket() {
		
		try {
			if (socket == null) {
				socket = new Socket(SERVERIP, SERVERPORT);
				outStream = new PrintWriter(socket.getOutputStream());
				inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			}
		} catch (IOException e) {
			Log.e(TAG, e.toString());
		}
		
		connected = true;
	}
	
	public synchronized void closeClientSocket() {

		if (socket != null) {
			if (socket.isConnected()) {
				try {
					inStream.close();
					outStream.close();
					socket.close();
					socket = null;
					outStream = null;
					inStream = null;
				} catch (IOException e) {
					Log.e(TAG, e.toString());
				}
			}
		}
		connected = false;
	}	
}
