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
public class BooksActivity extends Activity implements View.OnClickListener{
    Button buttonPageNext, buttonPagePrevious;
    TextView textViewPages;
    ListView listViewBooks;
    String stringJsonObject;
    int currentPage, resultCount, pageCount,currentPageData;
    String bookTitles[], bookISBNs[], bookAuthors[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_books);
        stringJsonObject = getIntent().getStringExtra("json_object");
        buttonPageNext = (Button) findViewById(R.id.button_page_next_tab_books);
        buttonPagePrevious = (Button) findViewById(R.id.button_page_previous_tab_books);
        textViewPages = (TextView) findViewById(R.id.text_view_pages_tab_books);
        listViewBooks = (ListView) findViewById(R.id.list_view_books);
        buttonPagePrevious.setOnClickListener(this);
        buttonPageNext.setOnClickListener(this);
        if (getMetaData(stringJsonObject))
            if (getBooksDetails(stringJsonObject))
                listViewBooks.setAdapter(new ListViewAdapter(3, bookTitles, bookISBNs, bookAuthors, this));
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
            bookTitles = new String[currentPageData];
            bookISBNs = new String[currentPageData];
            bookAuthors = new String[currentPageData];
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    protected boolean getBooksDetails(String jsonObjectString) {
        JSONObject rootObject, innerObject, authorObject;
        JSONArray rootArray, authorArray;
        try {
            rootObject = new JSONObject(jsonObjectString);
            rootArray = rootObject.getJSONArray("data");
            for (int i=0; i< rootArray.length(); i++){
                innerObject = rootArray.getJSONObject(i);
                bookTitles[i] = innerObject.getString("title");
                bookISBNs[i] = "ISBN13: " + innerObject.getString("isbn13");
                bookAuthors[i] = null;
                authorArray = innerObject.getJSONArray("author_data");
                for (int j=0; j<authorArray.length(); j++){
                    authorObject = authorArray.getJSONObject(j);
                    if (bookAuthors[i] == null)
                        bookAuthors[i] = "Author: " + authorObject.getString("name");
                    else
                        bookAuthors[i] = bookAuthors[i] + ", " + authorObject.getString("name");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_page_previous_tab_books:
                break;
            case R.id.button_page_next_tab_books:
                break;
            default:
                break;
        }
    }
}
