package com.example.davidnissenoff.miniapp2;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by David Nissenoff on 3/23/2018.
 */

public class Recipe {
    // instance variables or fields
    public String title;
    public String image;
    public String prepTime;
    public String instructionURL;
    public String description;
    public String label;
    public int servings;
    // constructor
    // default

    // method
    // static methods that raed the json gile in and load into Recipe
    // this method will return an array list of recipes constructed from the JSON file
    public static ArrayList<Recipe> getRecipesFromFile(String filename, Context context){
        ArrayList<Recipe> recipelist = new ArrayList<Recipe>();
        // try to read from JSON file
        // get info by using the tags
        // construct a Recipe object for each recipe in JSON
        // add object to arraylist
        // return arraylist
        try{
            String jsonString = loadJsonFromAsset("recipes.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("recipes");
            for (int i = 0; i < recipes.length(); i++){
                Recipe recipe = new Recipe();
                recipe.prepTime = recipes.getJSONObject(i).getString("prepTime");
                recipe.title = recipes.getJSONObject(i).getString("title");
                recipe.description = recipes.getJSONObject(i).getString("description");
                recipe.image = recipes.getJSONObject(i).getString("image");
                recipe.instructionURL = recipes.getJSONObject(i).getString("url");
                recipe.label = recipes.getJSONObject(i).getString("dietLabel");
                recipe.servings = recipes.getJSONObject(i).getInt("servings");
                recipelist.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipelist;
    }

    // helper method that loads from any Json file
    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
