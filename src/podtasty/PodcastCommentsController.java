/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Podcast;
import entities.PodcastComment;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import services.CRUDComments;

/**
 * FXML Controller class
 *
 * @author khail
 */
public class PodcastCommentsController implements Initializable {

    @FXML
    private TableView<PodcastComment> commentsContainer;
    @FXML
    private TableColumn<PodcastComment, String> commentUser;
    @FXML
    private TableColumn<PodcastComment, String> commentText;
    @FXML
    private TableColumn<PodcastComment, Date> commentDate;
    private Podcast p;
    @FXML
    private ToggleButton commentingStatusButton;
    @FXML
    private Button deleteCheckedComment;
    private int clickedCommentId = -1;
    @FXML
    private Label commentsNumber;
    @FXML
    private Label comnumberLabel;
    @FXML
    private AnchorPane container;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showComments();
        deleteCheckedComment.setDisable(true);
       
    } 
    
    
   public void showComments() {
       
        CRUDComments cr = new CRUDComments();
        p = new Podcast();
        p.setId(1);
        p.setCommentsAllowed(0);
        if(p.getCommentsAllowed() == 0) {
            commentingStatusButton.setText("Activate commenting");
        } else {
                        commentingStatusButton.setText("Deactivate commenting");

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
    private void changeCommentingStatus(MouseEvent event) {
         if(p.getCommentsAllowed() == 0) {
            commentingStatusButton.setText("Deactivate commenting");
            p.setCommentsAllowed(1);

        } else {
            commentingStatusButton.setText("Activate commenting");
            p.setCommentsAllowed(0);


        }

    }

    @FXML
    private void deleteCommentClick(MouseEvent event) {
         Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete this comment ?");
           Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
            CRUDComments cr = new CRUDComments();
            if(cr.deleteComment(clickedCommentId)) {
                clickedCommentId = -1;
                deleteCheckedComment.setDisable(true);
                showComments();
            } else {
            Alert al = new Alert(AlertType.ERROR);
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
    
}
