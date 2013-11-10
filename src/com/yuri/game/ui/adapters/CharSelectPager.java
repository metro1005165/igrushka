package com.yuri.game.ui.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yuri.game.ui.fragments.CharSelectFragment;

public class CharSelectPager extends FragmentPagerAdapter {
	
	private final int COUNT = 4;
	//private final FragmentManager fm;

	public CharSelectPager(FragmentManager fm) {
		super(fm);
		//this.fm = fm;
	}

	@Override
	public Fragment getItem(int position) {
		
		Fragment fragment = new CharSelectFragment();
		Bundle args = new Bundle();
		
		switch (position) {
		case 0:
			args.putString("RACE", "Elf");
			break;
		case 1:
			args.putString("RACE", "Orc");
			break;
		case 2:
			args.putString("RACE", "Undead");
			break;
		case 3:
			args.putString("RACE", "Gnome");
			break;
		}
		
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		return COUNT;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return "Elf";
		case 1:
			return "Orc";
		case 2:
			return "Undead";
		case 3:
			return "Gnome";
		}
		return null;
	}
}
