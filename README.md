
# Sunny Sip App

Sunny Sip App is a fun and easy-to-use cocktail application that allows you to search for cocktail recipes, discover random cocktails, and explore cocktails by ingredients.


## Features

- Search Cocktails: Enter a cocktail name and find its recipe.
- Random Cocktails: Discover new cocktails with a single click.
- Search by Ingredient: Find cocktails that include your favorite ingredients.
- Cocktail Details: View detailed information about each cocktail, including ingredients, instructions, and an image.



## How to use

1. Search for a Cocktail:

- Type the name of a cocktail in the search bar.
- Click the "Search" button.

2. Discover a Random Cocktail:

- Click the "Random Cocktails" button.
- Search by Ingredient:

3. Type the name of an ingredient in the search bar.
- Click the "Search by Ingredient" button.
4. Reset:

- Click the "Reset" button to clear the search and start over.




## Installation

- Download or clone the repository.
- Open the project in your favorite Java IDE (like IntelliJ IDEA or Eclipse).
- Run the CocktailApp.java file.
    
## Screenshots

![App Screenshot](https://imagizer.imageshack.com/img922/6667/Mzi4gQ.png)

![App Screenshot](https://imagizer.imageshack.com/img922/1377/ufVtIl.png)



## Credits
- API: This app uses [the CocktailDB API](https://www.thecocktaildb.com/). to fetch cocktail data.
- Background Images: The background images were downloaded from [Freepik](https://www.freepik.com/).
## Roadmap

1.  Research API:

- Explore available cocktail APIs.
- Select TheCocktailDB API for its comprehensive cocktail database.
- Review API documentation and endpoints for fetching cocktail data.
2. Define Requirements:

- List out the app features: search by name, random cocktails, search by ingredients, view cocktail details.
- Identify the UI components needed: search bar, buttons, labels, image views, grid view.
3. Design the Main Layout:

- Sketch the main screen layout with search bar, buttons, and result display area.
- Plan the detailed layout for displaying cocktail information.
- Design the grid layout for showing search results by ingredient.

## API Reference

#### Search Cocktails by Name

```http
  GET /search.php?s=${name}

```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | **Required**.  Name of cocktail to search |

#### Get Random Cocktail

```http
  GET /random.php

```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `none`      | `-` | No parameters needed |

#### Search Cocktails by Ingredient

```http
  GET /filter.php?i=${ingredient}


```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `ingredient` | `string` |  **Required**. Ingredient to search |


## Badges



[![Java Lenguage](https://img.shields.io/badge/Lenguage-Java-blue)](https://choosealicense.com/licenses/mit/)



## Authors

- [@luiscsepulveda](https://github.com/luiscsepulveda)

