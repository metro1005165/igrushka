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

public class ActorStatsParser extends DefaultHandler {

	private final String TAG = "ActorStatsParser";

	private GameApplication app;
	private Player actor;
	private StringBuffer buffer; 
	private int code;

	public ActorStatsParser(GameApplication app) {
		actor = new Player();
		this.app = app;
		buffer = new StringBuffer();
	}
	
	public static void startParsing(GameApplication app, String message) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			ActorStatsParser handler = new ActorStatsParser(app);
			saxParser.parse(new InputSource(new StringReader(message)), handler);
		} catch (Exception ex) {
			Log.e("startParsing" , "SAXParser: parse() failed");
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
		
		// Should measure the speed later if slow remove double check...
		if (code == 102) {
			if (actor != null) {
				if (localName.equalsIgnoreCase("name")) {
					actor.setName(buffer.toString());
				}

				else if (localName.equalsIgnoreCase("race")) {
					actor.setRace(RaceType.valueOf(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("gender")) {
					actor.setGender(GenderType.valueOf(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("level")) {
					actor.setLevel(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("experience")) {
					actor.setExperience(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("minDamage")) {
					actor.setMinDamage(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("maxDamage")) {
					actor.setMaxDamage(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("hp")) {
					actor.setMaxHP(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("currentHp")) {
					actor.setCurrentHP(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("strength")) {
					actor.setStrength(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("agility")) {
					actor.setAgility(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("luck")) {
					actor.setLuck(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("toughness")) {
					actor.setToughness(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("critical")) {
					actor.setCritical(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("antiCritical")) {
					actor.setAntiCritical(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("dodge")) {
					actor.setDodge(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("antiDodge")) {
					actor.setAntiDodge(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("headArmor")) {
					actor.setHeadArmor(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("chestArmor")) {
					actor.setChestArmor(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("abdomenArmor")) {
					actor.setAbdomenArmor(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("legArmor")) {
					actor.setLegArmor(Integer.parseInt(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("place")) {
					actor.setLocationType(LocationType.valueOf(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("onlineState")) {
					actor.setState(ActorState.valueOf(buffer.toString()));
				}

				else if (localName.equalsIgnoreCase("gameMessage")) {
					Log.e(TAG, actor.toString());
					app.controllers.actorStatsController.updateActorStats(actor);
				}
			}
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		
		buffer.setLength(0);

		if (qName.equalsIgnoreCase("gameMessage")) {
			String value = attributes.getValue("code");
			if (value != null) {
				code = Integer.parseInt(value);
				Log.e(TAG, "Code = " + code);
			}
        }
	}
}
