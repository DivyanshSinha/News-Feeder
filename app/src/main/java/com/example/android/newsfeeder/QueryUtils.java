package com.example.android.newsfeeder;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    //private static final String SAMPLE_JSON_RESPONSE = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":154136,\"startIndex\":11,\"pageSize\":10,\"currentPage\":2,\"pages\":15414,\"orderBy\":\"relevance\",\"results\":[{\"id\":\"uk-news/2021/mar/06/meghan-markle-stirs-public-debate-ahead-of-oprah-interview\",\"type\":\"article\",\"sectionId\":\"uk-news\",\"sectionName\":\"UK news\",\"webPublicationDate\":\"2021-03-06T20:38:06Z\",\"webTitle\":\"Harry and Meghan stir public debate ahead of Oprah interview\",\"webUrl\":\"https://www.theguardian.com/uk-news/2021/mar/06/meghan-markle-stirs-public-debate-ahead-of-oprah-interview\",\"apiUrl\":\"https://content.guardianapis.com/uk-news/2021/mar/06/meghan-markle-stirs-public-debate-ahead-of-oprah-interview\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"fashion/2021/feb/27/trayvon-martin-hoodies-black-young-people\",\"type\":\"article\",\"sectionId\":\"fashion\",\"sectionName\":\"Fashion\",\"webPublicationDate\":\"2021-02-27T08:00:19Z\",\"webTitle\":\"Nine years after Trayvon Martin’s killing, hoodies still spark debate\",\"webUrl\":\"https://www.theguardian.com/fashion/2021/feb/27/trayvon-martin-hoodies-black-young-people\",\"apiUrl\":\"https://content.guardianapis.com/fashion/2021/feb/27/trayvon-martin-hoodies-black-young-people\",\"isHosted\":false,\"pillarId\":\"pillar/lifestyle\",\"pillarName\":\"Lifestyle\"},{\"id\":\"music/2021/apr/10/music-streaming-debate-what-songwriter-artist-and-industry-insider-say-publication-parliamentary-report\",\"type\":\"article\",\"sectionId\":\"music\",\"sectionName\":\"Music\",\"webPublicationDate\":\"2021-04-10T07:00:01Z\",\"webTitle\":\"The music streaming debate: what the artists, songwriters and industry insiders say\",\"webUrl\":\"https://www.theguardian.com/music/2021/apr/10/music-streaming-debate-what-songwriter-artist-and-industry-insider-say-publication-parliamentary-report\",\"apiUrl\":\"https://content.guardianapis.com/music/2021/apr/10/music-streaming-debate-what-songwriter-artist-and-industry-insider-say-publication-parliamentary-report\",\"isHosted\":false,\"pillarId\":\"pillar/arts\",\"pillarName\":\"Arts\"},{\"id\":\"world/2021/mar/14/britons-divided-by-ethnicity-age-debate-over-race\",\"type\":\"article\",\"sectionId\":\"world\",\"sectionName\":\"World news\",\"webPublicationDate\":\"2021-03-14T09:45:28Z\",\"webTitle\":\"Britons divided by their ethnicity and age in debate over race\",\"webUrl\":\"https://www.theguardian.com/world/2021/mar/14/britons-divided-by-ethnicity-age-debate-over-race\",\"apiUrl\":\"https://content.guardianapis.com/world/2021/mar/14/britons-divided-by-ethnicity-age-debate-over-race\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"world/2021/mar/10/mps-hit-back-after-india-summons-envoy-over-farmers-protest-debate\",\"type\":\"article\",\"sectionId\":\"world\",\"sectionName\":\"World news\",\"webPublicationDate\":\"2021-03-10T16:29:33Z\",\"webTitle\":\"MPs hit back after India summons envoy over farmers' protest debate\",\"webUrl\":\"https://www.theguardian.com/world/2021/mar/10/mps-hit-back-after-india-summons-envoy-over-farmers-protest-debate\",\"apiUrl\":\"https://content.guardianapis.com/world/2021/mar/10/mps-hit-back-after-india-summons-envoy-over-farmers-protest-debate\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"australia-news/2021/mar/09/harry-and-meghan-interview-stirs-debate-about-australia-becoming-a-republic\",\"type\":\"article\",\"sectionId\":\"australia-news\",\"sectionName\":\"Australia news\",\"webPublicationDate\":\"2021-03-09T04:31:21Z\",\"webTitle\":\"Harry and Meghan interview stirs debate about Australia becoming a republic\",\"webUrl\":\"https://www.theguardian.com/australia-news/2021/mar/09/harry-and-meghan-interview-stirs-debate-about-australia-becoming-a-republic\",\"apiUrl\":\"https://content.guardianapis.com/australia-news/2021/mar/09/harry-and-meghan-interview-stirs-debate-about-australia-becoming-a-republic\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"fashion/2021/mar/07/we-not-the-gatekeepers-the-golden-globes-spark-an-inclusion-debate\",\"type\":\"article\",\"sectionId\":\"fashion\",\"sectionName\":\"Fashion\",\"webPublicationDate\":\"2021-03-07T13:58:54Z\",\"webTitle\":\"'We're not the gatekeepers':  the Golden Globes spark an inclusion debate\",\"webUrl\":\"https://www.theguardian.com/fashion/2021/mar/07/we-not-the-gatekeepers-the-golden-globes-spark-an-inclusion-debate\",\"apiUrl\":\"https://content.guardianapis.com/fashion/2021/mar/07/we-not-the-gatekeepers-the-golden-globes-spark-an-inclusion-debate\",\"isHosted\":false,\"pillarId\":\"pillar/lifestyle\",\"pillarName\":\"Lifestyle\"},{\"id\":\"commentisfree/2021/apr/16/debate-tv-diversity-bbc-idris-elba-luther\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2021-04-16T13:57:01Z\",\"webTitle\":\"We need a proper debate about TV diversity, instead we get ludicrous arguments | Ellen E Jones\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2021/apr/16/debate-tv-diversity-bbc-idris-elba-luther\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2021/apr/16/debate-tv-diversity-bbc-idris-elba-luther\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"global-development/2021/feb/25/malawi-mps-debate-bill-to-liberalise-abortion-laws-as-churches-oppose\",\"type\":\"article\",\"sectionId\":\"global-development\",\"sectionName\":\"Global development\",\"webPublicationDate\":\"2021-02-25T07:15:20Z\",\"webTitle\":\"Malawi MPs debate bill to liberalise abortion laws as churches oppose\",\"webUrl\":\"https://www.theguardian.com/global-development/2021/feb/25/malawi-mps-debate-bill-to-liberalise-abortion-laws-as-churches-oppose\",\"apiUrl\":\"https://content.guardianapis.com/global-development/2021/feb/25/malawi-mps-debate-bill-to-liberalise-abortion-laws-as-churches-oppose\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"uk-news/2021/feb/21/keep-out-of-bristols-slaver-street-names-debate-ministers-are-told\",\"type\":\"article\",\"sectionId\":\"uk-news\",\"sectionName\":\"UK news\",\"webPublicationDate\":\"2021-02-21T08:15:55Z\",\"webTitle\":\"Keep out of Bristol’s slaver street names debate, ministers are told\",\"webUrl\":\"https://www.theguardian.com/uk-news/2021/feb/21/keep-out-of-bristols-slaver-street-names-debate-ministers-are-told\",\"apiUrl\":\"https://content.guardianapis.com/uk-news/2021/feb/21/keep-out-of-bristols-slaver-street-names-debate-ministers-are-told\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"}]}}";

    private QueryUtils(){}

    /**
     * Query the Guardian api and return a list of {@link News} objects.
     */
    public static List<News> fetchNewsData(String requestUrl) {

        Log.v("QueryUtils" , "fetchNewsData is working");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<News> books = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return books;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<News> extractFeatureFromJson(String newsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding news to
        ArrayList<News> news = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try
        {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray resultsArray = response.getJSONArray("results");
            for(int i=0 ; i<resultsArray.length();i++)
            {
                JSONObject currentNews = resultsArray.getJSONObject(i);
                String heading = currentNews.getString("sectionName");
                String time = currentNews.getString("webPublicationDate");
                String body = currentNews.getString("webTitle");
                String url = currentNews.getString("webUrl");

                String time_new = time.substring(0,10) + ' ' + ' ' + ' ' + time.substring(12,16);

                News headline = new News(heading , body , time_new , url);
                news.add(headline);

            }

        }
        catch (JSONException e)
        {
            Log.e("QueryUtils","Problem Parsing the news json" , e);
        }

        // Return the list of earthquakes
        return news;
    }


//    public  static ArrayList<News> extractNews()
//    {
//        ArrayList<News> news = new ArrayList<>();
//
//        try
//        {
//            JSONObject baseJsonResponse = new JSONObject(SAMPLE_JSON_RESPONSE);
//            JSONObject response = baseJsonResponse.getJSONObject("response");
//            JSONArray resultsArray = response.getJSONArray("results");
//            for(int i=0 ; i<resultsArray.length();i++)
//            {
//                JSONObject currentNews = resultsArray.getJSONObject(i);
//                String heading = currentNews.getString("sectionName");
//                String time = currentNews.getString("webPublicationDate");
//                String body = currentNews.getString("webTitle");
//                String url = currentNews.getString("webUrl");
//
//                String time_new = time.substring(0,10) + ' ' + ' ' + ' ' + time.substring(12,16);
//
//                News headline = new News(heading , body , time_new , url);
//                news.add(headline);
//
//            }
//
//        }
//        catch (JSONException e)
//        {
//            Log.e("QueryUtils","Problem Parsing the news json" , e);
//        }
//
//        return news;
//    }

}
