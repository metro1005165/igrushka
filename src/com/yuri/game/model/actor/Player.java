package com.yuri.game.model.actor;

public final class Player extends Actor {

	// General Stats
	private int strength;
	private int agility;
	private int luck;
	private int toughness;

	// Battle Stats
	private int critical;
	private int antiCritical;
	private int dodge;
	private int antiDodge;

	// Armor Stats
	private int headArmor;
	private int chestArmor;
	private int abdomenArmor;
	private int legArmor;

	// Action Stats
	private int level;
	private int experience;
	private int minDamage;
	private int maxDamage;
	private int currentHP;
	private int maxHP;

	private LocationType locationType;
	
	// Duel Request Flags
	private boolean hasActiveDuelRequest;
	private boolean acceptedDuelRequest;

	public Player() {
		super();
	}

	public Player(int level, int experience, int minDamage, int maxDamage,
			int currentHP, int maxHP, int strength, int agility, int luck,
			int toughness, int critical, int antiCritical, int dodge,
			int antiDodge, int headArmor, int chestArmor, int abdomenArmor,
			int legArmor, LocationType locationType, RaceType race,
			GenderType gender, String name, ActorState state) {
		super(race, gender, name, state);

		this.level = level;
		this.experience = experience;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.currentHP = currentHP;
		this.maxHP = maxHP;
		this.strength = strength;
		this.agility = agility;
		this.luck = luck;
		this.toughness = toughness;
		this.critical = critical;
		this.antiCritical = antiCritical;
		this.dodge = dodge;
		this.antiDodge = antiDodge;
		this.headArmor = headArmor;
		this.chestArmor = chestArmor;
		this.abdomenArmor = abdomenArmor;
		this.legArmor = legArmor;
		this.locationType = locationType;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getLuck() {
		return luck;
	}

	public void setLuck(int luck) {
		this.luck = luck;
	}

	public int getToughness() {
		return toughness;
	}

	public void setToughness(int toughness) {
		this.toughness = toughness;
	}

	public int getCritical() {
		return critical;
	}

	public void setCritical(int critical) {
		this.critical = critical;
	}

	public int getAntiCritical() {
		return antiCritical;
	}

	public void setAntiCritical(int antiCritical) {
		this.antiCritical = antiCritical;
	}

	public int getDodge() {
		return dodge;
	}

	public void setDodge(int dodge) {
		this.dodge = dodge;
	}

	public int getAntiDodge() {
		return antiDodge;
	}

	public void setAntiDodge(int antiDodge) {
		this.antiDodge = antiDodge;
	}

	public int getHeadArmor() {
		return headArmor;
	}

	public void setHeadArmor(int headArmor) {
		this.headArmor = headArmor;
	}

	public int getChestArmor() {
		return chestArmor;
	}

	public void setChestArmor(int chestArmor) {
		this.chestArmor = chestArmor;
	}

	public int getAbdomenArmor() {
		return abdomenArmor;
	}

	public void setAbdomenArmor(int abdomenArmor) {
		this.abdomenArmor = abdomenArmor;
	}

	public int getLegArmor() {
		return legArmor;
	}

	public void setLegArmor(int legArmor) {
		this.legArmor = legArmor;
	}

	public int getMinDamage() {
		return minDamage;
	}

	public void setMinDamage(int minDamage) {
		this.minDamage = minDamage;
	}

	public int getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}

	public LocationType getLocationType() {
		return locationType;
	}

	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}

	public boolean hasActiveDuelRequest() {
		return hasActiveDuelRequest;
	}

	public void setHasActiveDuelRequest(boolean hasActiveDuelRequest) {
		this.hasActiveDuelRequest = hasActiveDuelRequest;
	}

	public boolean acceptedDuelRequest() {
		return acceptedDuelRequest;
	}

	public void setAcceptedDuelRequest(boolean acceptedDuelRequest) {
		this.acceptedDuelRequest = acceptedDuelRequest;
	}

	@Override
	public String toString() {
		return "Player [strength=" + strength + ", agility=" + agility
				+ ", luck=" + luck + ", toughness=" + toughness + ", critical="
				+ critical + ", antiCritical=" + antiCritical + ", dodge="
				+ dodge + ", antiDodge=" + antiDodge + ", headArmor="
				+ headArmor + ", chestArmor=" + chestArmor + ", abdomenArmor="
				+ abdomenArmor + ", legArmor=" + legArmor + ", level=" + level
				+ ", experience=" + experience + ", minDamage=" + minDamage
				+ ", maxDamage=" + maxDamage + ", currentHP=" + currentHP
				+ ", maxHP=" + maxHP + ", locationType=" + locationType
				+ ", hasActiveDuelRequest=" + hasActiveDuelRequest
				+ ", acceptedDuelRequest=" + acceptedDuelRequest + "]";
	}
}
