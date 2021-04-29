/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Playlist;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.PlaylistService;

/**
 * FXML Controller class
 *
 * @author Lwiss
 */
public class PlaylistViewController implements Initializable {

    @FXML
    private Button btnAddPlaylist;
    @FXML
    private Button btnUpdatePlaylist;
    @FXML
    private Button btnDeletePlaylist;
    @FXML
    private TableView<Playlist> PlaylistList;
    @FXML
    private TableColumn<Playlist, Integer> PlaylistId;
    @FXML
    private TableColumn<Playlist, String> PlaylistName;
    @FXML
    private TableColumn<Playlist, String> PlaylistDescription;
    @FXML
    private TableColumn<Playlist, Date> PlaylistCreationDate;

    
    public static int testBtn=0;
    public static int selectedValue=0;
    private static Stage addPlaylist;
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
      
        showPlaylists();
    }    

    
    
    
    
    public void showPlaylists(){
        PlaylistService ps=new PlaylistService();
        ObservableList<Playlist> list= ps.getAllPlaylists();
        PlaylistId.setCellValueFactory(new PropertyValueFactory<>("id"));
        PlaylistName.setCellValueFactory(new PropertyValueFactory<>("playlistName"));
        
        PlaylistDescription.setCellValueFactory(new PropertyValueFactory<>("playlistDescription"));
        PlaylistCreationDate.setCellValueFactory(new PropertyValueFactory<>("playlistCreationDate"));
        PlaylistList.setItems(list);
    }
    
    
    @FXML
    private void AddAction(ActionEvent event) {
       
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("AddPlaylist.fxml"));
        try {
            loader.load();


              } catch (IOException ex) {
                  System.out.println(ex.getMessage());
        }

        Parent parent = loader.getRoot();
        stage.setScene(new Scene(parent));
        stage.show();
        
    }

    @FXML
    private void deleteAction(ActionEvent event) {
        if (PlaylistList.getSelectionModel().getSelectedItem()==null) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Select a playlist");
                alert.setHeaderText(null);
                alert.setContentText("Please select a playlist and try again.");
                alert.showAndWait();
                return;
        }
                Alert alert = new Alert(AlertType.ERROR);
                ButtonType buttonTypeOne = new ButtonType("Yes");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll( buttonTypeOne,buttonTypeCancel);
        alert.setTitle("Playlist deletion");
        alert.setHeaderText("You're about to delete this playlist");
        alert.setContentText("Do you want to proceed?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
           PlaylistService ps=new PlaylistService();
        ps.deletePlaylist(PlaylistList.getSelectionModel().getSelectedItem().getId());
        showPlaylists();
        } else {
           showPlaylists();
        }
     
    }

        
    public static void closeUpdatePlaylist() {
        addPlaylist.close();
    }

    @FXML
    private void updateAction(ActionEvent event) throws IOException {
        if (PlaylistList.getSelectionModel().getSelectedItem()==null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No playlist was selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the playlist you want to update");
            alert.showAndWait();
            return;
        }
        
        
         testBtn=1;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPlaylist.fxml"));
            Parent root = (Parent)loader.load();
            AddPlaylistController AddPlaylistController = (AddPlaylistController)loader.getController();
            addPlaylist = new Stage();
            addPlaylist.setTitle("Edit channel");
            addPlaylist.setScene(new Scene(root));
            addPlaylist.initModality(Modality.WINDOW_MODAL);
            addPlaylist.initOwner(((Node)(event.getSource())).getScene().getWindow());
       AddPlaylistController.setaddPlaylistTtitle("Update playlist");
        AddPlaylistController.setbtnaddPlaylist("Update");
        AddPlaylistController.setaddPlaylistNameLabel("Update the playlist's name");
        AddPlaylistController.setaddPlaylistDescriptionLabel("Edit the playlist's description");
        AddPlaylistController.setPlaylistNameField(PlaylistList.getSelectionModel().getSelectedItem().getPlaylistName());
        AddPlaylistController.setPlaylistDescriptionField(PlaylistList.getSelectionModel().getSelectedItem().getPlaylistDescription());
        addPlaylist.show();
                        
       
    }

    
    
    
}
