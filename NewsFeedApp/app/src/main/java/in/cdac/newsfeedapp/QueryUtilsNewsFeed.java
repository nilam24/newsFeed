package in.cdac.newsfeedapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dell1 on 28/01/2018.
 */


public class QueryUtilsNewsFeed {


    private static String TAG = QueryUtilsNewsFeed.class.getName();
    private static HttpURLConnection connection = null;
    private static InputStream inputStream = null;


    private QueryUtilsNewsFeed() {
    }


    public static List<NewsPojo> fetchDataFromQueryUtils(String request) {
        List<NewsPojo> newsPojoList = new ArrayList<NewsPojo>();

        try {
            URL url = createURL(request);
            String response = makeConnection(url);
            newsPojoList = extractJsonNewsFeedString(response);

        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
        }


        return newsPojoList;


    }


    private static URL createURL(String requsetUrl) {
        URL url = null;

        try {
            url = new URL(requsetUrl);
        } catch (MalformedURLException e) {

            e.printStackTrace();
        }

        return url;
    }


    private static String makeConnection(URL url) {
        String jsonRes = "";

        if (url == null) {
            return jsonRes;

        }

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {

                Log.e(TAG, "connection ok");
                inputStream = connection.getInputStream();
                jsonRes = readFromInputStream(inputStream);

            } else {
                Log.e(TAG, "connection==" + connection.getResponseMessage());
            }
        } catch (IOException e) {

            e.printStackTrace();

        }

        return jsonRes;

    }


    private static String readFromInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        try {

            if (inputStream != null) {

                inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();

                while (line != null) {

                    stringBuilder.append(line);
                    line = reader.readLine();
                }

                Log.e(TAG, "" + stringBuilder);


                return stringBuilder.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (connection != null) {
                connection.disconnect();
            }

            if (inputStream != null) {

                try {
                    inputStream.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }


        return stringBuilder.toString();

    }

    private static List<NewsPojo> extractJsonNewsFeedString(String jsonResponse) throws ParseException {
        Bitmap bitmapExtracted = null;

        List<NewsPojo> newsPojoList = new ArrayList<NewsPojo>();
        String webTitle = "", sectionName = "", webPublicationDate = "", contributorName = "", webUrl = "";
        String firstName = "", lastName = "", authorName = "";
        String murlImage = null;

        if (jsonResponse == null) {

            Log.e(TAG, "" + jsonResponse);

            return null;

        }

        try {

            JSONObject rootjsonobj = new JSONObject(jsonResponse);
            JSONObject responseobj = rootjsonobj.getJSONObject("response");
            JSONArray resultArray = responseobj.getJSONArray("results");

            int i = 0;

            for (i = 0; i < resultArray.length(); i++) {
                JSONObject resultObj = resultArray.getJSONObject(i);

                if (resultObj.has("sectionName")) {

                    sectionName = resultObj.getString("sectionName");
                }

                if (resultObj.has("webPublicationDate")) {

                    webPublicationDate = resultObj.getString("webPublicationDate");

                }

                if (resultObj.has("webTitle")) {

                    webTitle = resultObj.getString("webTitle");

                }


                if (resultObj.has("webUrl")) {

                    webUrl = resultObj.optString("webUrl");
                }


                if (resultObj.has("tags")) {

                    JSONArray tagsArray = resultObj.optJSONArray("tags");

                    for (int j = 0; j < tagsArray.length(); j++) {
                        JSONObject tagJsonObj = tagsArray.getJSONObject(j);

                        if (tagJsonObj.has("type")) {

                            contributorName = tagJsonObj.getString("type");
                            Log.e(TAG, "type" + contributorName);

                        }


                        if (tagJsonObj.has("firstName")) {

                            firstName = tagJsonObj.optString("firstName");

                        }

                        if (tagJsonObj.has("lastName")) {
                            lastName = tagJsonObj.optString("lastName");

                        }


                        if (tagJsonObj.has("bylineImageUrl")) {

                            murlImage = tagJsonObj.optString("bylineImageUrl");
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(murlImage);

                            URL url = null;

                            try {
                                url = new URL(stringBuilder.toString());
                            } catch (MalformedURLException e) {

                                e.printStackTrace();
                            }

                            try {

                                connection = (HttpURLConnection) url.openConnection();
                                connection.setReadTimeout(15000);
                                connection.setConnectTimeout(10000);
                                connection.setRequestMethod("GET");
                                connection.connect();

                            } catch (IOException e) {

                                e.printStackTrace();
                            }

                            try {

                                if (connection.getResponseCode() == 200) {

                                    if (connection.getInputStream() != null) {

                                        inputStream = connection.getInputStream();
                                        bitmapExtracted = BitmapFactory.decodeStream(inputStream);

                                    } else {

                                        Log.e("input stream is", "null" + inputStream);
                                    }


                                }
                            } catch (IOException e) {

                                e.printStackTrace();

                            }


                        }

                    }
                }

                Log.e("first name", "last name" + firstName + "," + lastName + " <<>>>>" + bitmapExtracted);


                //   Instant instant=Instant.parse(webPublicationDate);

                //   Log.e(TAG,"instant"+instant);


                NewsPojo newsPojo = new NewsPojo(webTitle, sectionName, webPublicationDate, webUrl, firstName, lastName, bitmapExtracted);

                newsPojoList.add(newsPojo);

            }
        } catch (JSONException e) {

            e.printStackTrace();
        } catch (OutOfMemoryError o) {
            o.printStackTrace();
        }

        return newsPojoList;

    }

}
