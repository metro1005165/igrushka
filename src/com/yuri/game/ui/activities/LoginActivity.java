package com.yuri.game.ui.activities;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yuri.game.GameApplication;
import com.yuri.game.R;
import com.yuri.game.context.ControllerContext;
import com.yuri.game.controller.listeners.LoginListener;
import com.yuri.game.controller.listeners.SystemErrorListener;
import com.yuri.game.model.duel.DuelRequest;
import com.yuri.game.network.ServerRequestFormer;

public class LoginActivity extends Activity implements LoginListener, SystemErrorListener {

	private String login;
	private String pwd;
	private ControllerContext controllers;
	private GameApplication gameApplication;

	// UI references.
	private EditText loginView;
	private EditText pwdView;
	private TextView lnkRegisterView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		
		gameApplication = GameApplication.getApplicationFromActivityContext(this);

        this.controllers = gameApplication.controllers;
        controllers.systemErrorController.systemErrorListeners.add(this);
        controllers.loginController.loginListeners.add(this);

		// Set up the login form.
		loginView = (EditText) findViewById(R.id.login_name);
		pwdView = (EditText) findViewById(R.id.txtPassword);
		lnkRegisterView = (TextView) findViewById(R.id.lnkRegister);
		
		findViewById(R.id.btnLogin).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
		
		lnkRegisterView.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
						LoginActivity.this.finish();
					}
				});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		controllers.systemErrorController.systemErrorListeners.remove(this);
        controllers.loginController.loginListeners.remove(this);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		if (item.getItemId() == R.id.action_close_socket) {
			controllers.networkController.closeConnection();
			Toast.makeText(this, "Connection is closed!", Toast.LENGTH_LONG).show();
		} else if (item.getItemId() == R.id.action_show) {
			List<DuelRequest> duels = gameApplication.world.modelContainer.location.getDuelRequestsList();
			StringBuilder b = new StringBuilder();
			for (DuelRequest p: duels) {
				b.append(p.getOwner() + "\n");
			}
			Toast.makeText(this, b.toString(), Toast.LENGTH_LONG).show();
		}

		return super.onMenuItemSelected(featureId, item);
	}

	private void attemptLogin() {

		// Reset errors.
		loginView.setError(null);
		pwdView.setError(null);

		// Store values at the time of the login attempt.
		login = loginView.getText().toString();
		pwd = pwdView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid login.
		if (TextUtils.isEmpty(login)) {
			loginView.setError(getString(R.string.error_field_required));
			focusView = loginView;
			cancel = true;
		}

		// Check for a valid password.
		if (TextUtils.isEmpty(pwd)) {
			pwdView.setError(getString(R.string.error_field_required));
			focusView = pwdView;
			cancel = true;
		} else if (pwd.length() < 4) {
			pwdView.setError(getString(R.string.error_invalid_password));
			focusView = pwdView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			if (isOnline()) {
				if (gameApplication.clientSocket.connected) {
					controllers.networkController
							.sendRequest(new ServerRequestFormer().loginPlayer(
									login, pwd));
				} else {
					controllers.networkController.connectToServer();
					controllers.networkController
							.startPendingRequest(new ServerRequestFormer()
									.loginPlayer(login, pwd));
					controllers.networkController
							.startListeningToServer();
					Log.e("attemptLogin", "Launched 3 threads");
				}
			} else {
				showSystemErrorDialog(R.string.failed_to_open_socket);
			}
		}
	}

	@Override
	public void onLoginSuccess() {
		startActivity(new Intent(getApplicationContext(), MainActivity.class));
		LoginActivity.this.finish();
	}

	@Override
	public void onSocketTerminated() {
		if (!isFinishing()) {
			showSystemErrorDialog(R.string.socket_is_closed_by_server);
		}
	}

	@Override
	public void onWrongRequestStructure() {		
		if (!isFinishing()) {
			showSystemErrorDialog(R.string.wrong_request_structure);
		}
	}

	@Override
	public void onWrongCommandOrder() {
		if (!isFinishing()) {
			showSystemErrorDialog(R.string.wrong_command_order);
		}		
	}

	@Override
	public void onMissingParameters() {
		if (!isFinishing()) {
			showSystemErrorDialog(R.string.params_are_missing);
		}	
	}

	@Override
	public void onParameterNotFound() {
		if (!isFinishing()) {
			showSystemErrorDialog(R.string.param_not_found);
		}		
	}	
	
	public void showSystemErrorDialog(int messageResourceID) {
		Log.e("showSystemErrorDialog() Log", "inside");
		Dialog d = new AlertDialog.Builder(this)
				.setTitle(R.string.system_error_message)
				.setMessage(messageResourceID)
				.setNeutralButton(android.R.string.ok, null).create();
		d.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				//LoginActivity.this.finish();
			}
		});
		d.show();
	}

	@Override
	public void onLoginFail() {
		if (!isFinishing()) {
			showSystemErrorDialog(R.string.error_incorrect_password);
		}
	}
	
	// Checks the network state
	public boolean isOnline() {

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}

		return false;
	}
}