

public class Playlist {
	private String name;
	// This data structure has the required performance criteria
	// and works here because we can be assured of unique keys
	private AVLTree innerList;
	
	public Playlist(String name){
		this.name = name;
		this.innerList = new AVLTree(new IntegerComparator());
	}
	
	public String getName(){
		return this.name;
	}
	
	public void add(Song song) {
		int key = song.getLength();
		innerList.add(key, song);
	}

	public void remove(Song song) {
		int key = song.getLength();
		innerList.remove(key);
	}
	
	public Song find(int length) {
		int key = length;
		Song foundSong = (Song) innerList.find(key);
		return foundSong;
	}
	
	public Song findKthSong(int k) {
		Song kThSong = (Song) innerList.findKthElement(k);
		return kThSong;
	}
	
	public LinkedList getKthtillHthSongs(int k,int h) {
		return innerList.getKthTillHth(k,h);
	}
	
	public int size(){
		return innerList.size();
	}
}
