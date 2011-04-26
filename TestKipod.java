import java.util.Iterator;

/** 
 * @warining remove
 * @author Shani
 *
 */
public class TestKIIPOD {
	public static void main(String[] args) {		
		SimpleCheck();
		testDeleteSongs(10);
		testDeleteSongs(100);
		testDeleteSongs(1000);
		testDeletePlayLists(10);
		testDeletePlayLists(100);
		testDeletePlayLists(1000);
		testDeleteSongFromPlayLists(10);
		testDeleteSongFromPlayLists(100);
		testDeleteSongFromPlayLists(1000);
		testPlayList(5,5);
		testPlayList(10,5);
		testPlayList(500,700);
	}

	private static void testPlayList(int x, int y) {
		System.out.println("running testPlayList("+x+","+y+")");
		KiPod ki = new KiPod();
		Playlist p1 = new Playlist("1");
		Playlist p2 = new Playlist("2");
		ki.addPlaylist(p1);
		ki.addPlaylist(p2);
		for (int i = 1; i <= x+y; i++) {
			Song song  = new Song(""+i, i);
			ki.addSong(song);
			ki.addSongToPlaylist(song, p1);
			ki.addSongToPlaylist(song, p2);
		}
		
		for (int i = 1; i <= x; i++) {
			Song song = ki.findSong(""+i);
			ki.removeSong(song);			
		}
		
		for (int i = x+1; i <= x+y; i++) {
			Song song = ki.findSong(""+i);
			ki.removeSongFromPlaylist(song, p1);			
		}
		if (p1.size()>0)
			System.err.println("p1 suppose to be empty");
		if (p2.size()!= y)
			System.err.println("p2 suppose to be with "+ y + "songs");
		if (ki.numOfSongs()!=y)
			System.err.println("suppose to be "+y+" songs");
		System.out.println("finish");
	}

	private static void SimpleCheck() {
		KiPod a = new KiPod();
		Playlist p = new Playlist("R");
		a.addPlaylist(p);
		for (int i = 1; i <= 8; i++) {
			a.addSong(new Song("bla"+i,i));
			Song song = a.findSong("bla"+i);
			a.addSongToPlaylist(song, p);
		}
		
		for (int k = 1; k <= p.size(); ++k) {
			Song song  = a.findKthSongInPlaylist(p, k);
			if (!song.getTitle().equals("bla"+k))
				System.err.println("find "+ k + " song fail. "+song.getTitle()+ "instead of bla"+k);
		}
		
		int counter = 1;
		for (Object b: a.getKthTillHthSongInPlaylist(p, 1, 6)) {
				Song song = (Song)b;
				if (counter!=song.getLength())
					System.err.println("getKthTillHthSongInPlaylist fail");
				++counter;
		}
		
		if (!a.getKthTillHthSongInPlaylist(p, 9, 10).isEmpty())
			System.err.println("dont have 9 items");		
	}

