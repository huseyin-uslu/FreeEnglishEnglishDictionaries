package com.huseyinuslu.freeenglishenglishdictionariesapp.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huseyinuslu.freeenglishenglishdictionariesapp.R;
import com.huseyinuslu.freeenglishenglishdictionariesapp.data.DataModel;
import com.huseyinuslu.freeenglishenglishdictionariesapp.data.DictionaryData;

import java.util.ArrayList;

public class DictionaryFragmentViewModel extends ViewModel {

    private final MutableLiveData<String> word = new MutableLiveData<>();
    private final MutableLiveData<DataModel> selectedDictionaryItem = new MutableLiveData<>();

    public static final  String SHARED_INDEX_NUMBER_KEY = "SHARED_KEY_NUMBER";
    public static final  String SHARED_STRING_LINK_RES_NUMBER_KEY = "SHARED_LINK_KEY_NUMBER";

    private SharedPreferences sP = null;
    private Resources res = null;
    private DataModel[] data;
    private int selectedIndexNumber;
    private int linkStrRes;

    public void initialization() {
            selectedIndexNumber   = sP.getInt(SHARED_INDEX_NUMBER_KEY,0);
            linkStrRes = sP.getInt(SHARED_STRING_LINK_RES_NUMBER_KEY,selectedIndexNumber);
            selectedDictionaryItem.setValue(data[selectedIndexNumber]);
    }

    public void setSharedPreferences(@NonNull Activity context){
        sP   = context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
        res  = context.getResources();
        data = DictionaryData.getData(context);
    }

    public void setSelectedDictionary(@NonNull Integer index){
        selectedDictionaryItem.setValue(data[index]);
        setIndexNumber(index);
   }

   public Integer getIndexNumber(){
        return selectedIndexNumber;
   }

    public void setWord(@NonNull String word){
       this.word.setValue(word);
    }

    public String getWord(){
        return this.word.getValue();
    }

    private void setIndexNumber(@NonNull Integer index){
        selectedIndexNumber = index;
        sP.edit().putInt(SHARED_INDEX_NUMBER_KEY,index).apply();
    }
}
