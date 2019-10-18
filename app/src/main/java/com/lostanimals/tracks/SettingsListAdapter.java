package com.lostanimals.tracks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.lostanimals.tracks.utils.PreferencesUtility;

import java.util.ArrayList;

public class SettingsListAdapter extends BaseAdapter implements ListAdapter, View.OnClickListener {
    private ArrayList<String> list;
    private Context context;

    public SettingsListAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int ignored) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.settings_list_item, null);
        }

        final TextView listItemText = view.findViewById(R.id.list_item_text);
        listItemText.setText(list.get(position));

        final Switch toggle = view.findViewById(R.id.list_item_switch);
        final ImageButton imageButton = view.findViewById(R.id.list_item_button);
        final TextView versionText = view.findViewById(R.id.list_item_version);

        switch (list.get(position)) {
            case "Logout":
                toggle.setOnClickListener(null);
                toggle.setVisibility(View.INVISIBLE);

                versionText.setVisibility(View.INVISIBLE);

                // TODO: Set a proper icon.
                imageButton.setBackgroundResource(android.R.drawable.ic_delete);
                imageButton.setOnClickListener(this);
                break;
            case "Version":
                toggle.setOnClickListener(null);
                toggle.setVisibility(View.INVISIBLE);
                imageButton.setOnClickListener(null);
                imageButton.setVisibility(View.INVISIBLE);

                versionText.setText(R.string.app_version);
                break;
            default:
                switch (list.get(position)) {
                    case "Dark Mode":
                        toggle.setChecked(PreferencesUtility.getUserInfo().isDarkModeEnabled());
                        break;
                    case "Notifications":
                        toggle.setChecked(PreferencesUtility.getUserInfo().isNotificationsEnabled());
                        break;
                }
                imageButton.setOnClickListener(null);
                imageButton.setVisibility(View.INVISIBLE);

                versionText.setVisibility(View.INVISIBLE);

                // TODO: move the onclick stuff into the overridden method.
                toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (list.get(position)) {
                            case "Dark Mode":
                                Toast.makeText(context, "Dark Mode Toggled!", Toast.LENGTH_SHORT).show();
                                PreferencesUtility.setDarkMode(toggle.isEnabled());
                                break;
                            case "Notifications":
                                Toast.makeText(context, "Notifications Toggled!", Toast.LENGTH_SHORT).show();
                                PreferencesUtility.setNotifications(toggle.isEnabled());
                                break;
                        }
                    }
                });
                break;
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context, "LOGOUT!", Toast.LENGTH_SHORT).show();
    }
}