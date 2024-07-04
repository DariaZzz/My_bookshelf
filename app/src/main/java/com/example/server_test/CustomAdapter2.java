package com.example.server_test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.ViewHolder> {

    private String[] localDataSet;
    private String[] localIdes;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final SwitchCompat aSwitch;
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

           aSwitch = (SwitchCompat) view.findViewById(R.id.switcher1);
           textView = (TextView) view.findViewById(R.id.later_name);
        }

        public SwitchCompat getaSwitch() {
            return aSwitch;
        }
        public TextView getTextView(){return textView;}

    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView
     *
     */
    public CustomAdapter2(String[] dataSet, String[] ides) {
        localIdes = ides;
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_later_book, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(localDataSet[position]);
        viewHolder.getaSwitch().setId(Integer.parseInt(localIdes[position]));
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}

