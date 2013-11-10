package com.yuri.game.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.yuri.game.R;
import com.yuri.game.ui.adapters.LocationsList;

public class LocationsFragment extends Fragment {

	public LocationsList adapter;
	
	private ActivityCallback callback;

	//private GameApplication app;
	
	private OnItemClickListener listClick = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
			if (callback != null) {
				callback.onCallback(parent, view, position, id);
			}
		}
	};

	public LocationsFragment() {

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		//app = GameApplication.getApplicationFromActivity(activity);
		adapter = new LocationsList(activity);
		callback = (ActivityCallback) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_locations, container,
				false);
		ListView lvLocations = (ListView) view.findViewById(R.id.lv_locations);
		lvLocations.setAdapter(adapter);
		lvLocations.setOnItemClickListener(listClick);

		return view;
	}
}
