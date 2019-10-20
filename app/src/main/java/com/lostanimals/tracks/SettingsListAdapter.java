package com.lostanimals.tracks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.ArrayList;

public class SettingsListAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> mItemsList;
    private Context mContext;

    public SettingsListAdapter(ArrayList<String> list, Context context) {
        this.mItemsList = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mItemsList.size();
    }

    @Override
    public Object getItem(int pos) {
        return mItemsList.get(pos);
    }

    @Override
    public long getItemId(int ignored) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.settings_list_item, null);
        }

        final TextView listItemText = view.findViewById(R.id.list_item_text);
        final Switch listItemSwitch = view.findViewById(R.id.list_item_switch);
        final ImageButton imageButton = view.findViewById(R.id.list_item_button);
        final TextView appVersionText = view.findViewById(R.id.list_item_version);
        final Button contactButton = view.findViewById(R.id.list_item_contact_button);

        listItemText.setText(mItemsList.get(position));
        contactButton.setText(mItemsList.get(position));
        appVersionText.setText(R.string.app_version);

//        listItemText.setVisibility(View.INVISIBLE);
        listItemSwitch.setVisibility(View.INVISIBLE);
        imageButton.setVisibility(View.INVISIBLE);
        appVersionText.setVisibility(View.INVISIBLE);
        contactButton.setVisibility(View.INVISIBLE);

        listItemSwitch.setOnClickListener(null);
        imageButton.setOnClickListener(null);
        contactButton.setOnClickListener(null);

        switch (mItemsList.get(position)) {
            case "Logout":
                imageButton.setVisibility(View.VISIBLE);
                imageButton.setBackgroundResource(R.drawable.ic_exit_to_app);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog confirmLogoutDialog = new AlertDialog.Builder(mContext).create();
                        confirmLogoutDialog.setTitle("Logout");
                        confirmLogoutDialog.setIcon(R.drawable.ic_exit_to_app);
                        confirmLogoutDialog.setMessage("Are you sure you want to log out?");
                        confirmLogoutDialog.setCancelable(true);
                        confirmLogoutDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface alertDialog, int ignored) {
                                Intent logoutIntent = new Intent(mContext, LogoutActivity.class);
                                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mContext.startActivity(logoutIntent);
                            }
                        });
                        confirmLogoutDialog.setButton(DialogInterface.BUTTON_POSITIVE, "No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface alertDialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                        confirmLogoutDialog.show();
                    }
                });
                break;
            case "Version":
                appVersionText.setVisibility(View.VISIBLE);
                break;
            case "Contact":
                listItemText.setVisibility(View.INVISIBLE);
                contactButton.setVisibility(View.VISIBLE);
                contactButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View ignored) {
                        AlertDialog contactDialog = new AlertDialog.Builder(mContext).create();
                        contactDialog.setTitle("Contact us:");
                        contactDialog.setIcon(R.drawable.ic_mail_outline_black_24dp);
                        contactDialog.setMessage("support@tracksapp.io");
                        contactDialog.setCancelable(true);
                        contactDialog.show();
                    }
                });
                break;
            default:
                switch (mItemsList.get(position)) {
                    case "Dark Mode":
                        listItemSwitch.setChecked(PreferencesUtility.getUserInfo().isDarkModeEnabled());
                        break;
                    case "Notifications":
                        listItemSwitch.setChecked(PreferencesUtility.getUserInfo().isNotificationsEnabled());
                        break;
                }
                listItemSwitch.setVisibility(View.VISIBLE);
                listItemSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View ignored) {
                        switch (mItemsList.get(position)) {
                            case "Dark Mode":
                                Toast.makeText(mContext, "Dark Mode Toggled!", Toast.LENGTH_SHORT).show();
                                PreferencesUtility.setDarkMode(listItemSwitch.isEnabled());
                                break;
                            case "Notifications":
                                Toast.makeText(mContext, "Notifications Toggled!", Toast.LENGTH_SHORT).show();
                                PreferencesUtility.setNotifications(listItemSwitch.isEnabled());
                                break;
                        }
                    }
                });
                break;
        }
        return view;
    }
}