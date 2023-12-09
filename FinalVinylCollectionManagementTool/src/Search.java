/**
 * Code for the search methods used to search the collection list of Vinyls
 *
 *
 * @author Stephan Langworthy
 * @since 1.0
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Search {

    private boolean containsIgnoreCase(String item, String query) {
        return item.toLowerCase().contains(query.toLowerCase());
    }

    public ObservableList<Vinyl> searchAndFilter(ObservableList<Vinyl> vinylList, String query, boolean isSpecialPressSelected, boolean isNotSpecialPressSelected, boolean isContributingArtistSelected, boolean isNoContributingArtistSelected) {
        ObservableList<Vinyl> searchedList = FXCollections.observableArrayList();
    
        for (Vinyl vinyl : vinylList) {
            if (vinyl.getArtistName().toLowerCase().contains(query.toLowerCase()) ||
                    vinyl.getAlbumName().toLowerCase().contains(query.toLowerCase()) ||
                    containsIgnoreCase(vinyl.getContributingArtists(), query)) {
                searchedList.add(vinyl);
            }
        }
    
        // Apply filters based on radio button selection
        if (isSpecialPressSelected || isNotSpecialPressSelected) {
            searchedList = Filter.specialPressFilter(searchedList, isSpecialPressSelected, isNotSpecialPressSelected);
        }
    
        if (isContributingArtistSelected || isNoContributingArtistSelected) {
            searchedList = Filter.contributingArtistFilter(searchedList, isContributingArtistSelected, isNoContributingArtistSelected);
        }
    
        return searchedList;
    }
    
    
}