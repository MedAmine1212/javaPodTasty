/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entities.Podcast;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.Grid;
import services.CRUDPodcast;

/**
 *
 * @author MedAmine
 */
public class PodTasty extends Application implements Initializable {
    @FXML
    GridPane podcastlist ;
    @Override
    public void stop() {
        System.out.println("destroying");
        LoadAudio ld = LoadAudio.getInstance();
        try {
            ld.stopAudio();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        LoadAudio.destroyInstance();
    }
    @Override
    public void start(Stage stage) throws Exception {
            
          
//        Parent root = FXMLLoader.load(getClass().getResource("PodcastComments.fxml"));
        
//        Parent root = FXMLLoader.load(getClass().getResource("TagsFxmlDocument.fxml"));


        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        Scene scene = new Scene(loader.load());

        Home controller =  loader.getController();
        CRUDPodcast crudPodcast=new CRUDPodcast();
        ArrayList<entities.Podcast> podcasts=(ArrayList<entities.Podcast>)crudPodcast.getAll();
        ObservableList<Podcast> observableList = FXCollections.observableArrayList(podcasts);
        controller.showPlaylist(observableList);
        stage.setScene(scene);
        stage.show();
        
        


    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


    public void showPlaylist(ObservableList<entities.Podcast> podcasts) {
        //podcastlist.getChildren().clear();

        podcastlist.setAlignment( Pos.BASELINE_CENTER);
        System.out.println(podcasts.size());
        int i = 3;
        for(Podcast pod : podcasts) {
            System.out.println("ADDING PODCAST");
            try {

                FXMLLoader fx = new FXMLLoader(getClass().getResource("podcastView.fxml"));
                Pane pn = fx.load();
                PodcastViewController controller = fx.getController();
                controller.setView(pod);
                pn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        // TODO
                    }
                });
                podcastlist.add(pn, 0, i);
                i++;
            } catch (IOException e ) {
                System.out.println("error 1: "+e.getMessage());
            }
        }
    }
    @FXML
    private void addPodcastAction(MouseEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Podcast.fxml"));

            Stage reviewStage = new Stage();
            reviewStage.setTitle("Add Podcast");
            reviewStage.setScene(new Scene(root));
            reviewStage.initModality(Modality.WINDOW_MODAL);
            reviewStage.initOwner(((Node) (event.getSource())).getScene().getWindow());
            reviewStage.show();
        /*Label secondLabel = new Label("I'm a Label on new Window");

        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);

        Scene secondScene = new Scene(secondaryLayout, 230, 100);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.


        newWindow.show();*/
            new PodcastReport().start(reviewStage);
        } catch (Exception e )
        {
            System.out.printf(e.getMessage());
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        podcastlist  = new GridPane();
        podcastlist.setHgap(10);
        podcastlist.setVgap(10);
        podcastlist.setStyle("-fx-background-color: red");
        CRUDPodcast crudPodcast=new CRUDPodcast();
        ArrayList<entities.Podcast> podcasts=(ArrayList<entities.Podcast>)crudPodcast.getAll();
        ObservableList<Podcast> observableList = FXCollections.observableArrayList(podcasts);

        showPlaylist(observableList);
    }
}
