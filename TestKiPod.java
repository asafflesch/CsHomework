import java.util.Iterator;
import java.util.Random;

/**
 * A class for checking the AVL implementation
 */
public class TestKiPod {
	static int MAX_SONGS = 1100000;
	
	static int[] lengths = new int[MAX_SONGS];
	static int lIndex = 0;
	static Random rand = new Random();
	
	
	static public class KiPodTestData
	{
		public KiPod k;
		public Song[] songs;
		public Playlist[] playlists;
	}
	static public class KiPodTestFailed extends RuntimeException
	{
		public KiPodTestData k;
		public KiPodTestFailed(KiPodTestData k, String msg)
		{
			super(msg);
			this.k = k;
		}
	}
	
	
	private static void swap(int[] arr,int i,int j){
		final int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	private static void shuffle(int[] arr){
		int top = arr.length - 1;
		Random rand = new Random();
		while (top >= 0){
			swap(arr,top,rand.nextInt(top+1));
			top--;
		}
	}

	
	static public void initLengths()
	{
		lIndex = 0;
		for (int i=0; i<MAX_SONGS; ++i)
		{
			lengths[i] = i;
		}
		shuffle(lengths);
	}
	static public Song generateRandomSong()
	{
		// Find unique random length
		int i = lengths[lIndex];
		++lIndex;
		// Use length to make song name unique
		Song s = new Song("L:"+i + " R:" + rand.nextInt(), i);
		return s;
	}
	
	static public boolean isPlaylistInSong(Song s, Playlist pl)
	{
		Iterator it = s.getPlaylists().iterator();
		while (it.hasNext())
		{
			if (it.next()==pl)
			{
				return true;
			}
		}
		return false;
	}
	
	
	
	
	// Will insert at max "songs" songs, and exactly "playlists" playlists
	// Performs simple tests while inserting. Does not ensure integrity.
	static public KiPodTestData generateRandomKiPod(int songs, int playlists)
	{
		initLengths();
		System.out.println("Generating random Kipod. Songs:" + songs + " Playlists:" + playlists);
		
		int maxSPP = songs / playlists;
		KiPod k = new KiPod();
		int s = 0; int p = 0;
		Song[] sar = new Song[songs];
		Playlist[] plar = new Playlist[playlists];
		// Generate random playlists with songs
		for (int i=0; i<playlists; ++i)
		{
			Playlist pl = new Playlist("Pl_"+i); // Not so random, aye? Fix later. Remember uniqueness
			plar[p] = pl;
			k.addPlaylist(pl);
			
			if (k.findPlaylist(pl.getName()) != pl)
			{
				System.err.println("Playlist was inserted to kipod but not found afterwards!");
			}
			
			int songNum = rand.nextInt((maxSPP / 2)) + (maxSPP / 2);
			for (int j=0; j<songNum; ++j)
			{
				sar[s] = generateRandomSong();
				k.addSong(sar[s]);
				if (k.findSong(sar[s].getTitle()) != sar[s])
				{
					System.err.println("Song was inserted to kipod but not found afterwards!");
					throw new RuntimeException("Test failed!");
				}
				k.addSongToPlaylist(sar[s],pl);
				if (pl.find(sar[s].getLength()) != sar[s])
				{
					System.err.println("Song with length " + sar[s].getLength() +" was inserted to playlist but not found there afterwards! ");
					throw new RuntimeException("Test failed!");
				}
				if (!isPlaylistInSong(sar[s],pl))
				{
					System.err.println("Song was inserted to playlist but not found there afterwards!");
					throw new RuntimeException("Test failed!");
				}
				
				
				++s;
			}
			++p;
		}
		// Now add random songs to random playlists
		for (int i=0; i<20; ++i)
		{
			for (int j=0; j< songs; ++j)
			{
				if (sar[j] == null) {continue ;}
				int plnum = rand.nextInt(playlists);
				Playlist pl = k.findPlaylist("Pl_"+plnum);
				if (pl.find(sar[j].getLength())!=null) {continue ;}
				k.addSongToPlaylist(sar[j], pl);
			}
		}
		KiPodTestData kp = new KiPodTestData();
		kp.k = k;
		kp.songs = sar;
		kp.playlists = plar;
		System.out.println("Done generating random kipod");
		return kp;
	}
	
	public static LinkedList cloneList(LinkedList lst)
	{
		Iterator it = lst.iterator();
		LinkedList lst2 = new LinkedList();
		while (it.hasNext())
		{
			lst2.addLast(it.next());
		}
		return lst2;
	}
	
	// Remove random songs, check integrity.
	public static void testSongRemovals(int songsnumber, int playlistsnumber)
	{
		System.out.println("Starting testSongRemovals with songs:" + songsnumber + " playlists:"+ playlistsnumber);
		KiPodTestData td = generateRandomKiPod(songsnumber, playlistsnumber);
		if (td == null)
		{
			return;
		}
		
		// Remove random songs, check that all is well
		for (int i=0; i< (td.songs.length / 2); ++i)
		{
			int s = rand.nextInt(td.songs.length);
			if (td.songs[s] == null) { continue;}
			Song song = td.songs[s];
			td.songs[s] = null;
			// Make sure it's there...
			if (td.k.findSong(song.getTitle())!=song)
			{
				System.err.println("Song was not found in kipod!");
				throw new RuntimeException("Test failed!");
			}
			// Make a copy of the songs' playlists 
			LinkedList lst = cloneList(song.getPlaylists());
			// And remove the song 
			td.k.removeSong(song);
			// Check that it was removed
			if (td.k.findSong(song.getTitle())!=null)
			{
				System.err.println("Song was deleted from kipod but it's still there!");
				throw new RuntimeException("Test failed!");
			}
			// Check that it was removed from all of its playlists
			Iterator it = lst.iterator();
			while (it.hasNext())
			{
				Playlist curPl = (Playlist)it.next();
				if (curPl.find(song.getLength()) != null)
				{
					System.err.println("Song was deleted from kipod but it remains in one of the playlists!");
					throw new RuntimeException("Test failed!");
				}
			}
			
			
		}
		// Check integrity of kipod with new data
		AssertIntegrity(td);
		System.out.println("Done testSongRemovals!");
	}
	
	// Remove random playlists, check integrity
	public static void testPlaylistRemovals(int songsnumber, int playlistsnumber)
	{
		System.out.println("Starting testPlaylistRemovals with songs:" + songsnumber + " playlists:"+ playlistsnumber);
		KiPodTestData td = generateRandomKiPod(songsnumber, playlistsnumber);
		if (td == null)
		{
			return;
		}
		
		// Remove random playlists, check that all is well
		for (int i=0; i< (td.playlists.length / 2); ++i)
		{
			int s = rand.nextInt(td.playlists.length);
			if (td.playlists[s] == null) { continue;}
			Playlist pl = td.playlists[s];
			td.playlists[s] = null;
			// Make sure it's there...
			if (td.k.findPlaylist(pl.getName())!=pl)
			{
				System.err.println("Playlist was not found in kipod!");
				throw new RuntimeException("Test failed!");
			}
			
			// And remove the playlist 
			td.k.removePlaylist(pl);
			// Check that it was removed
			if (td.k.findPlaylist(pl.getName())!=null)
			{
				System.err.println("Playlist was deleted from kipod but it's still there!");
				throw new RuntimeException("Test failed!");
			}
			// Check that it was removed from all of its songs
			Iterator it = pl.getKthtillHthSongs(1, pl.size()).iterator();
			while (it.hasNext())
			{
				Song curSong = (Song)it.next();
				if (isPlaylistInSong(curSong, pl))
				{
					System.err.println("Playist was deleted from kipod but it remains in one of the songs!");
					throw new RuntimeException("Test failed!");
				}
			}
			
			
		}
		// Check integrity of kipod with new data
		AssertIntegrity(td);
		System.out.println("Done testPlaylistRemovals!");
	}
	
	// Checks for complete integrity of everything in Kipod. Songs in correct playlists, playlists in correct songs, etc..
	public static void AssertIntegrity(KiPodTestData td)
	{
		System.out.println("Checking for integrity of kipod after test...");
		// Check songs
		for (int i =0; i< td.songs.length; ++i)
		{
			// Make sure each song is in KiPod, and in it's playlist, which is also in kipod
			if (td.songs[i] == null)
			{
				continue;
			}
			
			if (td.k.findSong(td.songs[i].getTitle()) != td.songs[i])
			{
				System.err.println("Integrity failed: Song "+ td.songs[i].getTitle() + " should be in kipod but isn't!");
				throw new KiPodTestFailed(td,"Test failed!");
			}
			Iterator it = td.songs[i].getPlaylists().iterator();
			while( it.hasNext())
			{
				Playlist pl = (Playlist)it.next();
				if (pl.find(td.songs[i].getLength())!=td.songs[i])
				{
					System.err.println("Integrity failed: Playlist " + pl.getName() +" in song " + td.songs[i].getTitle()+ ", but song not in playlist!");
					throw new KiPodTestFailed(td, "Test failed!");
				}
				if (td.k.findPlaylist(pl.getName())!=pl)
				{
					System.err.println("Integrity failed: Playlist " + pl.getName() +" in song " + td.songs[i].getTitle()+ ", but isn't in kipod!");
					throw new KiPodTestFailed(td, "Test failed!");
				}
			}
		}
		// Check playlists
		for (int i=0; i<td.playlists.length; ++i)
		{
			// Also for playlists
			if (td.playlists[i]==null) {
				continue;
			}
			
			if (td.k.findPlaylist(td.playlists[i].getName())!=td.playlists[i])
			{
				System.err.println("Integrity failed: Playlist "+td.playlists[i].getName() +" should be in kipod but isn't!");
				throw new KiPodTestFailed(td,"Test failed!");
			}
			Iterator it = td.playlists[i].getKthtillHthSongs(1, td.playlists[i].size()).iterator();
			while(it.hasNext())
			{
				Song s = (Song)it.next();
				// Make sure song really is in the playlist, is in kipod, and kipod contains the playlist
				if (td.playlists[i].find(s.getLength())!= s)
				{
					System.err.println("Integrity failed: Song " + s.getTitle()+ " in linked-list of playlist "+td.playlists[i].getName() +", but not in the playlist's tree!");
					throw new KiPodTestFailed(td, "Test failed!");
				}
				
				if (td.k.findSong(s.getTitle())!=s)
				{
					System.err.println("Integrity failed: Song " + s.getTitle()+ " in playlist "+td.playlists[i].getName() +", but not in kipod!");
					throw new KiPodTestFailed(td, "Test failed!");
				}
				if (!isPlaylistInSong(s, td.playlists[i]))
				{
					System.err.println("Integrity failed: Song " + s.getTitle() + "in playlist"+td.playlists[i].getName() +", but playlist not in song!");
					throw new KiPodTestFailed(td, "Test failed!");
				}
			}
			
		}
		System.out.println("Kipod integrity intact!");
	}
	
	
	public static void main(String[] args) {
		
		try
		{
			System.out.println("-----START PHASE 1: Performing sanity tests----");
			// Simple sanity checks in rising size:
			AssertIntegrity(generateRandomKiPod(100,10));
			AssertIntegrity(generateRandomKiPod(1000,10));		
			AssertIntegrity(generateRandomKiPod(10000,100));
			System.out.println("-----DONE PHASE 1------------------------------");
			
			System.out.println("\n\n-----PHASE 2: Performing song deletion tests----------");
			testSongRemovals(100,10);
			testSongRemovals(1000,10);
			testSongRemovals(10000,100);
			System.out.println("-----DONE PHASE 2------------------------------");
			
			System.out.println("\n\n-----PHASE 3: Performing playlist deletion tests----------");
			testPlaylistRemovals(100,10);
			testPlaylistRemovals(1000,10);
			testPlaylistRemovals(10000,100);
			System.out.println("-----DONE PHASE 3------------------------------");
			
			System.out.println("\n\n-----PHASE 4: Performing playlist heavy-load tests----------");
			System.out.println("-----WARNING: This test might take some time, but shouldn't take longer than 4-5 minutes----------");
			AssertIntegrity(generateRandomKiPod(200000,1000));
			testSongRemovals(200000,1000);
			testPlaylistRemovals(200000,1000);
			System.out.println("-----DONE PHASE 4------------------------------");
		}
		catch(KiPodTestFailed kf)
		{
			// This code reports the entire test data to a folder (playlists, songs, and the such) as .dot files
			// To uncomment this, create the relevant methods in the relevant classes.
			/*String ROOT_FOLDER = "z:\\DSTestData\\";
			System.err.println("ERROR: An assert integrity test failed, writing data to: " + ROOT_FOLDER);
			AVLTree pl = kf.k.k.getPlaylistTree();
			pl.toDotFile(ROOT_FOLDER+"MAINPlaylistTree.dot");
			kf.k.k.getSongTree().toDotFile(ROOT_FOLDER+"MAINSongTree.dot");
			Iterator it = pl.getKthTillHth(1, pl.size()).iterator();
			while(it.hasNext())
			{
				Playlist p = (Playlist)it.next();
				p.getTree().toDotFile(ROOT_FOLDER+"Playlist_"+p.getName());
			}*/
			
		}
	}
	
}
