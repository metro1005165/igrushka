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
import com.yuri.game.model.actor.ActorState;
import com.yuri.game.model.actor.GenderType;
import com.yuri.game.model.actor.LocationType;
import com.yuri.game.model.actor.Player;
import com.yuri.game.model.actor.RaceType;

public class PlayerStatsParser extends DefaultHandler {

	private final String TAG = "PlayerStatsParser";

	private GameApplication app;
	private Player player;
	private StringBuffer buffer;

	public PlayerStatsParser(GameApplication app) {
		player = app.world.modelContainer.player;
		this.app = app;
		buffer = new StringBuffer();
	}

	public static void startParsing(GameApplication app, String message) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			PlayerStatsParser handler = new PlayerStatsParser(app);
			saxParser
					.parse(new InputSource(new StringReader(message)), handler);
			Log.e("PlayerStatsParser", "Started parsing...");
		} catch (Exception ex) {
			Log.e("PlayerStatsParser", "SAXParser: parse() failed");
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

		if (localName.equalsIgnoreCase("name")) {
			player.setName(buffer.toString());
		}

		else if (localName.equalsIgnoreCase("race")) {
			player.setRace(RaceType.valueOf(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("gender")) {
			player.setGender(GenderType.valueOf(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("level")) {
			player.setLevel(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("experience")) {
			player.setExperience(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("minDamage")) {
			player.setMinDamage(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("maxDamage")) {
			player.setMaxDamage(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("hp")) {
			player.setMaxHP(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("currentHp")) {
			player.setCurrentHP(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("strength")) {
			player.setStrength(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("agility")) {
			player.setAgility(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("luck")) {
			player.setLuck(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("toughness")) {
			player.setToughness(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("critical")) {
			player.setCritical(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("antiCritical")) {
			player.setAntiCritical(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("dodge")) {
			player.setDodge(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("antiDodge")) {
			player.setAntiDodge(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("headArmor")) {
			player.setHeadArmor(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("chestArmor")) {
			player.setChestArmor(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("abdomenArmor")) {
			player.setAbdomenArmor(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("legArmor")) {
			player.setLegArmor(Integer.parseInt(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("place")) {
			player.setLocationType(LocationType.valueOf(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("onlineState")) {
			player.setState(ActorState.valueOf(buffer.toString()));
		}

		else if (localName.equalsIgnoreCase("gameMessage")) {
			Log.e(TAG, player.toString());
			app.controllers.actorStatsController.updatePlayerStats();
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);

		buffer.setLength(0);
	}
}