	private static void testDeleteSongs(int size) {
		System.out.println("Running testDeleteSongs("+size+")");
		try{
			KiPod ki = new KiPod();
			for (int i=0;i<size;i++){
				Song song =new Song(new String("Bla Bla" + i),i);
				Playlist playList = new Playlist("P"+i); 
				ki.addSong(song);
				ki.addPlaylist(playList);
				ki.addSongToPlaylist(song, playList);
			}
			
			//remove songs			
			for (int i=0;i<size;i=i+2){
				Song song  = ki.findSong(new String("Bla Bla" + i));
				if (song== null)
					System.err.println("Bla Bla"+i+" was not found");
				else
				{
					ki.removeSong(song);
					if (ki.findPlaylist("P"+i).size()>0)
						System.err.println("P"+i+" has songs in it");
				}
			}
			
			for (int i=0;i<size;i++){
				Song ans = ki.findSong(new String("Bla Bla" + i));
				if (i % 2 == 0 && ans != null){
					//deleted but still found
					System.err.println("Found object that was deleted. key was "+ ans.getTitle());
					return;
				}else if(i % 2 != 0 && ans == null){
					//not deleted and not found
					System.err.println("didn't find object that was inserted. key was " + i);
					return;
				}else if (i % 2 != 0 && ans !=null && !("Bla Bla"+i).equals(ans.getTitle())){
					//didn't find the right object
					System.err.println("didn't find the object that we looked for ! " + ans + " instead of " + ("Bla Bla"+i));
					return;
				}
			}

		}catch (Exception e) {
			System.err.println("Problem running testDelete("+size+")");
			e.printStackTrace();
			return;
		}
		System.out.println("Finished\n---");
	}
	private static void testDeletePlayLists(int size) {
		System.out.println("Running testDeletePlaylists("+size+")");
		try{
			KiPod ki = new KiPod();
			for (int i=0;i<size;i++){
				Song song =new Song(new String("Bla Bla" + i),i);
				Playlist playList = new Playlist("P"+i); 
				ki.addSong(song);
				ki.addPlaylist(playList);
				ki.addSongToPlaylist(song, playList);
			}
			
			//remove songs			
			for (int i=0;i<size;i=i+2){
				Playlist playList  = ki.findPlaylist(new String("P" + i));
				if (playList== null)
					System.err.println("P"+i+" was not found");
				else
				{
					ki.removePlaylist(playList);
					if (!ki.findSong("Bla Bla" + i).getPlaylists().isEmpty())
						System.err.println("Bla Bla"+i+" has playlists in it");
				}
			}
			
			for (int i=0;i<size;i++){
				Playlist ans = ki.findPlaylist(new String("P" + i));
				if (i % 2 == 0 && ans != null){
					//deleted but still found
					System.err.println("Found object that was deleted. key was "+ ans.getName());
					return;
				}else if(i % 2 != 0 && ans == null){
					//not deleted and not found
					System.err.println("didn't find object that was inserted. key was " + i);
					return;
				}else if (i % 2 != 0 && ans !=null && !("P"+i).equals(ans.getName())){
					//didn't find the right object
					System.err.println("didn't find the object that we looked for ! " + ans + " instead of " + ("P"+i));
					return;
				}
			}

		}catch (Exception e) {
			System.err.println("Problem running testDelete("+size+")");
			e.printStackTrace();
			return;
		}
		System.out.println("Finished\n---");
	}
	private static void testDeleteSongFromPlayLists(int size) {
		System.out.println("Running testDeleteSongFromPLaylists("+size+")");
		try{
			KiPod ki = new KiPod();
			for (int i=0;i<size;i++){
				Song song =new Song(new String("Bla Bla" + i),i);
				Playlist playList = new Playlist("P"+i); 
				ki.addSong(song);
				ki.addPlaylist(playList);
				ki.addSongToPlaylist(song, playList);
			}
			
			//remove songs	from playList
			for (int i=0;i<size;i=i+2){
				Song song  = ki.findSong(new String("Bla Bla" + i));
				Playlist playlist = (Playlist)song.getPlaylists().getFirst();
				if (song== null)
					System.err.println("Bla Bla"+i+" was not found");
				else
				{
					ki.removeSongFromPlaylist(song, playlist);
					if (playlist.size()>0)
						System.err.println("Bla Bla"+i+" has playlists in it");
				}
			}
			
			for (int i=0;i<size;i++){
				Playlist playList = ki.findPlaylist(new String("P" + i));
				Song ans = playList.find(i);
				if (i % 2 == 0 && ans != null){
					//deleted but still found
					System.err.println("Found object that was deleted. key was "+ ans.getTitle());
					return;
				}else if(i % 2 != 0 && ans == null){
					//not deleted and not found
					System.err.println("didn't find object that was inserted. key was " + i);
					return;
				}else if (i % 2 != 0 && ans !=null && !("Bla Bla"+i).equals(ans.getTitle())){
					//didn't find the right object
					System.err.println("didn't find the object that we looked for ! " + ans + " instead of " + ("Bla Bla"+i));
					return;
				}
			}

		}catch (Exception e) {
			System.err.println("Problem running testDelete("+size+")");
			e.printStackTrace();
			return;
		}
		System.out.println("Finished\n---");
	}

}
