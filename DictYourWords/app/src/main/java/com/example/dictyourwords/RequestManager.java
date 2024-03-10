package com.example.dictyourwords;


import android.content.Context;
import com.example.dictyourwords.Modals.ApiResponse;
import android.widget.Toast;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RequestManager {

    private Context context;
    private Retrofit retrofit= new Retrofit.Builder().baseUrl("https://api.dictionaryapi.dev/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();

    public RequestManager(Context context){
        this.context = context;
    }

    public void getMeaning(OnFetchData data,String word){
        CallDictionary dictionary = retrofit.create(CallDictionary.class);
        Call<ArrayList<ApiResponse>> responseList = dictionary.getResponse(word);
        try{
            responseList.enqueue(new Callback<ArrayList<ApiResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<ApiResponse>> call, Response<ArrayList<ApiResponse>> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(context,"Try Again Later",Toast.LENGTH_SHORT).show();
                    }
                    data.onFetchData(response.body().get(0));
                }

                @Override
                public void onFailure(Call<ArrayList<ApiResponse>> call, Throwable t) {

                }
            });

        }catch(Exception msg){

        }
    }

    private interface CallDictionary {

        @GET("entries/en/{word}")
        Call<ArrayList<ApiResponse>> getResponse(
                @Path("word") String word
        );
    }

}
