package com.example.udrecipesbakingapp.Helpers;

import com.example.udrecipesbakingapp.models.Recipe;

import java.util.ArrayList;

public class RecipeJSONResponse {

    private ArrayList<Recipe> recipe;

    public ArrayList<Recipe> getRecipe() {
        return recipe;
    }

    public void setRecipe(ArrayList<Recipe> recipe) {
        this.recipe = recipe;
    }
}
