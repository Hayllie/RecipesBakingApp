package com.example.udrecipesbakingapp.Helpers;

import android.content.Context;

public interface ClickCallBack {
    void onClick(Context context, Integer id, String description, String url, String thumbnailUrl);
}
