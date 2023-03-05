package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class SpotifyRepository {
    public HashMap<Artist, List<Album>> artistAlbumMap;
    public HashMap<Album, List<Song>> albumSongMap;
    public HashMap<Playlist, List<Song>> playlistSongMap;
    public HashMap<Playlist, List<User>> playlistListenerMap;
    public HashMap<User, Playlist> creatorPlaylistMap;
    public HashMap<User, List<Playlist>> userPlaylistMap;
    public HashMap<Song, List<User>> songLikeMap;

    public List<User> users;
    public List<Song> songs;
    public List<Playlist> playlists;
    public List<Album> albums;
    public List<Artist> artists;

    public SpotifyRepository(){
        //To avoid hitting apis multiple times, initialize all the hashmaps here with some dummy data
        artistAlbumMap = new HashMap<>();
        albumSongMap = new HashMap<>();
        playlistSongMap = new HashMap<>();
        playlistListenerMap = new HashMap<>();
        creatorPlaylistMap = new HashMap<>();
        userPlaylistMap = new HashMap<>();
        songLikeMap = new HashMap<>();

        users = new ArrayList<>();
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        albums = new ArrayList<>();
        artists = new ArrayList<>();
    }

    public User createUser(String name, String mobile) {
        users.add(new User(name,mobile));
        return new User(name,mobile);
    }

    public Artist createArtist(String name) {
        artists.add(new Artist(name));
        return new Artist(name);
    }

    public Album createAlbum(String title, String artistName) {
        List<Album>temp=new ArrayList<>();
        Album album=new Album(title);
        if(!artists.contains(artistName)){
            Artist curr= new Artist(artistName);
            temp.add(album);
            artistAlbumMap.put(curr,temp);
        }
        else{
            for(Artist art:artists){
                if(art.getName().equals(artistName)){
                    temp=artistAlbumMap.get(art);
                    temp.add(album);
                    artistAlbumMap.put(art,temp);
                }
            }
        }
        albums.add(album);

        return album;
    }

    public Song createSong(String title, String albumName, int length) throws Exception{
        Album album=new Album(albumName);
        if(!albums.contains(album)) throw new Exception("Album does not exist");
        List<Song>temp=new ArrayList<>();
        Song curr=new Song(title,length);

        for(Album alb : albums){
            if(alb.getTitle().equals(albumName)){
                temp=albumSongMap.get(alb);
                temp.add(curr);
                albumSongMap.put(album,temp);
            }
        }
        albums.add(album);
        songs.add(curr);
        return curr;
    }
//    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
//        if(playlists.contains(title)) throw new Exception("playlist already exist");
//
//        Playlist plt=new Playlist(title);
//    }

    //manual change
    public Playlist createPlaylistOnLength(String user, String title, int length) throws Exception {
            User user1=new User(user);
        if(!users.contains(user)) throw new Exception("User does not exist");
//        public HashMap<Playlist, List<Song>> playlistSongMap;
//        public HashMap<Playlist, List<User>> playlistListenerMap;
        Playlist plt=new Playlist(title);
        List<Song>temp=new ArrayList<>();
        for(Song song: songs){
            if(song.getLength()==length){
                temp.add(song);
            }
        }

        playlistSongMap.put(plt,temp);

        creatorPlaylistMap.put(user1,plt);

        List<User>list=new ArrayList<>();
        list=playlistListenerMap.get(plt);
        list.add(user1);
        playlistListenerMap.put(plt,list);


        return plt;
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {

       //if(!playlists.contains(title)) throw  new Exception("playlist already exist");
        User user=new User(mobile);
        if(!users.contains(user)) throw  new Exception("User does not exist");

        List<Song>temp=new ArrayList<>();
        Playlist plt=new Playlist(title);
        for(String str:songTitles){
            Song song= new Song(str);
            if(songs.contains(song)){
                temp.add(song);
            }
        }
        playlistSongMap.put(plt,temp);
        creatorPlaylistMap.put(user,plt);

        return plt;
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
        Playlist playlist=new Playlist(playlistTitle);
        if(!playlists.contains(playlist)) throw new Exception("Playlist does not exist");
        User user=new User(mobile);
        if(!users.contains(mobile)) throw new Exception("User does not exist");

        //public HashMap<Playlist, List<User>> playlistListenerMap;
        List<User>temp=new ArrayList<>();
        temp=playlistListenerMap.get(playlist);

        //public HashMap<User, Playlist> creatorPlaylistMap;
        if(creatorPlaylistMap.get(user)!=temp && !playlistListenerMap.get(playlist).contains(mobile)){
            temp.add(user);
        }

        return playlist;

    }

    public Song likeSong(String mobile, String songTitle) throws Exception {
        User user=new User(mobile);
        Song song=new Song(songTitle);

        if(!users.contains(user)) throw new Exception("User does not exist");
        if(!songs.contains(song)) throw new Exception("Song does not exist");
        //public HashMap<Song, List<User>> songLikeMap;
        List<User>temp=new ArrayList<>();
        int likes=1;
        temp=songLikeMap.getOrDefault(song,new ArrayList<User>());

//        public HashMap<Artist, List<Album>> artistAlbumMap;
//        public HashMap<Album, List<Song>> albumSongMap
        if(!temp.contains(user)){
            temp.add(user);
            songLikeMap.put(song,temp);
            song.setLikes(song.getLikes()+likes);
            Artist art=new Artist();

            //serching artist corresponding to given song;
            for(Artist artist:artists){
                List<Album>temp2=artistAlbumMap.get(artist);
                for(Album album :temp2){
                    List<Song>temp3=albumSongMap.get(album);
                    if(temp3.contains(song)){
                        artist.setLikes(artist.getLikes()+1);
                    }
                }
            }
        }


        return song;

    }

    public String mostPopularArtist() {
        int max_likes=0;
        Artist popularArtist=null;
            for(Artist artist :artists){
                if(artist.getLikes()>max_likes){
                    max_likes= artist.getLikes();
                    popularArtist=artist;
                }
            }
            return  popularArtist.getName();
    }

    public String mostPopularSong() {
        int max_likes=0;
        Song mostpopularSong=null;
        for(Song song :songs){
            if(song.getLikes()>max_likes){
                max_likes=song.getLikes();
                mostpopularSong=song;
            }
        }

        return mostpopularSong.getTitle();
    }
}




