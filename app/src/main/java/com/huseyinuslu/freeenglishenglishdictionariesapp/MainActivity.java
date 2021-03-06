package com.huseyinuslu.freeenglishenglishdictionariesapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.huseyinuslu.freeenglishenglishdictionariesapp.data.DictionaryData;


/**
 * This app is as an open source project in the public.
 * It means you can use it whatever way you want and develop it.
 *
 * @author Huseyin Uslu
 * @since 5.11.2021
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DictionaryData.pullWordsFromAsset(this);
    }
}