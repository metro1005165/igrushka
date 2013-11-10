package com.yuri.game.model.actor;

public class Actor {

	protected String name;
	protected RaceType race;
	protected GenderType gender;
	protected ActorState state;
	
	public Actor() {}

	public Actor(RaceType race, GenderType gender, String name, ActorState state) {
		this.race = race;
		this.gender = gender;
		this.name = name;
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RaceType getRace() {
		return race;
	}

	public void setRace(RaceType race) {
		this.race = race;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public ActorState getState() {
		return state;
	}

	public void setState(ActorState state) {
		this.state = state;
	}
}
