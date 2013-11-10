package com.yuri.game.resource.parsers;

import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.yuri.game.GameApplication;
import com.yuri.game.model.actor.GenderType;
import com.yuri.game.model.location.LocationPlayer;
import com.yuri.game.network.ServerRespondCodes;

public class LocationPlayersParser extends DefaultHandler {

	private final String TAG = "LocationPlayersParser";

	private GameApplication app;
	private GenderType gender;
	private String name;
	private int level;
	private StringBuffer buffer;
	private int code;

	public LocationPlayersParser(GameApplication app) {
		this.app = app;
		buffer = new StringBuffer();
	}

	public static void startParsing(GameApplication app, String message) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			LocationPlayersParser handler = new LocationPlayersParser(app);
			saxParser.parse(new InputSource(new StringReader(message)), handler);
		} catch (Exception ex) {
			Log.e("LocationPlayersParser", "SAXParser: parse() failed");
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
			name = buffer.toString();
			Log.e(TAG, "Name: " + name + " Level: " + level + " Gender: " + gender.toString());
			LocationPlayer player = new LocationPlayer(name, level, gender);
			
			if (code == ServerRespondCodes.PLAYER_GONE_IN_LOCATION) {
				app.world.modelContainer.location.removePlayer(player);
			} else {
				app.world.modelContainer.location.addNewPlayer(player);
			}
		}

		else if (localName.equalsIgnoreCase("gameMessage")) {
			switch (code) {
			case ServerRespondCodes.PLAYERS_LIST_IN_LOCATION:
				app.controllers.locationController.locationPlayersListChanged();
				break;
			case ServerRespondCodes.NEW_PLAYER_IN_LOCATION:
				app.controllers.locationController.onNewPlayerAddedToLocation();
				break;
			case ServerRespondCodes.PLAYER_GONE_IN_LOCATION:
				app.controllers.locationController.onPlayerRemovedFromLocation();
				break;
			}
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);

		buffer.setLength(0);

		if (localName.equalsIgnoreCase("character")) {
			String v1 = attributes.getValue(0);
			if (v1 != null) {
				gender = GenderType.valueOf(v1);
				Log.e(TAG, "Gender = " + gender.toString());
			}
			String v2 = attributes.getValue(1);
			if (v2 != null) {
				level = Integer.parseInt(v2);
				Log.e(TAG, "Level = " + level);
			}
		}
	}
}
