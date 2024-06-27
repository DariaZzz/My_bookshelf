package com.example.server_test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private String[] localDataSet;
    private String[] localIdes;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button but;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            but = (Button) view.findViewById(R.id.b);
        }

        public Button getButton() {
            return but;
        }
    }

/**
 * Initialize the dataset of the Adapter
 *
 * @param dataSet String[] containing the data to populate views to be used
 * by RecyclerView
 *
 */
    public CustomAdapter(String[] dataSet, String[] ides) {
        localIdes = ides;
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getButton().setText(localDataSet[position]);
        viewHolder.getButton().setId(Integer.parseInt(localIdes[position]));
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}
