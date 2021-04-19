/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Podcast;
import entities.PodcastComment;
import entities.User;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.CRUDComments;
import services.CRUDFavorite;

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
    private static Stage reviwStage;
    @FXML
    private TextField searchInput;
    
    private User u;
    private Podcast p;
    private int clickedCommentId = -1;
    private boolean filtered = false;
    private boolean isFavorite = false;
    @FXML
    private Button addFavoriteButt;
    @FXML
    private Button removeFavoriteButt;
    @FXML
    private GridPane commentsContainer;
    
    LoadAudio audioLoader;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        u = new User();
        u.setId(1);
        searchInput.setPromptText("Search comments...");
        if(isFavorite) {
            addFavoriteButt.setVisible(false);
        } else {
            removeFavoriteButt.setVisible(false);
        }
        CRUDComments cr = new CRUDComments();
        p = new Podcast();
        p.setId(1);
        p.setCommentsAllowed(0);
        
        audioLoader = LoadAudio.getInstance();
        audioLoader.start();
        
//        if(p.getCommentsAllowed() == 0) {
//        } else {
//
//        }
        ObservableList<PodcastComment> comList = cr.getCommentsByPodcast(p);
            
        showComments(comList ,1, null);
        deleteCheckedComment.setVisible(false);
        editCommentButton.setVisible(false);
        addCommentButton.setDisable(true);
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
                clickedCommentId = -1;
                deleteCheckedComment.setVisible(false);
                editCommentButton.setVisible(false);
                ObservableList<PodcastComment> comList = cr.getCommentsByPodcast(p);
                showComments(comList, 1, null);
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
                ObservableList<PodcastComment> comList = cr.getCommentsByPodcast(p);
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
    

    private void tblViewCurrentStoreOnClick(MouseEvent event) {
//        if (commentsContainer.getSelectionModel().getSelectedItem() != null) {
//         PodcastComment com =commentsContainer.getSelectionModel().getSelectedItem();
//         clickedCommentId = com.getId();
//         deleteCheckedComment.setVisible(true);
//        editCommentButton.setVisible(true);
//        }
    }

    @FXML
    private void addCommentAction(MouseEvent event) {
        searchInput.setText("");
        PodcastComment com = new PodcastComment();
        Podcast pod = new Podcast();
        pod.setId(1);
        com.setPodcastIdId(pod);
        com.setCommentText(commentTextInput.getText());
        com.setUserIdId(u);
        commentTextInput.setText("");
        addCommentButton.setDisable(true);
        CRUDComments cr = new CRUDComments();
        cr.addComment(com);
        ObservableList<PodcastComment> comList = cr.getCommentsByPodcast(p);
        showComments(comList, 1, null);
        searchInput.setText("");
        
    }

    @FXML
    private void deactivateButton(KeyEvent event) {
        if(commentTextInput.getText().length() <3) {
            addCommentButton.setDisable(true);

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
            reviwStage = new Stage();
            reviwStage.setTitle("Podcast review");
            reviwStage.setScene(new Scene(root));
            reviwStage.initModality(Modality.WINDOW_MODAL);
            reviwStage.initOwner(((Node)(event.getSource())).getScene().getWindow());
            reviwStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     public static Stage getReviwStage() {
        return reviwStage;
    }
     
    @FXML
    private void filerComments(KeyEvent event) {
        if ( searchInput.getText().length() == 0) {
            if (filtered) {
                CRUDComments cr = new CRUDComments();
                ObservableList<PodcastComment> comList = cr.getCommentsByPodcast(p);
                 showComments(comList, 1, null);
            }
        } else {
            filtered = true;
            CRUDComments rc = new CRUDComments();
            ObservableList<PodcastComment> fileredComments = rc.getCommentsByComText(p,searchInput.getText());
            showComments(fileredComments ,2, searchInput.getText());
        }
     }
    @FXML
    private void addFavoriteAction(MouseEvent event) {
        CRUDFavorite cr = new CRUDFavorite();
        cr.addFavorite(p, u);
        isFavorite = true;
        removeFavoriteButt.setVisible(true);
        addFavoriteButt.setVisible(false);
        
    }
    @FXML
    private void removeFavoriteAction(MouseEvent event) {
        CRUDFavorite cr = new CRUDFavorite();
        cr.removeFavorite(p, u);
        isFavorite = false;
        removeFavoriteButt.setVisible(false);
        addFavoriteButt.setVisible(true);
    }

    @FXML
    private void playAudioClick(MouseEvent event) {
        audioLoader.startAudio();
        
    }

    @FXML
    private void stopAudioClick(MouseEvent event) throws InterruptedException {
        audioLoader.pauseAudio();
    }
         
}
