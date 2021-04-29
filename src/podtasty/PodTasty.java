/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podtasty;


import entities.Post;
import entities.User;
import entities.UserInfo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import services.CRUDPost;

/**
 *
 * @author MedAmine
 */
public class PodTasty extends Application {

static User user = new User();
public static User getUser()
{
    return user;
}

static UserInfo userInfo = new UserInfo();
public static UserInfo getUserInfo()
{
    return userInfo;
}
    
    @Override
    public void start(Stage stage) throws Exception {
        user.setId(5);
        userInfo.setId(6);
        userInfo.setUserFirstName("Issam");
        userInfo.setUserLastName("Ben Ammar");
        
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

        
        launch(args);
    }

}
