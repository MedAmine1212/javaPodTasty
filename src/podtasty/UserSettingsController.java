/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.User;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.CRUDUser;

/**
 * FXML Controller class
 *
 * @author Douiri Amine
 */
public class UserSettingsController implements Initializable {

    @FXML
    private AnchorPane ChangeInfo;
    @FXML
    private TextField FSField;
    @FXML
    private TextField LSField;
    @FXML
    private TextField BioField;
    @FXML
    private Button SaveButton;
    @FXML
    private AnchorPane ChangePwd;
    @FXML
    private Button SaveButtonPwd;
    @FXML
    private Label ChangeInfoError;
    @FXML
    private PasswordField OldPassword;
    @FXML
    private PasswordField NewPassword;
    @FXML
    private PasswordField NewPasswordComfirm;
    @FXML
    private Label ChangePwdError;
    @FXML
    private ImageView back;
    private HomeScreenController homeScreen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        homeScreen = HomeScreenController.getInstance();
        User u = homeScreen.getCurrentUser();
        CRUDUser cr = new CRUDUser();

        this.FSField.setText(u.getUserInfoIdId().getUserFirstName());
        this.LSField.setText(u.getUserInfoIdId().getUserLastName());
        this.BioField.setText(u.getUserInfoIdId().getUserBio());
        this.ChangeInfo.setVisible(false);
        this.ChangePwd.setVisible(false);

    }

    @FXML
    private void ShowChangeInfo(ActionEvent event) {

        this.ChangeInfo.setVisible(true);
        this.ChangePwd.setVisible(false);
    }

    @FXML
    private void ShowChangePasswd(ActionEvent event) {

        this.ChangeInfo.setVisible(false);
        this.ChangePwd.setVisible(true);
    }

    @FXML
    private void DisableAccount(ActionEvent event) {
        User u = homeScreen.getCurrentUser();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Confirm switching");
        alert.setContentText("Are you sure you want to switch the status of this user?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            CRUDUser crudu = new CRUDUser();
            crudu.switchStatusAccount(u.getId());
        }
    }

    @FXML
    private void changePwdbtn(ActionEvent event) {
        this.ChangePwdError.setText("");
        if (this.OldPassword.getText().isEmpty() == false && this.NewPassword.getText().isEmpty() == false && this.NewPasswordComfirm.getText().isEmpty() == false) {
            CRUDUser cr = new CRUDUser();
            User u = homeScreen.getCurrentUser();

            if (cr.validate(u.getUserEmail(), this.OldPassword.getText())
                    && this.NewPassword.getText().equals(this.NewPasswordComfirm.getText())) {
                cr.changePassword(u.getId(), this.NewPassword.getText());
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Password Changed");
                alert.setHeaderText("You have changed your password successfully");
                alert.setContentText("Enjoy POD-TASTY!");
                alert.showAndWait();
                this.ChangePwd.setVisible(false);
            } else if (cr.validate(u.getUserEmail(), this.OldPassword.getText()) == false) {
                this.ChangePwdError.setText("Old Password Invalid");

            } else if (this.NewPassword.getText().equals(this.NewPasswordComfirm.getText()) == false) {
                this.ChangePwdError.setText("Confirm Password Invalid");

            }
        } else {
            this.ChangePwdError.setText("Required input are empty");
        }

    }

    @FXML
    private void GoBack(MouseEvent event) throws MalformedURLException {
        ProfileController.closeSettings();
    }

    @FXML
    private void ChangeInfo(ActionEvent event) {
        User u = homeScreen.getCurrentUser();
        CRUDUser cr = new CRUDUser();
        u.getUserInfoIdId().setUserFirstName(this.FSField.getText());
        u.getUserInfoIdId().setUserLastName(this.LSField.getText());
        u.getUserInfoIdId().setUserBio(this.BioField.getText());
        cr.updateUser(u, u.getUserInfoIdId(), u.getId());
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informations Changed");
        alert.setHeaderText("You have changed your infos successfully");
        alert.setContentText("Enjoy POD-TASTY!");
        alert.showAndWait();
        this.ChangeInfo.setVisible(false);

    }


}
