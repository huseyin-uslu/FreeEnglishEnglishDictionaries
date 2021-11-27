package com.huseyinuslu.freeenglishenglishdictionariesapp.data;

import android.content.Context;

import com.huseyinuslu.freeenglishenglishdictionariesapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DictionaryData {

    //data
    private static final List<String> WORDS = new ArrayList<String>();
    private static final String mPath = "words.txt";

    public static List<String> getWords() {
        return WORDS;
    }

    public static void pullWordsFromAsset(Context context) {
        Thread newThread = new Thread(() -> {
            WordBank wordBank = new WordBank(context);
            WORDS.clear();
            try {
                Future<List<String>> list = wordBank.readLine(mPath);

                while (!list.isDone()) {
                    Thread.sleep(300L);
                }
                WORDS.addAll(list.get());

            } catch (IOException | InterruptedException | ExecutionException e) {
                pullWordsFromAsset(context);
            }
        });
        newThread.start();
    }

    public static DataModel[] getData() {

        DataModel[] data = new DataModel[]{
                new DataModel(R.string.google_dictionary_name,
                        R.drawable.google,
                        R.string.google_link,
                        LinkType.PLUS
                ),
                new DataModel(R.string.oxford_dictionary_name,
                        R.drawable.oxford,
                        R.string.oxford_link,
                        LinkType.HYPEN
                ),
                new DataModel(R.string.cambridge_dictionary_name,
                        R.drawable.cambridge,
                        R.string.cambridge_link,
                        LinkType.HYPEN
                ),
                new DataModel(R.string.longman_dictionary_name,
                        R.drawable.longman,
                        R.string.longman_link,
                        LinkType.HYPEN
                ),
                new DataModel(R.string.collins_dictionary_name,
                        R.drawable.collins,
                        R.string.collins_link,
                        LinkType.HYPEN
                ),
                new DataModel(R.string.urban_dictionary_name,
                        R.drawable.urban,
                        R.string.urban_link,
                        LinkType.TWENTY_PERCENTAGE
                ),
                new DataModel(R.string.dictionary_dictionary_name,
                        R.drawable.dictionarycom,
                        R.string.dictionary_link,
                        LinkType.HYPEN
                ),
                new DataModel(R.string.macmillian_dictionary_name,
                        R.drawable.macmillian,
                        R.string.macmillan_link,
                        LinkType.HYPEN
                ),
                new DataModel(R.string.merriam_dictionary_name,
                        R.drawable.merriam,
                        R.string.merriam_link,
                        LinkType.TWENTY_PERCENTAGE),
                new DataModel(R.string.lexico_dictionary_name,
                        R.drawable.lexico,
                        R.string.lexico_link,
                        LinkType.HYPEN),
                new DataModel(R.string.wordreference_dictionary_name,
                        R.drawable.wordreference,
                        R.string.wordreference_link,
                        LinkType.TWENTY_PERCENTAGE),
                new DataModel(R.string.thefreedictionary_dictionary_name,
                        R.drawable.freedic, R.string.thefreedictionary_link,
                        LinkType.PLUS),
                new DataModel(R.string.yourdictionary_dictionary_name,
                        R.drawable.yourdic,
                        R.string.yourdictionary_link,
                        LinkType.HYPEN),
                new DataModel(R.string.vocabulary_dictionary_name,
                        R.drawable.vocabulary,
                        R.string.vocabulary_link,
                        LinkType.TWENTY_PERCENTAGE)
        };
        return data;
    }
}
