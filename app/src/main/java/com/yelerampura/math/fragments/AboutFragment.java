package com.yelerampura.math.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.yelerampura.math.R;
import com.yelerampura.math.activity.MainActivity;
import com.yelerampura.math.adapter.CustomExpandableListAdapter;
import com.yelerampura.math.base.BaseFragment;
import com.yelerampura.math.models.UserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AboutFragment extends BaseFragment{

    private OnFragmentInteractionListener mListener;
    private List<String> expandableListTitle;
    private HashMap<String, String> expandableListDetail2;
    private ExpandableListView expandableListView;
    private int lastExpandedGroupPosition;
    private View mView;
    private CustomExpandableListAdapter expandableListAdapter;
    CustomExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<UserDetails>> listDataChild;
    private ActionBar mActionBar;


    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance(String param1, String param2) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
       /* args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          /*  mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
        //initFirestore();
    }

    private void initFirestore() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("NAME", "Chanakya");
        hashMap.put("EMAIL", "chanakya@gmail.com");
        String phone = "9611620128";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mActionBar = ((MainActivity)mActivity).getSupportActionBar();
        if(mActionBar!=null)
        {
            mActionBar.setTitle(getResources().getString(R.string.about));
        }
        mView = inflater.inflate(R.layout.fragment_about, container, false);
        expandableListTitle = new ArrayList<String>();
        expandableListTitle.add("PDO");
        expandableListTitle.add("KAS");
        expandableListTitle.add("PDO");
        expandableListTitle.add("KAS");
        expandableListDetail2 = new HashMap<String, String>();
        expandableListDetail2.put("PDO","PDO");
        expandableListDetail2.put("KAS","PDO");
        expandableListDetail2.put("FDA","PDO");
        expandableListDetail2.put("SDA","PDO");
        expListView = mView.findViewById(R.id.expandableListView);

        //expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        prepareListData();

        listAdapter = new CustomExpandableListAdapter(mActivity, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != lastExpandedGroupPosition){
                    expListView.collapseGroup(lastExpandedGroupPosition);
                }
                lastExpandedGroupPosition = groupPosition;
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                return false;
            }
        });
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<UserDetails>>();

        listDataHeader.add("KAS");
        listDataHeader.add("PDO");
        listDataHeader.add("BANKING");
        listDataHeader.add("FDA");
        listDataHeader.add("SDA");
        listDataHeader.add("PSI");
        listDataHeader.add("NEET");
        listDataHeader.add("POLICE");
        listDataHeader.add("CET");
        listDataHeader.add("TET");
        listDataHeader.add("KES");

       /* CourseModel kas = new CourseModel();
        kas.setmEligibilty(getString(R.string.kas_elig));
        kas.setmAgeAttempts(getString(R.string.kas_attempts));
        kas.setPart1(getString(R.string.kas_syllabus));

        List<CourseModel> KAS = new ArrayList<CourseModel>();
        KAS.add(kas);


        CourseModel pdo = new CourseModel();
        pdo.setmEligibilty(getString(R.string.kas_elig));
        pdo.setmAgeAttempts(getString(R.string.kas_attempts));
        pdo.setPart1(getString(R.string.kas_syllabus));

        List<CourseModel> PDO = new ArrayList<CourseModel>();
        PDO.add(pdo);

        CourseModel banking = new CourseModel();
        banking.setmEligibilty(getString(R.string.kas_elig));
        banking.setmAgeAttempts(getString(R.string.kas_attempts));
        banking.setPart1(getString(R.string.kas_syllabus));

        List<CourseModel> BANKING = new ArrayList<CourseModel>();
        BANKING.add(banking);

        CourseModel fda = new CourseModel();
        fda.setmEligibilty(getString(R.string.kas_elig));
        fda.setmAgeAttempts(getString(R.string.kas_attempts));
        fda.setPart1(getString(R.string.kas_syllabus));

        List<CourseModel> FDA = new ArrayList<CourseModel>();
        FDA.add(fda);

        CourseModel sda = new CourseModel();
        sda.setmEligibilty(getString(R.string.kas_elig));
        sda.setmAgeAttempts(getString(R.string.kas_attempts));
        sda.setPart1(getString(R.string.kas_syllabus));

        List<CourseModel> SDA = new ArrayList<CourseModel>();
        SDA.add(sda);

        CourseModel psi = new CourseModel();
        psi.setmEligibilty(getString(R.string.kas_elig));
        psi.setmAgeAttempts(getString(R.string.kas_attempts));
        psi.setPart1(getString(R.string.kas_syllabus));

        List<CourseModel> PSI = new ArrayList<CourseModel>();
        PSI.add(psi);

        CourseModel neet = new CourseModel();
        neet.setmEligibilty(getString(R.string.kas_elig));
        neet.setmAgeAttempts(getString(R.string.kas_attempts));
        neet.setPart1(getString(R.string.kas_syllabus));

        List<CourseModel> NEET = new ArrayList<CourseModel>();
        NEET.add(neet);


        CourseModel police = new CourseModel();
        police.setmEligibilty(getString(R.string.kas_elig));
        police.setmAgeAttempts(getString(R.string.kas_attempts));
        police.setPart1(getString(R.string.kas_syllabus));

        List<CourseModel> POLICE = new ArrayList<CourseModel>();
        POLICE.add(police);

        CourseModel cet = new CourseModel();
        cet.setmEligibilty(getString(R.string.kas_elig));
        cet.setmAgeAttempts(getString(R.string.kas_attempts));
        cet.setPart1(getString(R.string.kas_syllabus));

        List<CourseModel> CET = new ArrayList<CourseModel>();
        CET.add(cet);


        CourseModel kes = new CourseModel();
        kes.setmEligibilty(getString(R.string.kas_elig));
        kes.setmAgeAttempts(getString(R.string.kas_attempts));
        kes.setPart1(getString(R.string.kas_syllabus));

        List<CourseModel> KES = new ArrayList<CourseModel>();
        KES.add(kes);

        CourseModel tet = new CourseModel();
        tet.setmEligibilty(getString(R.string.kas_elig));
        tet.setmAgeAttempts(getString(R.string.kas_attempts));
        tet.setPart1(getString(R.string.kas_syllabus));

        List<CourseModel> TET = new ArrayList<CourseModel>();
        TET.add(tet);

        listDataChild.put(listDataHeader.get(0), KAS); // Header, Child data
        listDataChild.put(listDataHeader.get(1), PDO); // Header, Child data
        listDataChild.put(listDataHeader.get(2), BANKING); // Header, Child data
        listDataChild.put(listDataHeader.get(3), FDA); // Header, Child data
        listDataChild.put(listDataHeader.get(4), SDA); // Header, Child data
        listDataChild.put(listDataHeader.get(5), PSI); // Header, Child data
        listDataChild.put(listDataHeader.get(6), NEET); // Header, Child data
        listDataChild.put(listDataHeader.get(7), POLICE); // Header, Child data
        listDataChild.put(listDataHeader.get(8), CET); // Header, Child data
        listDataChild.put(listDataHeader.get(9), TET); // Header, Child data
        listDataChild.put(listDataHeader.get(10), KES); // Header, Child data*/
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
