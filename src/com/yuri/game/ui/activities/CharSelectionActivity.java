package com.yuri.game.ui.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.yuri.game.GameApplication;
import com.yuri.game.R;
import com.yuri.game.context.ControllerContext;
import com.yuri.game.context.WorldContext;
import com.yuri.game.controller.listeners.LocationListener;
import com.yuri.game.controller.listeners.SystemErrorListener;
import com.yuri.game.model.actor.Actor;
import com.yuri.game.model.listeners.ActorStatsListener;
import com.yuri.game.network.ServerRequestFormer;
import com.yuri.game.ui.adapters.CharSelectPager;
import com.yuri.game.ui.fragments.CharSelectFragment.OnCharSelectedListener;

public class CharSelectionActivity extends FragmentActivity implements
		LocationListener, SystemErrorListener, ActorStatsListener,
		OnCharSelectedListener {

	public ControllerContext controllers;
	public WorldContext world;

	private CharSelectPager charSelectPagerAdapter;
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_char_selection);

		final GameApplication app = GameApplication
				.getApplicationFromActivity(this);
		this.controllers = app.controllers;
		this.world = app.world;

		Log.e("onCreate", world.modelContainer.player.toString());

		controllers.locationController.locationListeners.add(this);
		controllers.actorStatsController.actorStatsListeners.add(this);
		controllers.systemErrorController.systemErrorListeners.add(this);

		charSelectPagerAdapter = new CharSelectPager(
				getSupportFragmentManager());

		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(charSelectPagerAdapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		controllers.locationController.locationListeners.remove(this);
		controllers.actorStatsController.actorStatsListeners.remove(this);
		controllers.systemErrorController.systemErrorListeners.remove(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		if (featureId == 0) {
			controllers.networkController.closeConnection();
			Toast.makeText(this, "Connection is closed!", Toast.LENGTH_LONG).show();
		}

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onPlayerStatsChanged() {
		
	}

	@Override
	public void onCharNotFound(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSocketTerminated() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWrongRequestStructure() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWrongCommandOrder() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMissingParameters() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onParameterNotFound() {
		// TODO Auto-generated method stub

	}

	public void showSystemErrorDialog(String title, String message) {

		Dialog d = new AlertDialog.Builder(this).setTitle(title)
				.setMessage(message)
				.setNeutralButton(android.R.string.ok, null).create();
		d.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				// LoginActivity.this.finish();
			}
		});
		d.show();
	}

	@Override
	public void onWrongLocationCommand() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationPlayersListReceived() {
		if (!isFinishing()) {
			startActivity(new Intent(getApplicationContext(),
					MainActivity.class));
			CharSelectionActivity.this.finish();
		}
	}

	@Override
	public void onNewPlayerAddedToLocation() {
		if (!isFinishing()) {
			Log.e("onNewPlayerAddedToLocation()", "");
			world.modelContainer.location.showPlayersInLocation();
		}
	}

	@Override
	public void onPlayerRemovedFromLocation() {
		if (!isFinishing()) {
			Log.e("onPlayerRemovedFromLocation()", "");
			world.modelContainer.location.showPlayersInLocation();
		}
	}

	@Override
	public void onCharSelected(String race, String gender) {
		controllers.networkController
				.sendRequest(new ServerRequestFormer().createCharacter(race.toLowerCase(),
						gender.toLowerCase()));
	}

	@Override
	public void onActorStatsRequested(Actor actor) {
		// TODO Auto-generated method stub
		
	}
}
