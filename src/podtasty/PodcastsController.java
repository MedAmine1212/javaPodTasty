/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Podcast;
import entities.Tag;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import services.CRUDComments;
import services.CRUDTag;

/**
 * FXML Controller class
 *
 * @author khail
 */
public class PodcastsController implements Initializable {

    @FXML
    private GridPane tagsContainer;
    @FXML
    private GridPane podcastContainer;
    @FXML
    private ScrollPane podcastScroll;
    
    private String chosen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chosen = "";
            CRUDTag cc = new CRUDTag();
            ObservableList<Tag> tags = cc.getTags();
           
            showAllPods(null);
             showTags(tags);
}

public void filterPods(int id, String target){
    System.out.println();
        if (chosen.equals(target)) {
            return;
        }
       
    podcastContainer.getChildren().clear();
    CRUDComments cr = new CRUDComments();
            ObservableList<Podcast> podcasts = cr.getPodcastsByTag(id);
            showPods(podcasts);
    
    chosen = target;
}
public void openPodcastComments(Podcast pod) {
    
    HomeScreenController.setIsCom(true);
      String resource = "";
      if(HomeScreenController.getCurrentUser() == null) {
          
      PodcastCommentsFrontController.setCurrentPodcast(pod);
          resource = "PodcastCommentsFront.fxml";
      } else {
          
      if(HomeScreenController.getCurrentUser().getIsAdmin()) {
      PodcastCommentsController.setCurrentPodcast(pod);
          resource = "PodcastComments.fxml";
      }else {
          
      PodcastCommentsFrontController.setCurrentPodcast(pod);
          resource = "PodcastCommentsFront.fxml";
      }
      
      }
      FXMLLoader fx = new FXMLLoader(getClass().getResource(resource));
        try {
            Pane p = fx.load();
            HomeScreenController.getInstance().getContainer().getChildren().clear();
            HomeScreenController.getInstance().getContainer().getChildren().add(p);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    
}    

    @FXML
    private void showAllPods(MouseEvent event) {
        if (chosen.equals("all")) {
            return;
        }
         podcastContainer.getChildren().clear();
           CRUDComments cr = new CRUDComments();
            ObservableList<Podcast> podcasts = cr.getPodcasts();
            showPods(podcasts);
            
            chosen = "all";
             
        
    }
    
    
    public void showPods(ObservableList<Podcast> podcasts) {
         podcastContainer.getChildren().clear();
           
             int i = 0;
             int j = 0;
            for(Podcast pod : podcasts) {
                
        try {   
            
            FXMLLoader fx = new FXMLLoader(getClass().getResource("podcastView.fxml"));
            Pane pn = fx.load();
            PodcastViewController controller = fx.getController();
            controller.setView(pod, true);
            pn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                     openPodcastComments(pod);
                } 
            });
            podcastContainer.add(pn, i, j);
            i++;
            if(i == 4) {
                i =0;
                j++;
            }
        } catch (IOException e ) {
            System.out.println("error 1: "+e.getMessage());
        }
    }
    }
    
    
    public void showTags(ObservableList<Tag> tags) {
        int i =1;
       int j=0;
     for(Tag tag : tags) {
        try {   
             FXMLLoader fx = new FXMLLoader(getClass().getResource("TagView.fxml"));
            Pane pn = fx.load();
            TagViewController controller = fx.getController();
            controller.setView(tag);
            pn.setId(tag.getName()+""+tag.getId());
             pn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                     filterPods(tag.getId(), pn.getId());
                } 
            });
            tagsContainer.add(pn, i, j);
            i++;
            if(i == 7) {
                i =0;
                j++;
            }
        } catch (Exception e ) {
            System.out.println("error 1: "+e.getMessage());
        }
     }
    }
    
}
