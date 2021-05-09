package com.example.android.newsfeeder;

public class News {

    // heading
    private String mHeading;

    // body
    private String mBody;

    // timestamp
    private  String mTime;

    private String mUrl;

    /**
     *
     * @param heading is the heading of the article
     * @param body is the body of the article
     * @param time is the time stamp of the article
     */
    public News(String heading , String body , String time , String url)
    {
        mHeading = heading;
        mBody = body;
        mTime = time;
        mUrl = url;
    }

    /**
     *
     * @return Heading
     */
    public String getHeading() {
        return mHeading;
    }

    /**
     *
     * @return Body
     */
    public String getBody() {
        return mBody;
    }

    /**
     *
     * @return Time Stamp
     */
    public String getTime() {
        return mTime;
    }

    /**
     *
     * @return url of the webpage
     */
    public String getUrl() {return mUrl; }
}
