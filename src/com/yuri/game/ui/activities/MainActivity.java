package com.yuri.game.ui.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yuri.game.GameApplication;
import com.yuri.game.R;
import com.yuri.game.controller.listeners.DuelRequestListener;
import com.yuri.game.controller.listeners.LocationListener;
import com.yuri.game.model.actor.LocationType;
import com.yuri.game.network.ServerRequestFormer;
import com.yuri.game.ui.adapters.TabsPager;
import com.yuri.game.ui.fragments.ActivityCallback;
import com.yuri.game.ui.fragments.CharDetailsFragment;
import com.yuri.game.ui.fragments.DuelsFragment;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener, LocationListener, ActivityCallback,
		DuelRequestListener {

	private ViewPager viewPager;
	private TabsPager tabsPagerAdapter;
	private ActionBar actionBar;
	private GameApplication app;

	private ProgressDialog barProgressDialog;
	private Handler updateBarHandler;

	// Tab titles
	private String[] tabTitles = { "Main", "Locations", "Duels" };

	// Tab icons
	private int[] tabIcons = { R.drawable.pirate_smile,
			R.drawable.viking_angry, R.drawable.santa_money };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		tabsPagerAdapter = new TabsPager(getSupportFragmentManager());
		updateBarHandler = new Handler();

		viewPager.setAdapter(tabsPagerAdapter);
		// actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// subscribe
		app = GameApplication.getApplicationFromActivity(this);
		app.controllers.locationController.locationListeners.add(this);
		app.controllers.duelController.duelRequestListeners.add(this);

		for (int i = 0; i < tabTitles.length; i++) {
			actionBar.addTab(actionBar.newTab().setText(tabTitles[i])
					.setTabListener(this).setIcon(tabIcons[i]));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		if (featureId == 0) {
			app.controllers.networkController.closeConnection();
			Toast.makeText(this, "Connection is closed!", Toast.LENGTH_LONG)
					.show();
		}

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		app.controllers.locationController.locationListeners.remove(this);
		app.controllers.duelController.duelRequestListeners.remove(this);

		// cleanup resources here
		app.world.modelContainer.location.getPlayers().clear();
		app.world.modelContainer.location.getDuelRequestsList().clear();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
		Log.e("onTabSelected", "Position " + tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWrongLocationCommand() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationPlayersListReceived() {
		Fragment f = tabsPagerAdapter.getActiveFragment(viewPager, 0);
		CharDetailsFragment cdf = (CharDetailsFragment) f;
		cdf.adapter.notifyDataSetChanged();
	}

	@Override
	public void onNewPlayerAddedToLocation() {
		Fragment f = tabsPagerAdapter.getActiveFragment(viewPager, 0);
		CharDetailsFragment cdf = (CharDetailsFragment) f;
		cdf.adapter.notifyDataSetChanged();
	}

	@Override
	public void onPlayerRemovedFromLocation() {
		Fragment f = tabsPagerAdapter.getActiveFragment(viewPager, 0);
		CharDetailsFragment cdf = (CharDetailsFragment) f;
		cdf.adapter.notifyDataSetChanged();
	}

	@Override
	public void onCallback(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			setupLoadingDialog("Moving to Training Room...");
			launchLoadingDialog(position);
			break;
		case 1:
			setupLoadingDialog("Moving to Castle...");
			launchLoadingDialog(position);
			break;
		case 2:
			setupLoadingDialog("Moving to Barracks...");
			launchLoadingDialog(position);
			break;
		}

	}

	private void setupLoadingDialog(String where) {
		
		int currentOrientation = getResources().getConfiguration().orientation;
		if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
		   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		}
		else {
		   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		}
		
		barProgressDialog = new ProgressDialog(this);

		barProgressDialog.setTitle(where);
		barProgressDialog.setMessage("Please wait ...");
		barProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		barProgressDialog.setCancelable(false);
		barProgressDialog.setProgressNumberFormat(null);
		barProgressDialog.setProgressPercentFormat(null);
		barProgressDialog.setProgress(0);
		barProgressDialog.setMax(100);
		barProgressDialog.show();
	}

	private void move(int position) {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		
		switch (position) {
		case 0:
			app.controllers.networkController.sendRequest(new ServerRequestFormer().movePlayerTo(LocationType.TRAININGROOM));
			break;
		case 1:
			app.controllers.networkController.sendRequest(new ServerRequestFormer().movePlayerTo(LocationType.CASTLE));
			break;
		case 2:
			app.controllers.networkController.sendRequest(new ServerRequestFormer().movePlayerTo(LocationType.BARRACKS));
			break;
		}
		
		app.controllers.networkController.sendRequest(new ServerRequestFormer().getLocationPlayers());
		viewPager.setCurrentItem(0);
	}

	private void launchLoadingDialog(final int position) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					// Here you should write your time consuming task...
					while (barProgressDialog.getProgress() <= barProgressDialog
							.getMax()) {

						Thread.sleep(100);

						updateBarHandler.post(new Runnable() {

							public void run() {

								barProgressDialog.incrementProgressBy(1);

							}

						});

						if (barProgressDialog.getProgress() == barProgressDialog
								.getMax()) {

							barProgressDialog.dismiss();
							barProgressDialog = null;

							updateBarHandler.post(new Runnable() {

								public void run() {
									move(position);
								}

							});

						}
					}
				} catch (Exception e) {
				}
			}
		}).start();
	}

	@Override
	public void onDuelRequestListReceived() {
		Log.e("onDuelRequestListReceived", "called");

		Fragment fragment = tabsPagerAdapter.getActiveFragment(viewPager, 2);

		if (fragment != null) {
			if (fragment instanceof DuelsFragment) {
				DuelsFragment duelsFragment = (DuelsFragment) fragment;
				duelsFragment.adapter.notifyDataSetChanged();
				Log.e("onDuelRequestListReceived", "List redrawn!");
			}
		}
	}

	@Override
	public void onDuelRequestPublished() {
		Log.e("onDuelRequestPublished", "called");

		Fragment fragment = tabsPagerAdapter.getActiveFragment(viewPager, 2);

		if (fragment != null) {
			if (fragment instanceof DuelsFragment) {
				DuelsFragment duelsFragment = (DuelsFragment) fragment;
				duelsFragment.adapter.notifyDataSetChanged();
				Log.e("onDuelRequestPublished", "List redrawn!");
			}
		}
	}

	@Override
	public void onDuelRequestRemoved() {
		Log.e("onDuelRequestPublished", "called");

		Fragment fragment = tabsPagerAdapter.getActiveFragment(viewPager, 2);

		if (fragment != null) {
			if (fragment instanceof DuelsFragment) {
				DuelsFragment duelsFragment = (DuelsFragment) fragment;
				duelsFragment.adapter.notifyDataSetChanged();
				Log.e("onDuelRequestPublished", "List redrawn!");
			}
		}

	}

	@Override
	public void onDuelIsFull() {
		String message = "Duel is full";
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onNewPlayerAddedToDuel(String owner, String challenger) {
		Log.e("MAIN_ACTIVITY", "onNewPlayerAddedToDuel");
		
		if (!owner.equals(app.world.modelContainer.player.getName())) {
			return;
		}
		
		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_duel_request_accepted);
		TextView enemyName = (TextView) dialog
				.findViewById(R.id.tv_challenger_info);
		enemyName.setText("Player " + challenger + " accepted your request!");
		Button accept = (Button) dialog.findViewById(R.id.btn_accept);
		accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				app.controllers.networkController
						.sendRequest(new ServerRequestFormer().startDuel());
				dialog.dismiss();
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			}
		});

		Button decline = (Button) dialog.findViewById(R.id.btn_decline);
		decline.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				app.controllers.networkController
						.sendRequest(new ServerRequestFormer().startDuel());
				dialog.dismiss();
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			}
		});
		
		int currentOrientation = getResources().getConfiguration().orientation;
		if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
		   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		}
		else {
		   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
		}
		
		dialog.setCancelable(false);
		dialog.show();

	}

	@Override
	public void onDuelToStartNotFound() {
		String message = "Duel to start is not found";
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDuelStartFail() {
		String message = "Cannot start duel, because callenger withdrew the request";
		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDuelToRemoveNotFound() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDuelToAddNewPlayerNotFound() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDuelToRemovePlayerFromNotFound() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerRemovedFromDuel() {
		// TODO Auto-generated method stub

	}

}
