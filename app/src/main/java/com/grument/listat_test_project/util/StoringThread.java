package com.grument.listat_test_project.util;

import android.os.Environment;
import android.support.v7.widget.RecyclerView;
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
import java.io.PipedOutputStream;
import java.util.ArrayList;

public class StoringThread extends Thread {

    private final static String STORING_THREAD_TAG = "STORING_THREAD_TAG";

    private boolean isConnected = false;

    private PipedInputStream pipedInputStream = new PipedInputStream();

    private ObjectInputStream objectInputStream;

    private FileOutputStream outPutStream;

    private OutputStreamWriter outPutStreamWriter;

    private ArrayList<CalculationResult> calculationResults = new ArrayList<>();

    private RecyclerView recyclerView;

    private File fileToWrite = null;

    private CalculationViewFragment calculationViewFragment;

    public StoringThread(CalculationViewFragment calculationViewFragment) {
        this.calculationViewFragment = calculationViewFragment;
    }

    @Override
    public void run() {

        Log.i(STORING_THREAD_TAG, "RUN");

        try {
            objectInputStream = new ObjectInputStream(pipedInputStream);

            if (fileToWrite != null) {
                fileToWrite.createNewFile();
                outPutStream = new FileOutputStream(fileToWrite);
                outPutStreamWriter = new OutputStreamWriter(outPutStream);
            }

            RecycleViewCalculationAdapter adapter = (RecycleViewCalculationAdapter) calculationViewFragment.getRecyclerView().getAdapter();

            while (true) {

                if (isConnected) {
                    CalculationResult calculationResult = (CalculationResult) objectInputStream.readObject();
                    Log.i(STORING_THREAD_TAG, calculationResult.toString());
                    calculationResults.add(calculationResult);

                    if (fileToWrite != null) {
                        outPutStreamWriter.append(calculationResult.toString());
                    }

                    if (CalculationViewFragment.isActive()) {
                        calculationViewFragment.getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                adapter.swap(calculationResults);
                            }
                        });
                    }

                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public void connect(PipedOutputStream pipedOutputStream) {
        try {
            pipedInputStream.connect(pipedOutputStream);
            isConnected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reconnect(PipedOutputStream pipedOutputStream) throws IOException {
        if (!isConnected) {
            this.pipedInputStream.connect(pipedOutputStream);
            this.objectInputStream = new ObjectInputStream(pipedInputStream);
            isConnected = true;
        }
    }

    public boolean isConnected() {
        return isConnected;
    }


    public void saveToFile(String fileName) {
        String directoryPath =
                Environment.getExternalStorageDirectory()
                        + File.separator
                        + "LOGS"
                        + File.separator;

        File fileDirectory = new File(directoryPath);

        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
        fileToWrite = new File(directoryPath, fileName + ".txt");
    }

    public void closeConnection() {
        isConnected = false;
        try {
            pipedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
