package com.andrutyk.translater.api.response;

import android.content.Context;
import android.widget.Toast;

import com.andrutyk.translater.content.TranslatedText;
import com.andrutyk.translater.database.TranslatedTextHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by admin on 21.07.2016.
 */
public class TranslatedTextResponse extends Response {

    @Override
    public void save(Context context) {
        TranslatedText translatedText = getTypedAnswer();
        if (translatedText != null) {
            TranslatedTextHelper.save(getRealm(context), translatedText);
        }
    }

    private Realm getRealm(Context context) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();

        try {
            return Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e) {
            try {
                Realm.deleteRealm(realmConfiguration);
                return Realm.getInstance(realmConfiguration);
            } catch (Exception ex) {
                throw ex;
            }
        }
    }
}
