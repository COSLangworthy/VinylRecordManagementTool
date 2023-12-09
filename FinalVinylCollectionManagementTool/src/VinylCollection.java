/**
 * Code for deifning the vinyl colleciton list which holds elements of type Vinyl
 *
 * @author Stephan Langworthy
 * @since 1.0
 */

import java.io.*;
import java.util.ArrayList;

public class VinylCollection extends ArrayList<Vinyl> {

    private static final long serialVersionUID = 2112L;
    File file = new File("Resources/collection.dat");

    public VinylCollection() {
        super();

        // Deserialize and load collection if the file exists
        if (new File("Resources/collection.dat").exists()) {
            try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("Resources/collection.dat"))) {

                // Read the object from the object input stream
                VinylCollection loadedCollection = (VinylCollection) input.readObject();

                // Clear current collection and add all elements from the loaded collection
                this.clear();
                this.addAll(loadedCollection);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Failed to deserialize");
            }
        }
    }

    //*retun copy of vinyl collection list */
        public ArrayList<Vinyl> getVinylCollection(){

            return new ArrayList<>(this);
        }

    // Add Vinyl to the VinylCollection if does not exist already. Increment count of existing element if so
    public void addToVinylList(Vinyl newVinyl) {
        int vinylIndexExists = doesVinylExist(newVinyl);

        // Check if the vinyl already exists in the collection
        if (vinylIndexExists != -1) {
            // If index for matching vinyl exists, increments copies
            Vinyl existingVinyl = get(vinylIndexExists);
            
            existingVinyl.addVinylCopy(newVinyl.getNumberOfCopies());
        } else {
            // If it doesn't exist, add the new vinyl to the list
            add(newVinyl);
        }
    }

    // Check if vinyl exists with album name and artist and special pressing status, retun the index of the existing vinyl
    public int doesVinylExist(Vinyl newVinyl) {
        for (int i = 0; i < size(); i++) {
            Vinyl currentVinyl = get(i);
            
            if (currentVinyl.getArtistName().equals(newVinyl.getArtistName())
                    && currentVinyl.getAlbumName().equals(newVinyl.getAlbumName())
                    && currentVinyl.isSpecialPressString() == newVinyl.isSpecialPressString()) {
                // Return the index of the matched vinyl
                return i;
            }
        }
        return -1;
    }

    // Check if vinyl exists with album name and artist and special pressing status, retun the index of the existing vinyl
    public static int doesVinylExistStatic(VinylCollection vinylCollection, Vinyl newVinyl) {
        for (int i = 0; i < vinylCollection.size(); i++) {
            Vinyl currentVinyl = vinylCollection.get(i);
            
            if (currentVinyl.getArtistName().equals(newVinyl.getArtistName())
                    && currentVinyl.getAlbumName().equals(newVinyl.getAlbumName())
                    && currentVinyl.isSpecialPressString() == newVinyl.isSpecialPressString()) {
                // Return the index of the matched vinyl
                return i;
            }
        }
        return -1;
    }



    // Remove a copy or the whole object if no more
    public void removeFromVinylList(Vinyl oldVinyl, int numberToRemove) {
        int vinylIndexExists = doesVinylExist(oldVinyl);
    
        // Check if vinyl already exists in the collection
        if (vinylIndexExists != -1) {
            Vinyl existingVinyl = get(vinylIndexExists);
    
            // Check if remaining copies will be less than or equal to 0
            if ((existingVinyl.getNumberOfCopies() - numberToRemove) <= 0) {
                // Remove the vinyl from the list entirely
                remove(vinylIndexExists);
            } else {
                // Remove specified number of copies
                existingVinyl.removeVinylCopy(numberToRemove);
            }
        } else {
            System.out.println("There has been an issue removing this entry");
        }
    }
    

    // Serialize and save vinyl collection
    public void serializeCollection() throws ClassNotFoundException, IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("Resources/collection.dat"))) {
            // Write the object to the object output stream
            output.writeObject(this);
        } catch (IOException e) {
            System.out.println("Failed to serialize");
        }
    }

    // Deserialize and load vinyl collection
    private void deserializeCollection() throws ClassNotFoundException, IOException {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("Resources/collection.dat"))) {
            // Read the object from the object input stream
            VinylCollection loadedCollection = (VinylCollection) input.readObject();
            // Clear current collection and add all elements from the loaded collection

            this.clear();
            this.addAll(loadedCollection);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to deserialize");
        }
    }
}