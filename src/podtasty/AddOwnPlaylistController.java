/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Playlist;
import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import services.PlaylistService;

/**
 * FXML Controller class
 *
 * @author Lwiss
 */
public class AddOwnPlaylistController implements Initializable {

    @FXML
    private Label addPlaylistTtitle;
    @FXML
    private Button btnUpdatePlaylist;
    @FXML
    private Button btnCancelAddPlaylist;
    @FXML
    private Label addPlaylistNameLabel;
    @FXML
    private TextField PlaylistNameFieldu;
    @FXML
    private Label addPlaylistDescriptionLabel;
    @FXML
    private TextField PlaylistDescriptionFieldu;
    @FXML
    private Button uploadImageBtn;
    private File file;
    private HomeScreenController homeScreen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeScreen = HomeScreenController.getInstance();
        // TODO
    }    

    @FXML
    private void UpdatePlaylistAction(ActionEvent event) {
        if (PlaylistNameFieldu.getText().isEmpty()||PlaylistDescriptionFieldu.getText().isEmpty()) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please Fill in all the required fields");
                alert.showAndWait(); return;}else{
        PlaylistService ps= new PlaylistService();
        Playlist p=new Playlist();
        p.setPlaylistName(PlaylistNameFieldu.getText());
        p.setPlaylistDescription(PlaylistDescriptionFieldu.getText());
        java.util.Date date=new java.util.Date();
            java.sql.Date sqlDate=new java.sql.Date(date.getTime());
            p.setPlaylistCreationDate(sqlDate);
            p.setImageName(file.getName());
        ps.AddOwnPlaylist(homeScreen.getCurrentUser().getChannelIdId().getId(),p);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Playlist created");
        alert.setHeaderText(null);
        alert.setContentText("You have succesfully added a new Playlist!");
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
        
    }}

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

    @FXML
    private void uploadImageAction(ActionEvent event) throws IOException {
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png","*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
         file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            MultipartEntityBuilder entity = MultipartEntityBuilder.create();
            entity.addBinaryBody("myFile", file);
            HttpEntity mutiPartHttpEntity = entity.build();
            HttpPost request = new HttpPost("http://127.0.0.1:8000/api/profile/pic/post");
            request.setEntity(mutiPartHttpEntity);
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(request);
            Header[] responseEntity = response.getHeaders();
            System.out.println(response);
    }}
    
}
