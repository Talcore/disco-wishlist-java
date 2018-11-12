package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import observer.ObserverController;
import spotify.Track;
import spotify.WishTrack;
import wishlist.WishlistController;

public class DBTools {	
	
	//convert Tracks received from DB to ArrayList of spotify.Tracks items
	public static ArrayList<Track> convertTrackResulttoArray(ResultSet res, WishlistController controller) throws SQLException {
		ArrayList<Track> tracks = new ArrayList<>();
		while(res.next()) {
			tracks.add(new Track(res.getInt("track_id"), res.getString("name"), res.getString("interpret"), res.getString("album"), res.getString("spotify_id"), res.getString("cover_url"), controller));
		}
		res.close();
		return tracks;
	}
	
	//convert WishTracks received from DB to ArrayList of spotify.WishTrack items
	public static ArrayList<WishTrack> convertWishTrackResulttoArray(ResultSet res, ObserverController controller) throws SQLException {
		ArrayList<WishTrack> tracks = new ArrayList<>();
		while(res.next()) {
			tracks.add(new WishTrack(res.getInt("track_id"), res.getString("name"), res.getString("interpret"), res.getString("album"), res.getString("spotify_id"), res.getString("cover_url"), res.getInt("votes"), controller));
		}
		res.close();
		return tracks;
	}
	
}
