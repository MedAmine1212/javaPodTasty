/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Playlist;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.PlaylistService;

/**
 * FXML Controller class
 *
 * @author Lwiss
 */
public class EditOwnPlaylistController implements Initializable {

    @FXML
    private Label addPlaylistTtitle;
    @FXML
    private Button btnCancelAddPlaylist;
    @FXML
    private Label addPlaylistNameLabel;
    @FXML
    private Label addPlaylistDescriptionLabel;
    @FXML
    private TextField PlaylistNameFieldu;
    @FXML
    private TextField PlaylistDescriptionFieldu;
    @FXML
    private Button btnUpdatePlaylist;
    private int PlaylistId;
    @FXML
    private Button editCoverBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void CancelAddPlaylist(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();       
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("OwnChannel.fxml"));
        try {
            loader.load();


              } catch (IOException ex) {
                  System.out.println(ex.getMessage());
        }

        Parent parent = loader.getRoot();
        stage.setScene(new Scene(parent));
        stage.show();
    }
    
    public void setPlaylistNameFieldu (String s){
    this.PlaylistNameFieldu.setText(s);}
    public void setPlaylistDescriptionFieldu (String s){
    this.PlaylistDescriptionFieldu.setText(s);}
    public void setPlaylistId(int id){
        this.PlaylistId=id;
    }

    @FXML
    private void UpdatePlaylistAction(ActionEvent event) {
        if (PlaylistNameFieldu.getText().isEmpty()||PlaylistDescriptionFieldu.getText().isEmpty()) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please Fill in all the required fields");
                alert.showAndWait(); return;
    }else {
        
            PlaylistService ps=new PlaylistService();
            Playlist p= new Playlist();
            p.setPlaylistName(PlaylistNameFieldu.getText());
            p.setPlaylistDescription((PlaylistDescriptionFieldu.getText()));
            ps.updatePlaylist(PlaylistId, p);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Playlist Updated");
                alert.setHeaderText(null);
                alert.setContentText("You have succesfully updated the Playlist!");
                alert.showAndWait();
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("OwnChannel.fxml"));
        try {
            loader.load();


              } catch (IOException ex) {
                  System.out.println(ex.getMessage());
        }

        Parent parent = loader.getRoot();
        stage.setScene(new Scene(parent));
        stage.show();
        }
}

    @FXML
    private void editCoverAction(ActionEvent event) {
    }
}
