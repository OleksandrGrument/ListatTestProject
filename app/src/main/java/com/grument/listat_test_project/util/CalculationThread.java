package com.grument.listat_test_project.util;

import com.annimon.stream.IntStream;
import com.grument.listat_test_project.data_objects.IntervalInfo;
import com.grument.listat_test_project.data_objects.CalculationResult;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;


public class CalculationThread extends Thread {

    private ArrayList<IntervalInfo> intervalInfoArrayList;
    private LinkedBlockingQueue<CalculationResult> queue;

    public CalculationThread(ArrayList<IntervalInfo> intervalInfoList, LinkedBlockingQueue<CalculationResult> queue) {
        this.intervalInfoArrayList = intervalInfoList;
        this.queue = queue;
    }


    @Override
    public void run() {

        for (IntervalInfo intervalInfo : intervalInfoArrayList) {

            int primeNumbersSum =
                    IntStream.range(intervalInfo.getLowInterval(), intervalInfo.getHighInterval())
                            .filter(CalculationThread::isPrimeNumber)
                            .sum();

            CalculationResult calculationResult = new CalculationResult(intervalInfo.getId(), primeNumbersSum);

            queue.add(calculationResult);

        }
    }

    private static boolean isPrimeNumber(int number) {
        return number > 1 &&
                IntStream.rangeClosed(2, (int) Math.sqrt(number))
                        .noneMatch(i -> number % i == 0);
    }

}
