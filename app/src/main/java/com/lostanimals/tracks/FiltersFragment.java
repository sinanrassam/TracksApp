package com.lostanimals.tracks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.lostanimals.tracks.utils.PreferencesUtility;

public class FiltersFragment extends DialogFragment implements View.OnClickListener {
    private CheckBox cb_chipped;
    private CheckBox cb_not_chipped;

    @Override
    public void onClick(View v) {
        Log.d("test", ((CheckBox) v.findViewById(R.id.chipped_checkbox_yes)).isChecked() + "");
        PreferencesUtility.setmMicroYes(cb_chipped.isChecked()? "1" : "0");
    }

    public interface FilterFragmentListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }

    // Have an instance of the listener
    FiltersFragment.FilterFragmentListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.filters_fragment, container, false);
        cb_chipped = view.findViewById(R.id.chipped_checkbox_yes);
        cb_chipped.setOnClickListener(this);
        cb_not_chipped = view.findViewById(R.id.chipped_checkbox_no);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the FilterFragmentListener so we can send events to the host
            listener = (FiltersFragment.FilterFragmentListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.filters_fragment, null))
                .setPositiveButton(R.string.filters_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Filter the posts based on the selection
                        Log.d("ONCLICK_ONCLICK_ONCLICK", "ONCLCIK");

                        Log.d("test", cb_chipped.isChecked() + "");
                        PreferencesUtility.setmMicroYes(cb_chipped.isChecked()? "1" : "0");
                        if (cb_chipped.isChecked()) {
                            Log.d("CHECKCHECKCHECK", "CHECLk");
                            PreferencesUtility.setmMicroYes("'1'");
                        }
                        else {
                            Log.d("NONONONONONO", "No");
                            PreferencesUtility.setmMicroYes("");
                        }

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FeedFragment frag = (FeedFragment)fragmentManager.findFragmentById(R.id.fragment_feed);
                        frag.refresh();
                    }
                });
        return builder.create();
    }
}
