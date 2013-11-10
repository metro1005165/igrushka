package com.yuri.game.network;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.yuri.game.model.actor.LocationType;
import com.yuri.game.model.duel.AttackType;
import com.yuri.game.model.duel.BlockType;

public class ServerRequestFormer {
		
	
	public String getLocationPlayers() {
		return createServerRequest("location_people_list", null);
	}
	
	public String removePlayerFromDuelRequest() {
		return createServerRequest("location_remove_from_duel_application", null);
	}
	
	public String startDuel() {
		return createServerRequest("location_start_duel_application", null);
	}
	
	public String shutdown() {
		return createServerRequest("shutdown", null);
	}

	public String registerNewPlayer(String login, String pwd, String mail) {

		if (login.isEmpty() || pwd.isEmpty() || mail.isEmpty()) {
			return null;
		} else {
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("login", login);
			param.put("password", pwd);
			param.put("email", mail);

			return createServerRequest("register", param);
		}
	}
	
	public String createCharacter(String type, String gender) {

		if (type.isEmpty() || gender.isEmpty()) {
			return null;
		} else {
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("type", type);
			param.put("gender", gender);
	
			return createServerRequest("create", param);
		}	
	}
	
	public String loginPlayer(String login, String pwd) {
		
		if (login.isEmpty() || pwd.isEmpty()) {
			return null;
		} else {
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("login", login);
			param.put("password", pwd);

			return createServerRequest("login", param);		
		}
	}
	
	public String getPlayerChar() {
		
		return createServerRequest("get_character", null);	
	}
	
	public String getAnyChar(String name) {
		
		if (name.isEmpty()) {
			return null;
		} else {
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("name", name);

			return createServerRequest("get_character", param);
		}
	}
	
	public String movePlayerTo(LocationType location) {
		
		if (location == null) {
			return null;
		} else {
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("location", location.name().toLowerCase());

			return createServerRequest("character_move_to", param);	
		}
	}
	
	public String getDuelRequestList() {
		
		return createServerRequest("location_duel_application_list", null);
	}
	
	public String registerDuelRequest() {
		
		return createServerRequest("location_register_duel_application", null);
	}
	
	
	public String removeDuelRequest() {

		return createServerRequest("location_remove_duel_application", null);
	}
	
	public String removeFromDuelRequest() {

		return createServerRequest("location_remove_from_duel_application", null);
	}
	
	public String addPlayerToDuelRequest(String owner) {
		
		if (owner.isEmpty()) {
			return null;
		} else {
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("owner", owner);

			return createServerRequest("location_add_to_duel_application", param);	
		}
	}
	
	public String fightAttack(Object... objects) {
		
		boolean firstAttackTypeFound = false;
		boolean firstBlockTypeFound = false;
		boolean secondBlockTypeFound = false;
		
		AttackType attackType = null;
		BlockType blockType_1 = null;
		BlockType blockType_2 = null;
		
		for (Object object : objects) {
			if (object instanceof AttackType) {
				if (!firstAttackTypeFound) {
					attackType = (AttackType) object;
					firstAttackTypeFound = true;
				}
			} else if (object instanceof BlockType) {
				if (!firstBlockTypeFound) {
					blockType_1 = (BlockType) object;
					firstBlockTypeFound = true;
				} else {
					if (!secondBlockTypeFound) {
						blockType_2 = (BlockType) object;
						secondBlockTypeFound = true;
					}
				}
			}
		}
		
		if (attackType != null && blockType_1 != null && blockType_2 != null) {
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("attack", attackType.name().toLowerCase());
			param.put("block1", blockType_1.name().toLowerCase());
			param.put("block2", blockType_2.name().toLowerCase());

			return createServerRequest("fight_attack", param);
		} 
		
		return null;
	}
	
	public String createServerRequest(String command,
			HashMap<String, String> param) {

		if (!command.isEmpty()) {

			XmlSerializer serializer = Xml.newSerializer();
			StringWriter writer = new StringWriter();

			try {
				serializer.setOutput(writer);
				serializer.startDocument("UTF-8", true);

				serializer.startTag("", "request");
				serializer.attribute("", "command", command);

				if (param != null) {

					serializer.startTag("", "parameters");

					for (Map.Entry<String, String> entry : param.entrySet()) {

						serializer.startTag("", "parameter");
						serializer.attribute("", "name", entry.getKey());
						serializer.attribute("", "value", entry.getValue());
						serializer.endTag("", "parameter");
					}

					serializer.endTag("", "parameters");
				}

				serializer.endTag("", "request");

				serializer.endDocument();
				return writer.toString();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		return null;
	}	
}
