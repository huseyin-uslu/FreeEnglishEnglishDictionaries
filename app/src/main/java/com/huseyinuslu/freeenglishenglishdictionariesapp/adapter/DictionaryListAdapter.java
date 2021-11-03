package com.huseyinuslu.freeenglishenglishdictionariesapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huseyinuslu.freeenglishenglishdictionariesapp.R;
import com.huseyinuslu.freeenglishenglishdictionariesapp.data.DataModel;
import com.huseyinuslu.freeenglishenglishdictionariesapp.data.DictionaryData;
import com.huseyinuslu.freeenglishenglishdictionariesapp.viewmodel.DictionaryFragmentViewModel;

import java.util.ArrayList;

public class DictionaryListAdapter extends RecyclerView.Adapter<DictionaryListAdapter.ViewHolder>{
    private final Activity context;
    private final DataModel[] dictionaries;
    private int selectedDictionary;

    public  final ArrayList<ViewHolder> viewHolders = new ArrayList<>();

    public final OnDictionaryClicked onClicked;

    public interface OnDictionaryClicked{
        void onClick(@NonNull Integer position);
    }

    public DictionaryListAdapter (@NonNull Activity context,@NonNull int selectedDictionary ,@NonNull OnDictionaryClicked onClicked){
        this.context = context;
        this.dictionaries = DictionaryData.getData(context);
        this.onClicked = onClicked;
        this.selectedDictionary = selectedDictionary;
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

        viewHolders.add(holder);

        if(selectedDictionary <= position){
            checkInbox(selectedDictionary);
        }

        holder.imageView.setImageResource(dictionaries[position].getImageResource());
        holder.textView.setText(dictionaries[position].getName());


        holder.radioButton.setOnClickListener(view -> {
           checkInbox(holder.getAdapterPosition());
           onClicked.onClick(selectedDictionary);
        });
    }

    private void checkInbox(int holderIndex){
        for(ViewHolder v : viewHolders){
            v.radioButton.setChecked(false);
        }
        setChecked(holderIndex);
        //send the view
    }

    private void setChecked(@NonNull Integer position){
         //default value
        viewHolders.get(position).radioButton.setChecked(true);
        selectedDictionary = position;
    }


    @Override
    public int getItemCount() {
    return dictionaries.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

       public RadioButton radioButton;
       public TextView textView;
       public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           radioButton = itemView.findViewById(R.id.radioButtonForChoosingDictionary);
           textView = itemView.findViewById(R.id.textViewInEachItem);
           imageView = itemView.findViewById(R.id.imageViewInEachItem);
        }
    }
}
