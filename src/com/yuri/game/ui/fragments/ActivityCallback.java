package com.yuri.game.ui.fragments;

import android.view.View;
import android.widget.AdapterView;

public interface ActivityCallback {
	void onCallback(AdapterView<?> parent, View view,
		    int position, long id);

}
