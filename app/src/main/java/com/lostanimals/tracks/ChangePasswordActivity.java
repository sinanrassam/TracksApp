package com.lostanimals.tracks;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.lostanimals.tracks.entries.PreferenceEntry;
import com.lostanimals.tracks.utils.PreferencesUtility;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    String oldPass, newPass, newPassConfirm;
    TextView oldPassword, newPassword, newPasswordConfirm;
    Button savePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Change Password");

        getSupportFragmentManager().beginTransaction().replace(R.id.changePasswordContainer, new ChangePasswordFragment()).commit();

        oldPassword = (EditText)findViewById(R.id.old_password);

        newPassword = (EditText)findViewById(R.id.new_password);

        newPasswordConfirm = (EditText)findViewById(R.id.new_password_confirm);

        savePasswordButton = findViewById(R.id.save_changed_password_button);
        savePasswordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        oldPass = this.oldPassword.getText().toString();
        newPass = this.newPassword.getText().toString();
        newPassConfirm = this.newPasswordConfirm.getText().toString();

        if(v.getId() == R.id.save_changed_password_button){
            if(oldPass.isEmpty() || newPass.isEmpty() || newPassConfirm.isEmpty()){
                AlertDialog contactDialog = new AlertDialog.Builder(getApplicationContext()).create();
                contactDialog.setTitle("Error");
                contactDialog.setIcon(R.drawable.ic_edit_black);
                contactDialog.setMessage("Please eneter all text fields.");
                contactDialog.setCancelable(true);
                contactDialog.show();
                /*Toast.makeText(getApplicationContext(), "Please enter all text fields." , Toast.LENGTH_SHORT).show();*/
            }
        }
    }
}
