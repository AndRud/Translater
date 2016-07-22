package com.andrutyk.translater.api;

import com.andrutyk.translater.content.TranslatedText;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by admin on 21.07.2016.
 */
public interface TranslateService {
    @GET("api/v1.5/tr.json/translate")
    Call<TranslatedText> translateText(@Query("key") String apiKey, @Query("text") String text, @Query("lang") String lang);
}
