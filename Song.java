
/**
 * A class representing a Song in the KiPod player.
 */
public class Song {
	
	/** The title of the song. */
	private String title;
	
	/** The length of the song. */
	private int length;
	
	/** A list of playlist the contains the song. */
	private LinkedList playlists;
	
	/**
	 * Instantiates a new song.
	 *
	 * @param title the title
	 * @param length the length
	 */
	public Song(String title, int length) {
		this.title = title;
		this.length = length;
		this.playlists = new LinkedList();
	}
	
	/**
	 * Gets the title of the song.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the length of the song.
	 *
	 * @return the length
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * Gets the playlists.
	 *
	 * @return the playlists
	 */
	public LinkedList getPlaylists(){
		return this.playlists;
	}
	
	/**
	 * Adds the playlist to the list of attached playlists. this method doesn't add the song to the playlist
	 *
	 * @param playlist the playlist
	 */
	public void add(Playlist playlist){
		this.playlists.addLast(playlist);
	}
	
	/**
	 * Removes the the playlist from the list of playlists. this method dosen't remove the song from the playlist
	 *
	 * @param playlist the playlist
	 */
	public void remove(Playlist playlist){
		this.playlists.remove(playlist);
	}
	
	@Override
	public String toString() {
		return this.title + " (" + this.length + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Song))
			return false;
		Song other = (Song) obj;
		if (length != other.length)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
}
