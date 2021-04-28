/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;

import entities.Post;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.CRUDPost;

/**
 *
 * @author MedAmine
 */
public class PodTasty extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/posts.fxml"));
        stage.setTitle("Tasty posts");        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.out.println("ena fel main");
        Post p = new Post("faza");
        CRUDPost c = new CRUDPost();
        
         ObservableList<Post> posts = FXCollections.observableArrayList();
        posts= c.getPostByText(new Post("/fxml/post.fxml"));
        System.out.println(p.getId()+p.getText());
        //c.addPost(p);
       // for (Post p : c.getPosts()) {
           // System.out.println("Postid: "+p.getId()+" Post Text: "+p.getText());
       // }
        

        launch(args);
    }

}
