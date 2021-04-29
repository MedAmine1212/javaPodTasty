/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Channel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import services.ChannelService;

/**
 * FXML Controller class
 *
 * @author Lwiss
 */
public class EditOwnChannelController implements Initializable {

    @FXML
    private Label addChannelTtitle;
    @FXML
    private Button btneditChannel;
    @FXML
    private Button btnCancelEditChannel;
    @FXML
    private Label addChannelNameLabel;
    @FXML
    private TextField ChannelNameField;
    @FXML
    private Label addChannelDescriptionLabel;
    @FXML
    private TextField ChannelDescriptionField;
    private HomeScreenController homeScreen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeScreen = HomeScreenController.getInstance();
    }    
    
    

    @FXML
    private void EditChannelAction(ActionEvent event) throws SQLException {
        
        ChannelService sc=new ChannelService();
             Channel c=new Channel();
             c.setChannel_Name(ChannelNameField.getText());
             c.setChannel_Description(ChannelDescriptionField.getText());
             
             sc.updateOwnChannel(homeScreen.getCurrentUser().getChannelIdId().getId() ,c);
             homeScreen.getCurrentUser().setChannelIdId(sc.findById(homeScreen.getCurrentUser().getChannelIdId().getId()));
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Channel Updated");
                alert.setHeaderText(null);
                alert.setContentText("You have succesfully updated the Channel!");
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

    @FXML
    private void CancelEditChannel(ActionEvent event) {
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
    
    public void setChannelNameField(String s){
        this.ChannelNameField.setText(s);
    }
    public void setChannelDescriptionField(String s){
        this.ChannelDescriptionField.setText(s);
    }
    
}
