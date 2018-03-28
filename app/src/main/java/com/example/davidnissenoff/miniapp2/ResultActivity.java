package com.example.davidnissenoff.miniapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by David Nissenoff on 3/23/2018.
 */

public class ResultActivity extends AppCompatActivity {
    private Context mContext;
    private TextView mTextView;
    private ListView mListView;
    private ImageButton imageButton;

    private ArrayList<Recipe> searchList(ArrayList<Recipe> list) {
        ArrayList<Recipe> searchList = new ArrayList<>();
        int highTime;
        int lowTime;
        int highServ;
        int lowServ;
        String tag = this.getIntent().getExtras().getString("label");


        String prepTime = this.getIntent().getExtras().getString("time");
        if (prepTime.equals("30 min or less")) {
            highTime = 30;
            lowTime = 0;
        } else if (prepTime.equals("Less than 1 hr")) {
            highTime = 59;
            lowTime = 0;
        } else if (prepTime.equals("1 hr or more")) {
            highTime = 480;
            lowTime = 60;
        } else {
            highTime = 540;
            lowTime = 0;
        }
        String servings = this.getIntent().getExtras().getString("serving");
        if (servings.equals("Less than 4")) {
            highServ = 3;
            lowServ = 1;
        } else if (servings.equals("4-6")) {
            highServ = 6;
            lowServ = 4;
        } else if (servings.equals("7-9")) {
            highServ = 9;
            lowServ = 7;
        } else if (servings.equals("Pick One")) {
            highServ = 75;
            lowServ = 0;
        } else {
            highServ = 100;
            lowServ = 10;
        }

        ArrayList<Integer> time = timeChange(list);
        for (int i = 0; i < list.size(); i++) {
            if (!tag.equals("Pick One")) {
                if ((list.get(i).label.equals(tag)) && (lowServ <= list.get(i).servings) && (highServ >= list.get(i).servings)
                        && (time.get(i) >= lowTime) && (time.get(i) <= highTime)) {
                    searchList.add(list.get(i));

                } else {
                    if ((list.get(i).label.equals(tag))&& (highServ >= list.get(i).servings) && (lowServ <= list.get(i).servings) && (time.get(i) >= lowTime) && (time.get(i) <= highTime)) {
                        searchList.add(list.get(i));
                    }
                }
            } else{
                searchList.add(list.get(i));
            }

        }return searchList;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        ArrayList<Recipe> recipeArrayList = Recipe.getRecipesFromFile("recipes.json", this);
        ArrayList<Recipe> searchList = searchList(recipeArrayList);
        mContext = this;
        RecipeAdapter adapter = new RecipeAdapter(this, searchList);
        mTextView = findViewById(R.id.clicked_text_view);
        mListView = findViewById(R.id.recipe_list_view);
        imageButton = findViewById(R.id.start_cooking_image_button);
        mTextView.setText("Here are " + searchList.size() + " recipes!");
        mTextView.setTextSize(24);
        mTextView.setTextColor(0xff000000);
        mListView.setAdapter(adapter);

    }
    private ArrayList<Integer> timeChange(ArrayList<Recipe> list){
        ArrayList<Integer> time = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            int min = 0;
            String pTime = list.get(i).prepTime;
            String[] itemsHolder = pTime.split(" ");
            ArrayList<String> items = new ArrayList<>(Arrays.asList(itemsHolder));
            if(items.contains("hour") || items.contains("hours")){
                int k = items.indexOf("hours");
                if(k != -1){
                    min += 60*(Integer.valueOf(items.get(k-1)));
                } else {
                    min += 60;
                }
            }
            if(items.contains("minutes")){
                int k = items.indexOf("minutes");
                min += Integer.valueOf(items.get(k-1));
            }else{
                min += 60;
            } time.add(min);
        } return time;
    }

}
