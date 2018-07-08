package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        if (json != null) {
            try {
                JSONObject jsonContent = new JSONObject(json);
                JSONObject name = jsonContent.getJSONObject("name");
                String mainName = name.getString("mainName");
                List<String> alsoKnownAs = toList(name.getJSONArray("alsoKnownAs"));
                String placeOfOrigin = jsonContent.getString("placeOfOrigin");
                String description = jsonContent.getString("description");
                String image = jsonContent.getString("image");
                List<String> ingredients = toList(jsonContent.getJSONArray("ingredients"));
                Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
                return sandwich;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static List toList(JSONArray array) throws JSONException {
        List list = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            list.add(array.get(i));
        }
        return list;
    }
}
