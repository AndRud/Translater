package com.andrutyk.translater.database;

import android.support.annotation.NonNull;

import com.andrutyk.translater.content.TranslatedText;

import io.realm.Realm;

/**
 * Created by admin on 22.07.2016.
 */
public class TranslatedTextHelper {

    private final static String FIELD_ORIGINAL_TEXT = "originalText";

    public static void save(@NonNull Realm realm, TranslatedText translatedText) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(translatedText);
        realm.commitTransaction();
    }

    public static TranslatedText getTranslatedText(@NonNull Realm realm, @NonNull String originalText){
        return realm.where(TranslatedText.class).equalTo(FIELD_ORIGINAL_TEXT, originalText).findFirst();
    }
}
