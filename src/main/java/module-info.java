module org.cocktail.cocktailapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.net.http;


    opens org.cocktail.cocktailapp to javafx.fxml;
    exports org.cocktail.cocktailapp;
}