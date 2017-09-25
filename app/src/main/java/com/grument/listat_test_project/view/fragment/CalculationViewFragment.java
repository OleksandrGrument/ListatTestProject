package com.grument.listat_test_project.view.fragment;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grument.listat_test_project.data_objects.IntervalInfo;
import com.grument.listat_test_project.util.IntervalXMLHandler;
import com.grument.listat_test_project.R;
import com.grument.listat_test_project.util.CalculationThread;
import com.grument.listat_test_project.util.QueueThread;
import com.grument.listat_test_project.util.StoringThread;
import com.grument.listat_test_project.view.adapter.RecycleViewCalculationAdapter;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class CalculationViewFragment extends Fragment {

    public final static String CALCULATION_FRAGMENT_TAG = "CALCULATION_FRAGMENT_TAG";

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

        AssetManager assetManager = getActivity().getBaseContext().getAssets();
        try {

            InputStream inputStream = assetManager.open("stream_intervals.xml");
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();

            IntervalXMLHandler myXMLHandler = new IntervalXMLHandler();
            xmlReader.setContentHandler(myXMLHandler);
            InputSource inputSource = new InputSource(inputStream);
            xmlReader.parse(inputSource);
            inputStream.close();

            ArrayList<IntervalInfo> intervalInfoList = myXMLHandler.getIntervalInfos();


            ArrayList<IntervalInfo> intervalsForFirstThread = new ArrayList<>();
            ArrayList<IntervalInfo> intervalsForSecondThread = new ArrayList<>();

            for (IntervalInfo intervalInfo : intervalInfoList) {
                if (intervalInfo.getId() == 1) intervalsForFirstThread.add(intervalInfo);
                else intervalsForSecondThread.add(intervalInfo);
            }

            QueueThread queueThread = new QueueThread();
            queueThread.start();

            StoringThread storingThread = new StoringThread(this);
            storingThread.connect(queueThread.getPipedOutputStream());
            storingThread.start();

            CalculationThread firstCalculationThread = new CalculationThread(intervalsForFirstThread, queueThread);
            firstCalculationThread.start();

            CalculationThread secondCalculationThread = new CalculationThread(intervalsForSecondThread, queueThread);
            secondCalculationThread.start();


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

    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }

}


