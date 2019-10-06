package com.lostanimals.tracks;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.lostanimals.tracks.tasks.LoginTask;
import com.lostanimals.tracks.utils.NotificationUtility;
import com.lostanimals.tracks.utils.PreferencesUtility;
import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 *
 */
public class LoginActivity extends AppCompatActivity {
	private final static String TAG = "LOGIN_ACTIVITY";
	
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;
	private SignInButton googleSignInButton;
	private Intent feedIntent;
	private Intent registerIntent;
	private GoogleSignInClient googleSignInClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("Login");
		
		feedIntent = new Intent(this, FeedActivity.class);
		feedIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		registerIntent = new Intent(this, RegisterActivity.class);
		registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, feedIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationUtility.createNotification(this, "Tracks", "Test", true, pendingIntent);
		PreferencesUtility.setSharedPreferences(this);
		
		if (!PreferencesUtility.getUserInfo().getUsername().equals("")) {
			startActivity(feedIntent);
			this.finish();
		}
		
		setContentView(R.layout.activity_login);
		
		// Set up the login form.
		mEmailView = findViewById(R.id.login_username);
		mPasswordView = findViewById(R.id.login_password);
		googleSignInButton = findViewById(R.id.sign_in_button);
		Button mSignInBtn = findViewById(R.id.login_btn);
		mSignInBtn.setOnClickListener(new OnClickListener() {
			/**
			 *
			 * @param view
			 */
			@Override
			public void onClick(View view) {
				// TODO: Fix this
				try {
					if (isOnline() == false) {
						Log.d("LOGIN_TASK", "internet avaliable");
						attemptLogin();
					} else {
						attemptLogin();
						Log.d("LOGIN_TASK", "no internet");
					}
				} catch (ExecutionException | InterruptedException | JSONException e) {
					e.printStackTrace();
				}
			}
		});

		googleSignInButton = findViewById(R.id.sign_in_button);
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestEmail()
				.build();

		googleSignInClient = GoogleSignIn.getClient(this, gso);
		googleSignInButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent signInIntent = googleSignInClient.getSignInIntent();
				startActivityForResult(signInIntent, 101);
			}
		});

		Button mRegisterBtn = findViewById(R.id.login_register_btn);
		mRegisterBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(registerIntent);
				finish();
			}
		});
	}
	
	// TODO: Fix this

	/**
	 * @return
	 */
	public boolean isOnline() {
		Runtime runtime = Runtime.getRuntime();
		try {
			Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
			int exitValue = ipProcess.waitFor();
			return (exitValue == 0);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * @throws ExecutionException
	 * @throws InterruptedException
	 * @throws JSONException
	 */
	private void attemptLogin() throws ExecutionException, InterruptedException, JSONException {
		mEmailView.setError(null);
		mPasswordView.setError(null);
		
		// Store values at the time of the login attempt.
		String email = mEmailView.getText().toString().toLowerCase();
		String password = mPasswordView.getText().toString();
		
		boolean cancel = false;
		View focusView = null;
		
		// Check for a valid password, if the user entered one.
		if (TextUtils.isEmpty(password)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		}
		
		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		}
		
		if (cancel) {
			focusView.requestFocus();
		} else {
			LoginTask loginTask = new LoginTask(this);
			loginTask.execute(email, password);
			if (loginTask.get().get("response").equals("successful")) {
				NotificationUtility.displayNotification(0);
				
				startActivity(feedIntent);
				finish();
			}
		}
	}
}