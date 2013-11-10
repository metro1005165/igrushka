package com.yuri.game.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yuri.game.GameApplication;
import com.yuri.game.R;
import com.yuri.game.model.actor.Player;
import com.yuri.game.ui.adapters.LocationPlayersList;
import com.yuri.game.utils.HpBar;

public class CharDetailsFragment extends Fragment {
	
	public LocationPlayersList adapter;
	
	private GameApplication app;
	private Player player;
	
	public CharDetailsFragment() {
		
	}	

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		app = GameApplication.getApplicationFromActivity(activity);
		player = app.world.modelContainer.player;
		
		adapter = new LocationPlayersList(activity,
				0,
				app.world.modelContainer.location.getPlayers());
		adapter.notifyDataSetChanged();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_char_details, container, false);
		
		TextView tvName = (TextView) view.findViewById(R.id.tv_name_and_level);
		HpBar hpBar = (HpBar) view.findViewById(R.id.player_hp_bar);
		
		TextView tvStrength = (TextView) view.findViewById(R.id.tv_strength);
		TextView tvAgility = (TextView) view.findViewById(R.id.tv_agility);
		TextView tvLuck = (TextView) view.findViewById(R.id.tv_luck);
		TextView tvToughness = (TextView) view.findViewById(R.id.tv_toughness);
		
		TextView tvCritical = (TextView) view.findViewById(R.id.tv_critical);
		TextView tvAntiCritical = (TextView) view.findViewById(R.id.tv_anticritical);
		TextView tvDodge = (TextView) view.findViewById(R.id.tv_dodge);
		TextView tvAntiDodge = (TextView) view.findViewById(R.id.tv_antidodge);
		
		TextView tvHeadArmor = (TextView) view.findViewById(R.id.tv_head_armor);
		TextView tvChestArmor = (TextView) view.findViewById(R.id.tv_chest_armor);
		TextView tvAbsArmor = (TextView) view.findViewById(R.id.tv_abdomen_armor);
		TextView tvLegArmor = (TextView) view.findViewById(R.id.tv_leg_armor);
		
		TextView tvLocation = (TextView) view.findViewById(R.id.tv_location_name);
		ListView lvLocPlayers = (ListView) view.findViewById(R.id.lv_location_player_list);
		
		tvName.setText("" + player.getName() + " [" + player.getLevel() + "]");
		hpBar.setText("HP " + player.getCurrentHP() + "/" + player.getMaxHP());
		
		tvStrength.setText("Strength: " + player.getStrength());
		tvAgility.setText("Agility: " + player.getAgility());
		tvLuck.setText("Luck: " + player.getLuck());
		tvToughness.setText("Toughness: " + player.getToughness());
		
		tvCritical.setText("Critical: " + player.getCritical());
		tvAntiCritical.setText("AntiCritical: " + player.getAntiCritical());
		tvDodge.setText("Dodge: " + player.getDodge());
		tvAntiDodge.setText("AntiDodge: " + player.getAntiDodge());
		
		tvHeadArmor.setText("Head Armor: " + player.getHeadArmor());
		tvChestArmor.setText("Chest Armor: " + player.getChestArmor());
		tvAbsArmor.setText("Abdomen Armor: " + player.getAbdomenArmor());
		tvLegArmor.setText("Leg Armor: " + player.getLegArmor());
		
		tvLocation.setText("Location: " + player.getLocationType().toString());
		lvLocPlayers.setAdapter(adapter);

		return view;
	}
	
}
