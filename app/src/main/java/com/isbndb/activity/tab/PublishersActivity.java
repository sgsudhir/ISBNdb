package com.isbndb.activity.tab;

import android.app.Activity;
import android.os.Bundle;
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
public class PublishersActivity extends Activity implements View.OnClickListener{
    Button buttonPageNext, buttonPagePrevious;
    TextView textViewPages;
    ListView listViewPublishers;
    String stringJsonObject;
    int currentPage, resultCount, pageCount,currentPageData;
    String publishersNames[], publishersIds[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_publishers);
        stringJsonObject = getIntent().getStringExtra("json_object");
        buttonPageNext = (Button) findViewById(R.id.button_page_next_tab_publishers);
        buttonPagePrevious = (Button) findViewById(R.id.button_page_previous_tab_publishers);
        textViewPages = (TextView) findViewById(R.id.text_view_pages_tab_publishers);
        listViewPublishers = (ListView) findViewById(R.id.list_view_publishers);
        buttonPagePrevious.setOnClickListener(this);
        buttonPageNext.setOnClickListener(this);
        if (getMetaData(stringJsonObject))
            if (getPublishersDetails(stringJsonObject))
                listViewPublishers.setAdapter(new ListViewAdapter(1, publishersNames, null, null, this));
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
            publishersNames = new String[currentPageData];
            publishersIds = new String[currentPageData];
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    protected boolean getPublishersDetails(String jsonObjectString) {
        JSONObject object, innerObject;
        JSONArray array;
        try {
            object = new JSONObject(jsonObjectString.toString());
            array = object.getJSONArray("data");
            for (int i=0; i<array.length(); i++) {
                innerObject = array.getJSONObject(i);
                publishersNames[i] = innerObject.getString("name");
                publishersIds[i] = innerObject.getString("publisher_id");
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
