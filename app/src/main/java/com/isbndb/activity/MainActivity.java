package com.isbndb.activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.isbndb.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher {
    AutoCompleteTextView textViewSearch;
    Button buttonSearch, buttonClear, buttonSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewSearch = (AutoCompleteTextView) findViewById(R.id.searchbar_activity_main);
        buttonSearch = (Button) findViewById(R.id.button_search_activity_main);
        buttonClear = (Button) findViewById(R.id.button_clear_activity_main);
        buttonSetting = (Button) findViewById(R.id.button_setting_activity_main);

        buttonClear.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);
        buttonSetting.setOnClickListener(this);
        textViewSearch.addTextChangedListener(this);

        buttonClear.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_clear_activity_main:
                textViewSearch.setText("");
                buttonClear.setVisibility(View.INVISIBLE);
                break;
            case R.id.button_search_activity_main:
                break;
            case R.id.button_setting_activity_main:
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
        Toast.makeText(getApplicationContext(),s.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
