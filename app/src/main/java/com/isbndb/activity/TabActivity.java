package com.isbndb.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.Toast;

import com.isbndb.R;
import com.isbndb.activity.tab.AuthorsActivity;
import com.isbndb.activity.tab.BooksActivity;
import com.isbndb.activity.tab.CategoriesActivity;
import com.isbndb.activity.tab.PublishersActivity;
import com.isbndb.activity.tab.SubjectsActivity;
import com.isbndb.app.AppController;
import com.isbndb.network.JSONRequest;
import com.isbndb.utility.ApiDetails;

public class TabActivity extends android.app.TabActivity {
    TabHost tabHost;
    TabHost.TabSpec tabSpecBooks, tabSpecAuthors, tabSpecPublishers, tabSpecSubjects, tabSpecCategories;
    Intent intentBooks, intentAuthors, intentPublishers, intentSubjects, intentCategories;
    String searchQuery;
    ApiDetails apiDetails;
    ProgressDialog dialog;
    int dialogVote = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        searchQuery = getIntent().getStringExtra("searchQuery");
        tabHost = getTabHost();

        tabSpecBooks = tabHost.newTabSpec("Books");
        tabSpecAuthors = tabHost.newTabSpec("Authors");
        tabSpecPublishers = tabHost.newTabSpec("Publishers");
        tabSpecSubjects = tabHost.newTabSpec("Subjects");
        tabSpecCategories = tabHost.newTabSpec("Categories");

        tabSpecBooks.setIndicator("Books");
        tabSpecAuthors.setIndicator("Authors");
        tabSpecPublishers.setIndicator("Publishers");
        tabSpecSubjects.setIndicator("Subjects");
        tabSpecCategories.setIndicator("Categories");

        intentBooks = new Intent(TabActivity.this, BooksActivity.class);
        intentAuthors = new Intent(TabActivity.this, AuthorsActivity.class);
        intentPublishers = new Intent(TabActivity.this, PublishersActivity.class);
        intentSubjects = new Intent(TabActivity.this, SubjectsActivity.class);
        intentCategories = new Intent(TabActivity.this, CategoriesActivity.class);

        apiDetails = new ApiDetails(AppController.getKeyManagerInstant().getKey());
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait ...\nFetching details.");
        dialog.setCancelable(false);
        searchBooks(searchQuery);
        searchAuthors(searchQuery);
        searchPublishers(searchQuery);
        searchSubjects(searchQuery);
        searchCategories(searchQuery);

    }

    protected void searchBooks(String searchQuery) {
        if (!dialog.isShowing())
            dialog.show();
        new JSONRequest(this, apiDetails.getBooks(searchQuery), null) {
            @Override
            protected void onJSONErrorResponse(String response) {
                dialogVote ++;
                if (dialogVote == 5 && dialog.isShowing())
                    dialog.hide();
                Log.d("Response Error: :",response.toString() + ":Error");
            }

            @Override
            protected void onJSONResponse(String response) {
                dialogVote ++;
                if (dialogVote == 5 && dialog.isShowing())
                    dialog.hide();
                Log.d("Response: :",response.toString() + ":Success");
                intentBooks.putExtra("json_object", response.toString());
                tabSpecBooks.setContent(intentBooks);
                tabHost.addTab(tabSpecBooks);
            }
        }.execute("TAG_BOOKS");
    }

    protected void searchAuthors(String searchQuery) {
        if (!dialog.isShowing())
            dialog.show();
        new JSONRequest(this, apiDetails.getAuthors(searchQuery), null) {
            @Override
            protected void onJSONErrorResponse(String response) {
                dialogVote ++;
                if (dialogVote == 5 && dialog.isShowing())
                    dialog.hide();
                Log.d("Response Error: :",response.toString() + ":Error");
            }

            @Override
            protected void onJSONResponse(String response) {
                dialogVote ++;
                if (dialogVote == 5 && dialog.isShowing())
                    dialog.hide();
                Log.d("Response: :",response.toString() + ":Success");
                intentAuthors.putExtra("json_object", response.toString());
                tabSpecAuthors.setContent(intentAuthors);
                tabHost.addTab(tabSpecAuthors);
            }
        }.execute("TAG_AUTHORS");
    }

    protected void searchPublishers(String searchQuery) {
        if (!dialog.isShowing())
            dialog.show();
        new JSONRequest(this, apiDetails.getPublishers(searchQuery), null) {
            @Override
            protected void onJSONErrorResponse(String response) {
                dialogVote ++;
                if (dialogVote == 5 && dialog.isShowing())
                    dialog.hide();
                Log.d("Response Error: :",response.toString() + ":Error");
            }

            @Override
            protected void onJSONResponse(String response) {
                dialogVote ++;
                if (dialogVote == 5 && dialog.isShowing())
                    dialog.hide();
                Log.d("Response: :",response.toString() + ":Success");
                intentPublishers.putExtra("json_object", response.toString());
                tabSpecPublishers.setContent(intentPublishers);
                tabHost.addTab(tabSpecPublishers);
            }
        }.execute("TAG_PUBLISHERS");
    }

    protected void searchSubjects(String searchQuery) {
        if (!dialog.isShowing())
            dialog.show();
        new JSONRequest(this, apiDetails.getSubjects(searchQuery), null) {
            @Override
            protected void onJSONErrorResponse(String response) {
                dialogVote ++;
                if (dialogVote == 5 && dialog.isShowing())
                    dialog.hide();
                Log.d("Response Error: :", response + ":Error");
            }

            @Override
            protected void onJSONResponse(String response) {
                dialogVote ++;
                if (dialogVote == 5 && dialog.isShowing())
                    dialog.hide();
                Log.d("Response: :",response + ":Success");
                intentSubjects.putExtra("json_object", response.toString());
                tabSpecSubjects.setContent(intentSubjects);
                tabHost.addTab(tabSpecSubjects);
            }
        }.execute("TAG_SUBJECTS");
    }

    protected void searchCategories(String searchQuery) {
        if (!dialog.isShowing())
            dialog.show();
        new JSONRequest(this, apiDetails.getCategories(searchQuery), null) {
            @Override
            protected void onJSONErrorResponse(String response) {
                dialogVote ++;
                if (dialogVote == 5 && dialog.isShowing())
                    dialog.hide();
                Log.d("Response Error: :",response.toString() + ":Error");
            }

            @Override
            protected void onJSONResponse(String response) {
                dialogVote ++;
                if (dialogVote == 5 && dialog.isShowing())
                    dialog.hide();
                Log.d("Response: :",response.toString() + ":Success");
                intentCategories.putExtra("json_object", response.toString());
                tabSpecCategories.setContent(intentCategories);
                tabHost.addTab(tabSpecCategories);
            }
        }.execute("TAG_CATEGORIES");
    }
}
