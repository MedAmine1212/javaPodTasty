/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Podcast;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javax.sound.sampled.AudioFileFormat;
import javax.swing.JFileChooser;
import services.CRUDPodcast;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class PodcasttController implements Initializable {

    @FXML
    private Button Upaudio;
    @FXML
    private TextField podname;
    @FXML
    private TextArea poddesc;
    @FXML
    private Button upimg;
    @FXML
    private Button ajoutpodcast;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnuploadaudio(ActionEvent event) {
        
        JFileChooser fchooser = new JFileChooser();
        fchooser.showOpenDialog(null);
        File selectedFile = fchooser.getSelectedFile();
        String filename = selectedFile.getAbsolutePath();
        AudioFileFormat getAbsolutepath = null ;
        
    }

    @FXML
    private void btnuploadimg(ActionEvent event) {
        
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File selectedFile = chooser.getSelectedFile();
        String filename = selectedFile.getAbsolutePath();
        Image getAbsolutepath = null ;
       // AudioFileFormat getAbsolutpath = null;
        //ImageIcon icon = new Image
        //System.out.println("file name");
    }

    @FXML
    private void btnajoutpodcast(ActionEvent event) {
        
        Podcast pod = new Podcast (podname.getText(),poddesc.getText());
        CRUDPodcast crpod = CRUDPodcast.getInstance();
        crpod.addPodcast(pod);
        
        Alert alert = new Alert (AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("podcast successfully added");
        alert.show();
    }
    
}
