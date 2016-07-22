package com.andrutyk.translater.content;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by admin on 21.07.2016.
 */
public class TranslatedText extends RealmObject {

    @SerializedName("text")
    private RealmList<RealmString> translatedText;

    @SerializedName("code")
    private String code;

    @SerializedName("lang")
    private String lang;

    @PrimaryKey
    private String originalText;

    public TranslatedText() {
    }

    public RealmList<RealmString> getTranslatedText() {
        return this.translatedText;
    }

    public String getCode() {
        return this.code;
    }

    public String getLang() {
        return this.code;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setTranslatedText(RealmList<RealmString> translatedText) {
        this.translatedText = translatedText;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }
}
