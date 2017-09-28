package com.grument.listat_test_project.view.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grument.listat_test_project.data_objects.CalculationResult;
import com.grument.listat_test_project.data_objects.IntervalInfo;
import com.grument.listat_test_project.R;
import com.grument.listat_test_project.util.CalculationThread;
import com.grument.listat_test_project.util.IntervalXmlParseTask;
import com.grument.listat_test_project.util.QueueThread;
import com.grument.listat_test_project.util.StoringThread;
import com.grument.listat_test_project.view.adapter.RecycleViewCalculationAdapter;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class CalculationViewFragment extends Fragment {

    public final static String CALCULATION_FRAGMENT_TAG = "CALC_FRAGMENT_TAG";

    public CalculationViewFragment() {
        this.setRetainInstance(true);
    }

    private View fragmentView = null;

    private RecyclerView mRecyclerView;

    private static boolean isActive = false;

    public static CalculationViewFragment newInstance() {
        return new CalculationViewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_calculation, container, false);

            mRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.fragment_calculation_recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(new RecycleViewCalculationAdapter(new ArrayList<>(), getActivity()));

            parseXML();

        }
        return fragmentView;
    }


    private void parseXML() {

        try {

            ArrayList<IntervalInfo> intervalInfoList = new ArrayList<>();

            new IntervalXmlParseTask(getActivity(), intervalInfoList, new IntervalXmlParseTask.IntervalXmlParseTaskCallback() {

                @Override
                public void onXmlParseStart() {
                    Log.i(CALCULATION_FRAGMENT_TAG, "onXmlParseStart");
                }

                @Override
                public void onXmlParseEnd() {

                    Log.i(CALCULATION_FRAGMENT_TAG, "onXmlParseEnd");

                    SparseArray<ArrayList<IntervalInfo>> sortedByIdIntervals = new SparseArray<>();


                    for (IntervalInfo intervalInfo : intervalInfoList) {

                        int id = intervalInfo.getId();

                        ArrayList<IntervalInfo> intervalsForSpecificThread = sortedByIdIntervals.get(id, null);

                        if (intervalsForSpecificThread != null) {
                            intervalsForSpecificThread.add(intervalInfo);
                        } else {
                            intervalsForSpecificThread = new ArrayList<>();
                            intervalsForSpecificThread.add(intervalInfo);
                            sortedByIdIntervals.put(id, intervalsForSpecificThread);
                        }
                    }

                    LinkedBlockingQueue<CalculationResult> queue = new LinkedBlockingQueue<>();

                    StoringThread storingThread = new StoringThread(CalculationViewFragment.this);
                    storingThread.start();


                    new QueueThread(queue ,storingThread).start();


                    for (int i = 0; i < sortedByIdIntervals.size(); i++) {

                        new CalculationThread(sortedByIdIntervals.valueAt(i), queue).start();

                    }


                }
            }).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        isActive = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isActive = false;
    }

    public static boolean isActive() {
        return isActive;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

}


