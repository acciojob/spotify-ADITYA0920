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

        User user=new User(name,mobile);
        users.add(user);

        return user;
    }

    public Artist createArtist(String name) {

        Artist artist=new Artist(name);
        artists.add(artist);

        return artist;
    }

    public Album createAlbum(String title, String artistName) {
//        List<Album>temp=new ArrayList<>();
//        Artist artist=new Artist(artistName);
//        Album album=new Album(title);
//        if(!artists.contains(artist)){
//            Artist curr= new Artist(artistName);
//            temp.add(album);
//            artists.add(artist);
//            artistAlbumMap.put(curr,temp);
//        }
//        else{
//            for(Artist art:artists){
//                if(art.getName().equals(artistName)){
//                    temp=artistAlbumMap.get(art);
//                    temp.add(album);
//                    artistAlbumMap.put(art,temp);
//                }
//            }
//        }
//        albums.add(album);
        Artist artist1 = null;
        for (Artist artist : artists) {
            if (artist.getName() == artistName) {
                artist1 = artist;
                break;
            }

        }
        if (artist1 == null) {
            artist1 = createArtist(artistName);
            Album album = new Album();
            album.setTitle(title);
            album.setReleaseDate(new Date());
            albums.add(album);

            List<Album> list = new ArrayList<>();
            list.add(album);
            artistAlbumMap.put(artist1, list);
            return album;
        } else {
            Album album = new Album();
            album.setTitle(title);
            album.setReleaseDate(new Date());
            albums.add(album);

            List<Album> list = artistAlbumMap.get(artist1);
            if (list == null) {
                list = new ArrayList<>();

            }
            list.add(album);
            artistAlbumMap.put(artist1, list);

            return album;
        }
    }

    public Song createSong(String title, String albumName, int length) throws Exception{
//        Album album=new Album(albumName);
//        if(!albums.contains(album)) throw new Exception("Album does not exist");
//        List<Song>temp=new ArrayList<>();
//        Song curr=new Song(title,length);
//
//        for(Album alb : albums){
//            if(alb.getTitle().equals(albumName)){
//                temp=albumSongMap.get(alb);
//                temp.add(curr);
//            }
//        }
//        albumSongMap.put(album,temp);
//        albums.add(album);
//        songs.add(curr);
//        return curr;
        Album album=null;
        for (Album album1:albums){
            if(album1.getTitle()==albumName){
                album=album1;
                break;
            }
        }
        if(album==null){
            throw new Exception("Album does not exist");
        }else{
            Song song=new Song();
            song.setTitle(title);
            song.setLength(length);
            song.setLikes(0);
            songs.add(song);

            if(albumSongMap.containsKey(album)){
                List<Song> list=albumSongMap.get(album);
                list.add(song);
                albumSongMap.put(album,list);
            }else {
                List<Song>songList=new ArrayList<>();
                songList.add(song);
                albumSongMap.put(album,songList);
            }
            return song;
        }
    }
