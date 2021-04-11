/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

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
    private TableView<?> PlaylistList;
    @FXML
    private TableColumn<?, ?> PlaylistId;
    @FXML
    private TableColumn<?, ?> PlaylistName;
    @FXML
    private TableColumn<?, ?> PlaylistDescription;
    @FXML
    private TableColumn<?, ?> PlaylistCreationDate;

    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
    
}
