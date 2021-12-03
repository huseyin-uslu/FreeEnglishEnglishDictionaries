package com.huseyinuslu.freeenglishenglishdictionariesapp.data;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WordBank {
    private final Context context;

    public WordBank(Context context) {
        this.context = context;
    }

    private final ExecutorService executor
            = Executors.newSingleThreadExecutor();

    public Future<List<String>> readLine(String path) throws IOException {

        List<String> mLines = new ArrayList<>();

        AssetManager am = context.getAssets();

        InputStream is = am.open(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;

        try {
            while ((line = reader.readLine()) != null) {
                mLines.add(line);
            }
        } catch (IOException e) {
            readLine(path);
        }

        return executor.submit(() -> {
            return mLines;
        });
    }
}
