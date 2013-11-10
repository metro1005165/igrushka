package com.yuri.game.context;

import com.yuri.game.model.ModelContainer;

public final class WorldContext {
	
/*	public final ItemTypeCollection itemTypes;
	public final ItemCategoryCollection itemCategories;
	public final ActorConditionTypeCollection actorConditionsTypes;
	public final SkillCollection skills;

	public final TileManager tileManager;*/

	public ModelContainer modelContainer;

	public WorldContext() {
		this.modelContainer = new ModelContainer();
	}
}
