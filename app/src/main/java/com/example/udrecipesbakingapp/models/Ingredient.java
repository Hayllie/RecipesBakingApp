package com.example.udrecipesbakingapp.models;

import android.util.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Ingredient {

    Double quantity;
    String measure;
    String ingredient;

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }


    public static String toBase64String(Ingredient ingredient) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Base64.encodeToString(mapper.writeValueAsBytes(ingredient), 0);
        } catch (JsonProcessingException e) {
            //Logger.e(e.getMessage());
        }
        return null;
    }

    public static Ingredient fromBase64(String encoded) {
        if (!"".equals(encoded)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(Base64.decode(encoded, 0), Ingredient.class);
            } catch (IOException e) {
                //Logger.e(e.getMessage());
            }
        }
        return null;
    }
}
