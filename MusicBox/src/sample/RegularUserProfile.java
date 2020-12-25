package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class RegularUserProfile extends Stage{
    int song;
    ArrayList<Button> removeButtons = new ArrayList<>();
    ArrayList<Button> songTitles = new ArrayList<Button>();
    ArrayList<Text> artistNames = new ArrayList<>();
    ArrayList<Text> albumsTitles = new ArrayList<>();
    ArrayList<Text> duration = new ArrayList<>();
    ArrayList<HBox> hBoxes = new ArrayList<>();
    RegularUserProfile(RegularUser user)
    {
        this.setWidth(400);
        this.setHeight(600);
        this.setTitle(user.getUsername() + " Profile");
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        //gridPane.setAlignment(Pos.CENTER);
        Image image = new Image("GenericProfile.png");
        ImageView imageView=new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        gridPane.add(imageView,0,0);
        Text name=new Text(user.getName());
        Text title=new Text("'s Music Box");
        textDesign(name,15);
        textDesign(title,10);
        gridPane.add(name, 2, 0);
        gridPane.add(title,3,0);


        Text nrOfSongs=new Text("Songs: "+String.valueOf(user.getNrOfSongs()));
        Text nrOfPlaylists=new Text("Playlists: "+String.valueOf(user.getNrOfPlaylists()));
        Text nrOfArtists=new Text("Artists: "+String.valueOf(user.getNrOfArtists()));
        textDesign(nrOfSongs,15);
        textDesign(nrOfPlaylists,15);
        textDesign(nrOfArtists,15);

        gridPane.add(nrOfSongs, 0, 1);
        gridPane.add(nrOfPlaylists,0,2);
        gridPane.add(nrOfArtists,0,3);
        gridPane.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #63dafa, #e3b0dc);");
        Scene scene = new Scene(gridPane);

        Set<Artist> listOfArtists = new HashSet<Artist>();
        try {
            BufferedReader in = new BufferedReader(
                    new FileReader("users.txt"));
            String line;
            while ((line = in.readLine()) != null) {
                String[] userInfo = line.split(",");
                if(userInfo[3].equals("1"))
                {
                    Scanner scanner=new Scanner(new File(userInfo[1]+".txt"));
                    int nrOfAlbums = scanner.nextInt();
                    int nrOfArtistSongs=scanner.nextInt();
                    int nrOfSubscribers=scanner.nextInt();
                    Artist newArtist = new Artist(userInfo[1],userInfo[2],userInfo[0],"",nrOfAlbums,nrOfArtistSongs,nrOfSubscribers,1);
                    listOfArtists.add(newArtist);
                }
            }
        }
        catch (IOException e) {
            System.out.println("File Read Error");
        }

        Map<String, Song> listOfSongs = new HashMap<String, Song>();
        try{
            BufferedReader in = new BufferedReader(
                    new FileReader("songs.txt"));
            String line;
            while ((line = in.readLine())!= null) {
                String[] songInfo = line.split(",");
                Artist artist = findSongArtist((HashSet<Artist>) listOfArtists,songInfo[0]);
                Song song = new Song(artist,songInfo[1],songInfo[2],songInfo[3],songInfo[4],songInfo[5],songInfo[1]+".mp3");
                listOfSongs.put(songInfo[1],song);
            }

        }
        catch (IOException e) {
            System.out.println("File Read Error");
        }

        Text header1 = new Text("Song Title");
        textDesign(header1,16);
        Text header2 = new Text("Artist");
        textDesign(header2,16);
        Text header3 = new Text("Album");
        textDesign(header3,16);
        Text header4 = new Text("Duration");
        textDesign(header4,16);


        gridPane.add(header1,0,6);
        gridPane.add(header2,2,6);
        gridPane.add(header3,3,6);
        gridPane.add(header4,4,6);

        UserLibrary library = user.getUserSongs(user.getUsername()+".txt", (HashMap<String, Song>) listOfSongs);



        int position = 8;
        song = 0;
        for (String keys : library.userLibrarySongs.keySet()) {

            Button removeSong = new Button("-");
            Button newSong = new Button(library.userLibrarySongs.get(keys).getTitle());
            songTitles.add(newSong);
            removeButtons.add(removeSong);
            HBox newHbox = new HBox(removeSong,newSong);
            hBoxes.add(newHbox);

            Text artistName = new Text(library.userLibrarySongs.get(keys).getArtistName().getName());
            Text albumTitle = new Text(library.userLibrarySongs.get(keys).getAlbumName());
            Text durationSong = new Text("            " + library.userLibrarySongs.get(keys).getMinutes() + ":" + library.userLibrarySongs.get(keys).getSeconds());
            artistNames.add(artistName);
            albumsTitles.add(albumTitle);
            duration.add(durationSong);

            songTitles.get(song).setStyle("-fx-background-color: transparent;-fx-font: 13 arial; ");
            removeButtons.get(song).setTextFill(Color.WHITE);
            removeButtons.get(song).setStyle("-fx-font: 10 arial; -fx-base: #1c1d1d;");
            songTitles.get(song).setTextFill(Color.DARKBLUE);


            gridPane.add(hBoxes.get(song), 0, position);
            //gridPane.add(removeButtons.get(song),0,position);
            //gridPane.add(songTitles.get(song),2,position);
            gridPane.add(artistNames.get(song), 2, position);
            gridPane.add(albumsTitles.get(song), 3, position);
            gridPane.add(duration.get(song), 4, position);
            position++;


            newSong.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {

                    String path = library.userLibrarySongs.get(keys).getTitle() + ".mp3";
                    Media media = new Media(new File(path).toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    MediaView mediaView = new MediaView(mediaPlayer);
                    //gridPane.add(mediaView,0,10);
                    // mediaPlayer.setAutoPlay(true);
                    mediaPlayer.play();


                    new AudioPlayer(mediaPlayer, mediaView, library.userLibrarySongs.get(keys));

                }
            });

            removeSong.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                        removeSong.setVisible(false);
                        newSong.setVisible(false);
                        newHbox.setVisible(false);
                        gridPane.getChildren().remove(removeSong);
                        gridPane.getChildren().remove(newSong);
                        gridPane.getChildren().remove(newHbox);
                        gridPane.getChildren().remove(albumTitle);
                        gridPane.getChildren().remove(artistName);
                        gridPane.getChildren().remove(durationSong);


                        MusicManager.removeSong(newSong.getText(), library);
                        user.setNrOfSongs(user.getNrOfSongs()-1);
                        removeButtons.remove(removeSong);
                        songTitles.remove(newSong);
                        albumsTitles.remove(albumTitle);
                        artistNames.remove(artistName);
                        duration.remove(durationSong);

                        deleteAll(gridPane,user.getNrOfSongs());

                    int pos = 8;
                    for(int j=0;j<user.getNrOfSongs();j++)
                    {
                        HBox box= new HBox(removeButtons.get(j),songTitles.get(j));
                        gridPane.add(box, 0, pos);
                        gridPane.add(artistNames.get(j), 2, pos);
                        gridPane.add(albumsTitles.get(j), 3, pos);
                        gridPane.add(duration.get(j), 4, pos);
                        pos++;
                    }
                    nrOfSongs.setText("Songs: "+String.valueOf(user.getNrOfSongs()));
                }
            });
            song++;
        }

        this.setScene(scene);
        this.show();

    }
    public void textDesign(Text text, int font)
    {

        text.setFont(Font.font("Arial", FontWeight.BOLD, font));
        text.setFill(Color.BLACK);
        text.setStroke(Color.ROYALBLUE);
        text.setStrokeWidth(0.5);
        text.setFontSmoothingType(FontSmoothingType.LCD);
    }

    public Artist findSongArtist(HashSet<Artist> listOfArtists,String name)
    {
        for(Artist artist:listOfArtists)
        {
            if(artist.getName().equals(name))
                return artist;
        }
        return null;
    }
    public void deleteAll(GridPane gridpane, int nrSongs)
    {
        for(int i=0;i<nrSongs;i++)
        {
            gridpane.getChildren().remove(removeButtons.get(i));
            gridpane.getChildren().remove(songTitles.get(i));
            gridpane.getChildren().remove(hBoxes.get(i));
            gridpane.getChildren().remove(artistNames.get(i));
            gridpane.getChildren().remove(albumsTitles.get(i));
            gridpane.getChildren().remove(duration.get(i));
        }
    }

}
