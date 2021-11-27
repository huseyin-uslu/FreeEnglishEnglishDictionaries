package com.huseyinuslu.freeenglishenglishdictionariesapp.adapter;

import android.app.Activity;
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



import java.util.ArrayList;
import java.util.List;

/** an adapter of the dictionaries list */
public class DictionaryListAdapter extends RecyclerView.Adapter<DictionaryListAdapter.ViewHolder>{

    private final Activity context; //the activity for passing into some methods.
    private final DataModel[] dictionaries; // the instanstatic dictionaries list
    public  final List<ViewHolder> viewHolders = new ArrayList<>();
    public  final OnDictionaryClicked onClicked;

    private       String selectedDictionary; //the selected dictionary


    public interface OnDictionaryClicked{ //linking with MVVM simultaneously..
        void onClick(@NonNull int selectedIndex);
    }

    public DictionaryListAdapter (@NonNull Activity context, String selectedDictionary , @NonNull OnDictionaryClicked onClicked){
        this.context = context;
        this.dictionaries = DictionaryData.getData();
        this.onClicked = onClicked;
        this.selectedDictionary = selectedDictionary;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_of_dictionarieslist,parent,false);
        ViewHolder vHolder = new ViewHolder(view);
        viewHolders.add(vHolder); //holder filler
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.imageView.setImageResource(dictionaries[position].getImageResource());
        holder.textView.setText(dictionaries[position].getName());
        checkInbox();

        holder.radioButton.setOnClickListener(view -> {
            selectedDictionary = holder.textView.getText().toString();
            for(int i = 0;i<viewHolders.size();i++){
                viewHolders.get(i).radioButton.setChecked(false);
            }
            checkInbox();
           onClicked.onClick(holder.getLayoutPosition());
        });
    }

    private void checkInbox(){

        for(ViewHolder vh : viewHolders){
           if(selectedDictionary.equals(vh.textView.getText().toString())){
               vh.radioButton.setChecked(true);
               break;
           }
        }
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
           textView    = itemView.findViewById(R.id.textViewInEachItem);
           imageView   = itemView.findViewById(R.id.imageViewInEachItem);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
