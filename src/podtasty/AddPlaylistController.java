/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Playlist;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.PlaylistService;

/**
 * FXML Controller class
 *
 * @author Lwiss
 */
public class AddPlaylistController implements Initializable {

    @FXML
    private Button btnCancelAddPlaylist;
    @FXML
    private TextField PlaylistNameField;
    @FXML
    private TextField PlaylistDescriptionField;
    @FXML
    private Label addPlaylistTtitle;
    @FXML
    private Button btnaddPlaylist;
    @FXML
    private Label addPlaylistNameLabel;
    @FXML
    private Label addPlaylistDescriptionLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    

    public void setPlaylistNameField(String s){
        this.PlaylistNameField.setText(s);
    }
    public void setPlaylistDescriptionField(String s){
        this.PlaylistDescriptionField.setText(s);
    }
    
    
    public void setaddPlaylistTtitle (String s){
    addPlaylistTtitle.setText(s);}
    public void setbtnaddPlaylist (String s){
        btnaddPlaylist.setText(s);
    }
    public void setaddPlaylistNameLabel (String s){
    addPlaylistNameLabel.setText(s);}
    public void setaddPlaylistDescriptionLabel (String s){
    addPlaylistDescriptionLabel.setText(s);}
    
    
    @FXML
    private void AddPlaylistAction(ActionEvent event) {
        if (PlaylistNameField.getText().isEmpty()||PlaylistDescriptionField.getText().isEmpty()) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please Fill in all the required fields");
                alert.showAndWait(); return;
        } else{FXMLLoader loader = new FXMLLoader (); AddPlaylistController PlaylistViewController=loader.getController();}
            
            if (PlaylistViewController.testBtn==0){
            Playlist p=new Playlist();
            p.setPlaylistName(PlaylistNameField.getText());
            p.setPlaylistDescription(PlaylistDescriptionField.getText());
            java.util.Date date=new java.util.Date();
            java.sql.Date sqlDate=new java.sql.Date(date.getTime());
            p.setPlaylistCreationDate(sqlDate);
            PlaylistService ps=new PlaylistService();
            ps.AddPlaylist(p);
         Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Playlist created");
        alert.setHeaderText(null);
        alert.setContentText("You have succesfully added a new Playlist!");
        alert.showAndWait();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("PlaylistView.fxml"));
        try {
            loader.load();


              } catch (IOException ex) {
                  System.out.println(ex.getMessage());
        }

        Parent parent = loader.getRoot();
        stage.setScene(new Scene(parent));
        stage.show();
        
        } else{
                
                PlaylistService ps=new PlaylistService();
                Playlist p=new Playlist();
                p.setPlaylistName(PlaylistNameField.getText());
                p.setPlaylistDescription(PlaylistDescriptionField.getText());
                ps.updatePlaylist(PlaylistViewController.selectedValue, p);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Playlist Updated");
                alert.setHeaderText(null);
                alert.setContentText("You have succesfully updated the Playlist!");
                alert.showAndWait();
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("PlaylistView.fxml"));
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
    private void CancelAddPlaylist(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("PlaylistView.fxml"));
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
