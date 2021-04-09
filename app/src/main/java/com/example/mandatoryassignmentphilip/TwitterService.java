package com.example.mandatoryassignmentphilip;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TwitterService {

    @GET("Messages")
    Call<List<TwitterMessage>> getAllMessages();


    @POST("Messages")
    Call<TwitterMessage> saveTwitterBody(@Body TwitterMessage twitterMessage);

}
