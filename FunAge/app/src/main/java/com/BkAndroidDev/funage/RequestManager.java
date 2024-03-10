package com.BkAndroidDev.funage;

import android.content.Context;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {
    private Context context;
    private Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.agify.io/").addConverterFactory(GsonConverterFactory.create()).build();

    public RequestManager(Context context){
        this.context = context;
    }

    public void getAgeListener(onDataFetchListener listener,String name){
        getAge age = retrofit.create(getAge.class);
        Call<ApiObjectModal> getAgeCall = age.Age(name);
        try{
            getAgeCall.enqueue(new Callback<ApiObjectModal>() {
                @Override
                public void onResponse(Call<ApiObjectModal> call, Response<ApiObjectModal> response) {
                    listener.onDataFetch(response.body());
                }

                @Override
                public void onFailure(Call<ApiObjectModal> call, Throwable t) {

                }
            });
        }catch(Exception msg){

        }
    }

    private interface getAge{
        @GET("?")
        Call<ApiObjectModal> Age(
         @Query("name") String name
        );
    }
}
