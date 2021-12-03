package com.huseyinuslu.freeenglishenglishdictionariesapp.data;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

public class DataModel {
    private final int name;
    private final int imageResource;
    private final int link;
    private final LinkType linkType;

    public DataModel(
            @StringRes int name,
            @DrawableRes int imageResource,
            @StringRes int link,
            @NonNull LinkType linkType) {
        this.name = name;
        this.imageResource = imageResource;
        this.link = link;
        this.linkType = linkType;
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

    public LinkType getLinkType() {
        return linkType;
    }
}
