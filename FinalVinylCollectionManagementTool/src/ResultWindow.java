/**
 * Code for the result window which displays the currently selected vinyls attributes
 *
 *
 * @author Stephan Langworthy
 * @since 1.0
 */

import javafx.geometry.Pos;
import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ResultWindow {

    private static TextField albumNameTextField;
    private static TextField artistNameTextField;
    private static TextField genreTextField;
    private static TextField yearOfReleaseTextField;
    private static TextField specialPressTextField;
    private static TextField numberOfCopiesTextField;
    private static ListView contributingArtistListView;

    public static VBox resultWindow(){

        //*Creat vbox for entire result window */
        VBox resultsWindow = new VBox();
        resultsWindow.setPrefWidth(600);
        resultsWindow.setPadding(new Insets(10));

        //*Create vbox within the result window to show album artwork */
        VBox albumCoverVBox = new VBox();
        albumCoverVBox.setPrefHeight(350);

        //*Create vbox to hold album information */
        VBox albumInfoVBox = new VBox();
        albumInfoVBox.setSpacing(20); 
        
        //*Create Labels for vinyl attribute fields */
        Label albumNameLabel = new Label("Album Name: ");
        Label artistNameLabel = new Label("Artist: ");
        Label genreLabel = new Label("Genre: ");
        Label yearOfReleaseLabel = new Label("Year of Release: ");
        Label specialPressLabel = new Label("Special Pressing: ");
        Label numberOfCopiesLabel = new Label("Copies: ");
        Label contributingArtistLabel = new Label("Contributing Artist(s): ");

        //*Create textfields for vinyl attributes */
        albumNameTextField = new TextField();
        artistNameTextField = new TextField();
        genreTextField = new TextField();
        yearOfReleaseTextField = new TextField();
        specialPressTextField = new TextField();
        numberOfCopiesTextField = new TextField();
        contributingArtistListView = new ListView<>();

        //*Create save button with event that saves the changes made to any vinyl attribute */
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            if (UserTerminal.selectedVinyl != null) {
                String albumNameQuery = albumNameTextField.getText();
                String artistNameQuery = artistNameTextField.getText();
                String genreQuery = genreTextField.getText();
                String yearOfReleaseQuery = yearOfReleaseTextField.getText();
                String specialPressQuery = specialPressTextField.getText();
                String numberOfCopiesQuery = numberOfCopiesTextField.getText();
        
                //*Check if the boolean special press value is true or false */
                boolean specialPressValue = "true".equalsIgnoreCase(specialPressQuery);
        
                //*Check if vinyl attribute fields have been modified */
                boolean isModified = !UserTerminal.selectedVinyl.getAlbumName().equals(albumNameQuery) ||
                        !UserTerminal.selectedVinyl.getArtistName().equals(artistNameQuery) ||
                        !UserTerminal.selectedVinyl.getGenre().equals(genreQuery) ||
                        !UserTerminal.selectedVinyl.getYearOfRelease().equals(yearOfReleaseQuery) ||
                        UserTerminal.selectedVinyl.isSpecialPress() != specialPressValue || // Check for specialPressValue change
                        UserTerminal.selectedVinyl.getNumberOfCopies() != Integer.parseInt(numberOfCopiesQuery);
        
                //*If the vinyl is modified, apply changes */
                if (isModified) {
            
                    UserTerminal.selectedVinyl.setAlbumName(albumNameQuery);
                    UserTerminal.selectedVinyl.setArtistName(artistNameQuery);
                    UserTerminal.selectedVinyl.setGenre(genreQuery);
                    UserTerminal.selectedVinyl.setYearOfRelease(yearOfReleaseQuery);
                    UserTerminal.selectedVinyl.setSpecialPress(specialPressValue);
                    UserTerminal.selectedVinyl.setNumberOfCopies(Integer.parseInt(numberOfCopiesQuery));

                    //*If copies is 0, remove vinyl */
                    if(UserTerminal.selectedVinyl.getNumberOfCopies() < 1){

                        int vinylIndexToDelete = VinylCollection.doesVinylExistStatic(UserTerminal.vinylCollectionList, UserTerminal.selectedVinyl);
                        UserTerminal.vinylCollectionList.remove(vinylIndexToDelete);
                    }

                    //*Serialize/save collection */
                    try {
                        UserTerminal.vinylCollectionList.serializeCollection();
                        UserTerminal.observableVinylList.setAll(UserTerminal.vinylCollectionList.getVinylCollection());
                        UserTerminal.tableView.setItems(UserTerminal.observableVinylList);
                    } catch (ClassNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            if (UserTerminal.selectedVinyl != null){
              int vinylIndexToDelete = VinylCollection.doesVinylExistStatic(UserTerminal.vinylCollectionList, UserTerminal.selectedVinyl);
              UserTerminal.vinylCollectionList.remove(vinylIndexToDelete);
            }

            //*Serialize/save collection */
                    try {
                        UserTerminal.vinylCollectionList.serializeCollection();
                        UserTerminal.observableVinylList.setAll(UserTerminal.vinylCollectionList.getVinylCollection());

                        //*Clear result window text fields */
                        albumNameTextField.clear();
                        artistNameTextField.clear();
                        genreTextField.clear();
                        yearOfReleaseTextField.clear();
                        specialPressTextField.clear();
                        numberOfCopiesTextField.clear();
                        contributingArtistListView.getItems().clear();

                        //*update the listView */
                        UserTerminal.observableVinylList.setAll(UserTerminal.vinylCollectionList.getVinylCollection());

                        UserTerminal.tableView.setItems(UserTerminal.observableVinylList);
                    } catch (ClassNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
        });

        //*create contributing artist button to open edit contributing artist window*/
        Button contributingArtistButton = new Button("Contributing Artist(s)");
        contributingArtistButton.setOnAction(e ->{
            EditContributingArtistWindow editContributingArtistWindow = new EditContributingArtistWindow();
            HBox editContributingArtistWindowHBox = editContributingArtistWindow.createNewEditContributingArtistWindow();

            //*Create New Stage */
            Stage editContributingArtistStage = new Stage();
            Scene editContributingArtistScene = new Scene(editContributingArtistWindowHBox, 800, 50);

            //*Set the scene for the stage */
            editContributingArtistStage.setScene(editContributingArtistScene);

            //*Show the stage */
            editContributingArtistStage.show();
        });

        //*Create new vinyl button to open a new instance of NewVinylWindow */
        Button newVinylButton = new Button("New Vinyl");
        newVinylButton.setOnAction(e -> {
            NewVinylWindow newVinylWindow = new NewVinylWindow();
            VBox newVinylWindowContentVBox = newVinylWindow.createNewVinylWindow();
        
            // Create a new stage
            Stage newVinylStage = new Stage();
            Scene newVinylScene = new Scene(newVinylWindowContentVBox, 400, 700);
        
            // Set the scene for the stage
            newVinylStage.setScene(newVinylScene);
        
            // Show the stage
            newVinylStage.show();
        });

        //*Create hbox for save and delete buttons */
        HBox saveDeleteHBox = new HBox();
        saveDeleteHBox.setPrefHeight(50);
        saveDeleteHBox.getChildren().addAll(saveButton, deleteButton);
        saveDeleteHBox.setAlignment(Pos.BOTTOM_LEFT);

        HBox contributingArtistHBox = new HBox();
        contributingArtistHBox.setPrefHeight(50);
        contributingArtistHBox.getChildren().add(contributingArtistButton);
        contributingArtistHBox.setAlignment(Pos.TOP_RIGHT);
        
        //*add all components to albuminfo vbox */
        albumInfoVBox.getChildren().addAll(albumNameLabel, albumNameTextField, artistNameLabel, artistNameTextField, genreLabel, genreTextField, yearOfReleaseLabel, 
            yearOfReleaseTextField, specialPressLabel, specialPressTextField, numberOfCopiesLabel, numberOfCopiesTextField, contributingArtistLabel, contributingArtistListView, 
            contributingArtistHBox, saveDeleteHBox, newVinylButton);
        albumNameLabel.setAlignment(Pos.TOP_LEFT);
        resultsWindow.getChildren().addAll(albumCoverVBox, albumInfoVBox);
        resultsWindow.setStyle("-fx-background-color: LIGHTGREY");
        return resultsWindow;
    }

    //*Update resultWindow to  show attributs for selected vinyl */
    public static void updateResultWindow(Vinyl vinyl) {
        albumNameTextField.setText(vinyl.getAlbumName());
        artistNameTextField.setText(vinyl.getArtistName());
        genreTextField.setText(vinyl.getGenre());
        yearOfReleaseTextField.setText(vinyl.getYearOfRelease());
        specialPressTextField.setText(Boolean.toString(vinyl.isSpecialPress()));
        numberOfCopiesTextField.setText(vinyl.getNumberOfCopiesToString());

        List<String> contributingArtistsList = vinyl.getContributingArtistsAsList();

        if (contributingArtistsList != null && !contributingArtistsList.isEmpty()) {
            ObservableList<String> contributingArtists = FXCollections.observableArrayList(contributingArtistsList);
            contributingArtistListView.setItems(contributingArtists);
        } else {
            contributingArtistListView.setItems(FXCollections.emptyObservableList());
        }
    }

    
}