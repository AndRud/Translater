package com.andrutyk.translater.loaders;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.andrutyk.translater.api.TranslateService;
import com.andrutyk.translater.api.response.RequestResult;
import com.andrutyk.translater.api.response.Response;
import com.andrutyk.translater.api.ApiFactory;
import com.andrutyk.translater.api.response.TranslatedTextResponse;
import com.andrutyk.translater.content.TranslatedText;
import com.andrutyk.translater.database.TranslatedTextHelper;

import java.io.IOException;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;

/**
 * Created by admin on 21.07.2016.
 */
public class TranslatedLoader extends BaseLoader {

    private final String apiKey;
    private final String text;
    private final String lang;

    private volatile TranslatedTextResponse translatedTextResponse;

    public TranslatedLoader(Context context, String apiKey, String text,
                            String lang) {
        super(context);
        this.apiKey = apiKey;
        this.text = text;
        this.lang = lang;
        translatedTextResponse = new TranslatedTextResponse();
    }

    @Override
    protected Response onError() {
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                TranslatedText translatedText = TranslatedTextHelper.getTranslatedText(Realm.getInstance(getContext()), text);
                if (translatedText == null) {
                    translatedText = new TranslatedText();
                    translatedText.setOriginalText(text);
                }
                translatedTextResponse.setAnswer(translatedText)
                        .setRequestResult(RequestResult.SUCCESS);
            }
        };
        handler.post(runnable);

        return translatedTextResponse;
    }

    @Override
    protected Response apiCall() throws IOException {
        TranslateService translateService = ApiFactory.getTranslateService();
        Call<TranslatedText> call = translateService.translateText(apiKey, text, lang);
        TranslatedText translatedText = call.execute().body();
        translatedText.setOriginalText(text);
        return new TranslatedTextResponse()
                .setRequestResult(RequestResult.SUCCESS)
                .setAnswer(translatedText);
    }
}
