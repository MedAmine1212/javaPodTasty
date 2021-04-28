/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import entities.Channel;
import entities.Playlist;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.util.Collections.list;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.ChannelService;

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
    private Button EditOwnChannel;
    @FXML
    private Button DeleteOwnChannel;
    
    User u;
    List<Playlist> list;
    @FXML
    private Button BrowseChannelsBtn;
    @FXML
    private Button ReturnSingle1;
    @FXML
    private Button addPlaylist;
    private HomeScreenController homeScreen;
   
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    homeScreen = HomeScreenController.getInstance();
        ChannelService cs= new ChannelService();
        try {
            Channel ch=cs.findById(28);
            list=cs.getPlaylistsbyChannelId(16);
          
             u= new User(1,ch);
             welcome();
        } catch (SQLException ex) {
            Logger.getLogger(OwnChannelController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OwnChannelController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }    
    

    @FXML
    private void ReturnSingle(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
        
            loader.setLocation(getClass().getResource("Profile.fxml"));
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
    private void DeleteOwnChannelBtn(ActionEvent event) {
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
        ps.deleteChannel(u.getChannelIdId().getId());
        
        } 
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
    
    
    public void welcome() throws SQLException, IOException{
     /*     UserHolder holder = UserHolder.getInstance();
            User u = holder.getUser(); */
            
       

        
        
        
       
        
       
        setwelcomeTitle("Welcome back "+ u.getChannelIdId().getChannel_Name());
        setdescriptionLabel(u.getChannelIdId().getChannel_Description());
        Date date=u.getChannelIdId().getChannel_CreationDate();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
          String strDate = dateFormat.format(date);  
        setcreatedLabel(strDate);
        setownerLabel(list.size()+"");
        
        int i=0;
           System.out.println(list.size());
           for (Playlist pl : list) {    
                FXMLLoader fx=new FXMLLoader(getClass().getResource("PlaylistZoneOwer.fxml"));
                Pane p =fx.load();
                PlaylistZoneOwerController controller=fx.getController();
                controller.setView(pl);
                big.add(p, 0, i);
               
                i++;
              
            }
        
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

    @FXML
    private void BrowseChannelsBtn(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
        
            loader.setLocation(getClass().getResource("ChannelBrowser.fxml"));
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
    private void addPlaylistAction(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
        
            loader.setLocation(getClass().getResource("AddOwnPlaylist.fxml"));
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
