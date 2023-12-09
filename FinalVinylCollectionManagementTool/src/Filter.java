/**
 * Code for the filtering used during the Search of vinyls
 *
 *
 * @author Stephan Langworthy
 * @since 1.0
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Filter {

    public static ObservableList<Vinyl> specialPressFilter(
            ObservableList<Vinyl> observableVinylList,
            boolean isSpecialPressButtonPressed, boolean isNotSpecialPressButtonPressed) {

        ObservableList<Vinyl> filteredList = FXCollections.observableArrayList();

        for (Vinyl vinyl : observableVinylList) {
            if ((isSpecialPressButtonPressed && vinyl.isSpecialPressString().equals("Yes")) ||
                    (isNotSpecialPressButtonPressed && vinyl.isSpecialPressString().equals("No"))) {
                filteredList.add(vinyl);
            }
        }
        return filteredList;
    }

    public static ObservableList<Vinyl> contributingArtistFilter(
        ObservableList<Vinyl> observableVinylList,
        boolean isContributingArtistButtonPressed, boolean isNotContributingArtistButtonPressed) {

    ObservableList<Vinyl> filteredList = FXCollections.observableArrayList();

    for (Vinyl vinyl : observableVinylList) {
        if ((isContributingArtistButtonPressed && vinyl.hasContributingArtist()) ||
                (isNotContributingArtistButtonPressed && !vinyl.hasContributingArtist())) {
            filteredList.add(vinyl);
        }
    }
    return filteredList;
}
}
