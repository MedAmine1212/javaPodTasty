/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

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
        static Stage stg;

    @Override
    public void start(Stage stage) throws Exception {
       /* this.stg= stage;
        
        stg.setTitle("Pod-Tasty : The Desktop Application");*/
        Parent root = FXMLLoader.load(getClass().getResource("LogReg.fxml"));
        //        Parent root = FXMLLoader.load(getClass().getResource("Profile.fxml"));

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