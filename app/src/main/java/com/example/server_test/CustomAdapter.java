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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button but;

        public ViewHolder(View view) {
            super(view);
            but = (Button) view.findViewById(R.id.b);
        }
        public Button getButton() {
            return but;
        }
    }

    public CustomAdapter(String[] dataSet, String[] ides) {
        localIdes = ides;
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

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
