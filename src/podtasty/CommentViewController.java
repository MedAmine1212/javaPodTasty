/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import com.google.zxing.WriterException;
import entities.BadWords;
import entities.PodcastComment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;
import services.CRUDComments;

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
    @FXML
    private ScrollPane scroll;
    @FXML
    private Circle commentOwnerImage;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
     
    public void setView(PodcastComment comment) throws IOException{
        userName.setText(comment.getUserName());
        commentText.setText(comment.getCommentText());
        DateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");  
        String date = dateFormat.format(comment.getCommentDate());
         
       
        commentDate.setText(date);
         
           new Thread(new Runnable() {

    @Override
    public void run() {
        
        try {
            BufferedImage imgg;
            imgg = ImageIO.read(new File("src/images/avatar.jpg"));
            WritableImage im = SwingFXUtils.toFXImage(imgg, null);
            commentOwnerImage.setFill(new ImagePattern(im));
        } catch (IOException ex) {
            Logger.getLogger(CommentViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Platform.runLater(new Runnable() {

            @Override
            public void run() { 
     
         
         if(comment.getUserIdId().getUserInfoIdId().getUserImage() != null) {
            
             try {
                 BufferedImage imgg;
                 imgg = ImageIO.read(new URL("http://127.0.0.1:8000/Files/podcastFiles/"+comment.getUserIdId().getUserInfoIdId().getUserImage()));
                WritableImage im = SwingFXUtils.toFXImage(imgg, null);
            commentOwnerImage.setFill(new ImagePattern(im));
             } catch (MalformedURLException ex) {
                 Logger.getLogger(CommentViewController.class.getName()).log(Level.SEVERE, null, ex);
             } catch (IOException ex) {
                 Logger.getLogger(CommentViewController.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
       
        }
        });  
    }
        }).start();
        
    }
    
    
}
