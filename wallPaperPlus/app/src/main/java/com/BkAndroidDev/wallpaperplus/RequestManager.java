package com.BkAndroidDev.wallpaperplus;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class RequestManager {

    private Context context;

    private Retrofit retrofit  = new Retrofit.Builder().baseUrl("https://api.pexels.com/v1/").addConverterFactory(GsonConverterFactory.create()).build();

    public RequestManager(Context context){
        this.context = context;
    }

    public  void getResponse(onDataFetchListener listener,String PageNumber){

        getImage image = retrofit.create(getImage.class);
        Call<CurratedResponse> responseCall = image.response(PageNumber,"16");

            responseCall.enqueue(new Callback<CurratedResponse>() {
                @Override
                public void onResponse(Call<CurratedResponse> call, Response<CurratedResponse> response) {
                    listener.onDataFetchListener(response.body());
                }

                @Override
                public void onFailure(Call<CurratedResponse> call, Throwable t) {

                }
            });
    }

    public void searchImage(searchCall listener,String PageNumber,String query){
        searchImage image = retrofit.create(searchImage.class);
        Call<searchResponse> imageResponse = image.searchWallPaper(query,PageNumber,"16");
        imageResponse.enqueue(new Callback<searchResponse>() {
            @Override
            public void onResponse(Call<searchResponse> call, Response<searchResponse> response) {
                listener.searchImage(response.body());
            }

            @Override
            public void onFailure(Call<searchResponse> call, Throwable t) {

            }
        });
    }
    private interface getImage{
        @Headers({
                "Accept: application/json",
                "Authorization: 563492ad6f91700001000001ec6be7ccbc13401c881680148e6548cf"
        })
        @GET("curated/")
        Call<CurratedResponse> response(
                @Query("page") String page,
                @Query("per_page") String per_page
        );

    }
    private interface searchImage{
        @Headers({
                "Accept: application/json",
                "Authorization: 563492ad6f91700001000001ec6be7ccbc13401c881680148e6548cf"
        })
        @GET("search")
        Call<searchResponse> searchWallPaper(
                @Query("query")String query,
                @Query("page") String page,
                @Query("per_page") String per_page
        );
    }
}
