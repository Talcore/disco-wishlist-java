package spotify;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import observer.ObserverController;

public class WishTrack {
	private int track_id;
	private SimpleStringProperty name;
	private SimpleStringProperty interpret;
	private SimpleStringProperty album;
	private String spotify_id;
	private String album_cover;
	private SimpleIntegerProperty votes;
	private Button btndel;
	private Button btnnow;

	public Button getBtndel() {
		return btndel;
	}
	
	public Button getBtnnow() {
		return btnnow;
	}

	public WishTrack(int track_id, String name, String interpret, String album, String spotify_id, String album_cover, int votes, ObserverController controller) {
		this.track_id = track_id;
		this.name = new SimpleStringProperty(name);
		this.interpret = new SimpleStringProperty(interpret);
		this.album = new SimpleStringProperty(album);
		this.spotify_id = spotify_id;
		this.album_cover = album_cover;
		this.votes = new SimpleIntegerProperty(votes);
		this.btndel = new Button("-");
		this.btnnow = new Button("N");
		btndel.setOnAction((e) -> {
			controller.removeFromWishlist(track_id);
		});
		btnnow.setOnAction((e) -> {
			controller.setActivePlaying(this);
		});
	}

	@Override
	public String toString() {
		return "WishTrack [track_id=" + track_id + ", name=" + name + ", interpret=" + interpret + ", album=" + album
				+ ", spotify_id=" + spotify_id + ", album_cover=" + album_cover + ", votes=" + votes + "]";
	}

	@Override
	public boolean equals(Object wt) {
		return wt instanceof WishTrack && this.track_id == ((WishTrack) wt).track_id && this.votes.get() == ((WishTrack) wt).votes.get();
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

	public int getVotes() {
		return votes.get();
	}
}