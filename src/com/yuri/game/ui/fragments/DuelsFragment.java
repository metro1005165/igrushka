package com.yuri.game.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.yuri.game.GameApplication;
import com.yuri.game.R;
import com.yuri.game.network.ServerRequestFormer;
import com.yuri.game.ui.adapters.DuelRequestsList;

public class DuelsFragment extends Fragment {
	
	public DuelRequestsList adapter; // Should it be public?
	
	private GameApplication app;
	
	
	
	public DuelsFragment() {
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		app = GameApplication.getApplicationFromActivity(activity);
		adapter = new DuelRequestsList(activity, 0,
				app.world.modelContainer.location.getDuelRequestsList());
		app.controllers.networkController.sendRequest(new ServerRequestFormer()
				.getDuelRequestList());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_duels, container,
				false);
		ListView duelRequests = (ListView) view.findViewById(R.id.lv_duel_requests);
		view.findViewById(R.id.btn_post_duel_request).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				app.controllers.networkController.sendRequest(new ServerRequestFormer()
				.registerDuelRequest());
			}
		});
		
		duelRequests.setAdapter(adapter);
		
		duelRequests.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.e("LIST ROW CLICKED", "Position: " + arg2 + ", ID: " + arg3);
				
			}
			
		});

		return view;
	}	
}
