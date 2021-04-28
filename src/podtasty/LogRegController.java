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
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import services.CRUDUser;
import java.time.LocalDate;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpResponse;
import services.JavaMailUtils;

/**
 * FXML Controller class
 *
 * @author Douiri Amine
 */
public class LogRegController implements Initializable {

    @FXML
    private Button LoginBtn;
    @FXML
    private Button RegisterBtn;
    @FXML
    private PasswordField PasswordField;
    @FXML
    private TextField EmailField;
    @FXML
    private CheckBox RememberCheck;
    @FXML
    private TextField LSField;
    @FXML
    private Pane RegForm;
    @FXML
    private TextField FSField;
    @FXML
    private TextField EmailFieldReg;
    @FXML
    private DatePicker BirthDate;
    @FXML
    private Button RegisterBtnReg;
    @FXML
    private Button BackToLogBtn;
    @FXML
    private Pane LoginForm;
    @FXML
    private AnchorPane anchor;
    @FXML
    private PasswordField PasswordFieldReg;
    @FXML
    private PasswordField PasswordFieldRegComnirm;
    private AnchorPane loginmessage;
    @FXML
    private Label messagelogin;
    @FXML
    private Label messageloginSucceded;
    @FXML
    private AnchorPane logn;
    @FXML
    private CheckBox male;
    @FXML
    private CheckBox female;
    @FXML
    private Label FSreq;
    @FXML
    private Label LSreq;
    @FXML
    private Label EmailReq;
    @FXML
    private Label GenderReq;
    @FXML
    private Label BDReq;
    @FXML
    private Label passmatch;
    @FXML
    private Button GithubBtn;
    @FXML
    private Label emailexist;
    Preferences pref;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pref=Preferences.userNodeForPackage(LogRegController.class);
        if(pref!=null){
            if(pref.get("email",null)!=null&&!pref.get("email", null).isEmpty()){
                this.EmailField.setText(pref.get("email", null));
                this.PasswordField.setText(pref.get("password", null));
            }
        }
        this.RegisterBtn.setOnAction((e) -> {
            this.LoginForm.setVisible(false);
            this.RegForm.setVisible(true);
        });
        this.BackToLogBtn.setOnAction((e) -> {
            this.LoginForm.setVisible(true);
            this.RegForm.setVisible(false);
        });
        this.male.setOnAction((e) -> {
            this.female.setSelected(false);
        });

        this.female.setOnAction((e) -> {
            this.male.setSelected(false);
        });
    }

    private void clearReq() {
        this.FSreq.setVisible(false);
        this.EmailReq.setVisible(false);
        this.passmatch.setVisible(false);
        this.LSreq.setVisible(false);
        this.BDReq.setVisible(false);
        this.GenderReq.setVisible(false);
        this.emailexist.setVisible(false);

    }

    @FXML

    private void MakeUser(ActionEvent event) {
        this.clearReq();
        //this.RegisterBtnReg.setDisable(true);
        if (this.FSField.getText().isEmpty()) {
            this.FSreq.setVisible(true);
        }
        if (this.EmailFieldReg.getText().isEmpty()) {
            this.EmailReq.setVisible(true);
        }
        if (this.PasswordFieldReg.getText().isEmpty()) {
            this.passmatch.setVisible(true);
        }
        if (this.LSField.getText().isEmpty()) {
            this.LSreq.setVisible(true);
        }

        if (BirthDate.getValue() == null) {
            this.BDReq.setVisible(true);
        }

        if (this.male.isSelected() == false && this.female.isSelected() == false) {
            this.GenderReq.setVisible(true);
        }
        if (PasswordFieldRegComnirm.getText().equals(this.PasswordFieldReg.getText()) == false) {
            this.passmatch.setVisible(true);
        }

        if (this.FSField.getText().isEmpty() == false
                && this.LSField.getText().isEmpty() == false
                && this.EmailFieldReg.getText().isEmpty() == false
                && this.PasswordFieldReg.getText().isEmpty() == false
                && this.PasswordFieldRegComnirm.getText().equals(this.PasswordFieldReg.getText())
                && this.BirthDate.getValue() != null
                && (this.male.isSelected() == true || this.female.isSelected() == true)) {
            try {
                User user = new User();
                UserInfo userInfo = new UserInfo();
                userInfo.setUserFirstName(this.FSField.getText());
                userInfo.setUserLastName(this.LSField.getText());
                user.setUserEmail(this.EmailFieldReg.getText());
                user.setUserPassword(this.PasswordFieldReg.getText());
                ZoneId defaultZoneId = ZoneId.systemDefault();
                Date date = Date.from(this.BirthDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                userInfo.setUserBirthDate(date);
                if (this.male.isSelected()) {
                    userInfo.setUserGender("male");
                }

                if (this.female.isSelected()) {
                    userInfo.setUserGender("female");
                }

                CRUDUser cr = new CRUDUser();

                boolean i = cr.addUser(user, userInfo);
                if (i == false) {
                    this.emailexist.setVisible(true);
                }
                this.LoginForm.setVisible(true);
                this.RegForm.setVisible(false);

                JavaMailUtils.sendMail(this.EmailFieldReg.getText(),
                        cr.getUserByEmail(this.EmailFieldReg.getText()).getId());

            } catch (MessagingException ex) {
                Logger.getLogger(LogRegController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void Login(ActionEvent event) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(this.EmailField.getText());
        if (this.EmailField.getText().isEmpty() == false && this.PasswordField.getText().isEmpty() == false && m.matches()) {
            CRUDUser cr = new CRUDUser();
            if (cr.validate(this.EmailField.getText(), this.PasswordField.getText())) {
                User user = cr.getUserByEmail(this.EmailField.getText());
                /*  if (user.getDesactiveAccount()) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Alert");
                    alert.setHeaderText("Your acount is desactivated");
                    alert.setContentText("Your acount is desactivated");
                    alert.showAndWait();
                } else {*/
                if(this.RememberCheck.isSelected()){
                pref.put("email", this.EmailField.getText());
                pref.put("password", this.PasswordField.getText());
                    
                }
                this.messageloginSucceded.setVisible(true);
                if (user.getIsAdmin()) {
                    try {
                        UserHolder holder = UserHolder.getInstance();
                        holder.setUser(user);

                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GestionUsers.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                        PodTasty.stg.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        UserHolder holder = UserHolder.getInstance();
                        holder.setUser(user);
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                        Parent root = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                        PodTasty.stg.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } else {
                this.messagelogin.setVisible(true);
            }
        } else {
            this.messagelogin.setVisible(true);

        }
    }

    @FXML
    private void male(ActionEvent event) {
    }

    @FXML
    private void female(ActionEvent event) {
    }

    @FXML
    private void GithubBtnAction(ActionEvent event) throws IOException {
        //      HttpGet request = new HttpGet("http://127.0.0.1:8000/api/github");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpget = new HttpPost("http://127.0.0.1:8000/api/github");
        System.out.println("Request Type: " + httpget.getMethod());

        HttpResponse httpresponse = httpclient.execute(httpget);
        System.out.println("Response Status: " + httpresponse);

    }

    @FXML
    private void ForgetPassword(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ResetPassword.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        PodTasty.stg.close();

    }

}
