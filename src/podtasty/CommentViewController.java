/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.PodcastComment;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author khail
 */
public class CommentViewController implements Initializable {

    @FXML
    private Text userName;
    @FXML
    private Text commentDate;
    @FXML
    private Text commentText;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
     
    public void setView(PodcastComment comment){
        userName.setText(comment.getUserName());
        commentText.setText(comment.getCommentText());
        DateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");  
        String date = dateFormat.format(comment.getCommentDate());
        commentDate.setText(date);
    }
    
    
}
