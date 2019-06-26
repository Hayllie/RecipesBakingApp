package com.example.udrecipesbakingapp.Helpers;

import com.example.udrecipesbakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeRequestAPIService {

   @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getJSON();

}
