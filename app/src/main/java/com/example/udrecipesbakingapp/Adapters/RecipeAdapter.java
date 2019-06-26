package com.example.udrecipesbakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.udrecipesbakingapp.Activities.RecipeDetailsActivity;
import com.example.udrecipesbakingapp.Helpers.BackupImageSelector;
import com.example.udrecipesbakingapp.R;
import com.example.udrecipesbakingapp.models.Ingredient;
import com.example.udrecipesbakingapp.models.Recipe;
import com.example.udrecipesbakingapp.models.Step;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> recipesList;
    private List<Ingredient> ingredientList;
    private List<Step> stepList;
    private Context mContext;
    private Intent intent;
    private Gson gson;


    public RecipeAdapter(List<Recipe> recipesList, Context context) {

        this.recipesList = recipesList;
        this.mContext = context;
    }

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_recipe_item, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecipeAdapter.ViewHolder viewHolder, final int i) {

        final String name = String.valueOf(recipesList.get(i).getName());
        String servings = "Servings: "+String.valueOf(recipesList.get(i).getServings());

        viewHolder.tv_recipeTitle.setText(name);
        viewHolder.tv_servingsNumber.setText(servings);

        String recipeImage = recipesList.get(i).getImage();

        if (recipeImage.isEmpty())
        {
            Log.i("HayllieInfo","No Image");
            BackupImageSelector backupImageSelector = new BackupImageSelector();

            switch(name)
            {
                case "Nutella Pie":
                    backupImageSelector.setImage(BackupImageSelector.NUTELLA_PIE);
                    break;
                case "Brownies":
                    backupImageSelector.setImage(BackupImageSelector.BROWNIES);
                    break;
                case "Yellow Cake":
                    backupImageSelector.setImage(BackupImageSelector.YELLOW_CAKE);
                    break;
                case "Cheesecake":
                    backupImageSelector.setImage(BackupImageSelector.CHEESE_CAKE);
                    break;
                default:
                    backupImageSelector.setImage(BackupImageSelector.NO_IMAGE_AVAILABLE);
            }

            setbackupimage(viewHolder,backupImageSelector);

        } else {
            Picasso.with(mContext)
                    .load(recipeImage)
                    .error(R.drawable.error)
                    .placeholder(R.drawable.loading)
                    .into(viewHolder.iv_recipeImage);
        }

        viewHolder.cv_recipe_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientList = recipesList.get(i).getIngredients();
                stepList = recipesList.get(i).getSteps();
                if (ingredientList!=null)
                    Log.i("HayllieInfo","ingredientsList ready");
                if (stepList!=null)
                    Log.i("HayllieInfo","stepsList ready");

                intent = new Intent(mContext, RecipeDetailsActivity.class);
                gson = new Gson();
                String ingredientJson = gson.toJson(ingredientList);
                if (ingredientJson!=null)
                    Log.i("HayllieInfo","ingredientsList JSON ready");
                String stepJson = gson.toJson(stepList);
                if (stepJson!=null)
                    Log.i("HayllieInfo","stepsList JSON ready");

                intent.putExtra("IngredientsExtra", ingredientJson);
                Log.i("HayllieInfo", "ing put");

                intent.putExtra("StepsExtra", stepJson);
                Log.i("HayllieInfo", "stepss put");

                intent.putExtra("Recipe", String.valueOf(recipesList.get(i).getName()));
                Log.i("HayllieInfo", "recipes put");


                if (intent.hasExtra("IngredientsExtra") && intent.hasExtra("IngredientsExtra") && intent.hasExtra("Recipe")) {

                    Log.i("HayllieInfo", "lists put");
                } else {
                    Log.i("HayllieInfo", "nope");
                }

                viewHolder.cv_recipe_item.getContext().startActivity(intent);
                Log.i("HayllieInfo", "should start");


            }
        });
    }

    private void setbackupimage(RecipeAdapter.ViewHolder viewHolder, BackupImageSelector backupImageSelector ) {

        String receivedImage = backupImageSelector.getImage();
        switch(receivedImage)
        {
            case BackupImageSelector.BROWNIES:
                viewHolder.iv_recipeImage.setImageResource(R.drawable.brownies);
                break;
            case BackupImageSelector.YELLOW_CAKE:
                viewHolder.iv_recipeImage.setImageResource(R.drawable.yellowcake2);
                break;
            case BackupImageSelector.CHEESE_CAKE:
                viewHolder.iv_recipeImage.setImageResource(R.drawable.cheesecake);
                break;
            case BackupImageSelector.NUTELLA_PIE:
                viewHolder.iv_recipeImage.setImageResource(R.drawable.nutellapie);
                break;
            default:
                viewHolder.iv_recipeImage.setImageResource(R.drawable.noimageavailable);
        }
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_recipeTitle;
        private TextView tv_servingsNumber;
        private ImageView iv_recipeImage;
        private CardView cv_recipe_item;

        public ViewHolder(View view) {
            super(view);

            tv_recipeTitle = view.findViewById(R.id.tv_recipeTitle);
            tv_servingsNumber = view.findViewById(R.id.tv_servingsNumber);
            iv_recipeImage = view.findViewById(R.id.iv_recipeImage);

            cv_recipe_item = view.findViewById(R.id.cv_recipe_item);

        }

    }
}
