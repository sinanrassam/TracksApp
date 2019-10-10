package com.lostanimals.tracks;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class FilterHandlerFragment extends DialogFragment {
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialogFragment);
        public void onDialogNegativeClick(DialogFragment dialogFragment);
    }

    NoticeDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (NoticeDialogListener)context;
        }
        catch (ClassCastException e) {
            Log.e("FILTERHANDLERFRAGMENT_FILTERHANDLERFRAGMENT_FILTERHANDLERFRAGMENT", e.getStackTrace().toString());
        }
    }
}
