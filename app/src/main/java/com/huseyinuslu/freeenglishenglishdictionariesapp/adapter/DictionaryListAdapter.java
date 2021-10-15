package com.huseyinuslu.freeenglishenglishdictionariesapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huseyinuslu.freeenglishenglishdictionariesapp.R;

import java.util.List;

public class DictionaryListAdapter extends RecyclerView.Adapter<DictionaryListAdapter.ViewHolder>{
    final Activity context;
    final List<String>[] dictionaries;

    public DictionaryListAdapter (@NonNull Activity context,List<String>[] dictionaries){
        this.context = context;
        this.dictionaries = dictionaries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_of_dictionarieslist,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
