/*

Name: Luis C. Sepulveda
ID student: 200572241
Date: july 29-2024


This is the main application class for the Cocktail App.
 * It extends JavaFX Application class and sets up the UI.
 * It has several UI components like labels, text fields, buttons, image views, and layouts.
 * It fetches cocktail data from an API and displays it in the UI.
 * It supports searching for cocktails by name and ingredient, fetching random cocktails,
 * and displaying a grid of cocktails.


* */


package org.cocktail.cocktailapp;

//all the necesary imports from javaFx to create UI and handleing events/errors
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
// add font to the title
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.List;

//cocktail app that extend Aplication

public class CocktailApp extends Application {

    // initialize lots of components like label, textflow, imageView, gridpane, etc..
    private Label nameLabel = new Label("Cocktail Name");
    private Label ingredientsLabel = new Label("Ingredients:");
    private Label typeLabel = new Label("Type:");
    private Label categoryLabel = new Label("Category:");
    private Label glassLabel = new Label("Glass:");
    private TextFlow instructionsFlow = new TextFlow();
    //new label for to show the result
    private Label resultLabel = new Label();
    private ImageView imageView = new ImageView();
    private GridPane gridPane = new GridPane();
    private ImageView logoImageView;
    private StackPane contentStack = new StackPane();
    private List<Cocktail> currentCocktails;
    private int currentGridPage = 0;
    private int cocktailsPerPage = 8;
    private HBox contentBox;
    private VBox mainLayout;
    private VBox detailsLayout;

    @Override
    public void start(Stage primaryStage) {
        //Set an icon and title for the app.
        primaryStage.getIcons().add(new Image("apple-touch-icon.png"));
        primaryStage.setTitle("Cocktail Api - Assignment 2");


        //Title of the UI app
        Label titleLabel = new Label("Sunny Sip App");
        //titleLabel.setStyle("-fx-font-size: 26px; -fx-text-fill: white;");
        titleLabel.setAlignment(Pos.CENTER);
        //bring the style class to apply the font
        titleLabel.getStyleClass().add("title-label");


        //load font and set it to the title label "Summy sip app"
        Font customFont = Font.loadFont(getClass().getResourceAsStream("org/cocktail/cocktailapp/font/TitanOne-Regular.ttf"), 26);
        titleLabel.setFont(customFont);

        //textfield to search
        TextField searchField = new TextField();
        searchField.setPromptText("Search Cocktails");
        // 4 Buttons
        Button searchButton = new Button("Search");
        Button randomButton = new Button("Random Cocktails");
        Button ingredientButton = new Button("Search by Ingredient");
        Button resetButton = new Button("Reset");

        //organized in horizontal nad vertical box
        HBox buttonBox = new HBox(10, searchButton, randomButton, ingredientButton, resetButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox searchBox = new VBox(10, searchField, buttonBox, resultLabel);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setPadding(new Insets(10));

        VBox titleBox = new VBox(10, titleLabel, searchBox);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(10));


        //image view and size
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);

        // Set the color of the description labels to white
        nameLabel.setStyle("-fx-text-fill: white;");
        ingredientsLabel.setStyle("-fx-text-fill: white;");
        typeLabel.setStyle("-fx-text-fill: white;");
        categoryLabel.setStyle("-fx-text-fill: white;");
        glassLabel.setStyle("-fx-text-fill: white;");

        //description label set up in a vertical box
        VBox detailsBox = new VBox(10, nameLabel, ingredientsLabel, typeLabel, categoryLabel, glassLabel, instructionsFlow);
        detailsBox.setPadding(new Insets(10));
        instructionsFlow.setPrefWidth(400);
        instructionsFlow.setPadding(new Insets(10));

