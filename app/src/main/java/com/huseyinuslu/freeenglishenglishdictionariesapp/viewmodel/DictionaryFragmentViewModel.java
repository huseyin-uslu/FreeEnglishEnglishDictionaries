package com.huseyinuslu.freeenglishenglishdictionariesapp.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.huseyinuslu.freeenglishenglishdictionariesapp.data.DataModel;
import com.huseyinuslu.freeenglishenglishdictionariesapp.data.DictionaryData;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DictionaryFragmentViewModel extends ViewModel {

    public static final String SHARED_INDEX_NUMBER_KEY = "SHARED_KEY_NUMBER";
    private final MutableLiveData<String> word = new MutableLiveData<>();
    private final MutableLiveData<DataModel> selectedDictionaryItem = new MutableLiveData<>();
    private SharedPreferences sP = null;
    private Resources res = null;
    private DataModel[] data;
    private int selectedIndexNumber;
    private Integer linkStrRes;

    public void initialization(@NonNull Activity context) {
        sP = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        res = context.getResources();
        data = DictionaryData.getData(context);
        selectedIndexNumber = sP.getInt(SHARED_INDEX_NUMBER_KEY, 0);
        selectedDictionaryItem.setValue(data[selectedIndexNumber]);
        linkStrRes = Objects.requireNonNull(selectedDictionaryItem.getValue()).getLink();
    }

    public void setSelectedDictionary(@NonNull Integer index) {
        selectedDictionaryItem.setValue(data[index]);
        setIndexNumber(index);
    }

    public Integer getIndexNumber() {
        return selectedIndexNumber;
    }

    private void setIndexNumber(@NonNull Integer index) {
        selectedIndexNumber = index;
        sP.edit().putInt(SHARED_INDEX_NUMBER_KEY, index).apply();
    }

    public void setWord(@NonNull String word) {
        this.word.setValue(word);
    }

    public String getLink() {
        linkStrRes = Objects.requireNonNull(selectedDictionaryItem.getValue()).getLink();
        String blankLink = res.getString(linkStrRes, Objects.requireNonNull(word.getValue()));
        return linkAdjuster(blankLink);
    }

    private String linkAdjuster(@NonNull String blankLink) {
        List<String> array = Arrays.asList(DictionaryData.irregularLinkDictionariesArray());
        if (array.contains(res.getString(Objects.requireNonNull(selectedDictionaryItem.getValue()).getName()))) {
            return blankLink.replace(" ", "-");
        } else {
            return blankLink.replace(" ", "%20");
        }
    }
}
