package com.isbndb.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.isbndb.R;
import com.isbndb.app.AppController;
import com.isbndb.helper.KeyManager;
import com.isbndb.network.JSONRequest;
import com.isbndb.utility.ApiDetails;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonSetToken,buttonDefaultToken,buttonClearHistory;
    EditText editTextApiKey;
    TextView textViewApiKey;
    ImageView imageViewKeyStatus;

    private static final int INDICATOR_WRONG = android.R.drawable.presence_busy;
    private static final int INDICATOR_CHECKING = android.R.drawable.presence_away;
    private static final int INDICATOR_CORRECT = android.R.drawable.presence_online;
    private static final int INDICATOR_OFF = android.R.drawable.presence_invisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();
        buttonClearHistory = (Button) findViewById(R.id.button_delete_history_layout_setting);
        buttonDefaultToken = (Button) findViewById(R.id.button_default_apikey_layout_setting);
        buttonSetToken = (Button) findViewById(R.id.button_set_token_layout_setting);
        editTextApiKey = (EditText) findViewById(R.id.edit_text_apikey_layout_setting);
        textViewApiKey = (TextView) findViewById(R.id.text_view_apikey_layout_setting);
        imageViewKeyStatus = (ImageView) findViewById(R.id.image_view_status_layout_setting);

        buttonSetToken.setOnClickListener(this);
        buttonDefaultToken.setOnClickListener(this);
        buttonClearHistory.setOnClickListener(this);
        textViewApiKey.setOnClickListener(this);
        editTextApiKey.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_delete_history_layout_setting:
                AppController.getSearchHistoryInstant().reInitializeHistory();
                buttonClearHistory.setText("Total search : " + AppController.getSearchHistoryInstant().getHistoryLength() + " | Reset History");
                break;
            case R.id.button_default_apikey_layout_setting:
                AppController.getKeyManagerInstant().setKey(ApiDetails.DEFAULT_API_TOKEN);;
                AppController.getKeyManagerInstant().setKeyType(KeyManager.DEFAULT_KEY);
                AppController.getKeyManagerInstant().setIsValidKey(true);
                statusView(true, "Token Uses: Default", INDICATOR_OFF);
                break;
            case R.id.button_set_token_layout_setting:
                if (editTextApiKey.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Please enter an API TOKEN or use default token.", Toast.LENGTH_SHORT).show();
                    break;
                }
                setToken(editTextApiKey.getText().toString().trim());
                break;
            case R.id.edit_text_apikey_layout_setting:
            case R.id.text_view_apikey_layout_setting:
                statusView(false, null, INDICATOR_OFF);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        statusView(true, "Checking Token ...", INDICATOR_CHECKING);
        if (AppController.getKeyManagerInstant().getKeyType() == KeyManager.DEFAULT_KEY)
            statusView(true, "Token Uses: Default", INDICATOR_OFF);
        else
            setToken(AppController.getKeyManagerInstant().getKey().toString());
        buttonClearHistory.setText("Total search : " + AppController.getSearchHistoryInstant().getHistoryLength() + " | Reset History");
    }

    protected void setToken(final String key) {
        statusView(true, "Checking Token ...", INDICATOR_CHECKING);
        new JSONRequest(this, new ApiDetails(key).getBooks("Sudhir"), null) {
            @Override
            protected void onJSONErrorResponse(String response) {

            }

            @Override
            protected void onJSONResponse(String response) {
                AppController.getKeyManagerInstant().setKey(key, false, KeyManager.USER_KEY);
                statusView(true, "Token Status: Invalid", INDICATOR_WRONG);
                JSONObject object = new JSONObject();
                try {
                    object = new JSONObject(response);
                    object.getString("error");
                } catch (JSONException e) {
                    AppController.getKeyManagerInstant().setKey(key, true, KeyManager.USER_KEY);
                    statusView(true, "Token Uses: User Token", INDICATOR_CORRECT);
                }
            }
        }.execute("check_key");
    }

    protected void statusView(boolean status, String msg, int indicator) {
        if (status) {
            textViewApiKey.setText(msg);
            imageViewKeyStatus.setBackgroundResource(indicator);
            if (imageViewKeyStatus.getVisibility() == View.INVISIBLE)
                imageViewKeyStatus.setVisibility(View.VISIBLE);
            if (editTextApiKey.getVisibility() == View.VISIBLE)
                editTextApiKey.setVisibility(View.INVISIBLE);
            if (textViewApiKey.getVisibility() == View.INVISIBLE)
                textViewApiKey.setVisibility(View.VISIBLE);
        } else if (!status) {
            if (imageViewKeyStatus.getVisibility() == View.VISIBLE)
                imageViewKeyStatus.setVisibility(View.INVISIBLE);
            if (editTextApiKey.getVisibility() == View.INVISIBLE)
                editTextApiKey.setVisibility(View.VISIBLE);
            if (textViewApiKey.getVisibility() == View.VISIBLE)
                textViewApiKey.setVisibility(View.INVISIBLE);
        }
    }
}
