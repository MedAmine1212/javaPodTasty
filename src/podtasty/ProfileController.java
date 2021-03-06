/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.User;
import entities.UserInfo;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
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
    private HomeScreenController homeScreen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    homeScreen = HomeScreenController.getInstance();
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
            User u = homeScreen.getCurrentUser();
            this.FirstName.setText(u.getUserInfoIdId().getUserFirstName());
            this.LastName.setText(u.getUserInfoIdId().getUserLastName());
            this.Bio.setText(u.getUserInfoIdId().getUserBio());
            BufferedImage image;
            if(u.getUserInfoIdId().getUserImage()!=null){
            image = ImageIO.read(new URL("http://127.0.0.1:8000/Files/podcastFiles/" + u.getUserInfoIdId().getUserImage()));
            }else{
            image = ImageIO.read(new URL("http://127.0.0.1:8000/Files/podcastFiles/avatar.jpg"));
            
            }
            WritableImage img = SwingFXUtils.toFXImage(image, null);
            this.circle.setFill(new ImagePattern(img));
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
     
            
            User u = homeScreen.getCurrentUser();
            CRUDUser cr = new CRUDUser();
            MultipartEntityBuilder  entity = MultipartEntityBuilder.create();
            entity.addBinaryBody("myFile", file);
            HttpEntity mutiPartHttpEntity = entity.build();
            HttpPost request = new HttpPost("http://127.0.0.1:8000/api/profile/pic/post");
            request.setEntity(mutiPartHttpEntity);
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(request);
            Header[] responseEntity = response.getHeaders();
            System.out.print(file.getName());
            cr.updatePic(file.getName(), u.getUserInfoIdId().getId());
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
            User u = homeScreen.getCurrentUser();
        if (u.getChannelIdId().getChannel_Status()==0) {
             Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Banned for misconduct");
                alert.setHeaderText(null);
                alert.setContentText("You were banned for bad behavior, Please contact the admin for any verification request.");
                alert.showAndWait();
                return;
            
        }else{
        
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OwnChannel.fxml"));
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
    }}

    @FXML
    private void GoToTimline(ActionEvent event) {
    }

    private void Recherche(KeyEvent event) {
      
       if(event.getCode().toString().equals("ENTER"))
    {
        System.out.print(event);
    }
    }
}
