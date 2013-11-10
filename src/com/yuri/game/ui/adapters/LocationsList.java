package com.yuri.game.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuri.game.R;

public class LocationsList extends BaseAdapter {
	
	private final int TRAINING_ROOM_ICON = R.drawable.training_room;
	private final int CASTLE_ICON = R.drawable.castle;
	private final int BARRACKS_ICON = R.drawable.barracks;
	
	private final int TOTAL = 3;
	//private final Context context;
	private LayoutInflater inflater;
	private ViewHolder holder;
	
	public LocationsList(Context context) {
		//this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
	}
	
	static class ViewHolder {
    	TextView tvLocName;
    	ImageView ivLocIcon;
    }

	@Override
	public int getCount() {
		return TOTAL;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {

			// inflate the layout
			convertView = inflater.inflate(R.layout.list_locations,
					parent, false);

			// well set up the ViewHolder
			holder = new ViewHolder();
			holder.tvLocName = (TextView) convertView.findViewById(R.id.tv_location_name);
			holder.ivLocIcon = (ImageView) convertView.findViewById(R.id.iv_location_icon);

			// store the holder with the view.
			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();
		}
		
		switch (position) {
		case 0:
			holder.ivLocIcon.setBackgroundResource(TRAINING_ROOM_ICON);
			holder.tvLocName.setText("Training Room");
			break;
		case 1:
			holder.ivLocIcon.setBackgroundResource(CASTLE_ICON);
			holder.tvLocName.setText("Castle");	
			break;
		case 2:
			holder.ivLocIcon.setBackgroundResource(BARRACKS_ICON);
			holder.tvLocName.setText("Barracks");
			break;
		}
		
		return convertView;
	}
}
