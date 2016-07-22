package com.andrutyk.translater.api;

import android.support.annotation.NonNull;

import com.andrutyk.translater.content.RealmString;
import com.andrutyk.translater.content.TranslatedText;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import io.realm.RealmList;
import io.realm.RealmObject;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 21.07.2016.
 */
public class ApiFactory {

    private static final String API_ENDPOINT = "https://translate.yandex.net/";

    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 60;
    private static final int TIMEOUT = 60;

    private static final OkHttpClient CLIENT = new OkHttpClient();

    static {
        CLIENT.newBuilder().connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.newBuilder().writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        CLIENT.newBuilder().readTimeout(TIMEOUT, TimeUnit.SECONDS);
    }

    private static final Type token = new TypeToken<RealmList<RealmString>>() {
    }.getType();

    private static final Gson GSON = new GsonBuilder()
            .setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getDeclaringClass().equals(RealmObject.class);
                }

                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }
            })
            .registerTypeAdapter(token, new TypeAdapter<RealmList<RealmString>>() {
                @Override
                public void write(JsonWriter out, RealmList<RealmString> value) throws IOException {

                }

                @Override
                public RealmList<RealmString> read(JsonReader in) throws IOException {
                    RealmList<RealmString> list = new RealmList<>();
                    in.beginArray();
                    while (in.hasNext()) {
                        list.add(new RealmString(in.nextString()));
                    }
                    in.endArray();
                    return list;
                }
            })
            .create();

    @NonNull
    public static TranslateService getTranslateService() {
        return getRetrofit().create(TranslateService.class);
    }

    @NonNull
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .client(CLIENT)
                .build();
    }
}
