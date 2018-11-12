package spotify;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import wishlist.WishlistController;

public class Track {
	private int track_id;
	private SimpleStringProperty name;
	private SimpleStringProperty interpret;
	private SimpleStringProperty album;
	private String spotify_id;
	private String album_cover;
	private Button btnadd;

	public Button getBtnadd() {
		return btnadd;
	}

	public Track(int track_id, String name, String interpret, String album, String spotify_id, String album_cover, WishlistController controller) {
		this.track_id = track_id;
		this.name = new SimpleStringProperty(name);
		this.interpret = new SimpleStringProperty(interpret);
		this.album = new SimpleStringProperty(album);
		this.spotify_id = spotify_id;
		this.album_cover = album_cover;
		this.btnadd = new Button("+");
		btnadd.setOnAction((e) -> {
			controller.addToWishlist(track_id);
			controller.changeTextSearch("");
			
			//thread sleep to prevent spamming
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		});
	}

	@Override
	public String toString() {
		return "Track [track_id=" + track_id + ", name=" + name + ", interpret=" + interpret + ", album=" + album
				+ ", spotify_id=" + spotify_id + ", album_cover=" + album_cover + "]";
	}

	public int getTrack_id() {
		return track_id;
	}

	public String getName() {
		return name.get();
	}

	public String getInterpret() {
		return interpret.get();
	}

	public String getAlbum() {
		return album.get();
	}

	public String getSpotify_id() {
		return spotify_id;
	}

	public String getAlbum_cover() {
		return album_cover;
	}
}