/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import objects.Post;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import objects.Account;
import objects.PostAudience;

/**
 * FXML Controller class
 *
 * @author Issam
 */
public class PostsController implements Initializable {

    
     @FXML
    private VBox postsContainer;

    List<Post> posts;

     @Override
    public void initialize(URL location, ResourceBundle resources){
        posts = new ArrayList<>(getPosts());
    System.out.println("hedha");
        try {
            for (Post post : posts) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxml/post.fxml"));
                
                VBox vBox;
                vBox = fxmlLoader.load();
                
                System.out.println("hedha");
                PostController postController = fxmlLoader.getController();
                postController.setData(post);
                postsContainer.getChildren().add(vBox);
            }
        } catch (IOException ex) {
            System.out.println("leee");
        }
    }

    public List<Post> getPosts() {
        List<Post> ls = new ArrayList<>();

        Post post;

        for (int i = 0; i < 20; i++) {
            post = new objects.Post();
            Account account = new Account();
            account.setName("Issam Ben Ammar");
            account.setProfileImg("/img/user.png");
            account.setVerified(true);
            post.setAccount(account);
            post.setDate("Feb 18, 2021 at 12:00 PM");
            post.setAudience(PostAudience.PUBLIC);
            post.setCaption("Hello everybody.");
            post.setImage("/img/img2.jpg");
            post.setTotalReactions(10);
            post.setNbComments(2);
            post.setNbShares(3);

            ls.add(post);
        }
        return ls;
    }
}
