package com.example.udrecipesbakingapp.Helpers;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class BackupImageSelector {

    @Retention(RetentionPolicy.SOURCE)

    @StringDef({BROWNIES,YELLOW_CAKE,CHEESE_CAKE,NUTELLA_PIE,NO_IMAGE_AVAILABLE})
    @interface backingupImage{

    }

    public static final String BROWNIES = "BROWNIES";
    public static final String YELLOW_CAKE = "YELLOW_CAKE";
    public static final String CHEESE_CAKE = "CHEESE_CAKE";
    public static final String NUTELLA_PIE = "NUTELLA_PIE";
    public static final String NO_IMAGE_AVAILABLE = "NO_IMAGE_AVAILABLE";

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
