package com.lostanimals.tracks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lostanimals.tracks.utils.PostEntry;
import com.lostanimals.tracks.utils.PostsContent;
import com.lostanimals.tracks.utils.ServerManager;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PostListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "FRAGMENT";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostListFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PostListFragment newInstance(int columnCount) {
        PostListFragment fragment = new PostListFragment();


        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            Log.d("ARGS", "Not null");
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_post_list, container, false);

        ServerManager serverManager = new ServerManager(this.getContext());
        serverManager.execute("get", "5");
        Log.w("FEEDACTONCREATE", "SERVER_MAN_CREATE_SERVER_MAN_CREATE_SERVER_MAN_CREATE");


        // Set the adapter
        if (mView instanceof RecyclerView) {
            Context context = mView.getContext();
            RecyclerView recyclerView = (RecyclerView) mView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new PostRecyclerViewAdapter(PostsContent.ITEMS, mListener));
        }
        return mView;
    }


    @Override
    public void onAttach(Context context) {
        Log.w(TAG, "ATTACHATTACHATTACHATTACHATTACH");


        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        Log.w(TAG, "DETACHDETACHDETACHDETACHDETACHDETACH");
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        Log.w(TAG, "RESUMERESUMERESUMERESUMERESUME");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.w(TAG, "PAUSEPAUSEPAUSEPAUSEPAUSE");
        super.onPause();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        //void onListFragmentInteraction(DummyItem item);
        void onListFragmentInteraction(PostEntry item);
    }
}
