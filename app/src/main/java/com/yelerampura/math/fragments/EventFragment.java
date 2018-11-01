package com.yelerampura.math.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yelerampura.math.R;
import com.yelerampura.math.YelarampuraApplication;
import com.yelerampura.math.activity.MainActivity;
import com.yelerampura.math.adapter.EventDetailsAdapter;
import com.yelerampura.math.base.BaseFragment;
import com.yelerampura.math.helper.AppUtils;
import com.yelerampura.math.helper.SharedPrefsHelper;
import com.yelerampura.math.models.EventDetailsModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.yelerampura.math.helper.AppUtils.Constants.EVENTS;
import static com.yelerampura.math.helper.AppUtils.Constants.GALLERY;
import static com.yelerampura.math.helper.AppUtils.Constants.LANGUAGE;


public class EventFragment extends BaseFragment {

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View mView;
    private ActionBar mActionBar;
    private RecyclerView recyclerViewEvents;
    private ProgressBar mProgressBar;
    private DatabaseReference databaseReference;
    private List<EventDetailsModel> eventsModelList;

    private OnFragmentInteractionListener mListener;

    public EventFragment() {
        // Required empty public constructor
    }

    public static EventFragment newInstance(String param1, String param2) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        /*args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           /* mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mActionBar = ((MainActivity)mActivity).getSupportActionBar();
        if(mActionBar!=null)
        {
            mActionBar.setTitle(getResources().getString(R.string.title_events));
        }
        mView =  inflater.inflate(R.layout.fragment_event, container, false);
        initViews();
        return mView;
    }

    private void initViews()
    {
        recyclerViewEvents = mView.findViewById(R.id.rv_events);
        mProgressBar = mView.findViewById(R.id.progress_bar);
        getEvents();
    }

    private void getEvents() {
        mProgressBar.setVisibility(View.VISIBLE);
        String lang = SharedPrefsHelper.getInstance().get(LANGUAGE, "en");
        databaseReference  = YelarampuraApplication.getFireBaseRef();
        databaseReference = databaseReference.child(EVENTS).child(lang);
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventsModelList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    EventDetailsModel ob = new EventDetailsModel();
                    ob = snapshot.getValue(EventDetailsModel.class);
                    eventsModelList.add(ob);
                }
                Collections.reverse(eventsModelList);
                recyclerViewEvents.setAdapter(new EventDetailsAdapter(mActivity,eventsModelList));
                recyclerViewEvents.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mProgressBar.setVisibility(View.GONE);
                AppUtils.showSnackBar(mActivity, getString(R.string.some_thing_wrong));
            }
        });
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
