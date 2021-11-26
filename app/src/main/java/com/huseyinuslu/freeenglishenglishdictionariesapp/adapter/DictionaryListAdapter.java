package com.huseyinuslu.freeenglishenglishdictionariesapp.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.huseyinuslu.freeenglishenglishdictionariesapp.R;
import com.huseyinuslu.freeenglishenglishdictionariesapp.data.DataModel;
import com.huseyinuslu.freeenglishenglishdictionariesapp.data.DictionaryData;

import java.util.ArrayList;
/** an adapter of the dictionaries list */
public class DictionaryListAdapter extends RecyclerView.Adapter<DictionaryListAdapter.ViewHolder>{

    private final Activity context; //the activity for passing into some methods.
    private final DataModel[] dictionaries; // the instanstatic dictionaries list
    private int   selectedDictionary; //the selected dictionary

    public final ArrayList<ViewHolder> viewHolders = new ArrayList<>();

    public final OnDictionaryClicked onClicked;

    public interface OnDictionaryClicked{ //linking with MVVM simultaneously..
        void onClick(@NonNull Integer position);
    }

    public DictionaryListAdapter (@NonNull Activity context, int selectedDictionary , @NonNull OnDictionaryClicked onClicked){
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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        viewHolders.add(holder);

        if(selectedDictionary == position){
            checkInbox(selectedDictionary);
        }


        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(),dictionaries[position].getImageResource(),null);
        holder.imageView.setImageDrawable(drawable);
   //     -  Arza burada...!!


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
        viewHolders.get(holderIndex).radioButton.setChecked(true);
        selectedDictionary = holderIndex;
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
