/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    }

    @FXML
    private void btnuploadimg(ActionEvent event) {
    }

    @FXML
    private void btnajoutpodcast(ActionEvent event) {
    }
    
}
