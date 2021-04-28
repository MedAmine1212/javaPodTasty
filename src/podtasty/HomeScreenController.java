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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author khail
 */
public class HomeScreenController implements Initializable {

    private static User currentUser;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        currentUser = new User();
        currentUser.setId(1);
        currentUser.setIsAdmin(true);
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
      public static User getCurrentUser() {
         return currentUser;
     }
      
          public static void setCurrentUser(User user) {
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
        FXMLLoader fx = new FXMLLoader(getClass().getResource("podcasts.fxml"));
        try {
            Pane p = fx.load();
            container.getChildren().add(p);
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        isCom = false;
    }

    @FXML
    private void goBrowse(MouseEvent event) {
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
             FXMLLoader fx = new FXMLLoader(getClass().getResource("podcasts.fxml"));
        try {
            Pane p = fx.load();
            container.getChildren().add(p);
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        isCom = false;
    }

    @FXML
    private void goBlog(MouseEvent event) {
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
             FXMLLoader fx = new FXMLLoader(getClass().getResource("podcasts.fxml"));
        try {
            Pane p = fx.load();
            container.getChildren().add(p);
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        isCom = false;
    }

    @FXML
    private void goContact(MouseEvent event) {
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
             FXMLLoader fx = new FXMLLoader(getClass().getResource("podcasts.fxml"));
        try {
            Pane p = fx.load();
            container.getChildren().add(p);
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        isCom = false;
    }

    @FXML
    private void goSignIn(MouseEvent event) {
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
             FXMLLoader fx = new FXMLLoader(getClass().getResource("podcasts.fxml"));
        try {
            Pane p = fx.load();
            container.getChildren().add(p);
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        isCom = false;
    }

    @FXML
    private void goSignUp(MouseEvent event) {
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
             FXMLLoader fx = new FXMLLoader(getClass().getResource("podcasts.fxml"));
        try {
            Pane p = fx.load();
            container.getChildren().add(p);
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        isCom = false;
    }

    @FXML
    private void goProfile(MouseEvent event) {
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
             FXMLLoader fx = new FXMLLoader(getClass().getResource("podcasts.fxml"));
        try {
            Pane p = fx.load();
            container.getChildren().add(p);
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        isCom = false;
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
             FXMLLoader fx = new FXMLLoader(getClass().getResource("TagsFxmlDocument.fxml"));
        try {
            Pane p = fx.load();
            container.getChildren().add(p);
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        isCom = false;
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
    }

    @FXML
    private void goPlaylistAdmin(MouseEvent event) {
    }

    @FXML
    private void goReclamationAdmin(MouseEvent event) {
    }

    @FXML
    private void goProfileAdmin(MouseEvent event) {
    }
    
}
