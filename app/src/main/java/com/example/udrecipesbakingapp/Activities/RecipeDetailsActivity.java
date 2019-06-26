package com.example.udrecipesbakingapp.Activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udrecipesbakingapp.Helpers.ClickCallBack;
import com.example.udrecipesbakingapp.Widget.MyAppWidget;
import com.example.udrecipesbakingapp.R;
import com.example.udrecipesbakingapp.Adapters.VideoAdapter;
import com.example.udrecipesbakingapp.models.Ingredient;
import com.example.udrecipesbakingapp.models.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements ClickCallBack {

    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    String stepJson, ingredientJson;
    boolean rotationDetails;
    private List<Step> stepList;

    private List<Ingredient> ingredientList;
    Gson gson;
    ConstraintLayout DetailsPageLayout;
    private String[] mySteps;
    private ArrayList<String> stepsArrayList = new ArrayList<String>();

    TextView ingredients_text;

    private Parcelable mListState;

    String recipeName;

    String ingredientsText;
    private RecyclerView stepsRecyclerView;
    private LinearLayoutManager mLayoutManager;

    VideoAdapter videoAdapter;

    CardView add;



    public void minIngredients(View view){

        ViewGroup.LayoutParams params = ingredients_text.getLayoutParams();
        if (params.height == 0){
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            ingredients_text.setVisibility(View.VISIBLE);
        } else {
            params.height = 0;
            ingredients_text.setVisibility(View.INVISIBLE);
        }
        ingredients_text.setLayoutParams(params);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);


        if (savedInstanceState != null) {
            rotationDetails = savedInstanceState.getBoolean("rotaionDetail");
            mListState = savedInstanceState.getParcelable("rvState");

        }

        if (savedInstanceState == null) {

            ingredients_text = findViewById(R.id.ingredients_text);
            DetailsPageLayout = findViewById(R.id.DetailsPageLayout);

            add = findViewById(R.id.add);


            stepJson = getIntent().getStringExtra("StepsExtra");
            if (stepJson != null)
                Log.i("HayllieInfo", "stepsList received");
            ingredientJson = getIntent().getStringExtra("IngredientsExtra");
            if (ingredientJson != null)
                Log.i("HayllieInfo", "Ingredients received");

            recipeName = getIntent().getStringExtra("Recipe");

            if (recipeName != null)
                Log.i("HayllieInfo", "RecipeName received" + recipeName);

            if (Build.VERSION.SDK_INT >= 21) {
                selectBackgroundImage();
            }
            gson = new Gson();

            stepList = gson.fromJson(stepJson,
                    new TypeToken<List<Step>>() {
                    }.getType());
            Log.i("HayllieInfoStep", stepList.toString());

            // Recycler view for steps
            stepsRecyclerView = findViewById(R.id.rv_steps);
            mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            Log.i("HayllieInfo", "linear layout manager initiated");
            stepsRecyclerView.setLayoutManager(mLayoutManager);
            Log.i("HayllieInfo", "linear layout manager set");

            videoAdapter = new VideoAdapter(RecipeDetailsActivity.this, stepList);
            videoAdapter.setOnClick(this);
            Log.i("HayllieInfo","onClickSet");



            rotationDetails = true;



            ingredientList = gson.fromJson(ingredientJson,
                    new TypeToken<List<Ingredient>>() {
                    }.getType());
            Log.i("HayllieInfoIng", ingredientList.toString());

            StringBuffer ingredientsStringBuffer = new StringBuffer();
            int loopInt = 0;
            String space = " ";
            for (Ingredient ingredient : ingredientList) {
                loopInt++;

                if (loopInt < 10) space ="  ";
                else space = " ";
                ingredientsStringBuffer.append(loopInt + space + "\u2022 " + ingredient.getQuantity() + " " +
                        ingredient.getIngredient() + " " + ingredient.getMeasure() + "\n");

            }

            ingredientsText =  ingredientsStringBuffer.toString();
            ingredients_text.setText(ingredientsText);

            stepsRecyclerView.setAdapter(videoAdapter);
            Log.i("HayllieInfo", "adapter set");


            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Log.i("Wohoooooooo", "clicked");

                    SharedPreferences sharedPreferences = getSharedPreferences("recipeData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("recipeName", recipeName);
                    editor.putString("ingredientsText",ingredientsText);

                    editor.apply();

                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplication());
                    int[] widgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(getApplication(), MyAppWidget.class));
                    for (int appWidgetId : widgetIds) {
                        MyAppWidget.updateAppWidget(getApplicationContext(), appWidgetManager, appWidgetId);
                    }

                    Toast toast = Toast.makeText(getApplicationContext(),recipeName + " was sucessfully added to widget",Toast.LENGTH_SHORT);
                    toast.setMargin(50,50);
                    toast.show();
                }
            });

        }

    }


    private void selectBackgroundImage() {

        Log.i("HayllieInfoR","recipeName"+recipeName);


        if (Build.VERSION.SDK_INT >= 21) {
            switch (recipeName) {
                case "Nutella Pie":
                    DetailsPageLayout.setBackground(getResources().getDrawable(R.drawable.nutellapie));
                    break;
                case "Brownies":
                    DetailsPageLayout.setBackground(getResources().getDrawable(R.drawable.brownies));
                    break;
                case "Yellow Cake":
                    DetailsPageLayout.setBackground(getResources().getDrawable(R.drawable.yellowcake));
                    break;
                case "Cheesecake":
                    DetailsPageLayout.setBackground(getResources().getDrawable(R.drawable.cheesecake));
                    break;
                default:
                    DetailsPageLayout.setBackground(getResources().getDrawable(R.drawable.bakingappbackground));
            }

            DetailsPageLayout.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorHarp)));
            DetailsPageLayout.setBackgroundTintMode(PorterDuff.Mode.SCREEN);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mListState != null) {
            //Restoring recycler view state
            /*linearLayoutManager.onRestoreInstanceState(mListState);*/
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.getBoolean("rotaionDetail", rotationDetails);
        //storing recycler view state
        /*outState.putParcelable("rvState", linearLayoutManager.onSaveInstanceState());*/
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onClick(Context context, Integer id, String description, String url, String thumbnailUrl) {
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra("step_id", id);
        Log.i("HayllieInfo","Id: "+id);
        intent.putExtra("step_desc", description);
        Log.i("HayllieInfo","desc: "+description);
        intent.putExtra("step_url", url);
        Log.i("HayllieInfo","url: "+url);
        intent.putExtra("step_image", thumbnailUrl);
        Log.i("HayllieInfo","img: "+thumbnailUrl);
        startActivity(intent);
        Log.i("HayllieInfo","should start");


    }
}