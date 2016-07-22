package com.andrutyk.translater.ui;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andrutyk.translater.R;
import com.andrutyk.translater.api.response.Response;
import com.andrutyk.translater.content.TranslatedText;
import com.andrutyk.translater.loaders.TranslatedLoader;
import com.andrutyk.translater.utils.NetworkUtil;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Response>, View.OnClickListener {

    private final static String API_KEY = "trnsl.1.1.20160721T085454Z.e6ccaed5ee786c94.009d9a8304b0873ea10cd1133ac59d880367a6e9";
    private final static String LANG = "en-uk";

    private TextView tvInternetConn;
    private EditText edtInputText;
    private TextView tvTextResult;
    private ProgressBar pbTranslating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInternetConn = (TextView) findViewById(R.id.tvInternetConn);
        edtInputText = (EditText) findViewById(R.id.edtInputText);
        tvTextResult = (TextView) findViewById(R.id.tvTextResult);
        pbTranslating = (ProgressBar) findViewById(R.id.pbTranslating);
        setVisiblyProgressBar(false);
        checkConnectivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkConnectivity();
    }

    @Override
    public Loader<Response> onCreateLoader(int id, Bundle args) {
        String inputStr = edtInputText.getText().toString();
        return new TranslatedLoader(getBaseContext(), API_KEY, inputStr, LANG);
    }

    @Override
    public void onLoadFinished(Loader<Response> loader, Response data) {
        TranslatedText translatedText = data.getTypedAnswer();
        String textResult;
        try {
            textResult = translatedText.getTranslatedText().get(0).getVal();
        } catch (NullPointerException e) {
            textResult = "";
        }
        setVisiblyProgressBar(false);
        tvTextResult.setText(textResult);
    }

    @Override
    public void onLoaderReset(Loader<Response> loader) {

    }

    @Override
    public void onClick(View view) {
        getSupportLoaderManager().restartLoader(0, null, this);
        setVisiblyProgressBar(true);
    }

    private void setVisiblyProgressBar(boolean isVisibly) {
        if (isVisibly) {
            tvTextResult.setVisibility(View.GONE);
            pbTranslating.setVisibility(View.VISIBLE);
        } else {
            tvTextResult.setVisibility(View.VISIBLE);
            pbTranslating.setVisibility(View.GONE);
        }
    }

    private void checkConnectivity() {
        int conn = NetworkUtil.getConnectivityStatus(this);
        if (conn == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
            tvInternetConn.setText(getString(R.string.is_offline));
        } else {
            tvInternetConn.setText(getString(R.string.is_online));
        }
    }
}
