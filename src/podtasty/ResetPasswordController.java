/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.User;
import java.io.IOException;
import static java.lang.Math.random;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import services.CRUDUser;
import services.JavaMailUtils;

/**
 * FXML Controller class
 *
 * @author Douiri Amine
 */
public class ResetPasswordController implements Initializable {

    @FXML
    private TextField EmailField;
    @FXML
    private Pane rightcode;
    @FXML
    private TextField CodeField;
    @FXML
    private Pane ResetPane;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private PasswordField ConfirmPasswordField;
    @FXML
    private Pane emailPane;
    @FXML
    private Label emaildontexist;
    @FXML
    private Label emailrequired;
    @FXML
    private Label coderequired;
    @FXML
    private Label codeincorrect;
    @FXML
    private Label pwdrequired;
    @FXML
    private Label confirmpwdrequired;
    @FXML
    private Label passdontmatch;
    
    private int code;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.emailrequired.setVisible(false);
        this.emaildontexist.setVisible(false);
        this.emailPane.setVisible(true);
        this.rightcode.setVisible(false);
        this.ResetPane.setVisible(false);
        this.codeincorrect.setVisible(false);
        this.coderequired.setVisible(false);
        this.pwdrequired.setVisible(false);
        this.confirmpwdrequired.setVisible(false);
        this.passdontmatch.setVisible(false);
    }

    @FXML
    private void Sendemail(ActionEvent event) throws MessagingException {
        this.emailrequired.setVisible(false);
        this.emaildontexist.setVisible(false);
        if (this.EmailField.getText().isEmpty() == true) {
            this.emailrequired.setVisible(true);
        }
        if (this.EmailField.getText().isEmpty() == false) {
            CRUDUser cu = new CRUDUser();
            if (cu.getUserByEmail(this.EmailField.getText()) == null) {
                this.emaildontexist.setVisible(true);
            }
            if (cu.getUserByEmail(this.EmailField.getText()) != null) {
                Random rand = new Random();
                String cd = String.format("%04d", rand.nextInt(10000));
                code = Integer.parseInt(cd);
                JavaMailUtils.forgetPassword(this.EmailField.getText(), code);
                this.emailPane.setVisible(false);
                this.rightcode.setVisible(true);
            }

        }

    }

    @FXML
    private void VerifyCode(ActionEvent event) {
        this.codeincorrect.setVisible(false);
        this.coderequired.setVisible(false);
        CRUDUser cu = new CRUDUser();
        if (this.CodeField.getText().isEmpty() == true) {
            this.coderequired.setVisible(true);

        }
        if (Integer.parseInt(this.CodeField.getText()) != code) {
            this.codeincorrect.setVisible(true);
        }
        if (Integer.parseInt(this.CodeField.getText()) == code) {
            this.rightcode.setVisible(false);
            this.ResetPane.setVisible(true);
        }
    }

    @FXML
    private void Reset(ActionEvent event) throws IOException {
        this.pwdrequired.setVisible(false);
        this.confirmpwdrequired.setVisible(false);
        this.passdontmatch.setVisible(false);

        if (this.PasswordField.getText().isEmpty()) {
            this.pwdrequired.setVisible(true);
        }
        if (this.ConfirmPasswordField.getText().isEmpty()) {
            this.confirmpwdrequired.setVisible(true);
        }
        if (this.PasswordField.getText().equals(this.ConfirmPasswordField.getText()) == false) {
            this.passdontmatch.setVisible(true);
        }
        if (this.PasswordField.getText().equals(this.ConfirmPasswordField.getText())) {
            CRUDUser cr = new CRUDUser();
            int id = cr.getUserByEmail(this.EmailField.getText()).getId();
            cr.changePassword(id, this.PasswordField.getText());
                 LogRegController.closeForgetPass();
        }

    }

}
