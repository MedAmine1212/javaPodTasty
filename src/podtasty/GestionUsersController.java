/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.User;
import entities.UserHolder;
import entities.UserInfo;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javax.imageio.ImageIO;
import services.CRUDUser;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.ImagePattern;
import javax.imageio.ImageIO;
/**
 * FXML Controller class
 *
 * @author Douiri Amine
 */
public class GestionUsersController implements Initializable {

    @FXML
    private TableColumn<Integer, User> userId;
    @FXML
    private TableColumn<String, User> userEmail;
    @FXML
    private Label FirstName;
    @FXML
    private Label Lastname;
    @FXML
    private Label gender;
    @FXML
    private TableView<User> table_users;
    @FXML
    private AnchorPane showoneuser;
    @FXML
    private Button editbtn;
    @FXML
    private Button deletebtn;
    @FXML
    private Button desactivatebtn;
    @FXML
    private TableColumn<?, ?> userStatus;
    @FXML
    private Label email;
    @FXML
    private Button addbtn;
    @FXML
    private TextField firstname_input;
    @FXML
    private TextField lastname_input;
    @FXML
    private TextField email_input;
    @FXML
    private Label gender_label;
    @FXML
    private Button saveEdit;
    @FXML
    private Label username;
    @FXML
    private Circle imageUser;
    @FXML
    private Circle imageUsers;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         UserHolder holder = UserHolder.getInstance();
            User u = holder.getUser();
            CRUDUser cr = new CRUDUser();
            UserInfo Myinfo = cr.getUserInfoById(u.getUserInfoIdId());
           this.username.setText(Myinfo.getUserFirstName());
BufferedImage image = null;

                    if(Myinfo.getUserImage()!=null){
                    try {
                        image = ImageIO.read(new URL("http://127.0.0.1:8000/Files/podcastFiles/" + Myinfo.getUserImage()));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(GestionUsersController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(GestionUsersController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }else{
                    try {
                        image = ImageIO.read(new URL("http://127.0.0.1:8000/Files/podcastFiles/avatar.jpg"));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(GestionUsersController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(GestionUsersController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
            }
            WritableImage img = SwingFXUtils.toFXImage(image, null);
            this.imageUser.setFill(new ImagePattern(img));
           
        this.showUsers();
    }   
    public void showUsers(){
        
        this.firstname_input.setVisible(false);
        this.lastname_input.setVisible(false);
        this.email_input.setVisible(false);
        CRUDUser crudu=new CRUDUser();
        ObservableList<User> uList = crudu.getUsers();
        this.userId.setCellValueFactory(new PropertyValueFactory<Integer, User>("id"));
        this.userEmail.setCellValueFactory(new PropertyValueFactory<String, User>("userEmail"));
        this.userStatus.setCellValueFactory(new PropertyValueFactory<>("desactiveAccount"));
        this.showoneuser.setVisible(false);
        this.table_users.setItems(uList);
        this.table_users.setOnMouseClicked(e->{
            if (e.getClickCount() == 2 ){
                this.FirstName.setVisible(true);
        this.Lastname.setVisible(true);
        this.gender.setVisible(true);
        this.email.setVisible(true);
        this.gender_label.setVisible(true);
        this.firstname_input.setVisible(false);
        this.lastname_input.setVisible(false);
        this.email_input.setVisible(false);
        this.deletebtn.setVisible(true);
        this.desactivatebtn.setVisible(true);
        this.editbtn.setVisible(true);
        this.saveEdit.setVisible(false);
                    int id = table_users.getSelectionModel().getSelectedItem().getUserInfoIdId();
                    UserInfo info = crudu.getUserInfoById(id);
                    this.showoneuser.setVisible(true);
                    this.FirstName.setText(info.getUserFirstName());
                    this.Lastname.setText(info.getUserLastName());
                    this.gender.setText(info.getUserGender());
                    this.email.setText(table_users.getSelectionModel().getSelectedItem().getUserEmail());
                     BufferedImage image = null;

                    if(info.getUserImage()!=null){
                    try {
                        image = ImageIO.read(new URL("http://127.0.0.1:8000/Files/podcastFiles/" + info.getUserImage()));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(GestionUsersController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(GestionUsersController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }else{
                    try {
                        image = ImageIO.read(new URL("http://127.0.0.1:8000/Files/podcastFiles/avatar.jpg"));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(GestionUsersController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(GestionUsersController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
            }
            WritableImage img = SwingFXUtils.toFXImage(image, null);
            this.imageUsers.setFill(new ImagePattern(img));
                    if(table_users.getSelectionModel().getSelectedItem().getDesactiveAccount()){
                        this.desactivatebtn.setText("Activate");
                    }else {
                        this.desactivatebtn.setText("Desactivate");
                    }
                    
            }
        });
        
    }

    @FXML
    private void deleteuser(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Alert");
alert.setHeaderText("Confirm delete");
alert.setContentText("Are you sure you want to delete this user?");
Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
        int id = table_users.getSelectionModel().getSelectedItem().getId();
        CRUDUser crudu=new CRUDUser();
        crudu.deleteUser(id);
        this.showUsers();
}
    }

    @FXML
    private void edit(ActionEvent event) {
        this.FirstName.setVisible(false);
        this.Lastname.setVisible(false);
        this.gender.setVisible(false);
        this.email.setVisible(false);
        this.gender_label.setVisible(false);
        this.firstname_input.setVisible(true);
        this.lastname_input.setVisible(true);
        this.email_input.setVisible(true);
        this.deletebtn.setVisible(false);
        this.desactivatebtn.setVisible(false);
        this.editbtn.setVisible(false);
        this.saveEdit.setVisible(true);
    }

    @FXML
    private void desactivate(ActionEvent event) {
               Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Alert");
alert.setHeaderText("Confirm switching");
alert.setContentText("Are you sure you want to switch the status of this user?");
Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
        int id = table_users.getSelectionModel().getSelectedItem().getId();
        CRUDUser crudu=new CRUDUser();
        crudu.switchStatusAccount(id);
        this.showUsers();
}
                    
    }

    @FXML
    private void saveEdit(ActionEvent event) {
               Alert alert = new Alert(AlertType.CONFIRMATION);
alert.setTitle("Alert");
alert.setHeaderText("Edit delete");
alert.setContentText("Are you sure you want to edit this user?");
Optional<ButtonType> result = alert.showAndWait();
if (result.get() == ButtonType.OK){
         User user = table_users.getSelectionModel().getSelectedItem();
        CRUDUser crudu=new CRUDUser();
        UserInfo info = crudu.getUserInfoById(table_users.getSelectionModel().getSelectedItem().getUserInfoIdId());
        if(this.email_input.getText().isEmpty()==false){
        user.setUserEmail(this.email_input.getText());
        }
        if(this.firstname_input.getText().isEmpty()==false){
        info.setUserFirstName(this.firstname_input.getText());
        }
        if(this.lastname_input.getText().isEmpty()==false){
            info.setUserLastName(this.lastname_input.getText());
        }
        crudu.updateUser(user, info, user.getId());
        this.showUsers();
    }
    }
}
