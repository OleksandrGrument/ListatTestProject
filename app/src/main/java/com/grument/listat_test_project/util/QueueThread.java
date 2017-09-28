package com.grument.listat_test_project.util;

import android.util.Log;

import com.grument.listat_test_project.data_objects.CalculationResult;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.LinkedBlockingQueue;



public class QueueThread extends Thread {

    public QueueThread(LinkedBlockingQueue<CalculationResult> queue, StoringThread storingThread) {
        this.queue = queue;
        this.storingThread = storingThread;
    }

    private LinkedBlockingQueue<CalculationResult> queue;

    private StoringThread storingThread;

    private final static String QUEUE_THREAD_TAG = "QUEUE_THREAD_TAG";

    private PipedOutputStream pipedOutputStream = new PipedOutputStream();

    @Override
    public void run() {

        Log.i(QUEUE_THREAD_TAG, "START");

        try {

            Log.i(QUEUE_THREAD_TAG, "GET STREAM");

            Log.i(QUEUE_THREAD_TAG, "TRY TO CONNECT");
            storingThread.connect(this);

            while (true) {


                if (storingThread.isConnected()) {

                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(pipedOutputStream);

                    Log.i(QUEUE_THREAD_TAG, "TRYING TO GET RESULTS");

                    while (storingThread.isConnected() && !storingThread.isClosed()) {
                        try {
                            if (!queue.isEmpty()) {

                                Log.i(QUEUE_THREAD_TAG, "QUEUE NOT EMPTY ");

                                CalculationResult calculationResult = queue.take();

                                Log.i(QUEUE_THREAD_TAG, calculationResult.toString());

                                Log.i(QUEUE_THREAD_TAG, "SENDINDG RESULTS");
                                objectOutputStream.writeObject(calculationResult);

                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            if (!storingThread.isClosed()) storingThread.reconnect(this);
                            else return;
                        }

                    }

                } else {
                    storingThread.connect(this);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    PipedOutputStream getPipedOutputStream() {
        return pipedOutputStream;
    }
}