package com.yuri.game.model.location;

import com.yuri.game.model.actor.GenderType;

public class LocationPlayer {
	
	private String name;
	private int level;
	private GenderType gender;
	
	public LocationPlayer() {}
	
	public LocationPlayer(String name, int level, GenderType gender) {
		this.name = name;
		this.level = level;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "LocationPlayer [name=" + name + ", level=" + level
				+ ", gender=" + gender + "]";
	}
}