        contentBox = new HBox(10, imageView, detailsBox);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(10));

        //arraignment of the cocktails with gap btw pictures
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);


        //button next and previous for grid
        Button nextButton = new Button("Next");
        Button prevButton = new Button("Previous");

        //navigation box

        HBox navigationBox = new HBox(10, prevButton, nextButton);
        navigationBox.setAlignment(Pos.CENTER);


        //Initialize detailsLayout when cockteils is selected from the grid
        detailsLayout = new VBox(10, contentBox);
        detailsLayout.setAlignment(Pos.CENTER);
        detailsLayout.setPadding(new Insets(10));

        mainLayout = new VBox(10, titleBox, contentStack, navigationBox);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(10));

        // Load and size the logo image
        // I downloaded the logo in www.freepik.com
        logoImageView = new ImageView(new Image(getClass().getResource("/logo_cocktail1.png").toExternalForm()));
        logoImageView.setPreserveRatio(true);
        //set the size of the logo
        logoImageView.setFitWidth(300);
        logoImageView.setFitHeight(300);

        //Contentstack is a content management system,
        // It provides the infrastructure to create and manage content,
        contentStack.getChildren().addAll(logoImageView, detailsLayout, gridPane);
        detailsLayout.setVisible(false);
        gridPane.setVisible(false);

        // Set initial background image
        setBackgroundImage("/background3.png");

        //Create the main scene Layout and size and load the style.css
        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
        primaryStage.setScene(scene);

        //configurations the search button to fetch cocktail
        // details by name and update the User Interface (UI).

        searchButton.setOnAction(e -> {
            String searchText = searchField.getText();
            new Thread(() -> {
                try {
                    String jsonResponse = CocktailService.searchCocktailByName(searchText);
                    Cocktail cocktail = CocktailParser.parseCocktail(jsonResponse);
                    Platform.runLater(() -> {
                        if (cocktail != null) {
                            updateUI(cocktail);
                            resultLabel.setText("1 cocktail found.");
                        } else {
                            showError("No cocktails found for \"" + searchText + "\".");
                        }
                        detailsLayout.setVisible(true);
                        logoImageView.setVisible(false);
                        gridPane.setVisible(false);
                        setBackgroundImage("/background4.png");
                    });
                } catch (IOException ex) {
                    Platform.runLater(() -> showError("Error fetching cocktail: " + ex.getMessage()));
                    ex.printStackTrace();
                }
            }).start();
        });


        //Configure the action for the random button to fetch a random cocktail
        // and update the UI.
        randomButton.setOnAction(e -> {
            new Thread(() -> {
                try {
                    String jsonResponse = CocktailService.getRandomCocktail();
                    Cocktail cocktail = CocktailParser.parseCocktail(jsonResponse);
                    Platform.runLater(() -> {
                        if (cocktail != null) {
                            //debug print
                            System.out.println("Random Cocktail: " + cocktail.getName());
                            updateUI(cocktail);
                            resultLabel.setText("Random cocktail found.");
                        } else {
                            showError("No random cocktail found.");
                        }
                        detailsLayout.setVisible(true);
                        logoImageView.setVisible(false);
                        gridPane.setVisible(false);
                        setBackgroundImage("/background4.png");
                    });
                } catch (IOException ex) {
                    Platform.runLater(() -> showError("Error fetching random cocktail: " + ex.getMessage()));
                    ex.printStackTrace();
                }
            }).start();
        });


        //Configure the action for the ingredient button to fetch
        // cocktails by ingredient and update the grid
        ingredientButton.setOnAction(e -> {
            String ingredient = searchField.getText();
            new Thread(() -> {
                try {
                    String jsonResponse = CocktailService.searchCocktailsByIngredient(ingredient);
                    //debug print
                    System.out.println("Ingredient Search API Response: " + jsonResponse);
                    List<Cocktail> cocktails = CocktailParser.parseCocktails(jsonResponse);
                    Platform.runLater(() -> {
                        if (cocktails != null && !cocktails.isEmpty()) {
                            currentCocktails = cocktails;
                            currentGridPage = 0;
                            updateGrid();
                            resultLabel.setText(cocktails.size() + " cocktails found.");
                        } else {
                            showError("No cocktails found with ingredient \"" + ingredient + "\".");
                        }
                        contentBox.setVisible(false);
                        logoImageView.setVisible(false);
                        gridPane.setVisible(true);
                        setBackgroundImage("/background4.png");
                    });
                } catch (IOException ex) {
                    Platform.runLater(() -> showError("Error fetching cocktails: " + ex.getMessage()));
                    ex.printStackTrace();
                }
            }).start();
        });

        //configure the action for the reset button
        // in order to clear all the fields and reset the UI.
        resetButton.setOnAction(e -> {
            searchField.clear();
            resultLabel.setText("");
            nameLabel.setText("Cocktail Name");
            ingredientsLabel.setText("Ingredients:");
            typeLabel.setText("Type:");
            categoryLabel.setText("Category:");
            glassLabel.setText("Glass:");
            instructionsFlow.getChildren().clear();
            imageView.setImage(null);
            gridPane.getChildren().clear();
            detailsLayout.setVisible(false);
            logoImageView.setVisible(true);
            logoImageView.toFront();
            setBackgroundImage("/background3.png");
        });

        nextButton.setOnAction(e -> {
            if (currentCocktails != null && (currentGridPage + 1) * cocktailsPerPage < currentCocktails.size()) {
                currentGridPage++;
                updateGrid();
            }
        });

        prevButton.setOnAction(e -> {
            if (currentCocktails != null && currentGridPage > 0) {
                currentGridPage--;
                updateGrid();
            }
        });

        primaryStage.show();
    }

    //Method to update the UI with all the cockatials details
    private void updateUI(Cocktail cocktail) {
        Platform.runLater(() -> {
            if (cocktail != null) {
                // Debug prints to double check data
                System.out.println("Updating UI with cocktail details:");
                System.out.println("Name: " + cocktail.getName());
                System.out.println("Ingredients: " + cocktail.getIngredients());
                System.out.println("Type: " + cocktail.getType());
                System.out.println("Category: " + cocktail.getCategory());
                System.out.println("Glass: " + cocktail.getGlass());
                System.out.println("Instructions: " + cocktail.getInstructions());
                System.out.println("Image URL: " + cocktail.getImageUrl());

                nameLabel.setText(cocktail.getName());
                ingredientsLabel.setText("Ingredients: " + cocktail.getIngredients());
                typeLabel.setText("Type: " + cocktail.getType());
                categoryLabel.setText("Category: " + cocktail.getCategory());
                glassLabel.setText("Glass: " + cocktail.getGlass());
                instructionsFlow.getChildren().clear();
                Text instructionsText = new Text("Instructions: " + cocktail.getInstructions());
                instructionsText.setStyle("-fx-fill: white;");
                instructionsText.wrappingWidthProperty().bind(instructionsFlow.widthProperty().subtract(20)); // Wrap text within TextFlow
                instructionsFlow.getChildren().add(instructionsText);
                imageView.setImage(new Image(cocktail.getImageUrl()));
                contentBox.toFront();
                contentBox.setVisible(true);
                gridPane.setVisible(false);
                System.out.println("Layout Updated with cocktail: " + cocktail.getName()); // Debug print
            } else {
                System.out.println("Cocktail is null, clearing UI.");
                nameLabel.setText("Cocktail Name");
                ingredientsLabel.setText("Ingredients:");
                typeLabel.setText("Type:");
                categoryLabel.setText("Category:");
                glassLabel.setText("Glass:");
                instructionsFlow.getChildren().clear();
                imageView.setImage(null);
            }
        });
    }


    //update the grid with cocktails
    private void updateGrid() {
        Platform.runLater(() -> {
            gridPane.getChildren().clear();
            int columns = 4;
            int rows = 2;
            int start = currentGridPage * cocktailsPerPage;
            int end = Math.min(start + cocktailsPerPage, currentCocktails.size());

            int row = 0;
            int column = 0;
            for (int i = start; i < end; i++) {
                Cocktail cocktail = currentCocktails.get(i);
                if (cocktail != null) {
                    ImageView imageView = new ImageView(new Image(cocktail.getImageUrl(), 150, 150, false, false));
                    imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                        if (cocktail != null) {
                            updateUI(cocktail);
                            contentBox.toFront();
                        }
                    });

                    // Set cursor to show a hand when hover
                    imageView.setStyle("-fx-cursor: hand;");
                    imageView.setOnMouseEntered(event -> {
                        imageView.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0.5, 0, 0); -fx-cursor: hand;");
                    });
                    imageView.setOnMouseExited(event -> {
                        imageView.setStyle("-fx-cursor: hand;");
                    });
                    gridPane.add(imageView, column, row);
                    column++;
                    if (column == columns) {
                        column = 0;
                        row++;
                        if (row == rows) {
                            break;
                        }
                    }
                }
            }
        });
    }


    //method to show any error messages
    private void showError(String message) {
        Platform.runLater(() -> {
            resultLabel.setText(message);
            nameLabel.setText("Cocktail Name");
            ingredientsLabel.setText("Ingredients:");
            typeLabel.setText("Type:");
            categoryLabel.setText("Category:");
            glassLabel.setText("Glass:");
            instructionsFlow.getChildren().clear();
            imageView.setImage(null);
            if (logoImageView != null && contentBox != null && gridPane != null) {
                logoImageView.toFront();
                contentStack.getChildren().setAll(logoImageView, detailsLayout, gridPane);
            } else {
                System.out.println("One or more nodes are null:");
                System.out.println("logoImageView: " + logoImageView);
                System.out.println("contentBox: " + contentBox);
                System.out.println("gridPane: " + gridPane);
            }
            logoImageView.setVisible(true);
            detailsLayout.setVisible(false);
            gridPane.setVisible(false);
            setBackgroundImage("/background3.png");
        });
    }

    private void setBackgroundImage(String imagePath) {
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(getClass().getResource(imagePath).toExternalForm(), 800, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        mainLayout.setBackground(new Background(backgroundImage));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

