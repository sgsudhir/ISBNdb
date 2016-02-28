package com.isbndb.activity.tab;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.isbndb.R;
import com.isbndb.adapter.ListViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Sgsudhir on 2/27/2016.
 */
public class AuthorsActivity extends Activity implements View.OnClickListener{
    Button buttonPageNext, buttonPagePrevious;
    TextView textViewPages;
    ListView listViewAuthors;
    String stringJsonObject;
    int currentPage, resultCount, pageCount,currentPageData;
    String authorNames[], authorIds[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_authors);
        stringJsonObject = getIntent().getStringExtra("json_object");
        buttonPageNext = (Button) findViewById(R.id.button_page_next_tab_authors);
        buttonPagePrevious = (Button) findViewById(R.id.button_page_previous_tab_authors);
        textViewPages = (TextView) findViewById(R.id.text_view_pages_tab_authors);
        listViewAuthors = (ListView) findViewById(R.id.list_view_authors);
        buttonPagePrevious.setOnClickListener(this);
        buttonPageNext.setOnClickListener(this);
        if (getMetaData(stringJsonObject))
            if (getAuthorsDetails(stringJsonObject))
                listViewAuthors.setAdapter(new ListViewAdapter(1, authorNames, null, null, this));
    }

    protected boolean getMetaData(String jsonObjectString) {
        JSONObject object;
        JSONArray array;
        try {
            object = new JSONObject(jsonObjectString.toString());
            currentPage = object.getInt("current_page");
            resultCount = object.getInt("result_count");
            pageCount = object.getInt("page_count");
            array = object.getJSONArray("data");
            currentPageData = array.length();
            textViewPages.setText(currentPage + "/" + pageCount);
            if (currentPage >= pageCount && buttonPagePrevious.getVisibility() == View.VISIBLE)
                buttonPageNext.setVisibility(View.INVISIBLE);
            if (currentPage <= 1 && buttonPagePrevious.getVisibility() == View.VISIBLE)
                buttonPagePrevious.setVisibility(View.INVISIBLE);
            authorNames = new String[currentPageData];
            authorIds = new String[currentPageData];
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    protected boolean getAuthorsDetails(String jsonObjectString) {
        JSONObject object, innerObject;
        JSONArray array;
        try {
            object = new JSONObject(jsonObjectString.toString());
            array = object.getJSONArray("data");
            for (int i=0; i<array.length(); i++) {
                innerObject = array.getJSONObject(i);
                authorNames[i] = innerObject.getString("name");
                authorIds[i] = innerObject.getString("author_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}
