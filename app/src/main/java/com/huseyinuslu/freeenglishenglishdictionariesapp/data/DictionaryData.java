package com.huseyinuslu.freeenglishenglishdictionariesapp.data;

import android.app.Activity;
import com.huseyinuslu.freeenglishenglishdictionariesapp.R;

public class DictionaryData {

    public static DataModel[] getData(Activity activity){

        DataModel[] data  = new DataModel[]{
                new DataModel(R.string.google_dictionary_name,
                        R.drawable.google,
                        R.string.google_link
                        ),
                new DataModel(R.string.oxford_dictionary_name,
                        R.drawable.oxford,
                        R.string.oxford_link
                ),
                new DataModel(R.string.cambridge_dictionary_name,
                        R.drawable.cambridge,
                        R.string.cambridge_link
                ),
                new DataModel(R.string.longman_dictionary_name,
                        R.drawable.longman,
                        R.string.longman_link
                ),
                new DataModel(R.string.collins_dictionary_name,
                        R.drawable.collins,
                        R.string.collins_link
                ),
                new DataModel(R.string.urban_dictionary_name,
                        R.drawable.urban,
                        R.string.urban_link
                ),
                new DataModel(R.string.dictionary_dictionary_name,
                        R.drawable.dictionarycom,
                        R.string.dictionary_link
                ),
                new DataModel(R.string.macmillian_dictionary_name,
                        R.drawable.macmillian,
                        R.string.macmillan_link
                )

        };
        return data;
    }

    public static String[] irregularLinkDictionariesArray() {
        String[] array = new String[]{
                "Macmillan","Longman","Oxford","Collins"
        };
        return array;
    }
}
