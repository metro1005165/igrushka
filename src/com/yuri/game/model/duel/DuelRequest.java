package com.yuri.game.model.duel;

import java.util.List;

public class DuelRequest {
	
	private String owner;
	private List<String> bluePlayers;
	private List<String> redPlayers;
	private String postTime;
	private String timeout;
	private String lifeSpan;
	
	public DuelRequest() {}

	public DuelRequest(String owner, List<String> bluePlayers,
			List<String> redPlayers, String postTime, String timeout,
			String lifeSpan) {
		this.owner = owner;
		this.bluePlayers = bluePlayers;
		this.redPlayers = redPlayers;
		this.postTime = postTime;
		this.timeout = timeout;
		this.lifeSpan = lifeSpan;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public List<String> getBluePlayers() {
		return bluePlayers;
	}

	public void setBluePlayers(List<String> bluePlayers) {
		this.bluePlayers = bluePlayers;
	}

	public List<String> getRedPlayers() {
		return redPlayers;
	}

	public void setRedPlayers(List<String> redPlayers) {
		this.redPlayers = redPlayers;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public String getTimeout() {
		return timeout;
	}

	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}

	public String getLifeSpan() {
		return lifeSpan;
	}

	public void setLifeSpan(String lifeSpan) {
		this.lifeSpan = lifeSpan;
	}

	@Override
	public String toString() {
		return "DuelRequest [owner=" + owner + ", bluePlayers=" + bluePlayers
				+ ", redPlayers=" + redPlayers + ", postTime=" + postTime
				+ ", timeout=" + timeout + ", lifeSpan=" + lifeSpan + "]";
	}	
}
