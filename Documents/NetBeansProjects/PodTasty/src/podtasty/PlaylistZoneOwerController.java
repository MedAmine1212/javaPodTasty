/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Playlist;
import entities.Podcast;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import services.PlaylistService;

/**
 * FXML Controller class
 *
 * @author Lwiss
 */
public class PlaylistZoneOwerController implements Initializable {

    @FXML
    private Pane space;
    @FXML
    private Label a;
    @FXML
    private Label nameplaylist;
    @FXML
    private ImageView imageplaylist;
    @FXML
    private Label descriptionplaylist;
    @FXML
    private Button EditPlaylistBtn;
    @FXML
    private Button DeletePlaylistBtn;
    private Playlist p;
    @FXML
    private Button PlayPlaylistBtn;
    private Playlist pl;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setView(Playlist pl) throws MalformedURLException, IOException{
        this.p=pl;
                nameplaylist.setText(pl.getPlaylistName());
                descriptionplaylist.setText(pl.getPlaylistDescription());
                BufferedImage image;
                image = ImageIO.read(new URL("http://127.0.0.1:8000/images/playlist/"+pl.getImageName()));
                WritableImage img = SwingFXUtils.toFXImage(image, null);
                imageplaylist.setImage(img);
                this.pl = pl;
                
               if( pl.getPodcastCollection().isEmpty()) {
                   PlayPlaylistBtn.setVisible(false);
               }
    }

    @FXML
    private void EditPlaylistBtn(ActionEvent event) {
                  
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();       
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("EditOwnPlaylist.fxml"));
        try {
            loader.load();


              } catch (IOException ex) {
                  System.out.println(ex.getMessage());
        }

        Parent parent = loader.getRoot();
        stage.setScene(new Scene(parent));
        EditOwnPlaylistController EditOwnPlaylistController=loader.getController();
        EditOwnPlaylistController.setPlaylistNameFieldu(p.getPlaylistName());
        EditOwnPlaylistController.setPlaylistDescriptionFieldu(p.getPlaylistDescription());
        EditOwnPlaylistController.setPlaylistId(p.getId());
        stage.show();
    }

    @FXML
    private void DeletePlaylistBtn(ActionEvent event) throws SQLException, IOException {
        
        Alert alert = new Alert(Alert.AlertType.ERROR);
                ButtonType buttonTypeOne = new ButtonType("Yes");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll( buttonTypeOne,buttonTypeCancel);
        alert.setTitle("Playlist deletion");
        alert.setHeaderText("You're about to delete this playlist");
        alert.setContentText("Do you want to proceed?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
           PlaylistService ps=new PlaylistService();
        ps.deletePlaylist(p.getId());
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
                
            
             
         
         
        } else {
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
    private void PlayPlaylistBtn(ActionEvent event) {
        
                
        Podcast pod = pl.getPodcastCollection().iterator().next();
        HomeScreenController.setIsCom(true);
     
      PodcastCommentsFrontController.setCurrentPodcast(pod);
      
      
      FXMLLoader fx = new FXMLLoader(getClass().getResource("PodcastCommentsFront.fxml"));
        try {
            Pane p = fx.load();
            HomeScreenController.getInstance().getContainer().getChildren().clear();
            HomeScreenController.getInstance().getContainer().getChildren().add(p);
        ProfileController.closeChannel();
        HomeScreenController.closeProfile();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }
}
    
    

