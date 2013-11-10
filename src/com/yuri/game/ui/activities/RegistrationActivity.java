package com.yuri.game.ui.activities;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yuri.game.GameApplication;
import com.yuri.game.R;
import com.yuri.game.context.ControllerContext;
import com.yuri.game.controller.listeners.RegistrationListener;
import com.yuri.game.controller.listeners.SystemErrorListener;
import com.yuri.game.network.ServerRequestFormer;

public class RegistrationActivity extends Activity implements
		RegistrationListener, SystemErrorListener {

	private String userName;
	private String password;
	private String mail;
	private ControllerContext controllers;
	private GameApplication gameApplication;

	private EditText userNameView;
	private EditText passwordView;
	private EditText emailView;
	private Button btnRegister;
	private TextView lnkLoginView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);

		gameApplication = GameApplication.getApplicationFromActivity(this);
		this.controllers = gameApplication.controllers;

		controllers.registrationController.registrationListeners.add(this);
		controllers.systemErrorController.systemErrorListeners.add(this);

		btnRegister = (Button) findViewById(R.id.btnRegister);
		lnkLoginView = (TextView) findViewById(R.id.lnkLogin);
		userNameView = (EditText) findViewById(R.id.txtUserName);
		passwordView = (EditText) findViewById(R.id.txtPassword);
		emailView = (EditText) findViewById(R.id.txtMail);

		btnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptRegister();
			}
		});

		lnkLoginView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getApplicationContext(),
						LoginActivity.class));
				RegistrationActivity.this.finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		controllers.registrationController.registrationListeners.remove(this);
		controllers.systemErrorController.systemErrorListeners.remove(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
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
	public void onSuccessfulRegistration() {
		startActivity(new Intent(getApplicationContext(),
				CharSelectionActivity.class));
		RegistrationActivity.this.finish();

	}

	@Override
	public void onUsernameExists() {
		if (!isFinishing()) {
			showSystemErrorDialog(R.string.username_exists);
		}
	}

	@Override
	public void onEmailExists() {
		if (!isFinishing()) {
			showSystemErrorDialog(R.string.email_exists);
		}
	}

	private void attemptRegister() {

		// Reset errors.
		userNameView.setError(null);
		passwordView.setError(null);
		emailView.setError(null);

		// Store values at the time of the login attempt.
		userName = userNameView.getText().toString();
		password = passwordView.getText().toString();
		mail = emailView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid login.
		if (TextUtils.isEmpty(userName)) {
			userNameView.setError(getString(R.string.error_field_required));
			focusView = userNameView;
			cancel = true;
		}

		// Check for a valid password.
		if (TextUtils.isEmpty(password)) {
			passwordView.setError(getString(R.string.error_field_required));
			focusView = passwordView;
			cancel = true;
		} else if (password.length() < 4) {
			passwordView.setError(getString(R.string.error_invalid_password));
			focusView = passwordView;
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
							.sendRequest(new ServerRequestFormer().registerNewPlayer(userName, password, mail));
				} else {
					controllers.networkController.connectToServer();
					controllers.networkController
							.startPendingRequest(new ServerRequestFormer().
							registerNewPlayer(userName, password, mail));
					controllers.networkController
							.startListeningToServer();
				}
			} else {
				showSystemErrorDialog(R.string.failed_to_open_socket);
			}
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

		Log.e("showSystemErrorDialog() Reg", "inside");
		Dialog d = new AlertDialog.Builder(this)
				.setTitle(R.string.system_error_message)
				.setMessage(messageResourceID)
				.setNeutralButton(android.R.string.ok, null).create();
		d.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				// LoginActivity.this.finish();
			}
		});
		d.show();
	}
}
