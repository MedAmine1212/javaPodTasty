/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Podcast;
import entities.PodcastReview;
import entities.User;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;
import services.CRUDReview;

/**
 * FXML Controller class
 *
 * @author khail
 */
public class PodcastReviewController implements Initializable {

    @FXML
    private Text questionOne;
    @FXML
    private Text questionTwo;
    @FXML
    private Text questionThree;
    @FXML
    private Text questionFour;
    private int currentQs;
    private float rate;
    private HashMap<Integer,Text> qsList= new HashMap();
    @FXML
    private GridPane rateContainer;
    @FXML
    private ImageView love;
    @FXML
    private ImageView like;
    @FXML
    private ImageView meh;
    @FXML
    private ImageView dislike;
    @FXML
    private ImageView hate;
    @FXML
    private ImageView loath;
    @FXML
    private Pane sendingPane;
    @FXML
    private Button closeButton;
    @FXML
    private AnchorPane reviewContainer;
    @FXML
    private Pane reviewSubmittedPane;
    @FXML
    private Text submittedReviewRating;
    @FXML
    private Button deleteReviewButton;
    private boolean reviewIsSubmitted;
    @FXML
    private Pane notSubmittedContainer;
    private String submittedRating = "";
   private PodcastReview review;
   private HomeScreenController homeScreen;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeScreen = HomeScreenController.getInstance();
        reviewIsSubmitted = PodcastCommentsFrontController.getReviewSubmitted();
        if(reviewIsSubmitted) {
            CRUDReview cr = new CRUDReview();
            review = cr.getReviewByUserAndPodcast(homeScreen.getCurrentUser(), PodcastCommentsFrontController.getCurrentPodcast());
        
        submittedRating = String.format("%.1f", review.getRating());
        }
        setView();
    }    
    private void setView() {
        if(reviewIsSubmitted) {
            submittedReviewRating.setText("Your review to this podcast is "+submittedRating+"/10");
            notSubmittedContainer.setVisible(false);
            sendingPane.setVisible(false);
            reviewSubmittedPane.setVisible(true);
        } else {
        reviewSubmittedPane.setVisible(false);
        notSubmittedContainer.setVisible(true);
        sendingPane.setVisible(true);
        qsList.put(1, questionOne);
        qsList.put(2, questionTwo);
        qsList.put(3, questionThree);
        qsList.put(4, questionFour);
        questionTwo.setVisible(false);
        questionThree.setVisible(false);
        questionFour.setVisible(false);
        sendingPane.setVisible(false);
        currentQs = 1;
        rate = 0;
        }
    }
    @FXML
    private void unfocusEmoji(MouseEvent event) throws IOException {
        
            setImage(((ImageView)event.getTarget()), 2);
    }

    @FXML
    private void focusEmoji(MouseEvent event) throws IOException {
            setImage(((ImageView)event.getTarget()), 1);
        
    }

    @FXML
    private void asnwerQuestion(MouseEvent event) {
        currentQs++;
        if (((ImageView)event.getTarget()).getId().equals("love")) {
            rate+= 10;
        }else if (((ImageView)event.getTarget()).getId().equals("like")) {
            rate+= 8;
        }else if (((ImageView)event.getTarget()).getId().equals("meh")) {
            rate+= 6;
        }else if (((ImageView)event.getTarget()).getId().equals("dislike")) {
            rate+= 4;
        }else if (((ImageView)event.getTarget()).getId().equals("hate")) {
            rate+= 2;
        }
        if(currentQs > 4) {
          
            new Thread(new Runnable() {
                
                @Override
                public void run() {
                     closeButton.setDisable(true);
                    questionFour.setVisible(false);
                    rateContainer.setVisible(false);
                    sendingPane.setVisible(true);
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PodcastReviewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Platform.runLater(new Runnable() {
                         @Override
                        public void run() {
                            sendRate();
                        }
                }
                );  
                 }
     }).start();
            
          
        } else {
            qsList.get(currentQs-1).setVisible(false);
            qsList.get(currentQs).setVisible(true);
        }
    
        
    }
   private void sendRate(){
       
       rate /=4;
       String strDouble = String.format("%.1f", rate);
       rate = Float.parseFloat(strDouble);
       qsList = null;
       PodcastReview review = new PodcastReview();
       review.setPodcastIdId(PodcastCommentsFrontController.getCurrentPodcast());
       review.setUserIdId(homeScreen.getCurrentUser());
       review.setRating(rate);
       CRUDReview cr = new CRUDReview(); 
       if(cr.addReview(review)) {
                
        PodcastCommentsFrontController.setReviewChanged(true);
                reviewIsSubmitted = true;
        PodcastCommentsFrontController.setReviewSubmitted(true);
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Success");
                al.setHeaderText("Review added successfully");
                al.showAndWait();
                closeThis(null);
       } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Error");
            al.setHeaderText("Couldn't add revuew");
            al.showAndWait();
       }
    }
    private void setImage(ImageView imageView, int sender) throws IOException {
    String src = "src/images/";
    if(sender == 1) {
        src +=imageView.getId()+"Hover";
    } else {
        src +=imageView.getId();
    }
    src+=".png";
    
    BufferedImage image;
    image = ImageIO.read(new File(src));
    WritableImage img = SwingFXUtils.toFXImage(image, null);
    imageView.setImage(img);
        
    }

    @FXML
    private void closeThis(MouseEvent event) {
        PodcastCommentsFrontController.getReviwStage().close();

    }

    @FXML
    private void deleteReviewAction(MouseEvent event) {
        CRUDReview cr = new CRUDReview();
        cr.deleteReview(review.getId());
        reviewIsSubmitted = false;
        PodcastCommentsFrontController.setReviewSubmitted(false);
        PodcastCommentsFrontController.setReviewChanged(true);
        setView();
    }
    
}
