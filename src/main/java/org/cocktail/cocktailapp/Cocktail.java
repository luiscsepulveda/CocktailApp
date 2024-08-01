
/*

Name: Luis C. Sepulveda
ID student: 200572241
Date: july 29-2024


* This class is about cocktails. It has many fields to save information of cocktail.
It has name, ingredients, type, category, glass, instructions and image URL.
The class have two constructors, one with all fields parameters,
and one default constructor. You can use the first to create cocktail with all
details at once, or you can use default constructor and set details later
 with setter methods. There are also getter methods to get the values of fields.
*
* */
package org.cocktail.cocktailapp;



public class Cocktail {

    private String name;
    private String ingredients;
    private String type;
    private String category;
    private String glass;
    private String instructions;
    private String imageUrl;


    //Parameterized Constructor to Create a cocktail objet
    public Cocktail(String name, String ingredients, String type, String category, String glass, String instructions, String imageUrl) {
        this.name = name;
        this.ingredients = ingredients;
        this.type = type;
        this.category = category;
        this.glass = glass;
        this.instructions = instructions;
        this.imageUrl = imageUrl;
    }

    // Default constructor with no arguments
    public Cocktail() {}

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
