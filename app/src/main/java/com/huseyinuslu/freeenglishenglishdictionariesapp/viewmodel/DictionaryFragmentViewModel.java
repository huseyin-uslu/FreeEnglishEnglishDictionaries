package com.huseyinuslu.freeenglishenglishdictionariesapp.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huseyinuslu.freeenglishenglishdictionariesapp.data.DataModel;
import com.huseyinuslu.freeenglishenglishdictionariesapp.data.DictionaryData;
import com.huseyinuslu.freeenglishenglishdictionariesapp.data.LinkType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DictionaryFragmentViewModel extends ViewModel {

    public static final String SHARED_INDEX_NUMBER_KEY = "SHARED_KEY_NUMBER";

    private final MutableLiveData<String> word = getWord();

    public LiveData<String> word(){
        return this.word;
    }

            private MutableLiveData<String> getWord(){
                if(word == null){
                    return new MutableLiveData<String>("");
                }else{
                    return word;
                }
            }

           public final List<String> wordList = new ArrayList<String>();

    private final MutableLiveData<DataModel> selectedDictionaryItem = getSelectedDictionaryItem();

    private MutableLiveData<DataModel> getSelectedDictionaryItem() {

        if(selectedDictionaryItem == null){
            return new MutableLiveData<DataModel>();
        }else{
            return selectedDictionaryItem;
        }
    }

    public final MutableLiveData<Boolean> dictionaryListState = getDictionaryListState();

    public void  setDictionaryListState(boolean aBoolean){
        dictionaryListState.setValue(aBoolean);
    }

    private MutableLiveData<Boolean> getDictionaryListState() {
        if(dictionaryListState == null){
            return new MutableLiveData<Boolean>(true);
        }else {
            return dictionaryListState;
        }
    }

    private SharedPreferences sP  = null;
    private Resources         res = null;

    private final DataModel[] data = DictionaryData.getData();

    private MutableLiveData<Integer> selectedIndexNumber;

    private MutableLiveData<Integer> getSelectedIndexNumber(int selectedNumber){
        if(selectedIndexNumber == null){
            return new MutableLiveData<Integer>(selectedNumber);
        }else{
            return selectedIndexNumber;
        }
    }

    private MutableLiveData<String> fullLink;

    public LiveData<String> fullLink() {
        return fullLink;
    }

    private MutableLiveData<String> getFullLink(DataModel data){
        if(fullLink == null){
            String link = res.getString(data.getLink(), getWordAccordingtoLink(data.getLinkType(),word.getValue())).toLowerCase(Locale.ROOT);
            return new MutableLiveData<String>(link);
        }else{
            return fullLink;
        }
    }

    public void initialization(@NonNull Activity context) {

        if(wordList.isEmpty())
            wordList.addAll(DictionaryData.getWords());

        if(sP == null)
        sP = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        if(res == null)
        res = context.getResources();

        selectedIndexNumber = getSelectedIndexNumber(sP.getInt(SHARED_INDEX_NUMBER_KEY, 0));
        selectedDictionaryItem.setValue(data[selectedIndexNumber.getValue()]);

        fullLink = getFullLink(selectedDictionaryItem.getValue());
    }

    public void setSelectedDictionary(@NonNull Integer index) {
        selectedDictionaryItem.setValue(data[index]);
        setIndexNumber(index);
    }

    public Integer getIndexNumber() {
        return selectedIndexNumber.getValue();
    }

    private void setIndexNumber(@NonNull Integer index) {
        selectedIndexNumber.setValue(index);
        sP.edit().putInt(SHARED_INDEX_NUMBER_KEY, index).apply();
    }

    public void setWord(@NonNull String word) {
        this.word.setValue(word);
    }

    public String getLink() {

        DataModel data = selectedDictionaryItem.getValue();
        String worD = word.getValue();
        String link = res.getString(data.getLink(), getWordAccordingtoLink(data.getLinkType(),worD)).toLowerCase(Locale.ROOT);
        fullLink.setValue(link);

        return link;
    }

    private String getWordAccordingtoLink(@NonNull LinkType type,@NonNull String word) {

        switch (type){
         case TWENTY_PERCENTAGE:
             return word.replace(" ","%20");
         case PLUS:
             return word.replace(" ","+");
         case HYPEN:
             return word.replace(" ","-");
         default:
             return word.replace(" ","%20");
     }

    }
}
