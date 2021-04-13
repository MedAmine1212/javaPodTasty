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
import javafx.stage.Stage;
import services.CRUDTag;

/**
 * FXML Controller class
 *
 * @author khail
 */

public class TagsListController implements Initializable {

    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<Tag> tagsList;
    @FXML
    private TableColumn<Tag, Integer> tagId;
    @FXML
    private TableColumn<Tag, String> tagName;
    @FXML
    private TableColumn<Tag, ImageView> tagStyle;
    
    private static int selectedId = -1;
   
    @FXML
    private StackPane spProductContent;
    @FXML
    private Button btnAddNew;
    @FXML
    private MenuItem miSellSelected;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         CRUDTag cr = new CRUDTag();
         Tag tag = new Tag();
         btnDelete.setDisable(true);
         btnUpdate.setDisable(true);
         showTags();
    }    
    
    
    
      public void showTags(){
         CRUDTag cr = new CRUDTag();
        ObservableList<Tag> tList = cr.getTags();
        tagId.setCellValueFactory(new PropertyValueFactory<Tag, Integer>("id") );
        tagName.setCellValueFactory(new PropertyValueFactory<Tag, String>("name") );
        tagStyle.setCellValueFactory(new PropertyValueFactory<Tag, ImageView>("tagColor") );
      
        tagsList.setItems(tList);      
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
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("AddTag.fxml"));
        try {
            loader.load();


              } catch (IOException ex) {
                  System.out.println(ex.getMessage());
        }

        Parent parent = loader.getRoot();
        stage.setScene(new Scene(parent));
        stage.show();
        
    }
    
    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
                        
        FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(getClass().getResource("AddTag.fxml"));
        try {
            loader.load();


              } catch (IOException ex) {
                  System.out.println(ex.getMessage());
        }

        Parent parent = loader.getRoot();
        stage.setScene(new Scene(parent));
        stage.show();
        
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



