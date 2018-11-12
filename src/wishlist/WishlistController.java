package wishlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.DBConnection;
import database.DBTools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import spotify.Track;

public class WishlistController {
	private ArrayList<Track> tracks = null;
	private ObservableList<Track> tlist = null;
	private FilteredList<Track> filterlist = null;
	
	@FXML TextField tFSearch;
	@FXML Button btnSearch;
	@FXML TableView<Track> tTracks;
	@FXML TableColumn<Track, String> colName;
	@FXML TableColumn<Track, String> colInterpret;
	@FXML TableColumn<Track, String> colAlbum;
	@FXML TableColumn<Track, Button> colAdd;
	
	@FXML
	public void searchClick() {
		
	}
	
	public static void preload() {
		new DBConnection();
	}
	
	public void changeTextSearch(String string) {
		tFSearch.textProperty().set(string);
	}
	
	private ObservableList<Track> getTrackList() {
		/*Track t1 = new Track(1, "Hello", "Adele", "Hello from", "abcdeggf54d6gd56g6s");
		t1.setBtnadd(new Button("+"));
		Track t2 = new Track(2, "Astronaut", "Andreas Bourani", "Space", "ifdiujadionio37grdhu");
		Track t3 = new Track(3, "Sugar", "Robin  Schulz", "Sugar EP", "iojtdfksfmkdnofns8395zriof432");
		return (ObservableList<Track>) FXCollections.observableArrayList(t1, t2, t3);*/
		
		return (ObservableList<Track>) FXCollections.observableArrayList(tracks);
	}

	//Initialize method invoked after FXML loaded the scene
	public void initialize() {
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colInterpret.setCellValueFactory(new PropertyValueFactory<>("interpret"));
		colAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
		colAdd.setCellValueFactory(new PropertyValueFactory<>("btnadd"));
		
		getAllTracks();
		
		tlist = getTrackList();
		
		filterlist = new FilteredList<>(tlist, p -> true);
		
		//set onTextChangeListener to searchbox
		tFSearch.textProperty().addListener((observable, oldValue, newValue) -> {
			filterlist.setPredicate(track -> {
				if(newValue == null || newValue.isEmpty()) return true;
				
				String lower = newValue.toLowerCase();
				
				if(track.getName().toLowerCase().contains(lower) ||
				track.getInterpret().toLowerCase().contains(lower) ||
				track.getAlbum().toLowerCase().contains(lower)) {
					return true;
				}
				return false;
			});
		});
		
		tFSearch.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldv, Boolean newv) {
				if(!newv) {
					tFSearch.requestFocus();
				}
			}
		});
		
		tTracks.setItems(filterlist);
	}
	
	private void getAllTracks() {
		String query = "SELECT * FROM alltracks";
		ResultSet res;
		try {
			res = DBConnection.getConnection().createStatement().executeQuery(query);
			tracks = DBTools.convertTrackResulttoArray(res, this);
			System.out.println("Retrieved " + tracks.size() + " Songs from db.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//return: -1 -> ERROR, 0 -> inserted (first vote), 1 -> already inserted (votes +1)
	public int addToWishlist(int id) {
		filterlist.setPredicate((p) -> true);
		Statement current;
		try {
			current = DBConnection.getConnection().createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
			return -1;
		}
		
		String query = "SELECT * FROM wishlist WHERE track_id = " + id;
		ResultSet res;
		try {
			res = current.executeQuery(query);
			if(res.first()) {
				query = "UPDATE wishlist SET votes = votes + 1 WHERE track_id = " + id;
				System.out.println(current.executeUpdate(query));
				return 1; 
			}else {
				query = "INSERT INTO wishlist(track_id) VALUES(" + id + ")";
				System.out.println(current.executeUpdate(query));
				return 0; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	@SuppressWarnings("unused")
	private Callback<TableColumn<Track, String>, TableCell<Track, String>> getCustomCellFactory(final String css) {
        return new Callback<TableColumn<Track, String>, TableCell<Track, String>>() {

            @Override
            public TableCell<Track, String> call(TableColumn<Track, String> param) {
                TableCell<Track, String> cell = new TableCell<Track, String>() {

                    @Override
                    public void updateItem(final String item, boolean empty) {
                        if (item != null) {
                            setText(item);
                            setStyle(css + ";");
                        }
                    }
                };
                return cell;
            }
        };
    }
	
	@SuppressWarnings("unused")
	private void  printAllTracks(){
		System.out.println(tracks.toString());
	}

	
}
