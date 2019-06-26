package com.example.udrecipesbakingapp.Activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.udrecipesbakingapp.R;
import com.example.udrecipesbakingapp.Adapters.RecipeAdapter;
import com.example.udrecipesbakingapp.Helpers.RecipeRequestAPIService;
import com.example.udrecipesbakingapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_recipe_list;
    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("log", "created");
        initViews();
    }

    private void initViews(){
        rv_recipe_list = (RecyclerView)findViewById(R.id.rv_recipe_list);
        rv_recipe_list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_recipe_list.setLayoutManager(layoutManager);



        loadJSON();
    }

    private void loadJSON(){

        try {
            String url = "https://d17h27t6h515a5.cloudfront.net/";
            Log.i("autolog", "https://d17h27t6h515a5.cloudfront.net/");

            Retrofit retrofit = null;
            Log.i("autolog", "retrofit");

            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Log.i("autolog", "build();");
            }


            RecipeRequestAPIService service = retrofit.create(RecipeRequestAPIService.class);
            Log.i("autolog", " APIService service = retrofit.create(APIService.class);");


            Call<List<Recipe>> call = service.getJSON();
            Log.i("autolog", "Call<List<Recipe>> call = service.getJSON();");

            call.enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                    Log.i("autolog", "onResponse");

                    recipeList = response.body();
                    Log.i("autolog", "List<Recipe> recipeList = response.body();");

                    RecipeAdapter recipeAdapter = new RecipeAdapter(recipeList, getApplicationContext());
                    Log.i("autolog", "RecipeAdapter recipeAdapter =new recipeAdapter(getApplicationContext(), userList);");
                    rv_recipe_list.setAdapter(recipeAdapter);
                    Log.i("autolog", "recyclerView.setAdapter(recipeAdapter);");

                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Log.i("autologFailure", t.getMessage());
                }
            });



        }catch (Exception e) {Log.i("autologException", "Exception");}

    }

}
