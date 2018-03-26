package com.example.davidnissenoff.miniapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by David Nissenoff on 3/23/2018.
 */

public class SearchActivity extends AppCompatActivity {
    private Context mContext;
    private Spinner mPrepSpin;
    private Spinner mDietSpin;
    private Spinner mServingSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = this;
        mPrepSpin = findViewById(R.id.cooking_time_spinner);
        mDietSpin = findViewById(R.id.dietRestriction_spinner);
        mServingSpin = findViewById(R.id.serving_size_spinner);
        final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);
        ArrayList<String> pickOneTag = new ArrayList<String>();
        pickOneTag.add("Pick One");
        for(int position = 0; position < recipeList.size(); position++){
            String pickOneCurrent = recipeList.get(position).label;
            if (!pickOneTag.contains(pickOneCurrent)){
                pickOneTag.add(pickOneCurrent);
            }

        }
        ArrayAdapter<String> tagAdapt = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pickOneTag);
        mDietSpin.setAdapter(tagAdapt);
        String[] serving = new String[]{"Pick One", "Less than 4", "4-6", "7-9", "More than 10"};
        ArrayAdapter<String> servingAdpat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, serving);
        mServingSpin.setAdapter(servingAdpat);
        String[] time = new String[]{"Pick One", "30 min or less", "Less than 1 hr", "More than 1 hr"};
        ArrayAdapter<String> timeAdapt = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, time);
        mPrepSpin.setAdapter(timeAdapt);


    }
    public void onClick(View view){
        Intent nextScreen = new Intent(getApplicationContext(), ResultActivity.class);
        nextScreen.putExtra("label", mDietSpin.getSelectedItem().toString());
        nextScreen.putExtra("serving", mServingSpin.getSelectedItem().toString());
        nextScreen.putExtra("time", mPrepSpin.getSelectedItem().toString());
        startActivity(nextScreen);
    }



}
