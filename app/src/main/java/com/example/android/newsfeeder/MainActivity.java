package com.example.android.newsfeeder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    /** URL for Book data from the Google Books dataset */
    private String partA = "https://content.guardianapis.com/search?";
    private String partB = "q=";
    private String partC = "api-key=96ebdc04-0125-49cc-b486-f90221506c24";
//    private String REQUEST_URL =
//            "https://content.guardianapis.com/search?api-key=96ebdc04-0125-49cc-b486-f90221506c24";
    private String REQUEST_URL =
        partA+partC;

    private NewsAdapter adapter;

    // String for search
    public String search;

    /**
     * Constant value for the news loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;

    /** TextView that is displayed when the list is empty */
    private ImageView mEmptyStateImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

        adapter = new NewsAdapter(this , new ArrayList<News>());

        ListView newsListView = (ListView) findViewById(R.id.list);

        newsListView.setAdapter(adapter);

        //Code for empty state view
        mEmptyStateImageView = (ImageView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateImageView);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateImageView.setImageResource(R.drawable.offline);
        }

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                News currentNews = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        Log.v(LOG_TAG , "onCreateLoader is working");
        // Create a new loader for the given URL
        return new NewsLoader(this, REQUEST_URL);
    }


    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> news) {
        // Clear the adapter of previous earthquake data
        adapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            adapter.addAll(news);
        }

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

//        mEmptyStateTextView.setVisibility(View.VISIBLE);
        mEmptyStateImageView.setImageResource(R.drawable.no_news);

        Log.v(LOG_TAG , "onLoadFinished is working");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        adapter.clear();
        Log.v(LOG_TAG , "onLoaderReset is working");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searcher , menu);

        MenuItem menuItem = menu.findItem(R.id.search_button);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search News");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search = s;
                REQUEST_URL = partA+partB+s+"&"+partC;

                getLoaderManager().restartLoader(NEWS_LOADER_ID,null,MainActivity.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.international:
                Toast.makeText(this,"International" , Toast.LENGTH_SHORT).show();
                REQUEST_URL = "https://content.guardianapis.com/search?api-key=96ebdc04-0125-49cc-b486-f90221506c24";
                getLoaderManager().restartLoader(NEWS_LOADER_ID,null,MainActivity.this);
                return true;
            case R.id.india:
                Toast.makeText(this,"india" , Toast.LENGTH_SHORT).show();
                REQUEST_URL = "https://content.guardianapis.com/search?q=india&api-key=96ebdc04-0125-49cc-b486-f90221506c24";
                getLoaderManager().restartLoader(NEWS_LOADER_ID,null,MainActivity.this);
                return true;
            case R.id.sports:
                Toast.makeText(this,"sports" , Toast.LENGTH_SHORT).show();
                REQUEST_URL = "https://content.guardianapis.com/search?q=sports&api-key=96ebdc04-0125-49cc-b486-f90221506c24";
                getLoaderManager().restartLoader(NEWS_LOADER_ID,null,MainActivity.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}