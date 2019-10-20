package com.lostanimals.tracks;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.lostanimals.tracks.tasks.RegisterTask;
import com.lostanimals.tracks.utils.EmailValidator;

public class RegisterActivity extends AppCompatActivity {

    // UI references.

    public static final String GOOGLE_ACCOUNT = "google_account";
    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mEmailView;
    private EditText mUsernameView;
    private EditText mPasswordView;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Register");

        mFirstNameView = findViewById(R.id.firstName);
        mLastNameView = findViewById(R.id.lastName);
        mEmailView = findViewById(R.id.email);
        mUsernameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);

        //TODO:stinky af but cant figure it out
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            try {
                setDataOnView();
            } catch (NullPointerException e) {
            }

        }


        Button mRegisterBtn = findViewById(R.id.register_btn);

        mRegisterBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
    }

    private void attemptRegister() {

        mFirstNameView.setError(null);
        mLastNameView.setError(null);
        mEmailView.setError(null);
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        String firstName = mFirstNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();
        String email = mEmailView.getText().toString().toLowerCase();
        String username = mUsernameView.getText().toString().toLowerCase();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // validate info
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!(EmailValidator.isValidEmail(email))) {
            mEmailView.setError(getString(R.string.email_incorrect));
            focusView = mEmailView;
            cancel = true;
        }
        if (TextUtils.isEmpty(lastName)) {
            mLastNameView.setError(getString(R.string.error_field_required));
            focusView = mLastNameView;
            cancel = true;
        }
        if (TextUtils.isEmpty(firstName)) {
            mFirstNameView.setError(getString(R.string.error_field_required));
            focusView = mFirstNameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            String name = firstName + " " + lastName;
            RegisterTask registerTask = new RegisterTask(this);
            registerTask.execute(name, email, username, password);
        }
    }

    private void setDataOnView() {
        GoogleSignInAccount googleSignInAccount = getIntent().getParcelableExtra(GOOGLE_ACCOUNT);
        mFirstNameView.setText(googleSignInAccount.getGivenName());
        mLastNameView.setText(googleSignInAccount.getFamilyName());
        mEmailView.setText(googleSignInAccount.getEmail());
    }
}