/*

Name: Luis C. Sepulveda
ID student: 200572241
Date: july 29-2024


This class is used for interacting with the cocktail API.
 * It has methods to search cocktail by name, get random cocktail,
 * search by ingredient and get cocktail by ID.
 * It also has a private method to get the JSON response from a URL.

* */


package org.cocktail.cocktailapp;


import java.io.IOException;
//read data from the connection
import java.io.InputStream;
//http request import
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.Scanner;

public class CocktailService {

    //define constant that hold the url for the cocktail API

    private static final String BASE_URL = "https://www.thecocktaildb.com/api/json/v2/9973533";

    //this method search cocktails by Name
    public static String searchCocktailByName(String name) throws IOException {
        //construct url for api request using the base url and the name
        String urlString = BASE_URL + "/search.php?s=" + name;
        return getJsonResponse(urlString);
    }

    //this method search random cocktails
    public static String getRandomCocktail() throws IOException {

        String urlString = BASE_URL + "/random.php";
        return getJsonResponse(urlString);
    }

    //this method search cocktails by ingrediente
    public static String searchCocktailsByIngredient(String ingredient) throws IOException {
        String urlString = BASE_URL + "/filter.php?i=" + ingredient;
        return getJsonResponse(urlString);
    }

    //this method search cocktails by id

    public static String getCocktailById(String id) throws IOException {
        String urlString = BASE_URL + "/lookup.php?i=" + id;
        return getJsonResponse(urlString);
    }

    //this method fetches the json respone from the given url, take urlString as a parameter
    private static String getJsonResponse(String urlString) throws IOException {
        //create a url object from the urlString
        URL url = new URL(urlString);
        //Open a HTtp connection to the url
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");


        //The try-with-resources statement is a try statement that declares one or more resources.
        // A resource is an object that must be closed after the program
        // is finished with it. The try-with-resources statement ensures
        // that each resource is closed at the end of the statement and preventing resource leaks.
        //https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (InputStream inputStream = connection.getInputStream();
             Scanner scanner = new Scanner(inputStream)) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
}
