/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import entities.Channel;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lwiss
 */
public class OwnChannelController implements Initializable {

    @FXML
    private Label welcomeTitle;
    @FXML
    private Label ownerLabel;
    @FXML
    private Label createdLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label a;
    @FXML
    private GridPane big;
    @FXML
    private Button ReturnSingle;
    @FXML
    private Button EditOwnChannel;
    @FXML
    private Button DeleteOwnChannel;
    
    private User u;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      java.util.Date date=new java.util.Date();
            java.sql.Date sqlDate=new java.sql.Date(date.getTime());
        Channel ch= new Channel(20,"Hard coded NAME", "BAAA3 DESC",sqlDate,1);
        this.u=new User(1,ch);
        
        welcome(u);
    }    
    

    @FXML
    private void ReturnSingle(ActionEvent event) {
    }


    @FXML
    private void DeleteOwnChannelBtn(ActionEvent event) {
    }
    
    public void setwelcomeTitle(String s){
        this.welcomeTitle.setText(s);
    }
    public void setownerLabel(String s){
        this.ownerLabel.setText(s);
    }
    public void setdescriptionLabel(String s){
        this.descriptionLabel.setText(s);
    }
    public void setcreatedLabel(String s){
        this.createdLabel.setText(s);
    }
    
    
    public void welcome(User u){
    
        setwelcomeTitle("Welcome back "+u.getChannelIdId().getChannel_Name());
        setdescriptionLabel(u.getChannelIdId().getChannel_Description());
        Date date=u.getChannelIdId().getChannel_CreationDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
          String strDate = dateFormat.format(date);  
        setcreatedLabel(strDate);
    }

    @FXML
    private void EditOwnChannelBtn(ActionEvent event) {
        
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
        
            loader.setLocation(getClass().getResource("EditOwnChannel.fxml"));
        try {
            loader.load();


              } catch (IOException ex) {
                  System.out.println(ex.getMessage());
        }

        Parent parent = loader.getRoot();
         
        EditOwnChannelController EditOwnChannelController=loader.getController();
        EditOwnChannelController.setChannelNameField(u.getChannelIdId().getChannel_Name());
        EditOwnChannelController.setChannelDescriptionField(u.getChannelIdId().getChannel_Description());
        stage.setScene(new Scene(parent));
        
        stage.show();
    }
    
}
