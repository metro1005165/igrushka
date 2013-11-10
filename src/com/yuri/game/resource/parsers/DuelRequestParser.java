package com.yuri.game.resource.parsers;

import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.yuri.game.GameApplication;
import com.yuri.game.model.duel.DuelRequest;
import com.yuri.game.model.duel.TeamType;
import com.yuri.game.model.location.Location;
import com.yuri.game.network.ServerRespondCodes;

public class DuelRequestParser extends DefaultHandler {
	
	private final String TAG = "DuelRequestParser";
	private final String TIMEOUT = "1 minute";
	private final String LIFE_SPAN = "5 minutes";

	private GameApplication app;
	private List<String> bluePlayers;
	private List<String> redPlayers;
	private Location location;
	private TeamType teamType;
	private String owner;
	private StringBuffer buffer;
	private int code;
	private String name;

	public DuelRequestParser(GameApplication app) {
		this.app = app;
		buffer = new StringBuffer();
		location = app.world.modelContainer.location;
		bluePlayers = new ArrayList<String>();
		redPlayers = new ArrayList<String>();
	}

	public static void startParsing(GameApplication app, String message) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			DuelRequestParser handler = new DuelRequestParser(app);
			saxParser.parse(new InputSource(new StringReader(message)), handler);
		} catch (Exception ex) {
			Log.e("DuelRequestParser", "SAXParser: parse() failed");
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);

		buffer.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		
		if (localName.equalsIgnoreCase("code")) {
			code = Integer.parseInt(buffer.toString());
			Log.e(TAG, "Code: " + code);	
		}
		
		if (localName.equalsIgnoreCase("character")) {
			switch (teamType) {
			case BLUE:
				String blue = buffer.toString();
				Log.e(TAG, "Blue: " + blue);
				if (!blue.isEmpty()) {
					bluePlayers.add(blue);
				}
				break;
			case RED:
				String red = buffer.toString();
				Log.e(TAG, "Red: " + red);
				if (!red.isEmpty()) {
					redPlayers.add(red);
				}
				break;
			}
		}
		
		if (localName.equalsIgnoreCase("owner")) {
			owner = buffer.toString();
			Log.e(TAG, "Owner: " + owner);
		}
		
		if (localName.equalsIgnoreCase("name")) {
			name = buffer.toString();
			Log.e(TAG, "Name: " + name);
			Log.e("if (localName.equalsIgnoreCase(name))", "Name was set!");
		}
		
		if (localName.equalsIgnoreCase("duel")) {
			switch (code) {
			case ServerRespondCodes.DUEL_REQUESTS_LIST:
			case ServerRespondCodes.NEW_DUEL_REQUEST_ADDED:
				location.addDuelRequest(new DuelRequest(owner, bluePlayers, redPlayers,
						getCurrentTime(), TIMEOUT, LIFE_SPAN));
				break;
			case ServerRespondCodes.DUEL_REQUEST_REMOVED:
				location.removeDuelRequest(new DuelRequest(owner, bluePlayers, redPlayers,
						getCurrentTime(), TIMEOUT, LIFE_SPAN));
				break;
			}
		}

		else if (localName.equalsIgnoreCase("gameMessage")) {
			switch (code) {
			case ServerRespondCodes.DUEL_REQUESTS_LIST:
				app.controllers.duelController.onDuelRequestListReceived();
				break;
			case ServerRespondCodes.NEW_DUEL_REQUEST_ADDED:
				app.controllers.duelController.onDuelRequestPublished();
				break;
			case ServerRespondCodes.DUEL_REQUEST_REMOVED:
				app.controllers.duelController.onDuelRequestRemoved();
				break;
			case ServerRespondCodes.PLAYER_ACCEPTED_DUEL_REQUEST:
				app.controllers.duelController.onNewPlayerAddedToDuel(owner, name);
				app.world.modelContainer.player.toString();
				break;
			}
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);

		buffer.setLength(0);

		if (localName.equalsIgnoreCase("blue")) {
			teamType = TeamType.BLUE;
		}
		
		if (localName.equalsIgnoreCase("red")) {
			teamType = TeamType.RED;
		}
	}
	
	private String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
