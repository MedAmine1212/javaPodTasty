/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Tag;
import java.awt.Color;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import services.CRUDTag;

/**
 * FXML Controller class
 *
 * @author khail
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private StackPane spProductContent;
    @FXML
    private Button btnAddNew;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<Tag> tagsList;
    @FXML
    private MenuItem miSellSelected;
    @FXML
    private TableColumn<Tag, Integer> tagId;
    @FXML
    private TableColumn<Tag, String> tagName;
    @FXML
    private TableColumn<Tag, Color> tagStyle;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         CRUDTag cr = new CRUDTag();
         Tag tag = new Tag();
         showTags();
    }    
    
    
    
      public void showTags(){
         CRUDTag cr = new CRUDTag();
        ObservableList<Tag> tList = cr.getTags();
        tagId.setCellValueFactory(new PropertyValueFactory<Tag, Integer>("id") );
        tagName.setCellValueFactory(new PropertyValueFactory<Tag, String>("name") );
      
        tagsList.setItems(tList);

      
    }
    

    

    @FXML
    private void btnAddNewOnAction(ActionEvent event) {
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
    }

    @FXML
    private void miSellSelectedOnAction(ActionEvent event) {
    }

    @FXML
    private void tblViewCurrentStoreOnClick(javafx.scene.input.MouseEvent event) {
         Tag tag =tagsList.getSelectionModel().getSelectedItem();
         System.out.println(tag.getId());
        
    }

    @FXML
    private void tblViewCurrentStoreOnScroll(ScrollEvent event) {
    }
    
}
