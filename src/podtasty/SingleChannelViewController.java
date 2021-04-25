/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Channel;
import entities.Playlist;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.sound.midi.MidiChannel;
import services.ChannelService;

/**
 * FXML Controller class
 *
 * @author Lwiss
 */
public class SingleChannelViewController implements Initializable {

    @FXML
    private Label welcomeTitle;
    @FXML
    private Label ownerLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label createdLabel;
    
     public Label a;
    private Pane bigspaceplaylist;
    @FXML
    private GridPane big;
    @FXML
    private Button ReturnSingle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     

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

    public void setId(int id) throws SQLException, MalformedURLException, IOException {
        ChannelService ps=new ChannelService();
        this.a.setText(""+id);
        
         int idCh=Integer.parseInt(a.getText());
           List<Playlist> list=ps.getPlaylistsbyChannelId(idCh);
           setownerLabel(list.size()+"");
           int i=0;
           System.out.println(list.size());
           for (Playlist pl : list) {    
                FXMLLoader fx=new FXMLLoader(getClass().getResource("PlaylistZone.fxml"));
                Pane p =fx.load();
                PlaylistZoneController controller=fx.getController();
                controller.setView(pl);
                big.add(p, 0, i);
               
                i++;
              
            }
           
           /* Collection<Playlist> pl=c.getPlaylistCollection();
             = new ArrayList<>(pl);
                nameplaylist.setText(newList.get(1).getPlaylistName());
                
                */
    }

    @FXML
    private void ReturnSingle(ActionEvent event) {
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
    
    
    
    

    
    
}
