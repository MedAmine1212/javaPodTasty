/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Podcast;
import entities.PodcastComment;
import entities.User;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.CRUDComments;

/**
 * FXML Controller class
 *
 * @author khail
 */
public class PodcastCommentsFrontController implements Initializable {

    @FXML
    private AnchorPane container;
    @FXML
    private Label commentsNumber;
    @FXML
    private Label comnumberLabel;
    @FXML
    private Button editCommentButton;
    @FXML
    private Button deleteCheckedComment;
    @FXML
    private TableView<PodcastComment> commentsContainer;
    @FXML
    private TableColumn<PodcastComment, String> commentUser;
    @FXML
    private TableColumn<PodcastComment, String> commentText;
    @FXML
    private TableColumn<PodcastComment, Date> commentDate;
    private int clickedCommentId = -1;
    private Podcast p;
    @FXML
    private Button addCommentButton;
    @FXML
    private TextField commentTextInput;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showComments();
        deleteCheckedComment.setVisible(false);
        editCommentButton.setVisible(false);
        addCommentButton.setDisable(true);
    }    

    @FXML
    private void editCommentClick(MouseEvent event) {
    }

   public void showComments() {
       
        CRUDComments cr = new CRUDComments();
        p = new Podcast();
        p.setId(1);
        p.setCommentsAllowed(0);
        if(p.getCommentsAllowed() == 0) {
        } else {

        }
        ObservableList<PodcastComment> comList = cr.getCommentsByPodcast(p);
        if(comList.size() > 0) {
        commentsNumber.setText(comList.size()+"");
        if(comList.size() > 1) {
            comnumberLabel.setText("Comments");        
            commentsNumber.setText("");

        } else {
            
            comnumberLabel.setText("Comment");
        }
        }else {
            comnumberLabel.setText("No comments on this podcast");
        }
        commentUser.setCellValueFactory(new PropertyValueFactory<PodcastComment, String>("userName") );
        commentText.setCellValueFactory(new PropertyValueFactory<PodcastComment, String>("commentText") );
        commentDate.setCellValueFactory(new PropertyValueFactory<PodcastComment, Date>("commentDate") );
        FXCollections.reverse(comList);
        commentsContainer.setItems(comList);
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
                showComments();
            } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle("Error");
            al.setHeaderText("Couldn't delete comment");
            al.showAndWait();
            }

            }
    }

    @FXML
    private void tblViewCurrentStoreOnClick(MouseEvent event) {
        if (commentsContainer.getSelectionModel().getSelectedItem() != null) {
         PodcastComment com =commentsContainer.getSelectionModel().getSelectedItem();
         clickedCommentId = com.getId();
         deleteCheckedComment.setDisable(false);
        }
    }

    @FXML
    private void addCommentAction(MouseEvent event) {
        PodcastComment com = new PodcastComment();
        Podcast pod = new Podcast();
        pod.setId(1);
        com.setPodcastIdId(pod);
        com.setCommentText(commentTextInput.getText());
        User u = new User();
        u.setId(1);
        com.setUserIdId(u);
        commentTextInput.setText("");
        addCommentButton.setDisable(true);
        CRUDComments cr = new CRUDComments();
        cr.addComment(com);
        showComments();
        
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
    
}
