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
		/* Find all the songs it features in,
                 remove from them*/

                 LinkedList songlist = playlist.getKthtillHthSongs
                         (1, playlist.size());

                 Iterator it = songlist.iterator();
                 while (it.hasNext()){
                     removeSongFromPlaylist((Song)(it.next()), playlist);
                 }

                /* Remove from data structure*/
                 playTree.remove(playlist.getName());		
	}

	/**
	 * Removes the song from playlist.
	 *
	 * @param song the song
	 * @param playlist the playlist
	 */
	public void removeSongFromPlaylist(Song song, Playlist playlist){
		song.remove(playlist);
                playlist.remove(song);
	}

	/**
	 * Removes the song.
	 *
	 * @param song the song
	 */
	public void removeSong(Song song){
		/* Find all the playlists it features in,
                 remove from them*/
                 Iterator it = song.getPlaylists().iterator();
                 while (it.hasNext()){
                     removeSongFromPlaylist(song, (Playlist)(it.next()));
                 }

                /* Remove from data structure*/
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
