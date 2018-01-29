package com.pg.helloworld;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by test on 6/10/17.
 */

public interface ApiInterface {

    @GET("get_leaderboard.php?action=get_leaderboard")
    Call<TrainerModel> getLeaderBoardData(@Query("member_id") String apiKey);


}
