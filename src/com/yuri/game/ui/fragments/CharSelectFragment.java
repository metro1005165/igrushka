package com.yuri.game.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;

import com.yuri.game.R;

public class CharSelectFragment extends Fragment {
	
	private static final int male_elf = R.drawable.male_elf;
	private static final int orc = R.drawable.orc;
	private static final int undead = R.drawable.undead;
	private static final int gnome = R.drawable.gnome;

	private OnCharSelectedListener charSelectListener;
	private String race;
	
	private OnClickListener btnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			View view = getView();

			if (view != null) {
				Spinner sp = (Spinner) view.findViewById(R.id.gender_spinner);
				String gender = sp.getSelectedItem().toString();
				if (charSelectListener != null) {
					charSelectListener.onCharSelected(race, gender);
				}
			}
		}
	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (activity instanceof OnCharSelectedListener) {
			charSelectListener = (OnCharSelectedListener) activity;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		race = getArguments().getString("RACE");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_char_select, container,
				false);
		ImageView charImage = (ImageView) view.findViewById(R.id.iv_char_select_image);
		
		if (race.equalsIgnoreCase("Elf")) {
			charImage.setBackgroundResource(male_elf);
		} else if (race.equalsIgnoreCase("Orc")) {
			charImage.setBackgroundResource(orc);
		} else if (race.equalsIgnoreCase("Undead")) {
			charImage.setBackgroundResource(undead);
		} else if (race.equalsIgnoreCase("Gnome")) {
			charImage.setBackgroundResource(gnome);
		}
		
		view.findViewById(R.id.btn_select_char).setOnClickListener(btnListener);
		return view;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		charSelectListener = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	public interface OnCharSelectedListener {
		void onCharSelected(String race, String gender);
	}
}
