package com.bi.replicate.interfaces;

import com.bi.replicate.model.ApiRequestModel;
import com.bi.replicate.utils.Consts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface InternetInterface {
    // Register User
    @GET(Consts.API_URL)
    Call<ApiRequestModel> request();
}
