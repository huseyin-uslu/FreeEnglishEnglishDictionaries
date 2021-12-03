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

import java.util.HashMap;

/**
 * an adapter of the dictionaries list
 */
public class DictionaryListAdapter extends RecyclerView.Adapter<DictionaryListAdapter.ViewHolder> {

    private final Activity context; //the activity for passing into some methods.
    private final DataModel[] dictionaries; // the instanstatic dictionaries list
    public final HashMap<String, ViewHolder> viewHolders = new HashMap<>();
    public final OnDictionaryClicked onClicked;
    public boolean isItemChecked = false;

    private String selectedDictionary; //the selected dictionary

    public interface OnDictionaryClicked { //linking with MVVM simultaneously..
        void onClick(@NonNull int selectedIndex);
    }

    public DictionaryListAdapter(@NonNull Activity context, String selectedDictionary, @NonNull OnDictionaryClicked onClicked) {
        this.context = context;
        this.dictionaries = DictionaryData.getData();
        this.onClicked = onClicked;
        this.selectedDictionary = selectedDictionary;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_of_dictionarieslist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String title = context.getResources().getString(dictionaries[position].getName());
        viewHolders.put(title, holder);//holder filler

        holder.imageView.setImageResource(dictionaries[position].getImageResource());
        holder.textView.setText(title);

        if (!isItemChecked) {
            if (title.equals(selectedDictionary)) {
                holder.radioButton.setChecked(true);
                isItemChecked = true;
            }
        }

        holder.radioButton.setOnClickListener(view -> {
            selectedDictionary = holder.textView.getText().toString();

            if (viewHolders.containsKey(selectedDictionary)) {

                for (ViewHolder vh : viewHolders.values()) {
                    vh.radioButton.setChecked(false);
                    if (!vh.equals(holder))
                        continue;
                    vh.radioButton.setChecked(true);
                }

                onClicked.onClick(holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dictionaries.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
