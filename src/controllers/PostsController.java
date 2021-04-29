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
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import objects.Account;
import objects.PostAudience;
import services.CRUDPost;

/**
 * FXML Controller class
 *
 * @author Issam
 */
public class PostsController implements Initializable {

    @FXML
    private VBox postsContainer;

    List<Post> posts;
    @FXML
    private TextField textFiled;
    @FXML
    private Button postButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        posts = new ArrayList<>(getDatabasePosts());
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

    public List<Post> getDatabasePosts() {
        List<Post> ls = new ArrayList<>();
        System.out.println("ena fel main");
        //entities.Post p = new entities.Post("faza");
        CRUDPost c = new CRUDPost();

        //ObservableList<entities.Post> posts = FXCollections.observableArrayList();
        //posts = c.getPostByText(new entities.Post("/fxml/post.fxml"));
        //System.out.println(p.getId()+p.getText());
        //c.addPost(p);
        c.getPosts();
        for (entities.Post p : c.getPosts()) {
            Post post = new Post();
            post.setId(p.getId());
            post.setCaption(p.getText());
            post.setAudience(PostAudience.FRIENDS);
            post.setIdUser(p.getUserId());
            /**
             * *******************
             *
             */
            Account account = new Account();
            account.setName("Issam Ben Ammar");
            account.setProfileImg("/img/user.png");
            account.setVerified(true);
            post.setAccount(account);
            /**
             * ***********************
             */

            post.setDate("Feb 18, 2021 at 12:00 PM");
            post.setTotalReactions(10);
            post.setNbComments(2);
            post.setNbShares(3);
            ls.add(post);
        }
        Collections.reverse(ls);
        return ls;
    }

    @FXML
    private void postAdd(ActionEvent event) {
         if (textFiled.getText().length() > 0) {
            CRUDPost c = new CRUDPost();
            
            entities.Post post = new entities.Post();
            post.setText(textFiled.getText());
            c.addPost(post);
            
            textFiled.setText("Issam,  what do you wanna tell your followers?");
    }

}

    @FXML
    private void textClear(MouseEvent event) {
        textFiled.setText("");
        
    }
}
