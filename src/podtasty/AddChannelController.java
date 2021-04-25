/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Channel;
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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import services.ChannelService;

/**
 * FXML Controller class
 *
 * @author Lwiss
 */
public class AddChannelController implements Initializable {

    @FXML
    private Label addChannelTtitle;
    @FXML
    private Button btnaddChannel;
    @FXML
    private Button btnCancelAddChannel;
    @FXML
    private Label addChannelNameLabel;
    @FXML
    private TextField ChannelNameField;
    @FXML
    private Label addChannelDescriptionLabel;
    @FXML
    private TextField ChannelDescriptionField;
   
    @FXML
    private ToggleButton toggleBanned;
    @FXML
    private ToggleGroup status;
    @FXML
    private ToggleButton toggleActive;
    @FXML
    private Label addChannelStatusLabel;
    private int selectedValue;
    private int testBtn=0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("ChannelViewController.fxml"));
       
        ChannelViewController ChannelViewController=loader.getController();
        if (ChannelViewController.testBtn==1) {
            
         ChannelNameField.setText("bbbb");
         ChannelDescriptionField.setText("aaa");}
    }    
    
   
    public void setChannelNameField(String s){
        this.ChannelNameField.setText(s);
    }
    public void setChannelDescriptionField(String s){
        this.ChannelDescriptionField.setText(s);
    }
    
    public void setselectedValue(int selectedValue){
    this.selectedValue=selectedValue;}
    
    public void setaddChannelTtitle(String s){
        addChannelTtitle.setText(s);
    }
    public void setbtnaddChannel(String s){
        btnaddChannel.setText(s);
    }
    public void setaddChannelNameLabel(String s){
        addChannelNameLabel.setText(s);
    }
    public void setaddChannelDescriptionLabel(String s){
        addChannelDescriptionLabel.setText(s);
    }
     public void setaddChannelStatusLabel(String s){
        addChannelStatusLabel.setText(s);
    }
    
    
    
    
    

    @FXML
    private void AddChannelAction(ActionEvent event) {
         if (ChannelNameField.getText().isEmpty()||ChannelDescriptionField.getText().isEmpty()) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing fields");
                alert.setHeaderText("");
                alert.setContentText("Please Fill in all the required fields");
                alert.showAndWait(); return;}
                
                else{
         if (ChannelViewController.testBtn==0) {
            
       
         Channel c = new Channel();
         
         c.setChannel_Name(ChannelNameField.getText());
         c.setChannel_Description(ChannelDescriptionField.getText());
         java.util.Date date=new java.util.Date();
         java.sql.Date sqlDate=new java.sql.Date(date.getTime());
         c.setChannel_CreationDate(sqlDate);
         if (status.getSelectedToggle()==toggleActive) {
             c.setChannel_Status(1);
        }else {
             c.setChannel_Status(0);
         }
         ChannelService cs= new ChannelService();
         cs.addChannel(c);
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Channel created");
        alert.setHeaderText(null);
        alert.setContentText("You have succesfully added a new Channel!");
        alert.showAndWait();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("ChannelView.fxml"));
        try {
            loader.load();


              } catch (IOException ex) {
                  System.out.println(ex.getMessage());
        }

        Parent parent = loader.getRoot();
        stage.setScene(new Scene(parent));
        stage.show(); }
         
         else if (ChannelViewController.testBtn==1) {
            
        
{
            
             ChannelService sc=new ChannelService();
             Channel c=new Channel();
             c.setChannel_Name(ChannelNameField.getText());
             c.setChannel_Description(ChannelDescriptionField.getText());
             if (status.getSelectedToggle()==toggleActive) {
             c.setChannel_Status(1);
        }else {
             c.setChannel_Status(0);
         }
             sc.updateChannel(selectedValue, c);
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Channel Updated");
                alert.setHeaderText(null);
                alert.setContentText("You have succesfully updated the Channel!");
                alert.showAndWait();
                Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("ChannelView.fxml"));
        try {
            loader.load();


              } catch (IOException ex) {
                  System.out.println(ex.getMessage());
        }

        Parent parent = loader.getRoot();
        stage.setScene(new Scene(parent));
        stage.show();}
                
            
             
         
         }}
    }

    @FXML
    private void CancelAddChannel(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("ChannelView.fxml"));
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
