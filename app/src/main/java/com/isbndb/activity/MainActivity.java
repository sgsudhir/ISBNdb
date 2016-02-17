package com.isbndb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.isbndb.R;
import com.isbndb.helper.SearchHistory;
import com.isbndb.network.JSONRequest;
import com.isbndb.utility.ApiDetails;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher {
    AutoCompleteTextView textViewSearch;
    Button buttonSearch, buttonClear, buttonSetting;
    SearchHistory history;
    String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        history = new SearchHistory(getApplicationContext());

        String []userHistory = history.getHistory();
        Log.d("Count",history.getHistoryLength() + ": Ok");
        for (int i=0; i< userHistory.length ;i++)
            Log.d("Data: ", userHistory[i] + " Ok");

        textViewSearch = (AutoCompleteTextView) findViewById(R.id.searchbar_activity_main);
        buttonSearch = (Button) findViewById(R.id.button_search_activity_main);
        buttonClear = (Button) findViewById(R.id.button_clear_activity_main);
        buttonSetting = (Button) findViewById(R.id.button_setting_activity_main);

        buttonClear.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonSetting.setOnClickListener(this);
        textViewSearch.addTextChangedListener(this);
        buttonClear.setVisibility(View.INVISIBLE);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,userHistory);
        textViewSearch.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_clear_activity_main:
                textViewSearch.setText("");
                buttonClear.setVisibility(View.INVISIBLE);
                break;
            case R.id.button_search_activity_main:
                searchQuery = null;
                searchQuery = textViewSearch.getText().toString().trim();
                if (searchQuery.isEmpty()) {
                    Toast.makeText(this, "Please enter a keyword to search !", Toast.LENGTH_SHORT).show();
                    break;
                }
                searchQuery = searchQuery.replaceAll("  ", "+");
                searchQuery = searchQuery.replaceAll(" ", "+");
                new JSONRequest(this, new ApiDetails(null).getBooks(searchQuery), null) {
                    @Override
                    protected void onJSONErrorResponse(String response) {
                        Log.d("Error Listener: ", response + ": ERROR");
                    }

                    @Override
                    protected void onJSONResponse(String response) {
                        Log.d("Response: ", response + ": Success");
                    }
                }.execute("first");
                break;
            case R.id.button_setting_activity_main:
             //   startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (count > 0)
            buttonClear.setVisibility(View.VISIBLE);
        else
            buttonClear.setVisibility(View.INVISIBLE);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}
