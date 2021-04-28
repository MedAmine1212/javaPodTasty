/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author khail
 */
public class HomeScreenController implements Initializable {

    private User currentUser;
    private static boolean isCom = false;
    private static HomeScreenController instance;
    @FXML
    private Button homeButton;
    @FXML
    private Button browseButton;
    @FXML
    private Button blogButton;
    @FXML
    private Button contactButton;
    @FXML
    private Button signInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button signOutButton;
    @FXML
    private StackPane container;
    @FXML
    private VBox userVbox;
    @FXML
    private Pane adminVbox;
    @FXML
    private Button podcastAdmin;
    @FXML
    private Button tagAdmin;
    @FXML
    private Button postAdmin;
    @FXML
    private Button storyAdmin;
    @FXML
    private Button userAdmin;
    @FXML
    private Button channelAdmin;
    @FXML
    private Button playlistAdmin;
    @FXML
    private Button reclamationAdmin;
    @FXML
    private Button profileAdmin;
    @FXML
    private Button signOutAdmin;
    @FXML
    private Pane adminVbox1;
    
    private static Stage logRegStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        currentUser = null;
        setUpView();
    }


        public void setUpView() {
           
       if (currentUser == null) {
            userVbox.setVisible(true);
            adminVbox.setVisible(false);
           signInButton.setVisible(true);
           signUpButton.setVisible(true);
           profileButton.setVisible(false);
           signOutButton.setVisible(false); 
       } else {
           if (currentUser.getIsAdmin()) {
               userVbox.setVisible(false);
            adminVbox.setVisible(true);
           } else {
            userVbox.setVisible(true);
            adminVbox.setVisible(false);
           signInButton.setVisible(false);
           signUpButton.setVisible(false);
           profileButton.setVisible(true);
           signOutButton.setVisible(true);
               
           }
       }
       
       goHome(null);
        }
      public User getCurrentUser() {
         return currentUser;
     }
      
          public void setCurrentUser(User user) {
         currentUser = user;
     }
     
          
        public StackPane getContainer() {
         return container;
     }
        
    public static HomeScreenController getInstance() {
        return instance;
    }

    @FXML
    private void goHome(MouseEvent event) {
       changeView("podcasts.fxml");
    }

    @FXML
    private void goBrowse(MouseEvent event) {
        changeView("ChannelBrowser.fxml");
           
    }

    @FXML
    private void goBlog(MouseEvent event) {
            
    }

    @FXML
    private void goContact(MouseEvent event) {
           
    }

    @FXML
    private void goSignIn(MouseEvent event) throws IOException {
        Parent root;
            root = FXMLLoader.load(getClass().getResource("LogReg.fxml"));
            logRegStage = new Stage();
            logRegStage.setTitle("Sign i");
            logRegStage.setScene(new Scene(root));
            logRegStage.initModality(Modality.APPLICATION_MODAL);
            logRegStage.initOwner(((Node)(event.getSource())).getScene().getWindow());
            logRegStage.show();
    }
    
    public static void closeSignIn() {
        logRegStage.close();
    }

    @FXML
    private void goSignUp(MouseEvent event) {
       
    }

    @FXML
    private void goProfile(MouseEvent event) {
           
    }

    @FXML
    private void goLogOut(MouseEvent event) {
              if (isCom) {
             LoadAudio ld = LoadAudio.getInstance();
        try {
            ld.stopAudio();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        LoadAudio.destroyInstance();
    }
              currentUser = null;
              setUpView();
              
    }
    
    public static void setIsCom(boolean bol) {
        isCom = bol;
    }

    @FXML
    private void goTagsAdmin(MouseEvent event) {
         changeView("TagsFxmlDocument.fxml");
    }

    @FXML
    private void goPostsAdmin(MouseEvent event) {
    }

    @FXML
    private void goStoriesAdmin(MouseEvent event) {
    }

    @FXML
    private void goUsersAdmin(MouseEvent event) {
    }

    @FXML
    private void goChannelAdmin(MouseEvent event) {
        changeView("ChannelView.fxml");
    }

    @FXML
    private void goPlaylistAdmin(MouseEvent event) {
        changeView("PlaylistView.fxml");
    }

    @FXML
    private void goReclamationAdmin(MouseEvent event) {
    }

    @FXML
    private void goProfileAdmin(MouseEvent event) {
    }
    
    
    public void changeView(String view) {
         if (isCom) {
             LoadAudio ld = LoadAudio.getInstance();
        try {
            ld.stopAudio();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        LoadAudio.destroyInstance();
    }
         container.getChildren().clear();
             FXMLLoader fx = new FXMLLoader(getClass().getResource(view));
        try {
            Pane p = fx.load();
            container.getChildren().add(p);
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        isCom = false;
    }
    
}
