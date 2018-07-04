package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        if (json != null) {
            try {
                JSONObject jsonContent = new JSONObject(json);
                JSONObject name = jsonContent.getJSONObject("name");
                String mainName = name.getString("mainName");
                List<String> alsoKnownAs = Arrays.asList(name.getJSONArray("alsoKnownAs").toString());
                String placeOfOrigin = jsonContent.getString("placeOfOrigin");
                String description = jsonContent.getString("description");
                String image = jsonContent.getString("image");
                List<String> ingredients = Arrays.asList(jsonContent.getString("ingredients"));
                Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
                return sandwich;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
