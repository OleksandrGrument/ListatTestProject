package com.grument.listat_test_project.util;

import android.util.Log;

import com.grument.listat_test_project.data_objects.CalculationResult;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class QueueThread extends Thread {

    public QueueThread() {}

    private final static String QUEUE_THREAD_TAG = "QUEUE_THREAD_TAG";

    private final BlockingQueue<CalculationResult> queue = new LinkedBlockingQueue<>();

    private PipedOutputStream pipedOutputStream = new PipedOutputStream();

    @Override
    public void run() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(pipedOutputStream);

            while (true) {

                if (!queue.isEmpty()) {


                    CalculationResult calculationResult = queue.take();
                    Log.i("QUEUE", calculationResult.toString());
                    objectOutputStream.writeObject(calculationResult);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            // Handle Error
            Log.i(QUEUE_THREAD_TAG, "Error:" + e);
        }
    }

    public void putInQueue(CalculationResult calculationResult) {
        queue.offer(calculationResult);
    }

    public PipedOutputStream getPipedOutputStream() {
        return pipedOutputStream;
    }

}