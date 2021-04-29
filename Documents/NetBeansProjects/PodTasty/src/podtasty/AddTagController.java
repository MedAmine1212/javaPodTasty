/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Tag;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import services.CRUDTag;

/**
 * FXML Controller class
 *
 * @author khail
 */
public class AddTagController implements Initializable {

    @FXML
    private TextField tagNameInput;
    @FXML
    private Button btnConfirmAddTag;
    @FXML
    private Button btnCancelAddTag;
    
    private String tagStyle = "primary"; 
    @FXML
    private ImageView currentColor;
    @FXML
    private Text inputTagError;
    @FXML
    private ImageView primary;
    @FXML
    private ImageView secondary;
    @FXML
    private ImageView info;
    @FXML
    private ImageView warning;
    @FXML
    private ImageView success; 
    @FXML
    private ImageView danger;
    @FXML
    private ImageView dark;
    @FXML
    private Label tagAddUpdateHeader;
    private Tag tag;
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inputTagError.setVisible(false);
        tag = new Tag();
        if(TagsListController.getSelectedId() == -1) {
        tagAddUpdateHeader.setText("ADD TAG");
        btnConfirmAddTag.setText("Add");
        } else {
        tagAddUpdateHeader.setText("UPDATE TAG");
        btnConfirmAddTag.setText("Update");
        CRUDTag cr = new CRUDTag();
        tag = cr.getTagById(TagsListController.getSelectedId());
        tagNameInput.setText(tag.getName());
        tagStyle = tag.getTagStyle();
            try {
                BufferedImage image;
                image = ImageIO.read(new File("src/images/"+tagStyle+".png"));
                WritableImage img = SwingFXUtils.toFXImage(image, null);
                currentColor.setImage(img);
            } catch (IOException ex) {
                Logger.getLogger(AddTagController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    

    @FXML
    private void setTagColorAction(MouseEvent event) {
        
        currentColor.setImage(((ImageView)event.getTarget()).getImage());
        tagStyle = ((ImageView)event.getTarget()).getId();
        
    }

    @FXML
    private void btnConfirmAddTagClick(ActionEvent event) throws IOException {
        if (tagNameInput.getText().length() > 0) {
            CRUDTag cr = new CRUDTag();
            tag.setName(tagNameInput.getText());
            tag.setTagStyle(tagStyle);
            if(TagsListController.getSelectedId() == -1) {
                if (cr.addTag(tag)) {
                tagNameInput.setText("");
                tagStyle = "primary";
                BufferedImage image = ImageIO.read(new File("src/images/primary.png"));
                WritableImage img = SwingFXUtils.toFXImage(image, null);
                currentColor.setImage(img);
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Success");
                al.setHeaderText("Tag added successfuly");
                al.showAndWait();
                } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Error");
                al.setHeaderText("Couldn't add tag");
                al.showAndWait();
                }
            } else {
                if (cr.updateTag(tag, TagsListController.getSelectedId())) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle("Success");
                al.setHeaderText("Tag updated successfuly");
                al.showAndWait();
                btnCancelAddTag.fire();
                } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Error");
                al.setHeaderText("Couldn't update tag");
                al.showAndWait();
                }
            }
            
        } else {
            inputTagError.setVisible(true);
        }
        
    }

    @FXML
    private void btnCancelAddTagClick(ActionEvent event) {
       
        TagsListController.closeStage();
    }

    @FXML
    private void removeErrorMsg(KeyEvent event) {
        inputTagError.setVisible(false);
        
    }
    
}
