package com.huseyinuslu.freeenglishenglishdictionariesapp.data;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

public class DataModel {
    private final int name;
    private final int imageResource;
    private final int link;

   public DataModel(
           @StringRes int name,
           @DrawableRes int imageResource,
           @StringRes int link){
        this.name = name;
        this.imageResource = imageResource;
        this.link = link;
    }

    public int getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getLink() {
        return link;
    }
}
