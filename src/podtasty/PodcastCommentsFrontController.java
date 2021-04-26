/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;
import com.google.zxing.WriterException;
import entities.BadWords;
import entities.GenerateQRCode;
import entities.Podcast;
import entities.PodcastComment;
import entities.PodcastReview;
import entities.User;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.controlsfx.control.Notifications;
import services.CRUDComments;
import services.CRUDFavorite;
import services.CRUDReview;

/**
 * FXML Controller class
 *
 * @author khail
 */
public class PodcastCommentsFrontController implements Initializable {
    @FXML
    private AnchorPane container;
    @FXML
    private Label comnumberLabel;
    @FXML
    private Button editCommentButton;
    @FXML
    private Button deleteCheckedComment;
    @FXML
    private Button addCommentButton;
    @FXML
    private TextField commentTextInput;
    private static Stage reviewStage;
    @FXML
    private TextField searchInput;
    private static boolean reviewChanged = false;
    private static User currentUser;
    private static Podcast currentPodcast;
    private int clickedCommentId = -1;
    private boolean filtered = false;
    private boolean ratingSaved = false;
    private static boolean reviewSubmitted;
    @FXML
    private ImageView addFavoriteButt;
    @FXML
    private ImageView removeFavoriteButt;
    @FXML
    private GridPane commentsContainer;
    private Thread loadingThread;
    LoadAudio audioLoader;
    @FXML
    private ImageView rate;
    @FXML
    private ImageView report;
    @FXML
    private Label podcastRating;
    @FXML
    private Label userRating;
    
    private Pane selectedCom;
    @FXML
    private ScrollPane commentsScroll;
    @FXML
    private Pane playerContainer;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;
    @FXML
    private GridPane playlistContainer;
    @FXML
    private ScrollPane playlistScroll;
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
    private Label playlistLabel;
    @FXML
    private BorderPane loading;
    
    private ObservableList<Podcast> playlist;
    @FXML
    private Label commentsDiabled;
    @FXML
    private ImageView qrCodeContainer;
    @FXML
    private Button commentDetails;
    @FXML
    private Label podcastDesc1;
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
                
