/**
 * This result list was meant to take over the logic and defining of the tableView that displays the vinyl colleciton list. This code does not work.
 *
 *
 * @author Stephan Langworthy
 * @since 1.0
 */

import javafx.collections.ObservableList;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.SortType;

public class ResultList {

    private TableView<Vinyl> tableView;

    public ResultList(ObservableList<Vinyl> observableVinylList) {
        initializeTableView(observableVinylList);
    }

    public TableView<Vinyl> getTableView() {
        return tableView;
    }

    private void initializeTableView(ObservableList<Vinyl> observableVinylList) {
        //*Create new table view and rows */
        tableView = new TableView<>();
        tableView.setRowFactory(e -> new TableRow<>());
        tableView.setStyle("-fx-font-size: 14;");
        tableView.setItems(observableVinylList);

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
                UserTerminal.setSelectedVinyl(newSelection);
                ResultWindow.updateResultWindow(UserTerminal.getSelectedVinyl());
            }
        });
    }
}
