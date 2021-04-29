/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Podcast;
import entities.PodcastComment;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.CRUDPodcast;

import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author khail
 */
public class PodcastViewController implements Initializable {

    public javafx.scene.control.Button commentairesboutton;
    @FXML
    private ImageView podcastImg;
    @FXML
    private Label podcastName;
    @FXML
    private Label podcastDesc;
    @FXML
    private Label podcastViews;
    @FXML private Label nbviews;
    @FXML public Button comm;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    entities.Podcast podcast;
     public void setView(Podcast pod){
         podcast=pod;
         if (pod.getIsBlocked()==1)
         {

                     this.commentairesboutton.setDisable(true);
             this.commentairesboutton.setText("Unavailable");
         }
        podcastName.setText(pod.getPodcastName());
         System.out.println("current description : "+pod.getPodcastDescription());
        podcastViews.setText(pod.getPodcastDate().toString());
        podcastDesc.setText(pod.getPodcastDescription().toString());
         System.out.println("NUMBER OF VIEWS  : "+pod.getPodcastViews());
         nbviews.setText(pod.getPodcastViews()+" Views");
        BufferedImage image;
        try {
            image = ImageIO.read(new URL("http://127.0.0.1:8000/getResources?file="+pod.getPodcastImage()));
            WritableImage img = SwingFXUtils.toFXImage(image, null);
            podcastImg.setImage(img);
        } catch (Exception ex) {
            Logger.getLogger(PodcastCommentsFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void seecomments(MouseEvent mouseEvent) throws IOException {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PodcastCommentsFront.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PodcastCommentsFront.fxml"));
        Scene scene = new Scene(loader.load());

        PodcastCommentsFrontController controller =  loader.getController();
        controller.initData(podcast);
        stage.setScene(scene);
        stage.show();

    }
}
