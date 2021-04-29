/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author MedAmine
 */
public class PodTasty extends Application {
    
    @Override
    public void stop() {
        System.out.println("destroying");
        LoadAudio ld = LoadAudio.getInstance();
        try {
            ld.stopAudio();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        LoadAudio.destroyInstance();
    }
    @Override
    public void start(Stage stage) throws Exception {
            
          
//        Parent root = FXMLLoader.load(getClass().getResource("PodcastComments.fxml"));
        
//        Parent root = FXMLLoader.load(getClass().getResource("TagsFxmlDocument.fxml"));
        
        
        Parent root = FXMLLoader.load(getClass().getResource("homeScreen.fxml"));
        
        
        
        Scene scene = new Scene(root);
        stage.setTitle("PodTasty");
        stage.getIcons().add(new Image("/images/favicon.ico"));
        stage.setScene(scene);
        stage.show();
  
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
