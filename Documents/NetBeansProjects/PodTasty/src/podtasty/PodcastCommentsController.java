/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;
import com.google.zxing.WriterException;
import entities.BadWords;
import entities.Podcast;
import entities.PodcastComment;
import entities.PodcastReview;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.controlsfx.control.Notifications;
import services.CRUDComments;
import services.CRUDReview;

/**
 * FXML Controller class
 *
 * @author khail
 */
public class PodcastCommentsController implements Initializable {
    @FXML
    private AnchorPane container;
    @FXML
    private Label comnumberLabel;
    private Button editCommentButton;
    @FXML
    private Button deleteCheckedComment;
    @FXML
    private TextField searchInput;
    private static Podcast currentPodcast;
    private int clickedCommentId = -1;
    private boolean filtered = false;
    @FXML
    private GridPane commentsContainer;
    private Thread loadingThread;
    LoadAudio audioLoader;
    @FXML
    private Label podcastRating;
    
    private Pane selectedCom;
    @FXML
    private ScrollPane commentsScroll;
    @FXML
    private Pane playerContainer;
    @FXML
    private Button prevButton;
   
    @FXML
    private Button stopPlayButton;
    
    private boolean playing = false;
    @FXML
    private ImageView palyStopImg;
    @FXML
    private ImageView podcastImage;
    @FXML
    private Label podcastName;
    @FXML
    private Label podcastViews;
    @FXML
    private Label podcastDesc;
    @FXML
    private BorderPane loading;
    
    @FXML
    private Button commentDetails;
    @FXML
    private Label podcastDesc1;
    @FXML
    private Button activateButton;
    @FXML
    private Button deactivateButton;
    @FXML
    private ImageView qrCodeContainer;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
        loading.setVisible(true);
        new Thread(new Runnable() {

    @Override
    public void run() {
        
        loading.setVisible(true);
        Platform.runLater(new Runnable() {

            @Override
            public void run() { 
                
        searchInput.setPromptText("Search comments...");
        try {

               setUpView();
           } catch (IOException ex) {
               Logger.getLogger(PodcastCommentsFrontController.class.getName()).log(Level.SEVERE, null, ex);
           
       }        catch (WriterException ex) {
                    Logger.getLogger(PodcastCommentsFrontController.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        BadWords.loadConfigs();
        }
        });  
    }
        }).start();
       
    }  
    
    
    
   public void setUpView() throws IOException, WriterException {
      
        audioLoader = LoadAudio.getInstance();
        audioLoader.setAudioUrl(currentPodcast.getPodcastSource());
        audioLoader.start();
        BufferedImage imgg;
        imgg = ImageIO.read(new File("src/images/play.png"));
        WritableImage im = SwingFXUtils.toFXImage(imgg, null);
        palyStopImg.setImage(im);
       this.podcastDesc.setText(currentPodcast.getPodcastDescription());
       this.podcastName.setText(currentPodcast.getPodcastName());
       this.podcastViews.setText(currentPodcast.getPodcastViews()+" Views");
     
        if(currentPodcast.getCommentsAllowed() == 1) {
            activateButton.setVisible(false);
            deactivateButton.setVisible(true);
        } else {
            
            activateButton.setVisible(true);
            deactivateButton.setVisible(false);
        }
       
        CRUDComments cr = new CRUDComments();
        CRUDReview crr = new CRUDReview();
        BufferedImage image;
        try {
            image = ImageIO.read(new URL("http://127.0.0.1:8000/Files/podcastFiles/"+currentPodcast.getPodcastImage()));
            WritableImage img = SwingFXUtils.toFXImage(image, null);
            podcastImage.setImage(img);
        } catch (Exception ex) {
            Logger.getLogger(PodcastCommentsFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
         ObservableList<PodcastReview> reviewList = crr.getReviewsByPodcast(currentPodcast);
        if(!reviewList.isEmpty()) {
            float rating = 0;
            rating = reviewList.stream().map(rv -> rv.getRating()).reduce(rating, (accumulator, _item) -> accumulator + _item);
            rating/=reviewList.size();
            String strDouble = String.format("%.1f", rating);
            podcastRating.setText("Rating: "+strDouble+"/10");
        } else {
            podcastRating.setText("");
        }
      
        ObservableList<PodcastComment> comList = cr.getCommentsByPodcast(currentPodcast);
            
        showComments(comList ,1, null);
        deleteCheckedComment.setVisible(false);
        commentDetails.setVisible(false);
      
        loading.setVisible(false);
   } 
   

   public void showComments(ObservableList<PodcastComment> comList, int caller, String text) {
       commentsContainer.getChildren().clear();
       if (caller == 1) {
        if(comList.size() > 0) {
            
        if(comList.size() > 1) {
            comnumberLabel.setText(comList.size()+" Comments");   

        } else {
            
            comnumberLabel.setText("1 Comment");
        }
        }else {
            comnumberLabel.setText("No comments on this podcast");
        }
       } else {
           comnumberLabel.setText("Searching \" "+text+" \" in comments");
       }
        if(!comList.isEmpty()) {
        FXCollections.reverse(comList);
        int i = 3;
        for(PodcastComment com : comList) {
        try {   
            
            FXMLLoader fx = new FXMLLoader(getClass().getResource("commentView.fxml"));
            Pane p = fx.load();
            CommentViewController controller = fx.getController();
            controller.setView(com);
            p.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    
                    commentDetails.setVisible(false);
                    if (selectedCom != null) {
                        selectedCom.opacityProperty().set(1);
                        selectedCom.setStyle("");
                    }
                    if (selectedCom == p) {
                    
                    clickedCommentId = -1;
                    selectedCom = null;
                    } else { 
                    selectedCom = p;
                    p.opacityProperty().set(0.6);
                    p.setStyle("-fx-border-color:  white");
                    clickedCommentId = com.getId();
                    commentDetails.setVisible(true); 
                    deleteCheckedComment.setVisible(true);
                   
                    }
                } 
            });
            commentsContainer.add(p, 0, i);
            i++;
        } catch (IOException e ) {
            System.out.println("error 1: "+e.getMessage());
        }
            
        }
        
    }
   }
   
