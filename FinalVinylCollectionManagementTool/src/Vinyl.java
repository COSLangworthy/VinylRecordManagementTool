/**
 * Code for defining the Vinyl class
 *
 *
 * @author Stephan Langworthy
 * @since 1.0
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Vinyl implements Serializable{

    String artistName;
    String albumName;
    String genre;
    int yearOfRelease;
    ArrayList<String> contributingArtists;
    int vinylSize;
    boolean specialPress = true;
    int numberOfCopies = 1; 
    
    //*Vinyl Constructer */
    public Vinyl (String artistName, String albumName, String genre, boolean specialPress, int numberOfCopies){

        this.artistName = artistName;
        this.albumName = albumName;
        this.genre = genre;
        this.specialPress = specialPress;
        this.numberOfCopies = numberOfCopies;
        this.contributingArtists = new ArrayList<>();
    }

    //*To string override */
    @Override
    public String toString(){
        return artistName + albumName + genre + specialPress + numberOfCopies;
    }

    //*Set year of release */
    public void setYearOfRelease(int yearOfRelease){

        this.yearOfRelease = yearOfRelease;
    }

    //*Get artist name */
    public String getArtistName(){
        return this.artistName;
    }

    //*Get album name */
    public String getAlbumName(){
        return this.albumName;
    }

    //*Get genre */
    public String getGenre(){
        return this.genre;
    }

    //*Get year of release */
    public String getYearOfRelease(){

        if(this.yearOfRelease < 1){

            return "n/a";
        }

        else{
           return Integer.toString(this.yearOfRelease); 
        }
    }

    //*Check if vinyl has contributing Artists */
    public boolean hasContributingArtist() {
        return this.contributingArtists != null && !this.contributingArtists.isEmpty();
    }

    //*Get list of contributing artist(s) using comma to seperate strings */
    public String getContributingArtists() {
        if (!hasContributingArtist()) {
            return "<None>";
        } else {
            return String.join(", ", this.contributingArtists);
        }
    }

    //*get list of contributing artist(s) as a list */
    public ArrayList<String> getContributingArtistsAsList(){
        return this.contributingArtists;
    }

    //*Set Vinyl Size */
    public void setVinylSize(int vinylSize){
        this.vinylSize = vinylSize;
    }

    //*Get Vinyl Size */
    public int getVinylSize(){
        return this.vinylSize;
    }

    //*Get is special press to string*/
    public String isSpecialPressString(){

        if (this.specialPress){
            return "Yes";
        }
        else{
            return "No";
        }
    }

    //*Get special press*/
    public boolean isSpecialPress() {
        return this.specialPress;
    }
    
    
    //*Get number of copies */
    public int getNumberOfCopies(){
        return this.numberOfCopies;
    }

    //*Get number of copies as string */
    public String getNumberOfCopiesToString(){
        return Integer.toString(this.numberOfCopies);
    }

    //*Add copy */
    public void addVinylCopy(int addedCount){
        this.numberOfCopies += addedCount;
    }

    //*Remove copy */
    public void removeVinylCopy(int removalCount){
        this.numberOfCopies -= removalCount;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setYearOfRelease(String yearOfRelease) {
        if ("n/a".equalsIgnoreCase(yearOfRelease)) {
            this.yearOfRelease = 0;
        } else {
            try {
                this.yearOfRelease = Integer.parseInt(yearOfRelease);
            } catch (NumberFormatException e) {
                // Handle the case where the provided yearOfRelease is not a valid integer
            }
        }
    }

    public void setContributingArtists(ArrayList<String> contributingArtists) {
        this.contributingArtists = contributingArtists;
        Collections.sort(this.contributingArtists);
    }

    //*Set contributing artists */
    public void setContributingArtist(String commaDelimString){

        contributingArtists.clear();

        if(!commaDelimString.equals("")){

            String[] stringValues = commaDelimString.split(",");


            for(String value : stringValues){

                value = value.trim();
                this.contributingArtists.add(value);
            }
        }
    }

    public void setSpecialPress(boolean specialPress) {
        this.specialPress = specialPress;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

}
