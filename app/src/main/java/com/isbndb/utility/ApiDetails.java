package com.isbndb.utility;

/**
 * Created by Sgsudhir on 2/17/2016.
 * http://isbndb.com/api/v2/json/J2LK3L4F/books?q=you_can_win
 */
public class ApiDetails {
    public static final String DEFAULT_API_TOKEN = "OG2GSEEE";
    public static final String BASE_URL = "http://isbndb.com/api/v2/json/";
    private String apiToken;

    public ApiDetails (String apiToken) {
        if (apiToken == null)
            this.apiToken = DEFAULT_API_TOKEN;
        else
            this.apiToken = apiToken;
    }

    public String getBooks(String query) {
        return BASE_URL + apiToken + "/books?q=" + query;
    }

    public String getAuthors(String query) {
        return BASE_URL + apiToken + "/authors?q=" + query;
    }

    public String getPublishers(String query) {
        return BASE_URL + apiToken + "/publishers?q=" + query;
    }

    public String getSubjects(String query) {
        return BASE_URL + apiToken + "/subjects?q=" + query;
    }

    public String getPrices(String query) {
        return BASE_URL + apiToken + "/prices/" + query;
    }

    public String getBook(String query) {
        return BASE_URL + apiToken + "/book/" + query;
    }

    public String getAuthor(String query) {
        return BASE_URL + apiToken + "/author/" + query;
    }

    public String getPublisher(String query) {
        return BASE_URL + apiToken + "/publisher/" + query;
    }

    public String getSubject(String query) {
        return BASE_URL + apiToken + "/subject/" + query;
    }

    public String getCategory(String query) {
        return BASE_URL + apiToken + "/category/" + query;
    }
}
