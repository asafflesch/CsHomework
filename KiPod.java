import java.util.Iterator;

/**
 * The Class representing the KiPod media player.
 */
public class KiPod{

        private AVLTree playTree;
        private AVLTree songTree;

	/**
	 * Instantiates a new kiPod.
	 */
	public KiPod(){
		playTree = new AVLTree(new StringComparator());
		songTree = new AVLTree(new StringComparator());
	}

	/**
	 * Adds the song to the player.
	 *
	 * @param song the song
	 */
	public void addSong(Song song){
		songTree.add(song.getTitle(), song);
	}
	
	/**
	 * Adds the playlist to the player.
	 *
	 * @param playlist the playlist
	 */
	public void addPlaylist(Playlist playlist){
		playTree.add(playlist.getName(), playlist);
	}
	
	/**
	 * Adds the song to playlist. assumes that {@link #addSong(Song)} was called for the song.
	 *
	 * @param song the song
	 * @param playlist the playlist
	 */
	public void addSongToPlaylist(Song song, Playlist playlist){
		song.add(playlist);
                playlist.add(song);
	}

	/**
	 * Removes the playlist.
	 *
	 * @param playlist the playlist
	 */
	public void removePlaylist(Playlist playlist){
		// Get all the songs this playlist features, and remove the playlist from each one of them
                 LinkedList songlist = playlist.getKthtillHthSongs(1, playlist.size());
                 Iterator it = songlist.iterator();
                 while (it.hasNext()){
			 Song currSong = (Song)it.next();
	                 removeSongFromPlaylist(currSong, playlist);
                 }

                // Now we can remove the playlist from our internal playlist list
                 playTree.remove(playlist.getName());		
	}

	/**
	 * Removes the song from playlist.
	 *
	 * @param song the song
	 * @param playlist the playlist
	 */
	public void removeSongFromPlaylist(Song song, Playlist playlist){
		// Remove each from the other. Why did Kiv Jobs order this architecture?
		song.remove(playlist);
                playlist.remove(song);
	}

	/**
	 * Removes the song.
	 *
	 * @param song the song
	 */
	public void removeSong(Song song){
		 // Find all the playlists song features in, and  remove it from them
                 Iterator it = song.getPlaylists().iterator();
                 while (it.hasNext()){
                     removeSongFromPlaylist(song, (Playlist)(it.next()));
                 }

                /// Now we can remove the song from our internal song list
                 songTree.remove(song.getTitle());
	}
	
	/**
	 * Find kth song in playlist.
	 *
	 * @param playlist the playlist
	 * @param k the k
	 * @return the song
	 */
	public Song findKthSongInPlaylist(Playlist playlist,int k){
		return playlist.findKthSong(k);
	}

	/**
	 * Gets the kth till hth song in playlist.
	 *
	 * @param playlist the playlist
	 * @param k the k
	 * @param h the h
	 * @return the kth till hth song in playlist
	 */
	public LinkedList getKthTillHthSongInPlaylist(Playlist playlist,int k,int h){
		return playlist.getKthtillHthSongs(k, h);
	}
	
	/**
	 * Find song.
	 *
	 * @param name the name
	 * @return the song
	 */
	public Song findSong(String name){
		return (Song)(songTree.find(name));
	}
	
	/**
	 * Find a playlist by a name.
	 *
	 * @param name the name
	 * @return the playlist
	 */
	public Playlist findPlaylist(String name){
		return (Playlist)(playTree.find(name));
	}
	
	/**
	 * Num of songs.
	 *
	 * @return the int
	 */
	public int numOfSongs(){
		return songTree.size();
	}
	
	/**
	 * Num of playlists.
	 *
	 * @return the int
	 */
	public int numOfPlaylists(){
		return playTree.size();
	}
}
