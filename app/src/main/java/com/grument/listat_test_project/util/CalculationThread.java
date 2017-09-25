package com.grument.listat_test_project.util;

import android.util.Log;

import com.grument.listat_test_project.data_objects.IntervalInfo;
import com.grument.listat_test_project.data_objects.CalculationResult;

import java.util.ArrayList;
import java.util.Random;

public class CalculationThread extends Thread {

    private final static String CALCULATION_THREAD_TAG = "CALCULATION_THREAD_TAG";

    private ArrayList<IntervalInfo> intervalInfoArrayList;
    private int number = 2;
    private Random random = new Random();
    private QueueThread queueThread;

    public CalculationThread(ArrayList<IntervalInfo> intervalInfoList, QueueThread queueThread) {
        this.intervalInfoArrayList = intervalInfoList;
        this.queueThread = queueThread;
    }


    @Override
    public void run() {

        for (IntervalInfo intervalInfo : intervalInfoArrayList) {
            try {

                int delayTime = (random.nextInt(intervalInfo.getHighInterval() - intervalInfo.getLowInterval() + 1) + intervalInfo.getLowInterval());
                sleep(delayTime);

                int somePrimeNumberCalculation = number + random.nextInt(99999);
                CalculationResult calculationResult = new CalculationResult(intervalInfo.getId(), somePrimeNumberCalculation);

                queueThread.putInQueue(calculationResult);

            } catch (InterruptedException e) {
                // Handle Error
                Log.i(CALCULATION_THREAD_TAG, "Error:" + e);
            }
        }
    }

}
