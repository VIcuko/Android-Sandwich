package com.udacity.sandwichclub;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void populateUI(Sandwich sandwich) {
        TextView originTv = findViewById(R.id.origin_tv);
        TextView descriptionTv = findViewById(R.id.description_tv);
        TextView ingredientsTv = findViewById(R.id.ingredients_tv);
        TextView alsoKnownTv = findViewById(R.id.also_known_tv);
        TextView descriptionOriginTv = findViewById(R.id.description_origin_tv);
        TextView descriptionDescriptionTv = findViewById(R.id.description_description_tv);
        TextView descriptionIngredientsTv = findViewById(R.id.description_ingredients_tv);
        TextView descriptionAlsoKnownTv = findViewById(R.id.description_also_known_tv);

        String originTvString = sandwich.getPlaceOfOrigin();
        String descritpionTvString = sandwich.getDescription();
        String ingredientsTvString = String.join(", ", sandwich.getIngredients());
        String alsoKownTvString = String.join(", ", sandwich.getAlsoKnownAs());

        checkAndInform(descriptionOriginTv, originTv,originTvString);
        checkAndInform(descriptionDescriptionTv, descriptionTv,descritpionTvString);
        checkAndInform(descriptionIngredientsTv, ingredientsTv,ingredientsTvString);
        checkAndInform(descriptionAlsoKnownTv, alsoKnownTv,alsoKownTvString);
    }

    private void checkAndInform(TextView description, TextView textView, String text){
        if (!text.isEmpty()) {
            textView.setText(text);
        } else {
            description.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
    }
}