    @FXML
    private void deleteCommentClick(MouseEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete this comment ?");
           Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
            CRUDComments cr = new CRUDComments();
            if(cr.deleteComment(clickedCommentId)) {
                Image img = new Image("/images/commentDeleted.png", true);
                Notifications notificationBuilder = Notifications.create()
                   .title("Comment").text("Comment deleted successfully").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                   .graphic(new ImageView(img))
                   .position(Pos.BOTTOM_RIGHT);
           notificationBuilder.darkStyle();
           notificationBuilder.show();
                clickedCommentId = -1;
                deleteCheckedComment.setVisible(false);
                commentDetails.setVisible(false);
                ObservableList<PodcastComment> comList = cr.getCommentsByPodcast(currentPodcast);
                showComments(comList, 1, null);
                 if (selectedCom != null) {
                    selectedCom.opacityProperty().set(1);
                    selectedCom.setStyle("");
                    selectedCom = null;
                }
                searchInput.setText("");
            } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Error");
            al.setHeaderText("Couldn't delete comment");
            al.showAndWait();
            }

            }
    }
    
   

    @FXML
    private void filerComments(KeyEvent event) {
        
        if ( searchInput.getText().length() == 0) {
            if (filtered) {
                CRUDComments cr = new CRUDComments();
                ObservableList<PodcastComment> comList = cr.getCommentsByPodcast(currentPodcast);
                 showComments(comList, 1, null);
            }
        } else {
            filtered = true;
            CRUDComments rc = new CRUDComments();
            ObservableList<PodcastComment> fileredComments = rc.getCommentsByComText(currentPodcast,searchInput.getText());
            showComments(fileredComments ,2, searchInput.getText());
        }
     }
  

    @FXML
    private void stopPlayAudio(MouseEvent event) throws IOException {
       String src = "";
        if (playing) {
            audioLoader.pauseAudio();
            playing = false;
            src = "src/images/play.png";
        } else {
            audioLoader.startAudio();
            playing = true;
            src = "src/images/pause.png";
        }
            BufferedImage image;
            image = ImageIO.read(new File(src));
            WritableImage img = SwingFXUtils.toFXImage(image, null);
            palyStopImg.setImage(img);
    }

    @FXML
    private void repeatAudio(MouseEvent event) throws IOException {
        audioLoader.repeat();
        
            playing = true;
            BufferedImage image;
            image = ImageIO.read(new File("src/images/pause.png"));
            WritableImage img = SwingFXUtils.toFXImage(image, null);
            palyStopImg.setImage(img);
            
    }

     
     public static Podcast getCurrentPodcast() {
         return currentPodcast;
     }   

     public static void setCurrentPodcast(Podcast pod) {
         currentPodcast = pod;
     } 
     
    @FXML
    private void showCommentDetails(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Comment info");
            CRUDComments cr = new CRUDComments();
            PodcastComment com = cr.getCommentById(clickedCommentId);
            
            DateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");   
            String date = dateFormat.format(com.getCommentDate());
            dateFormat = new SimpleDateFormat("hh:mm");   
            String time = dateFormat.format(com.getCommentDate());
            alert.setHeaderText("Owner: "+com.getUserIdId().getUserInfoIdId().getUserFirstName()+" "+com.getUserIdId().getUserInfoIdId().getUserLastName()+"\nDate: "+date+"\nTime: "+time);
            alert.showAndWait();
        
    }

    @FXML
    private void ActivateCommenting(MouseEvent event) {
        
        CRUDComments cr = new CRUDComments();
       
       if(cr.changeCommentingStatus(currentPodcast.getId(), 1)) {
            activateButton.setVisible(false);
            deactivateButton.setVisible(true);
       }
        
    }

    @FXML
    private void deactivateCommenting(MouseEvent event) {
        CRUDComments cr = new CRUDComments();
        
       if(cr.changeCommentingStatus(currentPodcast.getId(), 0)) {
            activateButton.setVisible(true);
            deactivateButton.setVisible(false);
       }
    }
}
