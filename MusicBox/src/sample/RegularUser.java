package sample;

import java.util.HashSet;
import java.util.Set;

public class RegularUser extends User implements MusicManager<Song> {
    private Set<Artist> listOfArtists = new HashSet<Artist>();
    private UserLibrary userLibrary;

    public RegularUser(String username,String password, String name, String imagePath,int type)
    {
        super(username,password,name,imagePath,type);

    }
    public Set<Artist> getListOfArtists() {
        return listOfArtists;
    }

    public void setListOfArtists(Set<Artist> listOfArtists) {
        this.listOfArtists = listOfArtists;
    }

    @Override
    public  void addSong(Song song) {

    }

}
