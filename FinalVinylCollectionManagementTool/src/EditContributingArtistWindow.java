/**
 * Code for the edit contributing artist(s) window that allows the user to edit the list of contributing artist(s) for a selected vinyl
 *
 *
 * @author Stephan Langworthy
 * @since 1.0
 */

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class EditContributingArtistWindow extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        HBox newEditContributingArtistWindow = createNewEditContributingArtistWindow();

        Scene scene = new Scene(newEditContributingArtistWindow);
        primaryStage.setTitle("Edit Contributing Artist(s)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    HBox createNewEditContributingArtistWindow(){

        HBox newEditContributingArtistWindow = new HBox();
        newEditContributingArtistWindow.setPadding(new Insets(10));
        newEditContributingArtistWindow.setSpacing(20);
        newEditContributingArtistWindow.setStyle("-fx-background-color: LIGHTGREY");

        TextField textField = new TextField(UserTerminal.selectedVinyl.getContributingArtists());
        textField.setPrefWidth(700);
        textField.setAlignment(Pos.CENTER);

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {

            UserTerminal.selectedVinyl.setContributingArtist(textField.getText());

            //*Serialize/save collection */
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
        });

        newEditContributingArtistWindow.getChildren().addAll(textField, updateButton);

        return newEditContributingArtistWindow;
    }
}
