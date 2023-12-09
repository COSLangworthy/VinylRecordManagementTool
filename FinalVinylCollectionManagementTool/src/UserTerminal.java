/**
 * Code for the main ser terminal, this code contains the main method
 *
 *
 * @author Stephan Langworthy
 * @since 1.0
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;

public class UserTerminal extends Application {

    static ObservableList<Vinyl> observableVinylList;
    static TableView<Vinyl> tableView;
    final static VinylCollection vinylCollectionList = new VinylCollection();
    private final Search search;
    private VBox resultWindow;
    private boolean isSpecialPressFilterSelected = false;
    private boolean isNotSpecialPressFilterSelected = false;
    private boolean isContributingArtistFilterSelected = false;
    private boolean isNotContributingArtistFilterSelected = false;
    static Vinyl selectedVinyl;

    public UserTerminal() {
        search = new Search();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException, IOException {
        initializeObservableList();

        //*Create new table view and rows */
        tableView = new TableView<>();
        tableView.setRowFactory(e -> new TableRow<>());
        tableView.setStyle("-fx-font-size: 14;");

        /**
         * Create a tableView columns for all artist, album, genre, specialpress, year of release, and contributingartists
         */
        TableColumn<Vinyl, String> artistColumn = new TableColumn<>("Artist");
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        artistColumn.setPrefWidth(300);

        TableColumn<Vinyl, String> albumColumn = new TableColumn<>("Album");
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("albumName"));
        albumColumn.setPrefWidth(300);

        TableColumn<Vinyl, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        genreColumn.setPrefWidth(125);

        TableColumn<Vinyl, String> specialPressColumn = new TableColumn<>("Special Press");
        specialPressColumn.setCellValueFactory(new PropertyValueFactory<>("specialPress"));
        specialPressColumn.setPrefWidth(125);

        TableColumn<Vinyl, String> yearOfReleaseColumn = new TableColumn<>("Year of Release");
        yearOfReleaseColumn.setCellValueFactory(new PropertyValueFactory<>("yearOfRelease"));

        TableColumn<Vinyl, String> contributingArtistsColumn = new TableColumn<>("Contributing Artists");
        contributingArtistsColumn.setCellValueFactory(new PropertyValueFactory<>("contributingArtists"));
        contributingArtistsColumn.setPrefWidth(350);

        //*add columns to table view and populate list with vinyls from the observable list*/
        tableView.getColumns().addAll(artistColumn, albumColumn, genreColumn, specialPressColumn, yearOfReleaseColumn, contributingArtistsColumn);
        tableView.setItems(observableVinylList);
        artistColumn.setSortType(SortType.ASCENDING);
        tableView.getSortOrder().add(artistColumn);
        tableView.sort();

        //*Create a selection model and listener to update result window with the selected vinyl from the table view */
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedVinyl = newSelection;
                ResultWindow.updateResultWindow(selectedVinyl);
            }
        });

        //*Create search bar for finding vinyls */
        TextField searchBar = new TextField();
        searchBar.setPrefWidth(300);
        searchBar.setPromptText("Search...");

        //*Create search button for search bar */
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            String query = searchBar.getText();
            ObservableList<Vinyl> searchedList = search.searchAndFilter(observableVinylList, query, isSpecialPressFilterSelected, isNotSpecialPressFilterSelected, isContributingArtistFilterSelected, isNotContributingArtistFilterSelected);
            tableView.setItems(searchedList);
        });

        //*Create radio buttons and toggle group for SpecialPress filtering */
        ToggleGroup specialPressToggleGroup = new ToggleGroup();
        RadioButton specialPressNARadioButton = new RadioButton("N/A");
        RadioButton specialPressRadioButton = new RadioButton("Special Pressing");
        RadioButton notSpecialPressRadioButton = new RadioButton("Not Special Pressing");
        specialPressNARadioButton.setToggleGroup(specialPressToggleGroup);
        specialPressRadioButton.setToggleGroup(specialPressToggleGroup);
        notSpecialPressRadioButton.setToggleGroup(specialPressToggleGroup);
        specialPressNARadioButton.setSelected(true);

        //*Create radio buttons and toggle group for contributingArtist filtering */
        ToggleGroup contributingArtistToggleGroup = new ToggleGroup();
        RadioButton contributingArtistNARadioButton = new RadioButton("N/A");
        RadioButton hasContributingArtistRadioButton = new RadioButton("Has Contributing Artist(s)");
        RadioButton doesNotHaveContributingArtistRadioButton = new RadioButton("Does Not Have Contributing Artist(s)");
        contributingArtistNARadioButton.setToggleGroup(contributingArtistToggleGroup);
        hasContributingArtistRadioButton.setToggleGroup(contributingArtistToggleGroup);
        doesNotHaveContributingArtistRadioButton.setToggleGroup(contributingArtistToggleGroup);
        contributingArtistNARadioButton.setSelected(true);

        //*If SpcialPress N/A button is pressed set booleans accordingly */
        specialPressNARadioButton.setOnAction(e -> {
            isSpecialPressFilterSelected = false;
            isNotSpecialPressFilterSelected = false;
        });

        //*If SpecialPress radio button is pressed set booleans accordingly */
        specialPressRadioButton.setOnAction(e -> {
            isSpecialPressFilterSelected = true;
            isNotSpecialPressFilterSelected = false;
        });

        //*If not special Press radio button is pressed set booleans accordingly */
        notSpecialPressRadioButton.setOnAction(e -> {
            isNotSpecialPressFilterSelected = true;
            isSpecialPressFilterSelected = false;
        });

        //*if contributing artists N/A button is pressed set booleans accordingly */
        contributingArtistNARadioButton.setOnAction(e -> {
            isContributingArtistFilterSelected = false;
            isNotContributingArtistFilterSelected = false;

        });

        //*If contributing artists radio button is pressed set booleans accordingly */
        hasContributingArtistRadioButton.setOnAction(e -> {
            isContributingArtistFilterSelected = true;
            isNotContributingArtistFilterSelected = false;
        });

        //*If not contributing artists radio button is pressed set booleans accordingly */
        doesNotHaveContributingArtistRadioButton.setOnAction(e -> {
            isContributingArtistFilterSelected = false;
            isNotContributingArtistFilterSelected = true;
        });


        //*Create Vertical box for Special press Filter label and radio buttons*/
        VBox specialPressFilterVBox = new VBox(5);
        Label specialPressLabel = new Label("Special Pressing:");
        specialPressFilterVBox.getChildren().add(specialPressLabel);
        specialPressFilterVBox.setPadding(new Insets(15, 15, 15, 15));

        //*Add Radio buttons to for Special press filtering */
        specialPressFilterVBox.getChildren().addAll(specialPressNARadioButton, specialPressRadioButton, notSpecialPressRadioButton);
        specialPressFilterVBox.setAlignment(Pos.BOTTOM_LEFT);

        //*Create vertical box for Contributing Artists Filter label */
        VBox contributingArtistFilterVBox = new VBox(5);
        Label contributingArtistLabel = new Label("Contributing Artists:");
        contributingArtistFilterVBox.getChildren().add(contributingArtistLabel);
        contributingArtistFilterVBox.setPadding(new Insets(15, 15, 15, 15));

        //*Add filter radio buttons to contributingartist filter vbox */
        contributingArtistFilterVBox.getChildren().addAll(contributingArtistNARadioButton, hasContributingArtistRadioButton, doesNotHaveContributingArtistRadioButton);
        contributingArtistFilterVBox.setAlignment(Pos.BOTTOM_LEFT);

        //*add searchbar, searchbutton and filter vboxes to searchBarHbox */
        HBox searchBarHBox = new HBox(15, searchBar, searchButton, specialPressFilterVBox, contributingArtistFilterVBox);
        searchBarHBox.setStyle("-fx-background-color: LIGHTGREY");
        searchBarHBox.setAlignment(Pos.BOTTOM_LEFT);

        //*create and add search window pane for search bar and tableview */
        BorderPane searchWindowPane = new BorderPane();
        searchWindowPane.setCenter(tableView);
        searchWindowPane.setTop(searchBarHBox);

        //*create resultwindow using ResultWindow */
        ResultWindow resultWindowController = new ResultWindow();
        resultWindow = resultWindowController.resultWindow();

        //*Add result window to result window pane */
        BorderPane resultWindowPane = new BorderPane();
        resultWindowPane.setCenter(resultWindow);

        //*Create borderpane and add resultWindowPane and searchWindowPane */
        BorderPane borderPane = new BorderPane();
        borderPane.setRight(resultWindowPane);
        borderPane.setLeft(searchWindowPane);

        //*Get primary screen width and height*/
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        //*Create and show scene */
        Scene scene = new Scene(borderPane);
        primaryStage.setTitle("Vinyl Collection");
        primaryStage.setWidth(screenWidth);
        primaryStage.setHeight(screenHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //*initialize observable list from vinylCollectionList */
    public static void initializeObservableList() {
        observableVinylList = FXCollections.observableList(vinylCollectionList.getVinylCollection());
    }

    //*return the observable vinylCollecitonList */
    public static ObservableList<Vinyl> getObservableVinylList() {
        return observableVinylList;
    }

    public static Vinyl getSelectedVinyl() {
        return selectedVinyl;
    }

    public static void setSelectedVinyl(Vinyl selectedVinyl) {
        UserTerminal.selectedVinyl = selectedVinyl;
    }
}
