/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author MedAmine
 */
public class PodTasty extends Application {
    
    @Override
    public void stop() {
        LoadAudio ld = LoadAudio.getInstance();
        try {
            ld.stopAudio();
        } catch (IOException ex) {
            Logger.getLogger(PodTasty.class.getName()).log(Level.SEVERE, null, ex);
        }
        LoadAudio.destroyInstance();
    }
    @Override
    public void start(Stage stage) throws Exception {
            
          
//        Parent root = FXMLLoader.load(getClass().getResource("PodcastComments.fxml"));
        
//        Parent root = FXMLLoader.load(getClass().getResource("TagsFxmlDocument.fxml"));
        
        
        Parent root = FXMLLoader.load(getClass().getResource("PodcastCommentsFront.fxml"));
        
        
        
        Scene scene = new Scene(root);
        
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
