package com.grument.listat_test_project.util;

import android.os.Environment;
import android.util.Log;

import com.grument.listat_test_project.data_objects.CalculationResult;
import com.grument.listat_test_project.view.adapter.RecycleViewCalculationAdapter;
import com.grument.listat_test_project.view.fragment.CalculationViewFragment;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;

import java.util.ArrayList;


public class StoringThread extends Thread {

    public StoringThread(CalculationViewFragment calculationViewFragment) {
        this.calculationViewFragment = calculationViewFragment;
    }

    private final static String STORING_THREAD_TAG = "STORING_THREAD_TAG";

    private OutputStreamWriter outPutStreamWriter;

    private File fileToWrite = null;

    private PipedInputStream pipedInputStream = new PipedInputStream();

    private ObjectInputStream objectInputStream;

    private CalculationViewFragment calculationViewFragment;

    private volatile boolean closed = false;

    private volatile boolean connected = false;


    @Override
    public void run() {

        Log.i(STORING_THREAD_TAG, "RUN");

        RecycleViewCalculationAdapter adapter = (RecycleViewCalculationAdapter) calculationViewFragment.getRecyclerView().getAdapter();


        try {

            if (fileToWrite != null) {
                fileToWrite.createNewFile();
                FileOutputStream outPutStream = new FileOutputStream(fileToWrite);
                outPutStreamWriter = new OutputStreamWriter(outPutStream);
            }

            while (true) {

                if (isConnected()) {

                    objectInputStream = new ObjectInputStream(pipedInputStream);

                    while (isConnected() && !isClosed()) {

                        CalculationResult calculationResult = (CalculationResult) objectInputStream.readObject();
                        Log.i(STORING_THREAD_TAG, calculationResult.toString());

                        if (fileToWrite != null) {
                            outPutStreamWriter.append(calculationResult.toString());
                        }

                        if (CalculationViewFragment.isActive()) {
                            calculationViewFragment.getActivity().runOnUiThread(() -> adapter.swap(calculationResult));
                        }
                    }
                    return;
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    void connect(QueueThread queueThread) {
        try {
            pipedInputStream.connect(queueThread.getPipedOutputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void reconnect(QueueThread queueThread) {
        if(!isConnected() && !isClosed())
        try {
            this.pipedInputStream.connect(queueThread.getPipedOutputStream());
            this.objectInputStream = new ObjectInputStream(pipedInputStream);
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveToFile(String fileName) {
        String directoryPath =
                Environment.getExternalStorageDirectory()
                        + File.separator
                        + "LOGS"
                        + File.separator;

        File fileDirectory = new File(directoryPath);

        if (fileDirectory.mkdirs())
            fileToWrite = new File(directoryPath, fileName + ".txt");
    }

    public void closeConnection() {
        connected = false;
        closed = true;
        try {
            pipedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean isClosed() {
        return closed;
    }

    boolean isConnected() {
        return connected;
    }
}
