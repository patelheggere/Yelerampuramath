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
import com.yelerampura.math.adapter.CurrentAffairsAdapter;
import com.yelerampura.math.base.BaseFragment;
import com.yelerampura.math.helper.SharedPrefsHelper;
import com.yelerampura.math.models.CurrentAffairsModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.yelerampura.math.helper.AppUtils.Constants.LANGUAGE;

public class MessageFragment extends BaseFragment {
    private static final String TAG = "MessageFragment";
    
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ActionBar mActionBar;
    private RecyclerView recyclerView;
    private ProgressBar mProgressBar;
    private CurrentAffairsAdapter adapter;
    private DatabaseReference databaseReference;
    private List<String> keyList = new ArrayList<>();
    private List<String> newsModelList = new ArrayList<>();
    
    private View mView;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MessageFragment() {
        // Required empty public constructor
    }

    
    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mActionBar = ((MainActivity)mActivity).getSupportActionBar();
        if(mActionBar!=null)
        {
            mActionBar.setTitle(getResources().getString(R.string.title_hitanudi));
        }
        mView =  inflater.inflate(R.layout.fragment_message, container, false);
        initViews();
        return mView;
        
    }

    private void initViews() {
        recyclerView = mView.findViewById(R.id.rv_current_affairs);
        mProgressBar = mView.findViewById(R.id.progress_bar);
        getNews();
    }

    private void getNews()
    {
        mProgressBar.setVisibility(View.VISIBLE);
        String lang = SharedPrefsHelper.getInstance().get(LANGUAGE, "ka");
        databaseReference  = YelarampuraApplication.getFireBaseRef();
        databaseReference = databaseReference.child("message").child(lang).child("hitanudi");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keyList = new ArrayList<>();
                newsModelList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    keyList.add(snapshot.getKey());
                    newsModelList.add(snapshot.getValue(CurrentAffairsModel.class).getMessage());
                }
                Collections.reverse(keyList);
                Collections.reverse(newsModelList);
                adapter = new CurrentAffairsAdapter(mActivity, keyList, newsModelList);
                recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(adapter);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
