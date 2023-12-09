/**
 * Code for the New Vinyl Window which allows the user to add a new vinyl to the vinyl collection list
 *
 *
 * @author Stephan Langworthy
 * @since 1.0
 */

import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewVinylWindow extends Application {

    private TextField albumNameTextField;
    private TextField artistNameTextField;
    private TextField genreTextField;
    private TextField yearOfReleaseTextField;
    private TextField specialPressTextField;
    private TextField numberOfCopiesTextField;
    private TextField contributingArtistTextField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox newVinylWindow = createNewVinylWindow();

        Scene scene = new Scene(newVinylWindow, 600, 400);
        primaryStage.setTitle("New Vinyl Window");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    VBox createNewVinylWindow() {
        VBox newVinylWindow = new VBox();
        newVinylWindow.setPadding(new Insets(10));
        newVinylWindow.setSpacing(20);
        newVinylWindow.setStyle("-fx-background-color: LIGHTGREY");

        Label albumNameLabel = new Label("Album Name: ");
        Label artistNameLabel = new Label("Artist: ");
        Label genreLabel = new Label("Genre: ");
        Label yearOfReleaseLabel = new Label("Year of Release: ");
        Label specialPressLabel = new Label("Special Pressing (True or False): ");
        Label numberOfCopiesLabel = new Label("Copies: ");
        Label contributingArtistLabel = new Label("Contributing Artist(s) (Comma Separated): ");

        albumNameTextField = new TextField();
        artistNameTextField = new TextField();
        genreTextField = new TextField();
        yearOfReleaseTextField = new TextField();
        specialPressTextField = new TextField();
        numberOfCopiesTextField = new TextField();
        contributingArtistTextField = new TextField();

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String albumName = albumNameTextField.getText();
            String artistName = artistNameTextField.getText();
            String genre = genreTextField.getText();
            int yearOfRelease = Integer.parseInt(yearOfReleaseTextField.getText());
            boolean specialPress = Boolean.parseBoolean(specialPressTextField.getText());
            int numberOfCopies = Integer.parseInt(numberOfCopiesTextField.getText());
            String contributingArtist = contributingArtistTextField.getText();

            // Perform actions with the entered data, e.g., create a new Vinyl object
            Vinyl newVinyl = new Vinyl(artistName, albumName, genre, specialPress, numberOfCopies);
            newVinyl.setYearOfRelease(yearOfRelease);
            newVinyl.setContributingArtist(contributingArtist);

            if(newVinyl.getNumberOfCopies() > 0){

                UserTerminal.vinylCollectionList.addToVinylList(newVinyl);

                try {
                    UserTerminal.vinylCollectionList.serializeCollection();
                    UserTerminal.observableVinylList.setAll(UserTerminal.vinylCollectionList.getVinylCollection());
                } catch (ClassNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                albumNameTextField.clear();
                artistNameTextField.clear();
                genreTextField.clear();
                yearOfReleaseTextField.clear();
                specialPressTextField.clear();
                numberOfCopiesTextField.clear();
                contributingArtistTextField.clear();
            }         
        });

        newVinylWindow.getChildren().addAll(
                albumNameLabel, albumNameTextField,
                artistNameLabel, artistNameTextField,
                genreLabel, genreTextField,
                yearOfReleaseLabel, yearOfReleaseTextField,
                specialPressLabel, specialPressTextField,
                numberOfCopiesLabel, numberOfCopiesTextField,
                contributingArtistLabel, contributingArtistTextField,
                saveButton
        );

        return newVinylWindow;
    }
}
