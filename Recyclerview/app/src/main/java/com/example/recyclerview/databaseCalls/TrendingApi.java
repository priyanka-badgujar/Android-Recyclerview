package com.example.recyclerview.databaseCalls;

import com.google.gson.annotations.SerializedName;

public class TrendingApi {

    @SerializedName("author")
    private String mAuthor;

    @SerializedName("name")
    private String mName;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("avatar")
    private String mAvatar;

    @SerializedName("language")
    private String mLanguage;

    @SerializedName("languageColor")
    private String mLanguageColor;

    @SerializedName("stars")
    private String mStars;

    @SerializedName("forks")
    private String mForks;

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmName() {
        return mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmAvatar() {
        return mAvatar;
    }

    public String getmLanguage() {
        return mLanguage;
    }

    public String getmLanguageColor() {
        return mLanguageColor;
    }

    public String getmStars() {
        return mStars;
    }

    public String getmForks() {
        return mForks;
    }
}
