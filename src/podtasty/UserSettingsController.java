/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.User;
import entities.UserHolder;
import entities.UserInfo;
import java.io.IOException;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserHolder holder = UserHolder.getInstance();
        User u = holder.getUser();
        CRUDUser cr = new CRUDUser();
        UserInfo info = cr.getUserInfoById(u.getUserInfoIdId());

        this.FSField.setText(info.getUserFirstName());
        this.LSField.setText(info.getUserLastName());
        this.BioField.setText(info.getUserBio());
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
        UserHolder holder = UserHolder.getInstance();
        User u = holder.getUser();

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
            UserHolder holder = UserHolder.getInstance();
            User u = holder.getUser();

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
    private void GoBack(MouseEvent event) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Profile.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            final Node source = (Node) event.getSource();
            final Stage Oldstage = (Stage) source.getScene().getWindow();
            Oldstage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ChangeInfo(ActionEvent event) {
        UserHolder holder = UserHolder.getInstance();
        User u = holder.getUser();
        CRUDUser cr = new CRUDUser();
        UserInfo info = cr.getUserInfoById(u.getUserInfoIdId());
        info.setUserFirstName(this.FSField.getText());
        info.setUserLastName(this.LSField.getText());
        info.setUserBio(this.BioField.getText());
        cr.updateUser(u, info, u.getId());
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informations Changed");
        alert.setHeaderText("You have changed your infos successfully");
        alert.setContentText("Enjoy POD-TASTY!");
        alert.showAndWait();
        this.ChangeInfo.setVisible(false);

    }

    @FXML
    private void LogOut(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LogReg.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        PodTasty.stg.close();

    }

}
