/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Tag;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.CRUDTag;

/**
 * FXML Controller class
 *
 * @author khail
 */

public class TagsListController implements Initializable {

    
    private static Stage addTagStage;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<Tag> tagsList;
    @FXML
    private TableColumn<Tag, String> tagName;
    @FXML
    private TableColumn<Tag, ImageView> tagStyle;
    
    private static int selectedId = -1;
   
    @FXML
    private Button btnAddNew;
    @FXML
    private MenuItem miSellSelected;
    private static TagsListController instance;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         instance = this;
         showTags();
    }    
    
    
    
      public void showTags(){
        tagsList.setItems(null);
         btnDelete.setDisable(true);
         btnUpdate.setDisable(true);
         CRUDTag cr = new CRUDTag();
        ObservableList<Tag> tList = cr.getTags();
        tagName.setCellValueFactory(new PropertyValueFactory<Tag, String>("name") );
        tagStyle.setCellValueFactory(new PropertyValueFactory<Tag, ImageView>("tagColor") );
      
        tagsList.setItems(tList);  
        tagsList.refresh();
    }
    

    


    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
         Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Are you sure you want to delete this tag ?");
           Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
            CRUDTag cr = new CRUDTag();
            if(cr.deleteTag(selectedId)) {
                selectedId = -1;
                btnDelete.setDisable(true);
                btnUpdate.setDisable(true);
                showTags();
            } else {
            Alert al = new Alert(AlertType.ERROR);
            al.setTitle("Error");
            al.setHeaderText("Couldn't delete tag");
            al.showAndWait();
            }

            }
        }
    

    @FXML
    private void tblViewCurrentStoreOnClick(javafx.scene.input.MouseEvent event) {
         Tag tag =tagsList.getSelectionModel().getSelectedItem();
         selectedId = tag.getId();
         
         btnDelete.setDisable(false);
         btnUpdate.setDisable(false);
        
    }

    @FXML
    private void btnAddNewOnAction(ActionEvent event) throws IOException {
        
        selectedId = -1;
        Parent root;
        root = FXMLLoader.load(getClass().getResource("AddTag.fxml"));
            addTagStage = new Stage();
            addTagStage.setTitle("Podcast review");
            addTagStage.setScene(new Scene(root));
            addTagStage.initModality(Modality.WINDOW_MODAL);
            addTagStage.initOwner(((Node)(event.getSource())).getScene().getWindow());
            addTagStage.show();
        
    }
    
    public static void closeStage() {
         addTagStage.close();
         TagsListController.getInstance().showTags();
    } 
    public static TagsListController getInstance() {
        return instance;
    }
    @FXML
    private void btnUpdateOnAction(ActionEvent event) throws IOException {
        
        Parent root;
        root = FXMLLoader.load(getClass().getResource("AddTag.fxml"));
            addTagStage = new Stage();
            addTagStage.setTitle("Podcast review");
            addTagStage.setScene(new Scene(root));
            addTagStage.initModality(Modality.WINDOW_MODAL);
            addTagStage.initOwner(((Node)(event.getSource())).getScene().getWindow());
            addTagStage.show();
        
    }

    @FXML
    private void miSellSelectedOnAction(ActionEvent event) {
    }

    @FXML
    private void tblViewCurrentStoreOnScroll(ScrollEvent event) {
    }
    
     public static int getSelectedId() {
       return selectedId;
    }
    
}



