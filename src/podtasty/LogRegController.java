/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.User;
import entities.UserInfo;
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.RegisterBtn.setOnAction((e)->{
            this.LoginForm.setVisible(false);
            this.RegForm.setVisible(true);
    });
        this.BackToLogBtn.setOnAction((e)->{
            this.LoginForm.setVisible(true);
            this.RegForm.setVisible(false);
        });
        this.male.setOnAction((e)->{
            this.female.setSelected(false);
        });
        
        this.female.setOnAction((e)->{
            this.male.setSelected(false);
        });
    }    

    @FXML
    
    private void MakeUser(ActionEvent event) {
        
        if(this.FSField.getText().isEmpty()==false&&
                this.LSField.getText().isEmpty()==false&&
                this.EmailFieldReg.getText().isEmpty()==false&&
                this.PasswordFieldReg.getText().isEmpty()==false&&
                this.PasswordFieldRegComnirm.getText().isEmpty()==false&&
                this.BirthDate.getValue() != null &&
                (this.male.isSelected()== true || this.female.isSelected()== true)){
        User user = new User();
        UserInfo userInfo = new UserInfo();
        System.out.println(this.FSField.getText());
        userInfo.setUserFirstName(this.FSField.getText());
        System.out.println(this.LSField.getText());
        userInfo.setUserLastName(this.LSField.getText());
        System.out.println(this.EmailFieldReg.getText());
        user.setUserEmail(this.EmailFieldReg.getText());
        System.out.println(this.PasswordFieldReg.getText());
        user.setUserPassword(this.PasswordFieldReg.getText());
        System.out.println(this.PasswordFieldRegComnirm.getText());
        System.out.println(this.BirthDate.getValue());
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date date = Date.from(this.BirthDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        userInfo.setUserBirthDate(date);
        if(this.male.isSelected()){
            userInfo.setUserGender("male");
        }
        
        if(this.female.isSelected()){
            userInfo.setUserGender("female");
        }
        
        CRUDUser cr = new CRUDUser();
        cr.addUser(user, userInfo);

        }
        
    }

    @FXML
    private void Login(ActionEvent event) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(this.EmailField.getText());
        if(this.EmailField.getText().isEmpty()==false && this.PasswordField.getText().isEmpty()==false&&m.matches()){
            CRUDUser cr = new CRUDUser();
            if(cr.validate(this.EmailField.getText(),this.PasswordField.getText())){

                this.messageloginSucceded.setVisible(true);           

          } else{
        this.messagelogin.setVisible(true);           
            } 
        }
        
        else {
        this.messagelogin.setVisible(true);           

        }
    }

    @FXML
    private void male(ActionEvent event) {
    }

    @FXML
    private void female(ActionEvent event) {
    }
    
}
