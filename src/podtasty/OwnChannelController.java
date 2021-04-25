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
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Lwiss
 */
public class OwnChannelController implements Initializable {

    @FXML
    private Label welcomeTitle;
    @FXML
    private Label ownerLabel;
    @FXML
    private Label createdLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label a;
    @FXML
    private GridPane big;
    @FXML
    private Button ReturnSingle;
    @FXML
    private Button EditOwnChannel;
    @FXML
    private Button DeleteOwnChannel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ReturnSingle(ActionEvent event) {
    }

    @FXML
    private void EditOwnChannelBtn(ActionEvent event) {
    }

    @FXML
    private void DeleteOwnChannelBtn(ActionEvent event) {
    }
    
}