//    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
//        if(playlists.contains(title)) throw new Exception("playlist already exist");
//
//        Playlist plt=new Playlist(title);
//    }

    //manual change
    public Playlist createPlaylistOnLength(String mobile, String title, int length) throws Exception {
//            User user1=new User(mobile);
//        if(!users.contains(mobile)) throw new Exception("User does not exist");
////        public HashMap<Playlist, List<Song>> playlistSongMap;
////        public HashMap<Playlist, List<User>> playlistListenerMap;
//        //public HashMap<User, List<Playlist>> userPlaylistMap;
//        Playlist plt=new Playlist(title);
//        List<Song>temp=new ArrayList<>();
//        for(Song song: songs){
//            if(song.getLength()==length){
//                temp.add(song);
//            }
//        }
//
//        //USER-PLAYLIST
//        List<Playlist>list=new ArrayList<>();
//        list=userPlaylistMap.get(user1);
//        list.add(plt);
//        userPlaylistMap.put(user1,list);
//
//        //PLAYLIST LIST
//        playlists.add(plt);
//
//        playlistSongMap.put(plt,temp);
//
//        creatorPlaylistMap.put(user1,plt);
//
//        List<User>list1=new ArrayList<>();
//        list1=playlistListenerMap.get(plt);
//        list1.add(user1);
//        playlistListenerMap.put(plt,list1);
//
//
//        return plt;
        User user=new User();
        for(User user1:users){
            if(user1.getMobile()==mobile){
                user=user1;
                break;
            }
        }
        if(user==null){
            throw new Exception("User does not exits");
        }
        else {
            Playlist playlist=new Playlist();
            playlist.setTitle(title);
            playlists.add(playlist);

            List<Song> list=new ArrayList<>();
            for(Song song:songs){
                if(song.getLength()==length){
                    list.add(song);
                }
            }
            playlistSongMap.put(playlist,list);

            List<User> list1=new ArrayList<>();
            list1.add(user);
            playlistListenerMap.put(playlist,list1);
            creatorPlaylistMap.put(user,playlist);

            if(userPlaylistMap.containsKey(user)){
                List<Playlist>userPlayList=userPlaylistMap.get(user);
                userPlayList.add(playlist);
                userPlaylistMap.put(user,userPlayList);
            }else{
                List<Playlist> playlists1=new ArrayList<>();
                playlists1.add(playlist);
                userPlaylistMap.put(user,playlists1);
            }
            return playlist;
        }
    }

    public Playlist createPlaylistOnName(String mobile, String title, List<String> songTitles) throws Exception {

       //if(!playlists.contains(title)) throw  new Exception("playlist already exist");
//        User user=new User(mobile);
//        if(!users.contains(user)) throw  new Exception("User does not exist");
//
//        List<Song>temp=new ArrayList<>();
//        Playlist plt=new Playlist(title);
//        for(String str:songTitles){
//            Song song= new Song(str);
//            if(songs.contains(song)){
//                temp.add(song);
//            }
//        }
//        //USER-PLAYLIST
//        List<Playlist>list=new ArrayList<>();
//        list=userPlaylistMap.get(user);
//        list.add(plt);
//        userPlaylistMap.put(user,list);
//
//        //PLAYLIST LIST
//        playlists.add(plt);
//
//
//        playlistSongMap.put(plt,temp);
//        creatorPlaylistMap.put(user,plt);
//
//        //playlistListenerMap
//        List<User>list1=new ArrayList<>();
//        list1=playlistListenerMap.get(plt);
//        list1.add(user);
//        playlistListenerMap.put(plt,list1);
//
//        return plt;
        User user=null;
        for (User user1:users){
            if(user1.getMobile()==mobile){
                user=user1;
                break;
            }
        }
        if(user==null){
            throw new Exception("User does not exist");
        }
        else{
            Playlist playlist=new Playlist();
            playlist.setTitle(title);
            playlists.add(playlist);

            List<Song> list=new ArrayList<>();
            for (Song song:songs){
                if(songTitles.contains(song.getTitle())){
                    list.add(song);
                }
            }
            playlistSongMap.put(playlist,list);

            List<User> list1=new ArrayList<>();
            list1.add(user);
            playlistListenerMap.put(playlist,list1);
            creatorPlaylistMap.put(user,playlist);

            if(userPlaylistMap.containsKey(user)){
                List<Playlist> userPlayList=userPlaylistMap.get(user);
                userPlayList.add(playlist);
                userPlaylistMap.put(user,userPlayList);
            }else{
                List<Playlist> playlists1=new ArrayList<>();
                playlists1.add(playlist);
                userPlaylistMap.put(user,playlists1);
            }
            return playlist;
        }
    }

    public Playlist findPlaylist(String mobile, String playlistTitle) throws Exception {
//        Playlist playlist=new Playlist(playlistTitle);
//        if(!playlists.contains(playlist)) throw new Exception("Playlist does not exist");
//        User user=new User(mobile);
//        if(!users.contains(mobile)) throw new Exception("User does not exist");
//
//        //public HashMap<Playlist, List<User>> playlistListenerMap;
//        List<User>temp=new ArrayList<>();
//        temp=playlistListenerMap.get(playlist);
//
//        //public HashMap<User, Playlist> creatorPlaylistMap;
//        if(creatorPlaylistMap.get(user)!=temp && !playlistListenerMap.get(playlist).contains(mobile)){
//            temp.add(user);
//        }
//
//        return playlist;
        User user=null;
        for (User user1:users){
            if (user1.getMobile()==mobile){
                user=user1;
                break;
            }
        }
        if (user==null){
            throw new Exception("User does not exist");
        }
        Playlist playlist=null;
        for (Playlist playlist1:playlists){
            if (playlist1.getTitle()==playlistTitle){
                playlist=playlist1;
                break;
            }
        }
        if(playlist==null){
            throw new Exception("Playlist does not exist");
        }
        if(creatorPlaylistMap.containsKey(user)){
            return playlist;
        }
        List<User> listener=playlistListenerMap.get(playlist);
        for(User user1:listener) {
            if (user1 == user) {
                return playlist;
            }
        }
        listener.add(user);
        playlistListenerMap.put(playlist,listener);

        List<Playlist> playlists1=userPlaylistMap.get(user);
        if(playlists1==null){
            playlists1=new ArrayList<>();
        }
        playlists1.add(playlist);
        userPlaylistMap.put(user,playlists1);
        return playlist;

    }

    public Song likeSong(String mobile, String songTitle) throws Exception {
//        User user=new User(mobile);
//        Song song=new Song(songTitle);
//
//        if(!users.contains(user)) throw new Exception("User does not exist");
//        if(!songs.contains(song)) throw new Exception("Song does not exist");
//        //public HashMap<Song, List<User>> songLikeMap;
//        List<User>temp=new ArrayList<>();
//        int likes=1;
//        temp=songLikeMap.getOrDefault(song,new ArrayList<User>());
//
////        public HashMap<Artist, List<Album>> artistAlbumMap;
////        public HashMap<Album, List<Song>> albumSongMap
//        if(!temp.contains(user)){
//            temp.add(user);
//            songLikeMap.put(song,temp);
//            song.setLikes(song.getLikes()+likes);
//
//
//            Artist art=new Artist();
//
//            //serching artist corresponding to given song;
//            for(Artist artist:artists){
//                List<Album>temp2=artistAlbumMap.get(artist);
//                for(Album album :temp2){
//                    List<Song>temp3=albumSongMap.get(album);
//                    if(temp3.contains(song)){
//                        artist.setLikes(artist.getLikes()+1);
//                    }
//                }
//            }
//        }
//
//
//        return song;
        User user=null;
        for(User user1:users){
            if(user1.getMobile()==mobile){
                user=user1;
                break;
            }
        }
        if(user==null){
            throw new Exception("User does not exist");
        }
        Song song=null;
        for (Song song1:songs){
            if (song1.getTitle()==songTitle){
                song=song1;
                break;
            }
        }
        if (song==null){
            throw new Exception("Song does not exist");
        }
        if(songLikeMap.containsKey(song)){
            List<User> list=songLikeMap.get(song);
            if(list.contains(user)){
                return song;
            }else{
                int likes=song.getLikes()+1;
                song.setLikes(likes);
                list.add(user);
                songLikeMap.put(song,list);

                Album album=null;
                for(Album album1:albumSongMap.keySet()){
                    List<Song> songList=albumSongMap.get(album1);
                    if(songList.contains(song)){
                        album=album1;
                        break;
                    }
                }
                Artist artist=null;
                for (Artist artist1:artistAlbumMap.keySet()){
                    List<Album> albumList=artistAlbumMap.get(artist1);
                    if(albumList.contains(album)){
                        artist=artist1;
                        break;
                    }
                }
                int likes1= artist.getLikes()+1;
                artist.setLikes(likes1);
                artists.add(artist);
                return song;

            }
        }else{
            int likes = song.getLikes() + 1;
            song.setLikes(likes);
            List<User> list=new ArrayList<>();
            list.add(user);
            songLikeMap.put(song,list);

            Album album=null;
            for (Album album1:albumSongMap.keySet()){
                List<Song> songList=albumSongMap.get(album1);
                if(songList.contains(song)){
                    album=album1;
                    break;
                }
            }
            Artist artist=null;
            for(Artist artist1:artistAlbumMap.keySet()){
                List<Album> albumList=artistAlbumMap.get(artist1);
                if (albumList.contains(album)){
                    artist=artist1;
                    break;
                }
            }
            int likes1=artist.getLikes()+1;
            artist.setLikes(likes1);
            artists.add(artist);
            return song;
        }

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
            if(popularArtist==null) return  null;
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
        if(mostpopularSong==null) return null;
        return mostpopularSong.getTitle();
    }
}




