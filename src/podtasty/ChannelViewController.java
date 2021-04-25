/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Channel;
import java.io.IOException;
import java.net.URL;

import java.sql.Date;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ChannelService;
import services.PlaylistService;

/**
 * FXML Controller class
 *
 * @author Lwiss
 */
public class ChannelViewController implements Initializable {

    @FXML
    private Button btnAddChannel;
    @FXML
    private Button btnUpdateChannel;
    @FXML
    private Button btnDeleteChannel;
    @FXML
    private TableView<Channel> ChannelList;
    @FXML
    private TableColumn<Channel, Integer> ChannelId;
    @FXML
    private TableColumn<Channel, String> ChannelName;
    @FXML
    private TableColumn<Channel, String> ChannelDescription;
    @FXML
    private TableColumn<Channel, Date> ChannelCreationDate;
    @FXML
    private TableColumn<Channel, Integer> ChannelUser;
    @FXML
    private TableColumn<Channel, Integer> ChannelStatus;
    public static int testBtn=0;
    public static int selectedValue=0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       showChannels();
    }    

    
    
     public void showChannels(){
         ChannelService ps=new ChannelService();
        ObservableList<Channel> list= ps.getAllChannels();
        ChannelId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ChannelName.setCellValueFactory(new PropertyValueFactory<>("channel_Name"));
        
        ChannelDescription.setCellValueFactory(new PropertyValueFactory<>("channel_Description"));
        ChannelCreationDate.setCellValueFactory(new PropertyValueFactory<>("channel_CreationDate"));
        ChannelStatus.setCellValueFactory(new PropertyValueFactory<>("channel_Status"));
        ChannelList.setItems(list);
    }
    
    
    
    
    @FXML
    private void AddAction(ActionEvent event) {
      testBtn=0;
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("AddChannel.fxml"));
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
    private void updateAction(ActionEvent event) {
        if (ChannelList.getSelectionModel().getSelectedItem()==null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No channel was selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the channel you want to update");
            alert.showAndWait();
            return;
        }
         Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("AddChannel.fxml"));
        try {
            loader.load();


              } catch (IOException ex) {
                  System.out.println(ex.getMessage());
        }

        Parent parent = loader.getRoot();
        AddChannelController AddChannelController=loader.getController();
        AddChannelController.setaddChannelTtitle("Update Channel");
        AddChannelController.setbtnaddChannel("Update");
        AddChannelController.setaddChannelNameLabel("Update the channel's name");
        AddChannelController.setaddChannelDescriptionLabel("Edit the channel's description");
        testBtn=1;
        AddChannelController.setselectedValue(ChannelList.getSelectionModel().getSelectedItem().getId());
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @FXML
    private void deleteAction(ActionEvent event) {
        
       if (ChannelList.getSelectionModel().getSelectedItem()==null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Select a channel");
                alert.setHeaderText(null);
                alert.setContentText("Please select a channel and try again.");
                alert.showAndWait();
                return;
        }
                Alert alert = new Alert(Alert.AlertType.ERROR);
                ButtonType buttonTypeOne = new ButtonType("Yes");
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll( buttonTypeOne,buttonTypeCancel);
        alert.setTitle("Channel deletion");
        alert.setHeaderText("You're about to delete this channel");
        alert.setContentText("Do you want to proceed?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
           ChannelService ps=new ChannelService();
        ps.deleteChannel(ChannelList.getSelectionModel().getSelectedItem().getId());
        showChannels();
        } else {
           showChannels();
        }
    }
    
}
