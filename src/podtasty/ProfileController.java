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
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import services.CRUDUser;
import services.JavaMailUtils;
import services.SMSUtils;
import sun.net.www.http.HttpClient;

/**
 * FXML Controller class
 *
 * @author Douiri Amine
 */
public class ProfileController implements Initializable {

    @FXML
    private Circle circle;
    @FXML
    private Label LastName;
    @FXML
    private Label FirstName;
    @FXML
    private Label Bio;
    @FXML
    private Label LastName1;
    @FXML
    private Label LastName2;
    @FXML
    private Label Followers;
    @FXML
    private Label following;
    @FXML
    private Button SettingsBtn;
    @FXML
    private Button SettingsBtn1;
    @FXML
    private Button SettingsBtn2;
    @FXML
    private Pane NotifPanel;
    static Boolean notifOn;
    @FXML
    private Button notif1;
    @FXML
    private TextField activateCode;
    @FXML
    private Label activation_error;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserHolder holder = UserHolder.getInstance();
            User u = holder.getUser();
            
        this.notifOn = false;
        this.NotifPanel.setVisible(false);
        // TODO 
        //    Image im = new Image("https://juicylinksmag.files.wordpress.com/2016/02/juliet-ibrahim.jpg", false);

        //   this.circle.setFill(new ImagePattern(im));
        try {
            this.setProfile();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setProfile() throws MalformedURLException {
        try {
            UserHolder holder = UserHolder.getInstance();
            User u = holder.getUser();
            CRUDUser cr = new CRUDUser();
            UserInfo info = cr.getUserInfoById(u.getUserInfoIdId());
            this.FirstName.setText(info.getUserFirstName());
            this.LastName.setText(info.getUserLastName());
            this.Bio.setText(info.getUserBio());
            BufferedImage image;
            if (info.getUserImage() != null) {
                image = ImageIO.read(new URL("http://127.0.0.1:8000/Files/podcastFiles/" + info.getUserImage()));
            } else {
                image = ImageIO.read(new URL("http://127.0.0.1:8000/Files/podcastFiles/avatar.jpg"));

            }
            WritableImage img = SwingFXUtils.toFXImage(image, null);
            this.circle.setFill(new ImagePattern(img));
            if (u.getDesactiveAccount()) {
                this.notif1.setVisible(true);
            } else {
                this.notif1.setVisible(true);
            }
        } catch (IOException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void uploadPic(ActionEvent event) throws ProtocolException, IOException {
        Stage primaryStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            UserHolder holder = UserHolder.getInstance();
            User u = holder.getUser();
            CRUDUser cr = new CRUDUser();
            UserInfo info = cr.getUserInfoById(u.getUserInfoIdId());
            MultipartEntityBuilder entity = MultipartEntityBuilder.create();
            entity.addBinaryBody("myFile", file);
            HttpEntity mutiPartHttpEntity = entity.build();
            HttpPost request = new HttpPost("http://127.0.0.1:8000/api/profile/pic/post");
            request.setEntity(mutiPartHttpEntity);
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(request);
            Header[] responseEntity = response.getHeaders();
            System.out.print(file.getName());
            cr.updatePic(file.getName(), info.getId());
            this.setProfile();
        }

    }

    @FXML
    private void GoToSettings(ActionEvent event) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UserSettings.fxml"));
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
    private void GoToChannel(ActionEvent event) {
    }

    @FXML
    private void GoToTimline(ActionEvent event) {
    }

    private void Recherche(KeyEvent event) {

        if (event.getCode().toString().equals("ENTER")) {
            System.out.print(event);
        }
    }

    @FXML
    private void ShowNotif(ActionEvent event) {
        this.notifOn = !notifOn;
        if (notifOn) {
            this.NotifPanel.setVisible(true);
        } else {
            this.NotifPanel.setVisible(false);
        }
    }

    @FXML
    private void Activate(ActionEvent event) {
        UserHolder holder = UserHolder.getInstance();
        User u = holder.getUser();

        if (this.activateCode.getText().equals(u.getId().toString())) {
            CRUDUser cr = new CRUDUser();
            cr.switchStatusAccount(u.getId());
            
        } else {
            this.activation_error.setVisible(true);
        }
    }
    @FXML
    private void ResendEmail(MouseEvent event) throws MessagingException {
        UserHolder holder = UserHolder.getInstance();
        User u = holder.getUser();
        JavaMailUtils.sendMail(u.getUserEmail(), u.getId());
    }

    @FXML
    private void S(MouseEvent event) {
    }

    @FXML
    private void sendSMS(MouseEvent event) {
        UserHolder holder = UserHolder.getInstance();
        User u = holder.getUser();
        SMSUtils.send(u.getId());
        System.out.print("sms sent");
        
    }
}
