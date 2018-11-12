package observer;

import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

import database.DBConnection;
import database.DBTools;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import spotify.WishTrack;

public class ObserverController {
	
	private static final String SOCKET_URL = "FILLIN SOCKET_URL";
	private ArrayList<WishTrack> wishtracks = null;
	private ObservableList<WishTrack> wtlist = null;
	private static Socket socket;
	
	@FXML TableView<WishTrack> tWishlist;
	@FXML TableColumn<WishTrack, String> colName;
	@FXML TableColumn<WishTrack, String> colInterpret;
	@FXML TableColumn<WishTrack, String> colAlbum;
	@FXML TableColumn<WishTrack, Integer> colVotes;
	@FXML TableColumn<WishTrack, Button> colDel;
	@FXML TableColumn<WishTrack, Button> colNow;
	
	public static void preload() throws URISyntaxException {
		new DBConnection();
		
		socket = IO.socket(SOCKET_URL);
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			
			@Override
			public void call(Object... arg0) {
				socket.emit("type", "Java Controller");
				System.out.println("Socket connected");
			}
		}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
			
			@Override
			public void call(Object... arg0) {
				System.out.println("Socket disconnected");
			}
		});
		socket.connect();
	}
	
	public void initialize() {
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colInterpret.setCellValueFactory(new PropertyValueFactory<>("interpret"));
		colAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
		colVotes.setCellValueFactory(new PropertyValueFactory<>("votes"));
		colDel.setCellValueFactory(new PropertyValueFactory<>("btndel"));
		colNow.setCellValueFactory(new PropertyValueFactory<>("btnnow"));
		
		updateWishList();
	}
	
	public void updateWishList() {
		getWishTracks();
		wtlist = getWishTrackList();
		tWishlist.setItems(wtlist);
	}
	
	private void getWishTracks() {
		String query = "SELECT * FROM alltracks JOIN wishlist ON alltracks.track_id = wishlist.track_id ORDER BY votes DESC";
		ResultSet res;
		try {
			res = DBConnection.getConnection().createStatement().executeQuery(query);
			wishtracks = DBTools.convertWishTrackResulttoArray(res, this);
			System.out.println("Retrieved " + wishtracks.size() + " Songs from db.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private ObservableList<WishTrack> getWishTrackList() {
		return (ObservableList<WishTrack>) FXCollections.observableArrayList(wishtracks);
	}
	
	public int removeFromWishlist(int id) {
		String query = "DELETE FROM wishlist WHERE track_id = " + id;
		try {
			System.out.println(DBConnection.getConnection().createStatement().executeUpdate(query));
			return 1;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return -1;
		}
	}

	public void setActivePlaying(WishTrack wishTrack) {
		String song = wishTrack.getName();
		String artist = wishTrack.getInterpret();
		String album_cover = wishTrack.getAlbum_cover();
		
		System.out.println("Now active: " + song + ", " + artist + ", " + album_cover);
		
		JSONObject obj = new JSONObject();
		obj.put("song", song);
		obj.put("artist", artist);
		obj.put("albumart", album_cover);
		//send active song to socket
		socket.emit("change now song", obj);
		
	}
}
