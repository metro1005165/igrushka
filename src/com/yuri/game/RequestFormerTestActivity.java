package com.yuri.game;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

import com.yuri.game.context.ControllerContext;
import com.yuri.game.model.actor.LocationType;
import com.yuri.game.model.duel.AttackType;
import com.yuri.game.model.duel.BlockType;
import com.yuri.game.network.ServerRequestFormer;

public class RequestFormerTestActivity extends Activity {
	
	private Spinner sp;
	private Button btn;
	private GameApplication app;
	private ControllerContext controllers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_former_test);
		
		app = GameApplication.getApplicationFromActivityContext(this);
		controllers = app.controllers;
		
		sp = (Spinner) findViewById(R.id.sp_request_former_test);
		btn = (Button) findViewById(R.id.btn_test_request_former);
		btn.setOnClickListener(listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.request_former_test, menu);
		return true;
	}
	
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String selection = sp.getSelectedItem().toString();
			int position = sp.getSelectedItemPosition();
			
			Log.e("onClick", "Selected Item: " + selection + ", Position: " + position);
			
			switch (position) {
			case 0:
				controllers.networkController
						.sendRequest(new ServerRequestFormer().getPlayerChar());
				break;
			case 1:
				controllers.networkController
						.sendRequest(new ServerRequestFormer()
								.getAnyChar("my name"));
				break;
			case 2:
				controllers.networkController
						.sendRequest(new ServerRequestFormer()
								.movePlayerTo(LocationType.TRAININGROOM));
				break;
			case 3:
				controllers.networkController
						.sendRequest(new ServerRequestFormer()
								.getDuelRequestList());
				break;
			case 4:
				controllers.networkController
						.sendRequest(new ServerRequestFormer()
								.registerDuelRequest());
				break;
			case 5:
				controllers.networkController
						.sendRequest(new ServerRequestFormer()
								.removeDuelRequest());
				break;
			case 6:
				controllers.networkController
						.sendRequest(new ServerRequestFormer()
								.removeFromDuelRequest());
				break;
			case 7:
				controllers.networkController
						.sendRequest(new ServerRequestFormer()
								.addPlayerToDuelRequest("artemka81"));
				break;
			case 8:
				controllers.networkController
						.sendRequest(new ServerRequestFormer().fightAttack(
								AttackType.ABDOMEN, BlockType.HEAD,
								BlockType.LEG));
				break;
			}
		}
	};

}
