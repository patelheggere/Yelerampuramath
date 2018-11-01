package com.yelerampura.math.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
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
import com.yelerampura.math.adapter.SlidingImage_Adapter;
import com.yelerampura.math.base.BaseFragment;
import com.yelerampura.math.helper.AppUtils;
import com.yelerampura.math.helper.SharedPrefsHelper;
import com.yelerampura.math.models.GalleryModel;

import java.util.ArrayList;
import java.util.List;

import static com.yelerampura.math.helper.AppUtils.Constants.GALLERY;
import static com.yelerampura.math.helper.AppUtils.Constants.LANGUAGE;

public class GalleryFragment extends BaseFragment {

    private static final String TAG = "GalleryFragment";
    private OnFragmentInteractionListener mListener;
    private View mView;
    private ActionBar mActionBar;
    private ViewPager mPager;
    private ProgressBar mProgressBar;
    private List<GalleryModel> galleryModelList;
    private DatabaseReference databaseReference;
    public GalleryFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          //  mParam1 = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mActionBar = ((MainActivity)mActivity).getSupportActionBar();
        if(mActionBar!=null)
        {
            mActionBar.setTitle(getResources().getString(R.string.gallery));
        }
        mView =  inflater.inflate(R.layout.fragment_gallery, container, false);
        initViews();
        return mView;
    }

    private void initViews() {

        mPager = mView.findViewById(R.id.image_pager);
        mProgressBar = mView.findViewById(R.id.progress_bar);
        getImageData();
    }

    private void getImageData() {
        mProgressBar.setVisibility(View.VISIBLE);
        String lang = SharedPrefsHelper.getInstance().get(LANGUAGE, "kn");
        databaseReference  = YelarampuraApplication.getFireBaseRef();
        databaseReference = databaseReference.child(GALLERY).child(lang);
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                galleryModelList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    GalleryModel ob = new GalleryModel();
                    ob = snapshot.getValue(GalleryModel.class);
                    galleryModelList.add(ob);
                }
                mPager.setAdapter(new SlidingImage_Adapter(mActivity,galleryModelList));
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mProgressBar.setVisibility(View.GONE);
                AppUtils.showSnackBar(mActivity,getString(R.string.some_thing_wrong));
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
