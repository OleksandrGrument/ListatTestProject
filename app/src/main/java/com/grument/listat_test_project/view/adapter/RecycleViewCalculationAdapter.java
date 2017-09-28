package com.grument.listat_test_project.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grument.listat_test_project.R;
import com.grument.listat_test_project.data_objects.CalculationResult;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewCalculationAdapter extends RecyclerView.Adapter<RecycleViewCalculationAdapter.ViewHolder> {

    private List<CalculationResult> calculationResults;

    private String threadNumber, primeNumbersGenerated;


    public RecycleViewCalculationAdapter(ArrayList<CalculationResult> calculationResults, Context context) {
        this.calculationResults = calculationResults;
        threadNumber = context.getString(R.string.main_activity_thread_number);
        primeNumbersGenerated = context.getString(R.string.main_activity_prime_number);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calculation_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder view, int position) {

        CalculationResult calculationResult = calculationResults.get(position);

        String threadInfo = threadNumber + calculationResult.getThreadNumber();
        String primeNumberInfo = primeNumbersGenerated + calculationResult.getPrimeNumberGenerated();

        view.streamNumberTextView.setText(threadInfo);
        view.primeNumberSumTextView.setText(primeNumberInfo);

        if (calculationResult.getThreadNumber() % 2 == 0) {
            view.streamNumberTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            view.primeNumberSumTextView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        }
    }

    @Override
    public int getItemCount() {
        return calculationResults.size();
    }

    public void swap(CalculationResult calculationResult) {
        calculationResults.add(calculationResult);
        notifyItemInserted(getItemCount());
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView streamNumberTextView;
        TextView primeNumberSumTextView;

        RecyclerView recyclerView;

        ViewHolder(View view) {
            super(view);
            this.setIsRecyclable(false);
            recyclerView = (RecyclerView) view.findViewById(R.id.fragment_calculation_recycler_view);
            streamNumberTextView = (TextView) view.findViewById(R.id.stream_number);
            primeNumberSumTextView = (TextView) view.findViewById(R.id.prime_number_sum);
        }
    }

}