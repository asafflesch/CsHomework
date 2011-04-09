
/**
 * The Class representing the KiPod media player.
 */
public class KiPod{	

	/**
	 * Instantiates a new kiPod.
	 */
	public KiPod(){
		
	}

	/**
	 * Adds the song to the player.
	 *
	 * @param song the song
	 */
	public void addSong(Song song){
		
	}
	
	/**
	 * Adds the playlist to the player.
	 *
	 * @param playlist the playlist
	 */
	public void addPlaylist(Playlist playlist){
		
	}
	
	/**
	 * Adds the song to playlist. assumes that {@link #addSong(Song)} was called for the song.
	 *
	 * @param song the song
	 * @param playlist the playlist
	 */
	public void addSongToPlaylist(Song song, Playlist playlist){
		
	}

	/**
	 * Removes the playlist.
	 *
	 * @param playlist the playlist
	 */
	public void removePlaylist(Playlist playlist){
		
	}

	/**
	 * Removes the song from playlist.
	 *
	 * @param song the song
	 * @param playlist the playlist
	 */
	public void removeSongFromPlaylist(Song song, Playlist playlist){
		
	}

	/**
	 * Removes the song.
	 *
	 * @param song the song
	 */
	public void removeSong(Song song){
		
	}
	
	/**
	 * Find kth song in playlist.
	 *
	 * @param playlist the playlist
	 * @param k the k
	 * @return the song
	 */
	public Song findKthSongInPlaylist(Playlist playlist,int k){
		return null;
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
		return null;
	}
	
	/**
	 * Find song.
	 *
	 * @param name the name
	 * @return the song
	 */
	public Song findSong(String name){
		return null;
	}
	
	/**
	 * Find a playlist by a name.
	 *
	 * @param name the name
	 * @return the playlist
	 */
	public Playlist findPlaylist(String name){
		return null;
	}
	
	/**
	 * Num of songs.
	 *
	 * @return the int
	 */
	public int numOfSongs(){
		return -1;
	}
	
	/**
	 * Num of playlists.
	 *
	 * @return the int
	 */
	public int numOfPlaylists(){
		return -1;
	}
}
