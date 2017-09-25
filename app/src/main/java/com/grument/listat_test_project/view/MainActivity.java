package com.grument.listat_test_project.view;


import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.FrameLayout;


import com.grument.listat_test_project.R;
import com.grument.listat_test_project.view.fragment.CalculationViewFragment;


public class MainActivity extends AppCompatActivity {


    private static final String MAIN_ACTIVITY_TAG = "MAIN_ACTIVITY_TAG";

    private static String CALCULATION_FRAGMENT_INSTANCE_NAME = "CALCULATION_FRAGMENT";

    private CalculationViewFragment calculationViewFragment = null;

    private FragmentManager fragmentManager;

    private FrameLayout calculationViewFragmentContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();

        calculationViewFragment = (CalculationViewFragment) fragmentManager.findFragmentByTag(CALCULATION_FRAGMENT_INSTANCE_NAME);

        calculationViewFragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);

        if (calculationViewFragment == null) {

            calculationViewFragment = CalculationViewFragment.newInstance();

            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, calculationViewFragment, CALCULATION_FRAGMENT_INSTANCE_NAME)
                    .commit();
        }

    }

}


