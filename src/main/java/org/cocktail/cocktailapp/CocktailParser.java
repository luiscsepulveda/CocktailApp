/*

Name: Luis C. Sepulveda
ID student: 200572241
Date: july 29-2024


cocktailParser class will be responsible for parsing json
responses from cocktail API and then converting them into Cocktail objetcs.
this class uses the Gson library in order to handle Json parsing.

*

it titerates through up to 15 possible ingredients and measure, appending them to a StringBuilder

The setLength part is for remove extra comma and space from end of string.
its looks better without comma and space

* */

package org.cocktail.cocktailapp;

//necesary imports from google Gson library for Json parsing
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
//java classes to handle exception and list
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CocktailParser {

    //
    public static Cocktail parseCocktail(String jsonResponse) {
        //
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray drinksArray = jsonObject.getAsJsonArray("drinks");

        //this condition basically check if the drinkarray is not null and has atleast one drink
        //or more than 0.
        if (drinksArray != null && drinksArray.size() > 0) {

            //if both conditions are met the code retrieves the first elemnt
            //
            JsonObject drinkObject = drinksArray.get(0).getAsJsonObject();

            // Debug prints
            System.out.println("Parsing cocktail:");
            System.out.println(drinkObject);

            //it extracts details like name, ingredient, type, category, glass, etc
            String name = getAsString(drinkObject, "strDrink");
            String ingredients = getIngredients(drinkObject);
            String type = getAsString(drinkObject, "strAlcoholic");
            String category = getAsString(drinkObject, "strCategory");
            String glass = getAsString(drinkObject, "strGlass");
            String instructions = getAsString(drinkObject, "strInstructions");
            String imageUrl = getAsString(drinkObject, "strDrinkThumb");

            //if all fields are empty it will return null
            if (name.isEmpty() && ingredients.isEmpty() && type.isEmpty() && category.isEmpty() && glass.isEmpty() && instructions.isEmpty() && imageUrl.isEmpty()) {
                return null;
            }
            //otherwise will create and return a cocktail object with his values
            return new Cocktail(name, ingredients, type, category, glass, instructions, imageUrl);
        }
        return null;
    }


    //here we create a list of cocktails object
    public static List<Cocktail> parseCocktails(String jsonResponse) throws IOException {
        List<Cocktail> cocktails = new ArrayList<>();
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray drinksArray = jsonObject.getAsJsonArray("drinks");

        //if dringkarray is not null is going to iterates through the array of drinks, and
        //gets detailed information for each drink by its ID,
        //adds each cocktails object to the list
        if (drinksArray != null) {
            for (int i = 0; i < drinksArray.size(); i++) {
                JsonObject drinkObject = drinksArray.get(i).getAsJsonObject();
                String id = getAsString(drinkObject, "idDrink");
                String detailedJsonResponse = CocktailService.getCocktailById(id);
                Cocktail cocktail = parseCocktail(detailedJsonResponse);
                if (cocktail != null) {
                    cocktails.add(cocktail);
                }
            }
        }
        return cocktails;
    }


    //this will retrieve a string value from jsonObjet for a given memberName
    private static String getAsString(JsonObject jsonObject, String memberName) {
        //this double check if the member exist and is not null before returning its value
        return jsonObject.has(memberName) && !jsonObject.get(memberName).isJsonNull() ? jsonObject.get(memberName).getAsString() : "";
    }

    //this method will create a string of ingredients with their messures from a jsonObject
    private static String getIngredients(JsonObject drinkObject) {
        StringBuilder ingredients = new StringBuilder();

        //it will iterate up to 15 ingredient and mesures
        //this API provide up to 15 ingredients and mesures for each cocktail
        for (int i = 1; i <= 15; i++) {
            String ingredientKey = "strIngredient" + i;
            String measureKey = "strMeasure" + i;
            String ingredient = getAsString(drinkObject, ingredientKey);
            String measure = getAsString(drinkObject, measureKey);
            //if the ingredient is not empty, it will append the ingredient to the Stringbuilder
            if (!ingredient.isEmpty()) {
                ingredients.append(ingredient);
                //if the measure is not empty, it will append in ()
                // next to the ingredient to the Stringbuilder
                if (!measure.isEmpty()) {
                    ingredients.append(" (").append(measure).append(")");
                }
                //they will be separate with a comma
                ingredients.append(", ");
            }
        }

        if (ingredients.length() > 0) {
            ingredients.setLength(ingredients.length() - 2);
        }
        return ingredients.toString();
    }
}
