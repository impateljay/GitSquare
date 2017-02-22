package com.jay.gitsquare;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Jay on 22-02-2017.
 */

public interface ApiInterface {
    @GET("repos/square/retrofit/contributors")
    Call<List<Contributor>> getContributors();
}
