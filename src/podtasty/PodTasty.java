/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
    public void start(Stage stage) throws Exception {
       
//         URL url = new URL("http://127.0.0.1:8000/Files/podcastFiles/"+podcast.getSource());
//
//         HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//         connection.setRequestProperty("accept", "application/json");
//
//         // This line makes the request
//         InputStream responseStream = connection.getInputStream();
//       
//         System.out.println("____________________________________");
//         int i;
//         byte[] b = new byte[2048];
//        while ((i = responseStream.read(b)) != -1)
//            System.out.println(b);
//        responseStream.close();
         
        
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
