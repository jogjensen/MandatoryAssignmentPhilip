package com.example.mandatoryassignmentphilip;

public class ApiUtils {

    private static final String BASE_URL = "https://anbo-restmessages.azurewebsites.net/api/messages/";

    public static TwitterService getTwitterSerivce() {
        return retrofitClient.getClient(BASE_URL).create(TwitterService.class);
    }
}