        currentUser = new User();
        currentUser.setId(1);
        searchInput.setPromptText("Search comments...");
        currentPodcast = new Podcast();
        currentPodcast.setId(1);
        currentPodcast.setCommentsAllowed(1);
        currentPodcast.setPodcastDescription("Description Description Description Description ");
        currentPodcast.setPodcastName("Podcast 1");
        currentPodcast.setPodcastViews(120);
        currentPodcast.setPodcastImage("1.jpeg");
        currentPodcast.setPodcastSource("6074b2a184d44.mp3");
        CRUDComments cr = new CRUDComments();
        playlist = cr.getPodcastByPlaylist(1, currentPodcast.getId());
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
       
       
        BufferedImage qrCode = GenerateQRCode.createQRImage(currentPodcast.getId().toString(), 125);
        WritableImage qrCodeImg = SwingFXUtils.toFXImage(qrCode, null);
        qrCodeContainer.setImage(qrCodeImg);
        reviewSubmitted = false;
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
       CRUDFavorite cf = new CRUDFavorite();
       if(cf.getFavoriteByPodcastAnduser(currentPodcast, currentUser)) {
            addFavoriteButt.setVisible(false);
            removeFavoriteButt.setVisible(true);
        } else {
            removeFavoriteButt.setVisible(false);
            addFavoriteButt.setVisible(true);
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
            
            reviewList.stream().filter(rv -> (Objects.equals(rv.getUserIdId().getId(), currentUser.getId()))).map(rv -> {
                ratingSaved = true;
                return rv;
            }).forEachOrdered(rv -> {
                try {
                    reviewSubmitted = true;
                    String strDouble = String.format("%.1f", rv.getRating());
                    userRating.setText(strDouble);
                    setImage(rate,  1);
                } catch (IOException ex) {
                    Logger.getLogger(PodcastCommentsFrontController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            rating/=reviewList.size();
            String strDouble = String.format("%.1f", rating);
            podcastRating.setText("Rating: "+strDouble+"/10");
        } else {
            ratingSaved = false;
            userRating.setText("");
            setImage(rate,  2);
            podcastRating.setText("");
        }
        
        if(currentPodcast.getCommentsAllowed() == 0) {
            this.commentTextInput.setVisible(false);
            this.addCommentButton.setVisible(false);
            commentsDiabled.setVisible(true);
        } else {
            this.commentTextInput.setVisible(true);
            this.addCommentButton.setVisible(true);
            commentsDiabled.setVisible(false);
        }
        ObservableList<PodcastComment> comList = cr.getCommentsByPodcast(currentPodcast);
            
        showComments(comList ,1, null);
        deleteCheckedComment.setVisible(false);
        editCommentButton.setVisible(false);
        commentDetails.setVisible(false);
        addCommentButton.setDisable(true);
       if(!playlist.isEmpty()) {
           playlistLabel.setText("Other podcasts you might like");
            showPlaylist(playlist);
       }
        loading.setVisible(false);
   } 
   
   public void showPlaylist(ObservableList<Podcast> playlist) {
        playlistContainer.getChildren().clear();
        int i = 3;
        for(Podcast pod : playlist) {
        try {   
            
            FXMLLoader fx = new FXMLLoader(getClass().getResource("podcastView.fxml"));
            Pane pn = fx.load();
            PodcastViewController controller = fx.getController();
            controller.setView(pod);
            pn.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                     gotToNext(pod);
                } 
            });
            playlistContainer.add(pn, 0, i);
            i++;
        } catch (IOException e ) {
            System.out.println("error 1: "+e.getMessage());
        }
    }
   }
   public void gotToNext(Podcast pod) {
             try {
                 audioLoader.stopAudio();
             } catch (IOException ex) {
                 System.out.println(ex.getMessage());
             }
             LoadAudio.destroyInstance();
                
                playlist.add(PodcastCommentsFrontController.currentPodcast);
                playlist.remove(pod);
                CRUDComments cr = new CRUDComments();
                for(Podcast p: playlist) {
                    p.setCommentsAllowed(cr.getCommentsAllowedForPod(p));
                }
                PodcastCommentsFrontController.currentPodcast = pod;
                
                loading.setVisible(true);
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                     loading.setVisible(true);
                     Platform.runLater(new Runnable() {

                         @Override
                         public void run() {
                             try {
                                     setUpView();
                                     } catch (IOException ex) {
                                     Logger.getLogger(PodcastCommentsFrontController.class.getName()).log(Level.SEVERE, null, ex);
                                 } catch (WriterException ex) {
                                 Logger.getLogger(PodcastCommentsFrontController.class.getName()).log(Level.SEVERE, null, ex);
                             }
                                 }
                                 });  
                             }
    }).start();

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
                    deleteCheckedComment.setVisible(false);
                    editCommentButton.setVisible(false);
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
                    if(Objects.equals(com.getUserIdId().getId(), currentUser.getId())) {
                        deleteCheckedComment.setVisible(true);
                        editCommentButton.setVisible(true);
                    }
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
                editCommentButton.setVisible(false);
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
    private void editCommentClick(MouseEvent event) {
        
       Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Update comment");

    ButtonType validateUpdate = new ButtonType("Update", ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(validateUpdate, ButtonType.CANCEL);

            GridPane gridPane = new GridPane();
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setPadding(new Insets(20, 150, 10, 10));

    TextField from = new TextField();
    from.setPromptText("Comment Text");

    gridPane.add(from, 0, 0);

    dialog.getDialogPane().setContent(gridPane);

    Platform.runLater(() -> from.requestFocus());

    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == validateUpdate) {
            return from.getText();
        }
        return null;
    });

    Optional<String> commentText = dialog.showAndWait();

    commentText.ifPresent(cmntText -> {
            CRUDComments cr = new CRUDComments();
            if(cr.updateComment(cmntText,clickedCommentId)) {
                clickedCommentId = -1;
                deleteCheckedComment.setVisible(false);
                editCommentButton.setVisible(false);
                commentDetails.setVisible(false);
                ObservableList<PodcastComment> comList = cr.getCommentsByPodcast(currentPodcast);
                showComments(comList , 1, null);
                searchInput.setText("");
                
            } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Error");
            al.setHeaderText("Couldn't update comment");
            al.showAndWait();
            }
    }
    );
    }
    @FXML
    private void addCommentAction(MouseEvent event) {
        
            CRUDComments cr = new CRUDComments();
        if (cr.getCommentsAllowedForPod(currentPodcast) == 1) {
            if (BadWords.filterText(commentTextInput.getText())) {
            commentTextInput.setText("");
            addCommentButton.setDisable(true);
            
        Image img = new Image("/images/commentBlocked.png", true);
            Notifications notificationBuilder = Notifications.create()
               .title("Comment").text("Hey watch your language !").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
               .graphic(new ImageView(img))
               .position(Pos.BOTTOM_RIGHT);
       notificationBuilder.darkStyle();
       notificationBuilder.show();
            
            } else {
            PodcastComment com = new PodcastComment();
            com.setPodcastIdId(currentPodcast);
            com.setUserIdId(currentUser);
            com.setCommentText(commentTextInput.getText());
            commentTextInput.setText("");
            addCommentButton.setDisable(true);
            cr.addComment(com);
            ObservableList<PodcastComment> comList = cr.getCommentsByPodcast(currentPodcast);
            showComments(comList, 1, null);
            if (selectedCom != null) {
                selectedCom.opacityProperty().set(1);
                selectedCom.setStyle("");
                selectedCom = null;
            }
            
            }
            } else {
            
            commentTextInput.setText("");
            addCommentButton.setDisable(true);
            Notifications notificationBuilder = Notifications.create()
               .title("Comment").text("Comments are currently diabled for this podcast !").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
               .position(Pos.BOTTOM_RIGHT);
       notificationBuilder.darkStyle();
       notificationBuilder.show();
        }
        searchInput.setText("");
        
    }

    @FXML
    private void deactivateButton(KeyEvent event) {
        
        if(event.getCode().toString().equals("ENTER") && !addCommentButton.isDisabled()){
            addCommentButton.setDisable(true);
            addCommentAction(null);
        } else {
            
            if(commentTextInput.getText().length() <3) {
                addCommentButton.setDisable(true);
            } 
        }
    }

    @FXML
    private void activateButton(KeyEvent event) {
        
            if(addCommentButton.isDisabled() && commentTextInput.getText().length() >= 2) {
                addCommentButton.setDisable(false);
            }
    }

    @FXML
    private void openRateView(MouseEvent event) {
        Parent root;
        try {            
            root = FXMLLoader.load(getClass().getResource("PodcastReview.fxml"));
            reviewStage = new Stage();
            reviewStage.setTitle("Podcast review");
            reviewStage.setScene(new Scene(root));
            reviewStage.initModality(Modality.WINDOW_MODAL);
            reviewStage.initOwner(((Node)(event.getSource())).getScene().getWindow());
            reviewStage.show();
            try {
            reviewStage.setOnHiding(ev -> {
                if (reviewChanged) {
                    CRUDReview cr = new CRUDReview();
                    PodcastReview rv = cr.getReviewByUserAndPodcast(currentUser, currentPodcast);
                    if (rv != null) {
                        ratingSaved = true;
                        reviewSubmitted = true;
                        String strDouble = String.format("%.1f", rv.getRating());
                        userRating.setText(strDouble);
                        try {
                            setImage(rate,  1);
                        } catch (IOException ex) {
                            Logger.getLogger(PodcastCommentsFrontController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        ratingSaved = false;
                        reviewSubmitted = false;
                        userRating.setText("");
                        try {
                            setImage(rate,  2);
                        } catch (IOException ex) {
                            Logger.getLogger(PodcastCommentsFrontController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            }catch(Exception e) {}
           
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     public static Stage getReviwStage() {
        return reviewStage;
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
    private void addFavoriteAction(MouseEvent event) {
        CRUDFavorite cr = new CRUDFavorite();
        cr.addFavorite(currentPodcast, currentUser);
        removeFavoriteButt.setVisible(true);
        addFavoriteButt.setVisible(false);
        Image img = new Image("/images/favAdded.png", true);
         Notifications notificationBuilder = Notifications.create()
               .title("Favorites").text("Podcast saved to favotites").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                .graphic(new ImageView(img))
               .position(Pos.BOTTOM_RIGHT);
       notificationBuilder.darkStyle();
       notificationBuilder.show();
        
    }
    @FXML
    private void removeFavoriteAction(MouseEvent event) {
        CRUDFavorite cr = new CRUDFavorite();
        cr.removeFavorite(currentPodcast, currentUser);
        removeFavoriteButt.setVisible(false);
        addFavoriteButt.setVisible(true);
        Image img = new Image("/images/favRemoved.png", true);
        Notifications notificationBuilder = Notifications.create()
               .title("Favorites").text("Podcast removed from favotites").graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                .graphic(new ImageView(img))
               .position(Pos.BOTTOM_RIGHT);
       notificationBuilder.darkStyle();
       notificationBuilder.show();
    }

    @FXML
    private void unhoverButton(MouseEvent event) throws IOException {
    int sender = 2;
    if (((ImageView)event.getTarget()).getId().equals("rate") && ratingSaved) {
        sender = 1;
    }
    setImage(((ImageView)event.getTarget()), sender);

    }

    @FXML
    private void hoverButton(MouseEvent event) throws IOException {
        int sender = 1;
    if (((ImageView)event.getTarget()).getId().equals("rate") && ratingSaved) {
        sender = 2;
    }
            setImage(((ImageView)event.getTarget()), sender);

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

    @FXML
    private void nextPodcast(MouseEvent event) {
        playing = false;
        gotToNext(playlist.get(0));
    }
    
    public static boolean getReviewSubmitted() {
        return reviewSubmitted;
    }
    
     public static void setReviewSubmitted(boolean status) {
        reviewSubmitted = status;
    }
     
    
     public static void setReviewChanged(boolean status) {
        reviewChanged = status;
    }
     
     
     
     public static User getCurrentUser() {
         return currentUser;
     }
     
     public static Podcast getCurrentPodcast() {
         return currentPodcast;
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
}
