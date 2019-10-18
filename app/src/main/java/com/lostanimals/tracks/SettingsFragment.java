package com.lostanimals.tracks;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.fragment.app.Fragment;


public class SettingsFragment extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_settings, container, false);
        Log.d("SETTINGS", "OnCreate");
        ImageButton logoutButton = view.findViewById(R.id.logOutButton_settings2);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SETTINGS", "CLICK");
                Toast.makeText(getActivity(), "Yes", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    /*public void onClick(View view){

        if(view.getId() == R.id.logOutButton_settings){
            Intent tempIntent = new Intent(getContext(), LogoutActivity.class);
            startActivity(tempIntent);
        }
        /*switch(view.getId()){
            case R.id.logOutButton_settings:
                Intent tempIntent = new Intent(getContext(), LogoutActivity.class);
                startActivity(tempIntent);
                break;
            default:
        }*/
}

    /*@Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preference, rootKey);
        final Preference logoutPref = findPreference(logout);

    }*/

    /*@Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String value = newValue.toString();

        if(preference instanceof PreferenceScreen){

        }

        return false;
    }
    */

    /*Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
        }

        return super.onOptionsItemSelected(item);
    }*/

    /*@Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals(logout)){
            PreferenceScreen logoutPref = findPreference(logout);
            logoutPref.setSummary("PLEASE WORK");
            Log.d("SHRD_PREF", "Bundle+ "+sharedPreferences.toString()+ " String: " + key);
            Toast.makeText(getContext(), "Hellow", Toast.LENGTH_SHORT).show();
            attemptLogout();
        } else if(key.equals(information)) {
            Log.d("BUTTON CLICKED!!!!!!", information+"NFO NFO NFO");
        } else if (key.equals(notif)) {
            Log.d("BUTTON CLICKED!!!!!!", information+"NOTIF NOTIF NOTIF");
        }
    }

    private void attemptLogout() {
        if (PreferencesUtility.removeUserEntry()) {
            Intent logoutIntent = new Intent(getActivity(), LoginActivity.class);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().finish();
            getActivity().startActivity(logoutIntent);
            Toast.makeText(getActivity(), "Logout Successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Logout Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }*/

