/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Podcast;
import entities.PodcastComment;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author khail
 */
public class PodcastViewController implements Initializable {

    @FXML
    private ImageView podcastImg;
    @FXML
    private Label podcastName;
    @FXML
    private Label podcastDesc;
    @FXML
    private Label podcastViews;
    @FXML
    private Pane textContainer;
    @FXML
    private Pane podContainer;
    @FXML
    private BorderPane podBorder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
     public void setView(Podcast pod, boolean big){
        podcastName.setText(pod.getPodcastName());
        podcastViews.setText(pod.getPodcastViews()+" Views");
        podcastDesc.setText(pod.getPodcastDescription());
        BufferedImage image;
        try {
            image = ImageIO.read(new URL("http://127.0.0.1:8000/Files/podcastFiles/"+pod.getPodcastImage()));
            WritableImage img = SwingFXUtils.toFXImage(image, null);
            podcastImg.setImage(img);
            if (big) {
                podcastImg.setFitWidth(220);
                podcastImg.setFitHeight(100);
            }
        } catch (Exception ex) {
            Logger.getLogger(PodcastCommentsFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
